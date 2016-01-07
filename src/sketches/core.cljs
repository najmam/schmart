(ns sketches.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:nbp 50})

(defn update-state [state]
  {:nbp 80
   :phase 2})

; (def t q/with-translation)
(def e q/ellipse)

(defn lin-scale
  [min val max]
  (+ min (* val (- max min))))

(defn square [x] (* x x))

(def canvas-side 500)
(def canvas-width canvas-side)
(def canvas-height canvas-side)
(def canvas-dim [canvas-width canvas-height])

(defn *sqrt2 [x] (* (.sqrt js/Math 2) x))
(defn sqrt [x] (.sqrt js/Math x))

(def w canvas-width)
(def h canvas-height)

(defn square-centered-at
  [x y size]
  (q/rect (- x (/ size 2))
          (- y (/ size 2))
          size size))

(defn msin
  [frame period]
  (let [t (* frame (/ (* 2 q/PI) period))]
    (/ (+ 1 (q/sin t)) 2)))
  

(defn draw-state [{:keys [nbp]}]
  (let [f (q/frame-count)
        bgcolor (lin-scale 0 (msin f 200) 360)]
  (q/background bgcolor)
  (q/no-stroke)
  (doseq [p (range 0 1 (/ 1 nbp))]
    (let [dc (/ w nbp)
          dc_2 (/ dc 2)
          x (lin-scale dc_2 p (- w dc_2))
          y (lin-scale dc_2 p (- h dc_2))
          ]
      (q/fill 255 255 255)
      (square-centered-at x y dc))))
    )

(q/defsketch sketches
  :host "canvas"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
