(ns echo.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [hiccup.core :as h]
            [hiccup.form :as hf]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn index []
  (h/html [:div [:h1 "Hello Hello!"]
           (hf/form-to [:post "/"]
                       (hf/text-field {} :greeting)
                       (hf/submit-button {} "Go!"))]))

(defroutes app-routes
  (GET "/" [] (index))
  (POST "/" [greeting] (str greeting))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults #'app-routes api-defaults))

(defn start [port]
  (ring/run-jetty app {:port port :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))

#_ (index)


