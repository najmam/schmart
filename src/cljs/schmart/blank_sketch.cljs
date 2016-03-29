(ns schmart.blank-sketch
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [thi.ng.color.core :as col]
            [schmart.util.math :as m]
            [schmart.util.draw :as d]
            [schmart.util.sketch-size :as sketch-size-helpers]))
(enable-console-print!)
(def frame-rate 30)
(def frame-duration (/ 1 frame-rate))
(def sketch-size (atom {:w 500 :h 500}))
(defn- sketch-width [] (:w @sketch-size))
(defn- sketch-height [] (:h @sketch-size))

(defn- initial-state []
  {})

(defn- new-state [st]
  st)

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (initial-state))

(defn- sketch-update [st]
  (let [f (q/frame-count)]
    st))

(defn- sketch-draw []
  (let [[fw fh] [(sketch-width) (sketch-height)]]
    (q/fill 255)
    (q/rect 0 0 fw fh)
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
