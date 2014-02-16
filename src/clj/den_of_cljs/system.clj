(ns den-of-cljs.system
  (:require cemerick.austin.repls
            den-of-cljs.webserver
            den-of-cljs.app
            cljs.closure))

;;TODO: extract the jetty webserver out to a webserver object
(defn init
  "Returns a new instance of the whole application"
  [port]
  (let [app #'den-of-cljs.app/app
        webserver (den-of-cljs.webserver/init (Integer. port) app)]
    {:state :initialized
     :webserver webserver}))

(defn start-webserver [system]
  (assoc system :webserver
         (den-of-cljs.webserver/start (:webserver system))))

(defn stop-webserver [system]
  (assoc system :webserver
         (den-of-cljs.webserver/stop (:webserver system))))

(defn compile-clojurescript [system]
  (cljs.closure/build "src/cljs/den_of_cljs/"
                      {:optimizations :whitespace
                       :pretty-print true
                       :output-dir "resources/public/js/files"
                       :output-to "resources/public/js/app.js" })
  system)

(defn start
  "Performs side effects to initialize a system, acquire resources, and start it running.
   Returns an updated instance of the app."
  [system]
  (assoc
      (compile-clojurescript (start-webserver system))
    :state :started))

(defn stop
  "Performs side effects to shut down system, and release its resources.
   Returns an updated instance of the system."
  [system]
  (assoc
      (compile-clojurescript (stop-webserver system))
    :state :stopped))

(def cljs-project-repl
  "Fire up a phantomjs-based ClojureScript REPL"
  cemerick.austin.repls/exec)

(defn cljs-project-chrome-repl
  "Fire up a Chrome-based ClojureScript REPL"
  []
  (cljs-project-repl
   :exec-cmds ["open" "-ga" "/Applications/Google Chrome.app"]))

(defn cljs-browser-repl
  "Fire up a browser-connected ClojureScript REPL"
  []
  (let [repl-env (reset! cemerick.austin.repls/browser-repl-env
                         (cemerick.austin/repl-env))]
    (cemerick.austin.repls/cljs-repl repl-env)))

(defn go! [port]
  (defonce ^:private server
    (start (init port))))

(defn -main [& args]
  (go! (or (first args) 8000)))
