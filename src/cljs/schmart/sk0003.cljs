(ns schmart.sk0003
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [schmart.util.math :as m]
            [schmart.util.draw :as d]
            [schmart.util.sketch-size :as sketch-size-helpers]))
(enable-console-print!)
(def frame-rate 30)
(def sketch-size (atom {:w 500 :h 500}))
(defn sketch-width [] (:w @sketch-size))
(defn sketch-height [] (:h @sketch-size))

(def NN 100)

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  {})

(defn- new-state []
  (let [[fw fh] [(sketch-width) (sketch-height)]]
    {:c1 0
     :vertices [[0 0] [fw 0] [fw fh] [0 fh]]}))

(defn- sketch-update [st]
  (let [f (q/frame-count)
        cycle-period 0.1
        c1 (rem (q/floor (/ (/ f cycle-period) frame-rate)) NN)]
    (if (= c1 0) (new-state)
      (-> st
          (assoc :c1 c1)
          (update :vertices (fn [points]
                              (for [i (range 0 (count points))]
                                (let [i+1 (rem (+ i 1) (count points))]
                                  (m/p-lerp (nth points i) (nth points i+1) (/ 1 NN))))
                              ))))))

(defn- sketch-draw [{:keys [c1 vertices]}]
  (let [f (q/frame-count)
        [fw fh] [(sketch-width) (sketch-height)]]
    (when (= c1 0)
      (q/no-stroke) (q/fill 0) (q/rect 0 0 fw fh))
    (q/no-fill) (q/stroke 255)
    (dotimes [i (count vertices)]
      (let [p_i (nth vertices i)
            p_i+1 (nth vertices (rem (+ i 1) (count vertices)))
            p_i+2 (nth vertices (rem (+ i 2) (count vertices)))]
        (q/line p_i (m/p-lerp p_i+1 p_i+2 (/ 1 NN)))))
    ))

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
