(ns soe-status-server.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [soe-status-server.routes.home :refer [home-routes]]
            [soe-status-server.models.db :as db]
            [ring.middleware.session.memory :refer [memory-store]]))

(defn init []
  (println "soe-status-server is starting"))

(defn destroy []
  (println "soe-status-server is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site
       (routes home-routes app-routes))
      (wrap-base-url)))
