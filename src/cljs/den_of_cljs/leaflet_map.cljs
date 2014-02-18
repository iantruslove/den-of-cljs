(ns den-of-cljs.leaflet-map)

(def lat-lon-downtown [39.75 -104.99])

(def initial-map-config
  {:element-id "map"
   :center lat-lon-downtown
   :initial-zoom 15
   :base-layer-url "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
   :state :initialized})

(def the-map (atom {}))

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

(defn create-geojson-layer [geojson-str]
  (.geoJson js/L (.parse js/JSON geojson-str)))

(defn add-layer-to-map [leaflet-map layer]
  (.addLayer leaflet-map layer))

(defn remove-layer-from-map [leaflet-map layer]
  (.removeLayer leaflet-map layer))

(defn update-bike-rack-layer! [geojson]
  (let [new-layer (create-geojson-layer geojson)
        leaflet-map (:leaflet-map @the-map)]
    (when-let [old-layer (:bike-rack-layer @the-map)]
      (remove-layer-from-map leaflet-map old-layer))
    (swap! the-map (fn [m] (assoc m :bike-rack-layer new-layer)))
    (add-layer-to-map leaflet-map new-layer)))

(defn on-model-change [key atom old new]
  (when-let [geojson (:feature-geojson new)]
    (update-bike-rack-layer! geojson)))

(defn init! [model-atom]
  (reset! the-map (assoc initial-map-config :model model-atom)))

(defn start! []
  (swap! the-map start-map)
  (add-watch (:model @the-map) :leaflet-map-observer on-model-change))

(defn stop! []
  (swap! the-map stop-map)
  (remove-watch (:model @the-map) :leaflet-map-observer))

(def restart! (comp start! stop!))
