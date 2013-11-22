(ns den-of-cljs.app
    (:require [clojure.browser.repl]
              [den-of-cljs.leaflet-map :as leaflet-map]))

(defn ^:export home_init []
  (leaflet-map/init!)
  (leaflet-map/start!))
