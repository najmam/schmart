(ns schmart.sk0001
  (:require [quil.core :as q :include-macros true]
            quil.middleware
            [schmart.util.draw :as d]
            [schmart.util.color :as c]))
(enable-console-print!)
(def frame-rate 30)
(def w 500)
(def h 500)

(defn color
  [i]
  (let [id (if (integer? i) i
             (get {:background 3} i 0))]
    (c/as-argb (c/color-in-palette :arne id))))

(defn *sqrt2 [x] (* x (q/sqrt 2)))

(defn- horizontal-bands []
  (let [nb-colors 16
        all-colors (vec (map color (range 0 nb-colors)))
        interesting-colors (subvec all-colors 2)
        minw 10
        maxw 50]
    (loop [bands []
           x-sofar 0]
      (if (> x-sofar (*sqrt2 (q/width))) bands
        (let [c (nth interesting-colors (q/floor (q/random (count interesting-colors))))
              width (q/floor (q/random minw maxw))
              newx (+ width x-sofar)
              newband {:w width :x x-sofar :color c}]
          (recur (conj bands newband) newx))))))

(defn- grid-bands []
  (loop [bands []
         y 0]
    (if (> y (*sqrt2 (q/height)))
      bands
      (let [h (q/height) #_ (q/floor (q/random (q/height)))
            hor-bands (horizontal-bands)
            with-h (map #(assoc % :h h :y y) hor-bands)
            newbands (reduce conj bands with-h)]
        (recur newbands (+ y h))))))

(defn- new-state []
  {:bands (grid-bands)
   :c1 0})

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (new-state))

(defn- sketch-update [st]
  (let [f (q/frame-count)
        nb-colors 16
        cycle-period 0.2
        c1 (q/floor (/ (/ f cycle-period) frame-rate))]
    (if (= (:c1 st) c1) st
      (assoc (new-state) :c1 c1))))

(defn- sketch-draw
  [{:keys [bands c1]}]
  (q/no-stroke)
  (let [spare1 7
        nbrot1 (+ (int (/ 90 6)) spare1)
        cycle1 (rem c1 nbrot1)
        rot1 (* cycle1 6)]
    (when (<= cycle1 (- nbrot1 spare1))
        (q/with-rotation [(q/radians rot1)]
          (doseq [{:keys [x y w h color]} bands]
            (q/fill color)
            (let [dx (q/floor (q/random -5 5))
                  dy (q/floor (q/random -5 5))]
              (q/rect (+ x dx) (+ y dy) w h))
            )))))

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
