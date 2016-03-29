(ns schmart.playground
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [schmart.util.math :as m]
            [schmart.util.draw :as d]
            [schmart.util.sketch-size :as sketch-size-helpers]
            [schmart.util.dimension :refer [stretch-to-fit]]))
(enable-console-print!)
(def frame-rate 30)
(def sketch-size (atom {:w 500 :h 500}))
(defn sketch-width [] (:w @sketch-size))
(defn sketch-height [] (:h @sketch-size))
(sketch-size-helpers/swap-when-fullscreen-and-window-is-resized! sketch-size)

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  {})

(defn- sketch-update [state]
  state)

(defn rect-fn
  [ar]
  (fn [x y w h]
    (let [{sx :x sy :y sw :w sh :h} (stretch-to-fit (sketch-width) (sketch-height) ar)]
      (q/rect (+ sx x) (+ sy y) w h))))

(defn- sketch-draw [{:keys [particles]}]
  (let [f (q/frame-count)
        my-ar 2
        {fw :w fh :h} (stretch-to-fit (sketch-width) (sketch-height) my-ar)
        R (rect-fn my-ar)]
    (q/fill 255)
    (q/rect 0 0 (sketch-width) (sketch-height))
    (q/no-stroke)
    
    (q/fill 255 0 0)
    (R 0 0 fw fh)
    (q/fill 0 255 0)
    (R 0 0 20 20)
    (q/fill 0 0 255)
    (R (- fw 40) (- fh 40) 40 40)
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
