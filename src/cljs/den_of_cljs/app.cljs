(ns den-of-cljs.app
    (:require [clojure.browser.repl]))

(def lat-lon-downtown [39.75 -104.99])

(defn initialize-leaflet-map []
  (let [map (L.Map. "map")
        osm-url "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        osm (.tileLayer js/L osm-url)
        center (clj->js lat-lon-downtown)
        initial-zoom 15]
    (-> map
        (.addLayer osm)
        (.setView center initial-zoom))))

(defn ^:export home_init []
  (initialize-leaflet-map)
  )
