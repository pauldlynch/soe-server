(ns soe-loader.core
  (:gen-class)
  (:require [cheshire.core :refer [parse-string]]
            [soe-loader.db :as db]
            [clojure.tools.cli :refer [parse-opts]]))

(def date-format (java.text.SimpleDateFormat. "yyyyMMddHHmmss"))

(defn load-server [game region server server-map date]
  "check that server exists in db, adds it if not, then loads the server data"
    (if (not (db/server-exists? game region server))
    (db/add-server game region server))
  (db/add-sample game region server server-map date))

(defn load-servers [game region region-map date]
  "iterate over servers"
   (dorun (for [[server server-map] region-map](load-server game region server server-map date))))

(defn load-game [game game-map date]
  "check that game exists in db, then iterate over regions"
  (if (not (db/game-exists? game))
    (db/add-game game ""))
  (dorun (for [[region region-map] game-map] (load-servers game region region-map date))))

(defn load-map [json-map date]
  (dorun (for [[game game-map] json-map]
    (load-game game game-map date))))

(defn json->map [string]
  "converts a string to a map as json"
  (parse-string string false))

(defn file->map [path]
  "loads contents at path as a string, returns a map converted from json"
  (json->map (slurp path)))

(def cli-options
  [["-d" "--date" "optional date of file (yyyyMMddHHmmss)" :id :date :parse-fn #(.parse date-format %) :default (java.util.Date.)]])

(defn -main
  "Processes an SOE status JSON file with a specified offset date."
  [& args]
  (if-not (.exists (java.io.File. "./soe_status.sq3"))
    (db/create-schema))
  (let [opts (parse-opts args cli-options)]
    ;;(println "Processing... opts: " opts)
    ;;(println "file: " (first (:arguments opts)))
    ;;(println "date: " (.parse date-format (second (:arguments opts))))
    (load-map (file->map (first (:arguments opts))) (.parse date-format (second (:arguments opts))))
    ;;(load-map (file->map (first (:arguments opts))) (:date (:options opts)))
    ))
