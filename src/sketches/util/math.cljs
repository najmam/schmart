(ns sketches.util.math
  (:require [quil.core :as q]))

(defn msin
  [frame period]
  (let [t (* frame (/ (* 2 q/PI) period))]
    (/ (+ 1 (q/sin t)) 2)))

(defn random
  "A random number between 0 (included) and 1 (excluded)."
  []
  (. js/Math random))

(defn random-in
  [min max]
  (+ min (* (- max min) (random))))

(defn random-points
  [w h nb]
  (->> (repeatedly random)
      (take nb)
      (partition 2)
      (map (fn [[x y]] [(* w x) (* h y)]))
      ))

(defn lin-scale
  [min val max]
  (+ min (* val (- max min))))

(def lerp lin-scale)

(defn square [x] (* x x))

(defn *sqrt2 [x] (* (.sqrt js/Math 2) x))
(defn sqrt [x] (.sqrt js/Math x))

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
  [bpm fps]
  (let [seconds-in-a-beat (/ bpm 60)]
    (* seconds-in-a-beat fps)))

(defn norm [x y] (sqrt (+ (square x) (square y))))

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
