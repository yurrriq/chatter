(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn generate-message-view
  "Generate the HTML for displaying messages."
  []
  (html5
   [:head
    [:title "Chatter"]]
   [:body
    [:h1 "Our Chat App"]]))

(defroutes app-routes
  (GET "/" [] (generate-message-view))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
