# den-of-cljs

The outline of a Clojure/ClojureScript web app stack, with support for a nREPL-enabled ClojureScript browser REPL.

## Prerequisites

* Leiningen 2

## Usage

* `lein ring server` runs the app without the system wrappers around the webserver and CLJS compile steps.
* `lein run -m demographics.system 8080` runs the server app on port 8080, ensuring that CLJS is compiled.

### Server (clj)

(These steps are what the second invocation above does internally)

* Fire up a `lein repl` in the project root
* Initialize the app with `(init)`
* Compile resources and start webserver with `(start)`
* Shut down the webserver with `(stop)`

### Client (cljs)

Run the server part, e.g. `lein ring server`, or like above.

One way to do client-side work is to edit the .cljs files and run
`lein cljsbuild auto` to monitor the .cljs source files and autocompile the JavaScript.
Browser refreshes are necessary.

Another way is to live-update the code in the browser using a combination of
the clojurescript browser repl, and piggieback or nREPL integration.

For a browser repl:
* Fire up another nREPL in emacs
* Into nREPL, enter:
    (require 'cljs.repl.browser)
    (cemerick.piggieback/cljs-repl :repl-env (cljs.repl.browser/repl-env :port 9000))
* Load http://localhost:8000 (or wherever the server part was fired up). The ClojureScript browser REPL should connect to nREPL via port 9000
  * To verify an active CLJS REPL, open up the browser console and
    look for a pending POST request to http://localhost:9000/ -
    this is the connection back to nREPL.
  * With this in hand, entering `(.alert js/window "Hello, World")` into the REPL should do what you expect.

## TODO

## License

Copyright © 2013 Ian Truslove

Distributed under the Eclipse Public License, the same as Clojure.
