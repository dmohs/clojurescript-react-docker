(def build-host (or (System/getenv "BUILD_HOST") "REPLACE-WITH-DOCKER-HOSTNAME-OR-IP"))
(def build-dir-relative "target")

(defproject dmohs/cljs-react-sample "0.1"
  :dependencies
  [
   ;; React library and cljs bindings for React.
   [dmohs/react "0.2.8"]
   [org.clojure/clojure "1.7.0"]
   [org.clojure/clojurescript "1.7.48"]
   ]
  :plugins [[lein-cljsbuild "1.0.6"] [lein-figwheel "0.3.7"]]
  :hooks [leiningen.cljsbuild]
  :profiles {:dev {:cljsbuild
                   {:builds {:client {:compiler
                                      {:optimizations :none
                                       :source-map true
                                       :source-map-timestamp true}
                                      :figwheel
                                      {:websocket-host ~build-host
                                       :on-jsload "dmohs.main/dev-reload"}}}}}
             :minimized {:cljsbuild
                         {:builds {:client {:compiler
                                            {:optimizations :advanced
                                             :pretty-print false
                                             :closure-defines {"goog.DEBUG" false}}}}}}}
  :cljsbuild {:builds {:client {:source-paths ["src/cljs"]
                                :compiler {:output-dir ~(str build-dir-relative "/build")
                                           :output-to ~(str build-dir-relative "/compiled.js")}}}})
