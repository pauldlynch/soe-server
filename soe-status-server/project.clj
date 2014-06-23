(defproject soe-status-server "0.1.0-SNAPSHOT"
  :description "Web application server for the SOE Status database"
  :url "http://paullynch.org/soe-status-server/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 ;; JDBC dependencies
                 [org.clojure/java.jdbc "0.2.3"]
                 ;;[java-jdbc/dsl "0.1.0"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 ;; JSON lib
                 [cheshire "5.2.0"]
                 ;; Joda time
                 [clj-time "0.7.0"]
                 ;; the web stuff
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler soe-status-server.handler/app
         :init soe-status-server.handler/init
         :destroy soe-status-server.handler/destroy}
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.1"]]}})
