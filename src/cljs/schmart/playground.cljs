(ns schmart.playground
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [schmart.util.math :as m]
            [schmart.util.draw :as d]
            [schmart.util.sketch-size :as sketch-size-helpers]))
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

(defn- sketch-draw [{:keys [particles]}]
  (let [f (q/frame-count)]
    (q/background 0)
    (q/no-stroke)
    (q/fill 255 0 0)
    (q/rect 0 0 20 20)
    (q/rect (- (sketch-width) 20) (- (sketch-height) 20) 20 20)))

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
