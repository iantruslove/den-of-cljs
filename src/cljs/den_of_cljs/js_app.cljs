(ns den-of-cljs.js-app
  (:require [clojure.browser.repl]
            [den-of-cljs.leaflet-map :as leaflet-map]
            [jayq.core])
  (:require-macros [jayq.macros :refer [let-ajax]]))

(def model (atom {}))

(defn retrieve-geojson []
  (let-ajax [geojson {:url "/api/bike_racks/"}]
            (swap! model (fn [m] (assoc m :feature-geojson geojson)))))

(defn ^:export init []
  (leaflet-map/init! model)
  (leaflet-map/start!)
  (retrieve-geojson))
