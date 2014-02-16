
;; THIS ISN'T USED ANY MORE

(ns user
  (:require [clojure.pprint :refer (pprint)]
            [clojure.repl :refer :all]
            [clojure.tools.namespace.repl :refer (refresh refresh-all)]
            [den-of-cljs.system :as system]))

;; The global Var to hold the whole system
(def system nil)

(defn init
  "Constructs the current development system"
  []
  (alter-var-root #'system
                  (constantly (system/init))
                  ))

(defn start
  "Starts the current development system"
  []
  (alter-var-root #'system
                  system/start))

(defn stop
  "Shuts down and cleans up the current development system"
  []
  (alter-var-root #'system
                  (fn [s] (when s (system/stop s)))))

(defn go
  "Initializes the current development system and starts it running."
  []
  (init)
  (start))

(defn restart []
  (stop)
  (refresh :after 'user/go))

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
