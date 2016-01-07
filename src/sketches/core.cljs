(ns sketches.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsv)
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

(defn draw-state [{:keys [nbp]}]
  (q/background 0)
  (doseq [point (range 0 (inc nbp))]
      (let [p (/ point nbp)
            f (q/frame-count)
            pos1 (fn [p min max] (lin-scale min p max))
            x (pos1 p (* 0.40 w) (* 0.85 w))
            y (pos1 p (* 0.25 h) (* 0.75 h))
            phase (/ f 25)
            hue (lin-scale 0 p 360)
            saturation (lin-scale 250 p 0)
            value (lin-scale 0 p 360)
            size 60]
        (q/fill hue saturation value)
        (q/rect (- x (/ size 2)) (- y (/ size 2)) size size))))
  
;  (q/fill (:color state) 255 255)
;  ; Calculate x and y coordinates of the circle.
;  (let [angle (:angle state)
;        x (* 150 (q/cos angle))
;        y (* 150 (q/sin angle))]
;    ; Move origin point to the center of the sketch.
;    (q/with-translation [(/ (q/width) 2)
;                         (/ (q/height) 2)]
;      ; Draw the circle.
;      (q/ellipse x y 100 100))))

(q/defsketch sketches
  :host "canvas"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
