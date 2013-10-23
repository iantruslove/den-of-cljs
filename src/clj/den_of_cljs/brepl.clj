;(ns den-of-cljs.webserver)
(ns den-of-cljs.brepl
  (:require [cljs.repl.browser]))

(defn connect []
  (cemerick.piggieback/cljs-repl
   :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
               cljs.repl/-setup)))


(comment 
  (require 'cljs.repl.browser)
  (cemerick.piggieback/cljs-repl :repl-env (cljs.repl.browser/repl-env :port 9000))
  )
