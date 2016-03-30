(ns schmart.example.perlin_and_gradient
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [thi.ng.color.core :as col]
            [schmart.util.math :as m]
            [thi.ng.math.core :as m2]
            [schmart.util.draw :as d]
            [schmart.util.sketch-size :as sketch-size-helpers]
            [thi.ng.math.noise :as noise]
            [thi.ng.color.gradients :as grad]))
(enable-console-print!)
(def frame-rate 30)
(def frame-duration (/ 1 frame-rate))
(def sketch-size (atom {:w 500 :h 500}))
(defn- sketch-width [] (:w @sketch-size))
(defn- sketch-height [] (:h @sketch-size))

(defn gradient-fn
  [nb-colors]
  (let [cols (->> [[0.940 0.281 0.941] [0.153 0.347 0.572] [0.235 0.238 0.432] [0.976 4.790 0.293]]
                  (grad/cosine-gradient nb-colors)
                  (map col/as-rgba)
                  (map deref)
                  (map (fn [v] (map (comp m2/floor (partial * 255)) v))))]
    (fn [t]
      (let [i (m2/floor (* t nb-colors))]
        (nth cols i)))))
(def color-at (gradient-fn 100))
(let [N 20]
  (dotimes [i N]
    (println "color at " (/ i N) ": " (color-at (/ i N)))))

(defn- initial-state []
  {})

(defn- new-state [st]
  st)

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (initial-state))

(defn- sketch-update [st]
  (let [f (q/frame-count)]
    st))

(defn- sketch-draw []
  (let [[fw fh] [(sketch-width) (sketch-height)]]
    (q/fill 255)
    (q/rect 0 0 fw fh)
    (doseq [x (range 0 fw)]
      (-> (q/noise (* 100 (/ x fw)) (* 0.02 (q/frame-count)))
          (color-at)
          ((fn [x] (apply q/stroke x))))
      (q/line [x 0] [x fh])
  )))

(q/defsketch mysketch
  :host "sketch"
  :size [(sketch-width) (sketch-height)]
  :setup sketch-setup
  :update sketch-update
  :draw sketch-draw
  :middleware [quil.middleware/fun-mode]
  :mouse-entered identity
  :mouse-exited identity
  :mouse-pressed identity
  :mouse-released identity
  :mouse-clicked identity
  :mouse-moved identity
  :mouse-dragged identity
  :mouse-wheel identity
  :key-pressed identity
  :key-released identity
  :key-typed identity)
