(require '[figwheel-sidecar.repl-api :as ra]
         '[cljs.build.api :as cljs])

(def build-dir (str (or (System/getenv "CLJS_BUILD_DIR") "build/www/js") "/"))
(def build-type (or (System/getenv "CLJS_BUILD") "playground"))
(def piece-ids-str (or (System/getenv "CLJS_PIECES") ""))
(def piece-ids (clojure.string/split piece-ids-str #","))

; open src/playground.html in the browser
(def playground-build
  {:id "playground"
   :source-paths ["src/cljs"]
   :figwheel true
   :compiler {:main "schmart.playground"
              :output-to (str build-dir "playground.js")
              :output-dir (str build-dir "playground")
              :asset-path "../build/www/js/playground"
              :optimizations :none
              :pretty-print true}})
(def figwheel-options
  {:figwheel-options {:css-dirs ["src"]
                      :open-file-command ["figwheel-open-subl"]}
   :build-ids ["playground"]
   :all-builds [playground-build]})
(when (= build-type "playground")
  (ra/start-figwheel! figwheel-options)
  (ra/cljs-repl))

(when (= build-type "production")
  (doseq [piece-id piece-ids]
    (let [compiler-options {:main (str "schmart.sk" piece-id)
                            :output-to (str build-dir "sk" piece-id ".js")
                            :output-dir (str build-dir "sk" piece-id)
                            :asset-path (str "js/sk" piece-id) ; unused ?
                            :optimizations :advanced
                            :cache-analysis true}]
      (cljs/build "src/cljs" compiler-options))))
