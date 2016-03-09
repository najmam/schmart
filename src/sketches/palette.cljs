(ns sketches.palette
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [sketches.util.draw :as d]
            [sketches.util.color :as c]))
(enable-console-print!)
(def frame-rate 30)
(def w 500)
(def h 500)

(defn- sketch-setup
  []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  {})

(defn color
  [i]
  (let [id (if (integer? i) i
             (get {:background 3} i 0))]
    (c/as-argb (c/color-in-palette :arne id))))

(defn- sketch-draw
  []
  (let [f (q/frame-count)
        nb-colors 16
        cycle-period 0.8
        seconds (q/floor (/ (/ f cycle-period) frame-rate))
        cur-color (rem seconds nb-colors)
        sqsz (/ (q/height) nb-colors)]
    (q/no-stroke)
    (q/fill (color cur-color))
    (q/rect 0 0 (q/width) (q/height))
    (doseq [i (range 0 nb-colors)]
      (q/fill (color i))
      (q/rect 0 (* i sqsz) sqsz (inc sqsz)))
    ))

(q/defsketch mysketch
  :host "sketch"
  :size [w h]
  :setup sketch-setup
  :update identity
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
