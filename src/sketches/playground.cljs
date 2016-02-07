(ns sketches.playground
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))
(enable-console-print!)

(def frame-rate 30)
(def canvas-side 500)
(def canvas-width canvas-side)
(def canvas-height canvas-side)
(def canvas-dim [canvas-width canvas-height])
(def w canvas-width)
(def h canvas-height)

(defn random
  "A random number between 0 (included) and 1 (excluded)."
  []
  (. js/Math random))

(defn random-in
  [min max]
  (+ min (* (- max min) (random))))

(defn random-points
  []
  (->> (repeatedly random)
      (take 400)
      (partition 2)
      (map (fn [[x y]] [(* w x) (* h y)]))
      ))

(def e q/ellipse)

(defn lin-scale
  [min val max]
  (+ min (* val (- max min))))

(defn square [x] (* x x))


(defn *sqrt2 [x] (* (.sqrt js/Math 2) x))
(defn sqrt [x] (.sqrt js/Math x))

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

(defn norm [x y] (sqrt (+ (square x) (square y))))

(defn rect2 [x y w h] (q/rect (- x (/ w 2)) (- y (/ h 2)) w h))

(defn slide
  [offset t]
  (if (< t offset)
    (+ t offset)
    (- t offset)))

(defn hypotenuse
  [x y] (sqrt (+ (square x) (square y))))

(defn rlerp
  "ça va de a à b et je veux que ça aille de x à y"
  [a b x y v]
  (let [t (/ (- v a) (- b a))]
    (+ x (* t (- y x)))))

(defn barycentre
  [x1 y1 x2 y2 x3 y3]
  [(/ (+ x1 x2 x3) 3)
   (/ (+ y1 y2 y3) 3)])

(defn scale-vec
  [v scale]
  [(* scale (first v)) (* scale (second v))])

(defn add-vec
  [& vecs]
  [(->> vecs (map first) (reduce +))
   (->> vecs (map second) (reduce +))])

; ---------------------------------------------------

(defn circle
  ([x y] (circle x y 3))
  ([x y radius]
   (q/ellipse x y radius radius)))

; ---------------------------------------------------

(defn setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (let [pts (random-points)]
    {:points pts}))

(defn update-state [state]
  state)

(defn draw-state [{:keys [points triangulation]}]
  (let [f (q/frame-count)
        w 500
        h 500]
  (q/background 0)
  (circle (/ w 2) (/ h 2) 10)
))

(q/defsketch sketches
  :host "sketch"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
