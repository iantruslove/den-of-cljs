(defproject den-of-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.2"]
                 [compojure "1.1.5"]
                 [ring "1.1.8"]
                 [ring-middleware-format "0.3.0"]
                 [ring/ring-json "0.2.0"]
                 [prismatic/dommy "0.1.1"]

                 ;; CLJS:
                 [org.clojure/clojurescript "0.0-1889"]
                 [cljsbuild "0.3.4"]
                 [domina "1.0.2"]
                 [enfocus "2.0.0"]]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.3"]]
                   :plugins [[com.cemerick/austin "0.1.1"]]}}

  :ring {:handler den-of-cljs.app/app
         :port 8000}

  :plugins [[lein-ring "0.8.5"]
            [lein-cljsbuild "0.3.4"]]

  :cljsbuild {:builds
              {:dev {:source-paths ["src/cljs/den_of_cljs"]
                     :compiler {:optimizations :whitespace
                                :pretty-print false
                                :output-dir "resources/public/js/files"
                                :output-to "resources/public/js/app.js"
                                ;; TODO: Get source maps working
                                ;;:source-map "app.js.map"
                                }}}}
  )
