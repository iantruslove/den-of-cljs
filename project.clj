(defproject den-of-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.2"]
                 [compojure "1.1.5"]
                 [ring "1.2.1"]
                 [ring/ring-json "0.2.0"]
                 [enlive "1.1.5"]

                 ;; CLJS:
                 [org.clojure/clojurescript "0.0-2138"]
                 [jayq "2.5.0"]
                 [prismatic/dommy "0.1.2"]
                 [enfocus "2.0.2"]
                 ]

  :profiles {:dev {:repl-options {:init-ns den-of-cljs.system}
                   :dependencies [[org.clojure/tools.namespace "0.2.3"]]
                   :plugins [[com.cemerick/austin "0.1.3"]
                             [lein-ring "0.8.5"]
                             [lein-cljsbuild "1.0.1"]]
                   :cljsbuild {:builds [{:source-paths ["src/cljs"]
                                         :compiler {:optimizations :whitespace ;; :advanced
                                                    :pretty-print true
                                                    :externs ["resources/public/lib/leaflet-externs.js"
                                                              "resources/public/lib/backbone-externs.js"
                                                              "resources/public/lib/underscore-externs.js"]
                                                    :output-dir "resources/public/js/files"
                                                    :output-to "resources/public/js/app.js"
                                                    :source-map "resources/public/js/app.js.map"}}]}}}

  :ring {:handler den-of-cljs.app/app :port 8000})
