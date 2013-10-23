(ns den-of-cljs.app
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.handler :as handler]
            [compojure.route :refer [resources not-found]]
            [ring.util.response :as resp]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [clojure.java.io]))


(defn _get-bike-rack-geojson []
  (slurp (clojure.java.io/resource "data/bike_racks.geojson")))

(def get-bike-rack-geojson (memoize _get-bike-rack-geojson))

(defroutes api-routes
  (GET "/bike_racks/" [] (resp/response (get-bike-rack-geojson))))

(defroutes app-routes
  (context "/api" [] (-> api-routes
                         (wrap-json-response)
                         (wrap-json-body {:keywords? true})))
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (resources "/")
  (not-found "<p>404 - page not found</p>\n"))

(def app
  (handler/site app-routes))
