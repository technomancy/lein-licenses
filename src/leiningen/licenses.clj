(ns leiningen.licenses
  (:require [leiningen.core.classpath :as classpath]
            [cemerick.pomegranate.aether :as aether]
            [clojure.pprint :as pp]
            [clojure.xml :as xml]
            [clojure.zip :as zip])
  (:import (java.util.jar JarFile)))

;; This code is a mess; sorry! But it gets the job done.

(defn- tag [tag]
  #(= (:tag %) tag))

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
      (println "   " (class e) (.getMessage e)))))

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
        jar (JarFile. file)
        pom (.getEntry jar pom-path)]
    (and pom (xml/parse (.getInputStream jar pom)))))

(defn- get-licenses [dep file]
  (if-let [pom (get-pom dep file)]
    (->> (iterate get-parent pom)
         (take-while identity)
         (map pom-license)
         (apply concat)
         ;; TODO: this might be trimming additional licenses?
         (map (comp first :content first :content))
         (filter (tag :name))
         first :content first)))

(defn licenses
  "List the license of each of your dependencies."
  [project]
  (let [deps (#'classpath/get-dependencies :dependencies project)
        deps (zipmap (keys deps) (aether/dependency-files deps))]
    (doseq [[[dep version] file] deps]
      (println (pr-str dep) \- (or (get-licenses dep file) "Unknown")))))
