(ns den-of-cljs.app
  (:require [clojure.data.json :as json]
            [compojure.core :refer [defroutes context GET]]
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

(defonce all-bike-racks-geojson
  (slurp (io/resource "data/bike_racks.geojson")))

(defonce all-bike-racks
  (json/read-str all-bike-racks-geojson :key-fn keyword))

(defn keep-n-features [feature-collection n]
  (assoc feature-collection
    :features (vec (take (Integer. n) (:features feature-collection)))))

(defroutes api-routes
  (GET "/bike_racks/" []
    (-> all-bike-racks
        (keep-n-features 1000)
        json/write-str
        resp/response)))

(defroutes app-routes
  (context "/api" [] (-> api-routes
                         (wrap-json-response)
                         (wrap-json-body {:keywords? true})))
  (GET "/" [] (resp/response (page)))
  (resources "/")
  (not-found "<p>404 - page not found</p>\n"))

(def app
  (handler/site app-routes))
