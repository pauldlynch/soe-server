(defproject soe-loader "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 ;; JDBC :dependencies
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 ;; JSON lib
                 [cheshire "5.2.0"]
                 ;; Joda time
                 [clj-time "0.7.0"]]
  :main ^:skip-aot soe-loader.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
