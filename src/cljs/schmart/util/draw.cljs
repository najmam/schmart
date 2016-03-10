(ns schmart.util.draw
  (:require [quil.core :as q :include-macros true]))

(defn square-centered-at
  [x y size]
  (q/rect (- x (/ size 2))
          (- y (/ size 2))
          size size))

(defn circle
  ([x y] (circle x y 3))
  ([x y radius]
   (q/ellipse x y radius radius)))

(defn rect2 [x y w h] (q/rect (- x (/ w 2)) (- y (/ h 2)) w h))
