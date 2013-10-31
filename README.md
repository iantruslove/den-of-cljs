# den-of-cljs

The outline of a Clojure/ClojureScript web app stack, with support for a nREPL-enabled ClojureScript browser REPL.

## Prerequisites

* Leiningen 2
* PhantomJS (optional)

## Usage

* `lein ring server` runs the app without the system wrappers around the webserver and CLJS compile steps.
* `lein run -m demographics.system 8080` runs the server app on port 8080, ensuring that CLJS is compiled.

### Server (clj)

(These steps are what the second invocation above does internally)

* Fire up a `lein repl` in the project root
* Initialize the app with `(init)`
* Compile resources and start webserver with `(start)`
* Shut down the webserver with `(stop)`

### ClojureScript client with headless REPL

Run the server part, e.g. `lein ring server`, or like above.

One way to do client-side work is to edit the .cljs files and run
`lein trampoline cljsbuild auto` to monitor the .cljs source files and autocompile the JavaScript.
Browser refreshes are necessary.

For a PhantomJS-based headless ClojureScript REPL, start a nREPL and run `(cljs-project-repl)` - it's defined in the `user` namespace so it should work as soon as the nPREL is connected.

For the same but using Chrome, run `(cljs-project-chrome-repl)` on OSX.

### ClojureScript client with in-browser REPL

Another way is to live-update the code in the browser using Austin's nREPL integration.
This requires a few different steps, including running the webserver from the REPL.

* Fire up a nREPL in emacs
* Run the server part of the app - `(go)` does both `(init)` and `(start)`
* Start the ClojureScript nPREL with `(cljs-browser-repl)`
* Load the app in the browser (e.g. visit http://localhost:8000/)
* Verify the ClojureScript REPL is connected by typing in `(.alert js/window "Hello, World")`

## TODO

## License

Copyright © 2013 Ian Truslove

Distributed under the Eclipse Public License, the same as Clojure.
