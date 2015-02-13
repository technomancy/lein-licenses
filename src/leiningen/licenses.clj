(ns leiningen.licenses
  (:require [leiningen.core.classpath :as classpath]
            [leiningen.core.main :as main]
            [cemerick.pomegranate.aether :as aether]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [clojure.string :as string]
            [clojure.xml :as xml]
            [clojure.zip :as zip])
  (:import (java.util.jar JarFile)))

;; This code is a mess; sorry! But it gets the job done.

(defn- tag [tag]
  #(= (:tag %) tag))

(defn- get-entry [^JarFile jar ^String name]
  (.getEntry jar name))

(def ^:private tag-content (juxt :tag (comp first :content)))

(defn- fetch-pom [{:keys [groupId artifactId version]}]
  (try
    (let [dep (symbol groupId artifactId)
          [file] (->> (aether/resolve-dependencies
                       :coordinates [[dep version :extension "pom"]])
                      (aether/dependency-files)
                      ;; possible we could get the wrong one here?
                      (filter #(.endsWith (str %) ".pom")))]
      (xml/parse file))
    (catch Exception e
      (binding [*out* *err*]
        (println "#   " (str groupId) (groupId artifactId) (class e) (.getMessage e))))))

(defn- get-parent [pom]
  (if-let [parent-tag (->> pom
                           :content
                           (filter (tag :parent))
                           first
                           :content)]
    (if-let [parent-coords (->> parent-tag
                                (map tag-content)
                                (apply concat)
                                (apply hash-map))]
      (fetch-pom parent-coords))))

(defn- pom-license [pom]
  (->> pom :content (filter (tag :licenses))))

(defn- get-pom [dep file]
  (let [group (or (namespace dep) (name dep))
        artifact (name dep)
        pom-path (format "META-INF/maven/%s/%s/pom.xml" group artifact)
        pom (get-entry file pom-path)]
    (and pom (xml/parse (.getInputStream file pom)))))

(def ^:private license-file-names #{"LICENSE" "LICENSE.txt" "META-INF/LICENSE"
                                    "META-INF/LICENSE.txt" "license/LICENSE"})

(defn- try-raw-license [file]
  (try
    (if-let [entry (some (partial get-entry file) license-file-names)]
      (with-open [rdr (io/reader (.getInputStream file entry))]
        (string/trim (first (remove string/blank? (line-seq rdr))))))
    (catch Exception e
      (binding [*out* *err*]
        (println "#   " (str file) (class e) (.getMessage e))))))

(defn- get-licenses [dep file]
  (if-let [pom (get-pom dep file)]
    (->> (iterate get-parent pom)
         (take-while identity)
         (map pom-license)
         (apply concat)
         ;; TODO: this might be trimming additional licenses?
         (map (comp first :content first :content))
         (filter (tag :name))
         first :content first)
    (try-raw-license file)))


(def formatters
  {":text"
   (fn [line]
     (string/join " - " line))

   ":csv"
   (fn [line]
     (let [quote-csv
           (fn [text]
             (str \" (clojure.string/replace text #"\"" "\"\"") \"))]
       (string/join "," (map quote-csv line))))})


(defn licenses
  "List the license of each of your dependencies.

USAGE: lein licenses [:text]
Show license information in the default text format

USAGE lein licenses :csv
Show licenses in CSV format"

  ([project]
     (licenses project ":text"))

  ([project output-style]
     (if-let [format-fn (formatters output-style)]
       (let [deps (#'classpath/get-dependencies :dependencies project)
             deps (zipmap (keys deps) (aether/dependency-files deps))]
         (doseq [[[dep version] file] deps]
           (let [line [(pr-str dep)
                       version
                       (or (get-licenses dep (JarFile. file)) "Unknown")]]
             (println (format-fn line)))))
       (main/abort "unknown formatter"))))
