(ns sketches.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def frame-rate 30)

(defn setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  {:nbp 50})

(defn update-state [state]
  {:nbg 30
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

(def lerp lin-scale)

(defn spike
  "f(0) = 0, f(0.5) = 1, f(1) = 0, lerp elsewhere."
  [t]
  (if (< t 0.5)
    (* 2 t)
    (- 1 (* 2 (- t 0.5)))
    ))

(defn mcycle
  [period frame]
  (/ (mod frame period) period))

(defn bpm-period-in-frames
  [bpm]
  (let [fps frame-rate
        seconds-in-a-beat (/ bpm 60)]
    (* seconds-in-a-beat fps)))

(def point-radius 3)
(defn dot [x y]
  (q/ellipse x y point-radius point-radius))

(defn norm [x y] (sqrt (+ (square x) (square y))))

(defn rect2 [x y w h] (q/rect (- x (/ w 2)) (- y (/ h 2)) w h))

(defn slide
  [offset t]
  (if (< t offset)
    (+ t offset)
    (- t offset)))

(defn draw-state [{:keys [nbg]}]
  (let [f (q/frame-count)]
  (q/background 255 120 0)
  (q/no-stroke)
  (q/fill 0)
  (doseq [i (range 0 (inc nbg))]
    (let [it (/ i nbg)
          color (lerp 90 it 180)
          thickness (/ w nbg)
          cx (lerp 0 it w)
          cy (lerp 0 it h)
          angle (/ q/PI 4)]
      (q/fill color)
      (q/with-translation
        [cx cy]
        (q/with-rotation
          [angle]
          (rect2 0 0 thickness (* 1.5 h))))
      )
    )
  
  
  ))

(q/defsketch sketches
  :host "canvas"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
