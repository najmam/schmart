(ns schmart.example.stretch-to-fit
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

(defn- sketch-draw [{:keys [particles]}]
  (let [f (q/frame-count)
        fill-stretched-rect (fn [ar]
                              (let [{:keys [x y w h]} (stretch-to-fit (sketch-width) (sketch-height) ar)]
                                (q/rect x y w h)
                                ))]
    (q/background 255)
    (q/no-stroke)
    #_ (do (q/fill 255 0 0) (fill-stretched-rect 1))
    #_ (do (q/fill 0 255 0) (fill-stretched-rect 2))
    (do (q/fill 0 0 255) (fill-stretched-rect 0.8))
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
