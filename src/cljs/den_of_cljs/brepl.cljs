(ns den-of-cljs.brepl)

(comment
  ;; In the clojure repl:
  (require 'cljs.repl.browser)
  (cemerick.piggieback/cljs-repl :repl-env (cljs.repl.browser/repl-env :port 9000))

  ;; then:
  (.alert js/window "testing, 1, 1, 2, 3, 5...")
  )
