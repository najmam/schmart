(defproject sketches "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 
                 [quil "2.3.0"]
                 
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
              :main "sketches.particles"
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
