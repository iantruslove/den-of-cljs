(ns den-of-cljs.leaflet-map
  (:require [jayq.core])
  (:require-macros [jayq.macros :refer [let-ajax]])
  )

(def lat-lon-downtown [39.75 -104.99])

(def initial-map-config {:element-id "map"
                         :center lat-lon-downtown
                         :initial-zoom 15
                         :base-layer-url "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                         :state :initialized})

(defn start-map [map-config]
  (let [leaflet-map (L.Map. (:element-id map-config))
        osm-url (:base-layer-url map-config)
        osm (.tileLayer js/L osm-url)
        center (clj->js (:center map-config))
        initial-zoom (:initial-zoom map-config)]
    (-> leaflet-map
        (.addLayer osm)
        (.setView center initial-zoom))
    (assoc map-config
      :leaflet-map leaflet-map
      :state :started)))

(defn stop-map [map-config]
  (let [leaflet-map (:leaflet-map map-config)]
    (assoc map-config
      :leaflet-map (.remove leaflet-map)
      :state :stopped)))

(defn create-geosjon-layer [geojson-str]
  (.geoJson js/L (.parse js/JSON geojson-str)))

(defn leaflet-add-layer [leaflet-map layer]
  (.addLayer leaflet-map layer))

(defn retrieve-bike-racks [handler]
  (let-ajax [geojson {:url "/api/bike_racks/"}]
            (handler geojson)))

(def the-map (atom {}))

(defn init []
  (reset! the-map initial-map-config))

(defn start []
  (swap! the-map start-map)
  (retrieve-bike-racks (fn [geojson]
                         (let [leaflet-map (:leaflet-map @the-map)]
                           (leaflet-add-layer leaflet-map (create-geosjon-layer geojson))))))

(defn stop [] 
  (swap! the-map stop-map))
