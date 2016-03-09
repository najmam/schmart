(ns sketches.particles
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [sketches.util.math :as m]
            [sketches.util.draw :as d]))
(enable-console-print!)
(def frame-rate 30)
(def w 500)
(def h 500)

(defn- move-particle [p]
  (let [pos (:position p)
        displacement [(q/random -0.5 0.5) (q/random -0.5 0.5)]
        new-position (m/add-vec pos displacement)]
    (assoc p :position new-position)))

(defn- move-particles [particles]
  (reduce
    (fn [pcls p]
      (conj pcls (move-particle p)))
    []
    particles))

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (let [pts (m/random-points w h 100)
        particles (map (fn [p] {:position p}) pts)]
    {:particles particles}))

(defn- sketch-update [state]
  (let [f (q/frame-count)]
    (-> state
        (update-in [:particles] move-particles))))

(defn- sketch-draw [{:keys [particles]}]
  (let [f (q/frame-count)]
  (q/background 0)
  (q/no-stroke)
  (q/fill 255 0 0)
  (doseq [p particles]
    (let [[x y] (:position p)]
      (d/circle x y 10)))))

(q/defsketch mysketch
  :host "sketch"
  :size [w h]
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
