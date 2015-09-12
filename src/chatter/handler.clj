(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.form :as form]
            [hiccup.page :as page]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.params :refer [wrap-params]]))

(def chat-messages
  (atom [{:name "blue",  :message "hello, world"}
         {:name "red",   :message "red is my favorite color"}
         {:name "green", :message "green makes it go faster"}]))

(def new-message-form
  (form/form-to
   [:post "/"]
   "Name: "    (form/text-field :nom)
   "Message: " (form/text-field :msg)
   (form/submit-button "Submit")))

(defn- render-message [m]
  [:tr
   [:td (:name m)]
   [:td (:message m)]])

(defn generate-message-view
  "Generate the HTML for displaying messages."
  [messages]
  (page/html5
   [:head
    [:title "Chatter"]]
   [:body
    [:h1 "Our Chat App"]
    new-message-form
    [:table (map render-message messages)]]))

(defn- update-messages!
  "Atomically add `{:name nom, :message msg}` to `messages`."
  [messages nom msg]
  (swap! messages conj {:name nom, :message msg}))

(defroutes app-routes
  (GET "/"  []
    (generate-message-view @chat-messages))
  (POST "/" [nom msg]
    (generate-message-view
     (update-messages! chat-messages nom msg)))
  (route/not-found "Not Found"))

(def app (wrap-params app-routes))
