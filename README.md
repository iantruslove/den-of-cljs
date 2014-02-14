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
* Start the ClojureScript nREPL with `(cljs-browser-repl)`
* Load the app in the browser (e.g. visit http://localhost:8000/)
* Verify the ClojureScript REPL is connected by typing in `(.alert js/window "Hello, World")`

### CLJS browser REPL + CLJ REPL

Edit and repl Clojure and ClojureScript at the same time?  A little trickier, but sure!

Using cider mode:

* `cider-jack-in` to jack in and open a REPL to use for CLJ
* Run the server part: `(go)`
* Open a new cider connection to that same nREPL:
  * `cider-display-current-connection-info` to display host and port
    info, you might need the port number
  * run `cider` and connect to the *same* nREPL session
  * Start the ClojureScript nRPEL in this cider with `(cljs-browser-repl)`
* Load the app in the browser (e.g. visit http://localhost:8000/) to
  connect the CLJS repl to the second cider window.

At this point there are two distinct cider clients for the same nREPL
session.  We use one for CLJ, and one for CLJS.  Use `C-c M-r`
(`cider-rotate-connection`) to cycle through the ciders to use for the
current buffer.  `C-c M-p` is useful to determine which REPL you're
interacting with - obviously loading CLJ code into the CLJS REPL isn't
likely to work too well.

The great thing is that the server code can be edited and eval'd, and
made part of the running server.  Corresponding edits can be made in
the client-side code, eval'd (in the appropriate REPL) and take
advantage of the already-changed server.  Neato!


## Outline of walkthrough steps

1. Basic setup and the ClojureScript REPL
2. Compiling to JS and loading the first page
3. Externs and a simple Leaflet map
4. Testing ClojureScript
5. ClojureScript MVC/MVP + event hub pattern for a SPWA
6. Basic DOM manipulation
7. Events
8. AJAX
9. Deployment to Heroku
10. Remote debugging (and hot fixes?)

## References

* http://lukevanderhart.com/2011/09/30/using-javascript-and-clojurescript.html

## License

Copyright © 2013 Ian Truslove

Distributed under the Eclipse Public License, the same as Clojure.
