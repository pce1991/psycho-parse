(defproject psycho-parse "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-wordnet "0.1.1-SNAPSHOT"]]
  :main ^:skip-aot psycho-parse.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
