(ns schmart.util.math
  (:require [quil.core :as q]))

(defn random-points
  [w h nb]
  (->> (partial q/random 1)
       (repeatedly)
       (take nb)
       (partition 2)
       (map (fn [[x y]] [(* w x) (* h y)]))))

(defn msin
  [frame period]
  (let [t (* frame (/ (* 2 q/PI) period))]
    (/ (+ 1 (q/sin t)) 2)))

(defn spike
  "f(0) = 0, f(0.5) = 1, f(1) = 0, linear interpolation in between."
  [t]
  (if (< t 0.5)
    (* 2 t)
    (- 1 (* 2 (- t 0.5)))))

(defn mcycle
  [period frame]
  (/ (mod frame period) period))

(defn bpm-period-in-frames
  [bpm fps]
  (let [seconds-in-a-beat (/ bpm 60)]
    (* seconds-in-a-beat fps)))

(defn slide
  [offset t]
  (if (< t offset)
    (+ t offset)
    (- t offset)))

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

(defn clamp [v min max]
  (cond
    (< v min) min
    (> v max) max
    :else v))

(defn p-lerp
  [[x1 y1] [x2 y2] t]
  [(q/lerp x1 x2 t) (q/lerp y1 y2 t)])
