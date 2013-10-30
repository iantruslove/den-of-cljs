(ns den-of-cljs.app
  (:require [den-of-cljs.views.home :as home]
            [clojure.browser.repl]))

(defn ^:export home_init []
  (home/init))
