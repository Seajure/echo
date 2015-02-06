(ns echo.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [hiccup.core :as h]
            [hiccup.form :as hf]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defonce greetings (atom []))

(defn index []
  (h/html [:div [:h1 "Hello Hello!"]
           [:ul (for [g @greetings]
                  [:li g])]
           (hf/form-to [:post "/"]
                       (hf/text-field {} :greeting)
                       (hf/submit-button {} "Go!"))]))

(defn handle-greeting [s]
  (swap! greetings conj s)
  (index))

(defroutes app-routes
  (GET "/" [] (index))
  (POST "/" [greeting] (handle-greeting greeting))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults #'app-routes api-defaults))

(defn start [port]
  (ring/run-jetty app {:port port :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))

#_ (index)
#_ @greetings
#_ (start 8080)
