(defproject schmart "1.0.0"
  :description "electronic schmart, bits and pieces by Naji Mammeri"
  :url "http://pascience.net/a/"
  :license {:name " CC0 1.0 Universal (CC0 1.0) Public Domain Dedication"
            :url "http://creativecommons.org/publicdomain/zero/1.0/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 
                 [quil "2.3.0"]
                 [thi.ng/geom "0.0.908"]
                 [thi.ng/color "1.0.1"]
                 [thi.ng/math "0.1.4"]
                 #_ [thi.ng/morphogen "0.1.1"]
                 #_ [thi.ng/shadergraph "0.1.1"]
                 #_ [thi.ng/dstruct "0.1.2"]
                
                 ; development tools
                 [figwheel-sidecar "0.5.0-4"]
                 #_ [devcards "0.2.1-2"]]
  :source-paths ["src/cljs"]
  :plugins [[lein-cljsbuild "1.1.3"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds []})
