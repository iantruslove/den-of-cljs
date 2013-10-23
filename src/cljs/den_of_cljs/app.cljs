(ns den-of-cljs.app
  (:require [den-of-cljs.views.home :as home]
            [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(defn ^:export home_init []
  (home/init))
