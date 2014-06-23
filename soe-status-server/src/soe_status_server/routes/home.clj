(ns soe-status-server.routes.home
  (:require [compojure.core :refer :all]
            [soe-status-server.views.layout :as layout]
            [hiccup.form :refer :all]
            [soe-status-server.models.db :as db]
            [cheshire.core :as json]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data {:pretty true})})

(defn home [& [game server from-date to-date error]]
  (layout/common [:h1 "SOE Status Server"]
                 [:p "Welcome to the SOE Status Database Server"]
                 [:p error]
                 [:hr]
                 ; here we create a form with text fields called "game" and "server"
                 ; these will be sent when the form posts to the server as keywords of
                 ; the same name
                 (form-to [:post "/"]
                  [:p "Game: " (text-field "game" game)]
                  [:p "Server: " (text-field "server" server)]
                  [:p "From: " (text-field "from-date" from-date) " date format: yyyy-MM-dd HH:mm:ss"]
                  [:p "To: " (text-field "to-date" to-date)]
                  (submit-button "get data"))))

(defn json-samples [game server samples]
  (json-response samples))

(defn get-samples-form [game server from-date to-date]
  (cond
   (empty? game)
   (home game server from-date to-date "You must give a game code.")
   (empty? server)
   (home game server from-date to-date "You must give a server name.")
   :else
   (do
     (json-samples game server (db/get-samples game server from-date to-date)))))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [game server from-date to-date] (get-samples-form game server from-date to-date))
  (ANY "/*" [] (home)))

