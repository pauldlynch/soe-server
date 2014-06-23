(ns soe-status-server.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to soe-status-server"]
     (include-css "/css/screen.css")]
    [:body body]))
