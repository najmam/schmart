(ns schmart.sk0002
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

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  {})

(def NN 20)

(defn- new-state [] {:c1 0})

(defn- sketch-update [st]
  (let [f (q/frame-count)
        cycle-period 0.1
        c1 (rem (q/floor (/ (/ f cycle-period) frame-rate)) NN)]
    (if (= (:c1 st) c1) st
      (assoc (new-state) :c1 c1))))

(defn- sketch-draw [{:keys [c1]}]
  (let [f (q/frame-count)
        [fw fh] [(sketch-width) (sketch-height)]]
    (q/fill 255) (q/rect 0 0 fw fh)
    (q/no-stroke) (q/no-fill)
    (q/stroke 0)
    (let [xstep (/ fw NN)
          ystep (/ fh NN)]
      (dotimes [i (m/clamp (inc c1) 1 NN)]
        (let [ix (* i xstep)
              iy (* i ystep)
              rix (- fw ix)
              riy (- fh iy)]
          (q/line [ix 0] [fw iy])
          (q/line [rix fh] [0 riy])
          (q/line [fw iy] [rix fh])
          (q/line [0 riy] [ix 0]))
        ))
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
