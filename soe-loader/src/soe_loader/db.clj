(ns soe-loader.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :refer [split]]
            [clj-time.core :as t]
            [clj-time.coerce :as tc])
  (:import java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname "soe_status.sq3"})

(defn create-game-table []
  (sql/with-connection db
    (sql/create-table
     :game
     [:code "varchar(10) PRIMARY KEY"]
     [:name "varchar(50)"])))

(defn get-game [code]
  (sql/with-connection db
    (sql/with-query-results
      results ["SELECT * FROM game WHERE code = ?" code]
      (first results))))

(defn game-exists? [game]
  (get-game game))

(defn add-game [code name]
  (sql/with-connection db
    (sql/insert-values
     :game
     [:code :name]
     [code name])))

(defn create-server-table []
  (sql/with-connection db
    (sql/create-table
     :server
     [:id "integer PRIMARY KEY AUTOINCREMENT"]
     [:region "varchar(20) NOT NULL"]
     [:name "varchar(50) NOT NULL"]
     [:game "varchar(10) NOT NULL"])))

(defn get-server [game region server]
  (sql/with-connection db
    (sql/with-query-results
      results ["SELECT * FROM server WHERE game = ? AND region = ? AND name = ?" game region server] (first results))))

(defn server-exists? [game region server]
  (get-server game region server))

(defn add-server [game region server]
  (sql/with-connection db
    (sql/insert-values
     :server
     [:region :name :game]
     [region server game])))

(defn create-sample-table []
  (sql/with-connection db
    (sql/create-table
     :sample
     [:id "integer PRIMARY KEY AUTOINCREMENT"]
     [:server "integer NOT NULL"]
     [:age "varchar(20) NOT NULL"]
     [:status "varchar(20) NOT NULL"]
     [:sample_date "datetime NOT NULL"])))

(defn age->time [str]
  (map #(read-string (first (re-seq #"[1-9]+[0-9]*|0{2}" %))) (split str #":")))

(defn offset-age [date age]
  (t/minus (tc/from-date date)
           (t/hours (first (age->time age)))
           (t/minutes (second (age->time age)))
           (t/seconds (nth (seq (age->time age)) 2))))

(defn add-sample [game region server server-map date]
  (sql/with-connection db
    (sql/insert-values
     :sample
     [:server :age :status :sample_date]
     [(:id (get-server game region server))
      (get server-map "age")
      (get server-map "status")
      (offset-age date (get server-map "age"))]
     )))


(defn load-standard-games []
  )

(defn create-schema []
  (create-game-table)
  (create-server-table)
  (create-sample-table)
  (load-standard-games))

