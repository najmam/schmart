(ns sketches.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:nbp 50})

(defn update-state [state]
  {:nbp 60
   :phase 2})

; (def t q/with-translation)
(def e q/ellipse)

(defn lin-scale
  [min val max]
  (+ min (* val (- max min))))

(defn square [x] (* x x))

(def canvas-width 500)

(defn *sqrt2 [x] (* (.sqrt js/Math 2) x))

(defn draw-state [{:keys [nbp]}]
  (q/background 0)
  (doseq [p (range 0 (inc nbp))]
      (let [step (/ (*sqrt2 canvas-width) nbp)
            f (q/frame-count)
            x (* step p)
            y (* step p)
            phase (/ f 7)
            color (lin-scale 0 (/ p nbp) 360)
            size (lin-scale 60 (q/sin (+ p phase)) 100)]
        (q/fill color 255 255)
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
