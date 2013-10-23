(ns den-of-cljs.views.home
  (:require [domina :as dom]
            [domina.css :as css]
            [domina.events :as ev]
            [clojure.browser.net :as net]
            [clojure.browser.event :as event]))

(defn log [o]
  (.log js/console o))

(defn populate-posts [ul posts]
  (doseq [post posts]
    nil
 ))

(defn find-posts-list []
  (css/sel "ul")
)

(defn clear-posts-list []
  nil)

(defn get-posts []
  )

(defn ^:export init []
  (clear-posts-list)
  (populate-posts (find-posts-list) (get-posts))
  )





(comment

  (defn populate-posts [ul posts]
  (doseq [post posts]
    (dom/append! ul (apply str ["<li>" post "</li>"]))))

(defn find-posts-list
  [] (dom/single-node (css/sel ".ex-posts")))

(defn clear-posts-list []
  (dom/destroy! (css/sel (find-posts-list) "li")))

(defn get-posts []
  ["This is the first post" "ClojureScript seems like good stuff"]
  )


  )


