(ns den-of-cljs.app
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.handler :as handler]
            [compojure.route :refer [resources not-found]]
            [ring.util.response :as resp]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [net.cgrand.enlive-html :as enlive]
            [clojure.java.io :as io]
            [cemerick.austin.repls :refer [browser-connected-repl-js]]))

(enlive/deftemplate page
  (io/resource "public/index.html")
  []
  [:body] (enlive/append
           (enlive/html [:script (browser-connected-repl-js)])))

(defonce bike-racks-geojson
  (slurp (io/resource "data/bike_racks.geojson")))

(defroutes api-routes
  (GET "/bike_racks/" [] (resp/response bike-racks-geojson)))

(defroutes app-routes
  (context "/api" [] (-> api-routes
                         (wrap-json-response)
                         (wrap-json-body {:keywords? true})))
  (GET "/" [] (resp/response (page)))
  (resources "/")
  (not-found "<p>404 - page not found</p>\n"))

(def app
  (handler/site app-routes))
