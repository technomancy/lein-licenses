(defproject lein-licenses "0.2.2"
  :description "List the license of each of your dependencies."
  :url "https://github.com/technomancy/lein-licenses"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :deploy-repositories {"releases" {:url "https://repo.clojars.org" :creds :gpg}})
