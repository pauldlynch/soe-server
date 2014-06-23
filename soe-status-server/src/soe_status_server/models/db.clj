(ns soe-status-server.models.db
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :refer [split]]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]
            [clj-time.format :as tf])
  (:import java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname (or (first *command-line-args*) "/Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/soe_status.sq3")})

(defn get-game [code]
  (sql/with-connection db
    (sql/with-query-results
      results ["SELECT * FROM game WHERE code = ?" code]
      (first results))))

(defn game-exists? [game]
  (get-game game))

(defn get-server [game server]
  (sql/with-connection db
    (sql/with-query-results
      results ["SELECT * FROM server WHERE game = ? AND name = ?" game server] (first results))))

(defn server-exists? [game server]
  (get-server game server))

(defn date-30 [date]
  (t/minus date (t/days 1)))

(def date-format (tf/formatter "yyyy-MM-dd HH:mm:ss"))

(defn string-to-date [string]
  "returns a valid clj-date or nil"
  (cond
   (nil? string) nil
   :else
   (try (tf/parse date-format string) (catch Exception _ nil))))

(defn get-samples [game server date_from date_to]
  "game code, server name, from date as string, to date as string"
  (let [now (t/now)
    date2 (or (string-to-date date_to) now)
    date1 (or (string-to-date date_from) (date-30 now))]
    (sql/with-connection db
      (sql/with-query-results
        results ["SELECT status, sample_date, server.name, game.code
                 FROM sample, server, game
                 WHERE server.game = game.code AND sample.server = server.id
                 AND game.code = ? AND server.name = ?
                 AND sample_date BETWEEN ? AND ?
                 ORDER BY datetime(sample_date)"
                 game server (tf/unparse date-format date1) (tf/unparse date-format date2)]
        (doall results)))))
