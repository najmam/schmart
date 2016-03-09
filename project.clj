(defproject sketches "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 
                 [quil "2.3.0"]
                 [thi.ng/geom "0.0.908"]
                 [thi.ng/color "1.0.1"]
                 [thi.ng/math "0.1.4"]
                 #_ [thi.ng/morphogen "0.1.1"] ; [thi.ng/morphogen "0.1.1"]
                 #_ [thi.ng/shadergraph "0.1.1"]
                 #_ [thi.ng/dstruct "0.1.2"] ; heap, disjoint set, bi-directional map
                 
                 ; development tools
                 [figwheel-sidecar "0.5.0"]
                 #_ [devcards "0.2.1-2"]]

  :plugins [[lein-cljsbuild "1.1.1"]]
  :hooks [leiningen.cljsbuild]

  :source-paths ["src/"]
  
  :figwheel {
    :css-dirs ["www/css"]
    :open-file-command ["figwheel-open-subl"]
  }
  
  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src"]
             :figwheel { :devcards false }
             :compiler
             {
              :output-to "www/js/dev.js"
              :output-dir "www/js/dev"
              :asset-path "js/dev"
              :main "sketches.palette"
              :optimizations :none
              :pretty-print true
              }
             }
            #_ {:id "devcards"
             :source-paths ["src/"]
             :figwheel { :devcards true }
             :compiler
             {
              :main "sketches.playground"
              :output-to "www/js/devcards.js"
              :output-dir "www/js/devcards"
              :asset-path "js/devcards"
              :optimizations :none
              :cache-analysis true
              :source-map-timestamp true
              :source-map true
             }
             }
            #_ {:id "standalone"
             :source-paths ["src/"]
             :compiler
             {
              :main "sketches.playground"
              :output-to "www/js/sketch.js"
              :output-dir "www/js/standalone"
              :asset-path "js/standalone"
              :optimizations :advanced
             }
             }
            ]})
