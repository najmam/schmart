;; the nearest-neighbor sorting is not correct
(ns schmart.sk0004
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
(defn sketch-width [] (:w @sketch-size))
(defn sketch-height [] (:h @sketch-size))

(def nb-points 20)
(def c1-ticks-in-cycle 20)
(def c1-tick-period 0.1)

(defn sorted-segment
  [p1 p2]
  (if (= -1 (compare p1 p2)) p1 p2))

(defn random-points
  [max-x max-y]
  (->> #(q/random 1)
       repeatedly
       (partition 2)
       (map (fn [[x y]] [(* max-x x) (* max-y y)]))))

(defn nearest-neighbor-map
  "Returns a map associating a point in pts1 to its nearest neighbor in pts2
  such that every point in pts2 is mapped to a point in pts1." ; does this description allow for multiple solutions ? it shouldn't
  [pts1 pts2]
  (let [distance->segment (apply hash-map (apply concat (for [i pts1 j pts2]
                                               [(apply q/dist (concat i j)) [i j]])))
        closer-segments (->> distance->segment
                             seq
                             #_ ((fn [xs] (println "-------\n" xs) xs))
                             (sort-by first)
                             (map second)
                             #_ ((fn [xs] (println "-------\n" xs) xs)))]
    (loop [olds #{}
           news #{}
           nn-map {}
           remaining-segments closer-segments]
      (if (empty? remaining-segments) nn-map
        (let [[oldp newp] (first remaining-segments)]
          (if (or (contains? olds oldp) (contains? news newp))
            (recur olds
                   news
                   nn-map
                   (rest remaining-segments))
            (recur (conj olds oldp)
                   (conj news newp)
                   (assoc nn-map oldp newp)
                   (rest remaining-segments)))
        )))))

(defn nn-distances
  [nn]
  (->> nn
       seq
       (map (fn [[oldp newp]] (apply q/dist (concat oldp newp))))))

(defn- initial-state []
  {:c1 0
   :c1-t 0
   :centroids (take nb-points (random-points (sketch-width) (sketch-height)))
   :old-centroids (take nb-points (random-points (sketch-width) (sketch-height)))})

(defn- new-state [st]
  {:c1 0
   :c1-t 0
   :old-centroids (:centroids st)
   :centroids (let [new-centroids (take nb-points (random-points (sketch-width) (sketch-height)))
                    nn-for-old-centroid (nearest-neighbor-map (:centroids st) new-centroids)]
                (map nn-for-old-centroid (:centroids st)))})

(defn- sketch-setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (initial-state))

(defn- sketch-update [st]
  (let [f (q/frame-count)
        elapsed-secs (* f frame-duration)
        c1-cur-ticks (q/floor (/ (/ f c1-tick-period) frame-rate))
        c1 (rem c1-cur-ticks c1-ticks-in-cycle)
        c1-starts-at (* c1-tick-period (- c1-cur-ticks c1))
        c1-t (/ (- elapsed-secs c1-starts-at) (* c1-ticks-in-cycle c1-tick-period))]
    (if (and (= 0 c1) (not= (:c1 st) c1)) (new-state st)
      (-> st
          (assoc :c1 c1 :c1-t c1-t)))
    ))

(defn color-for-hue
  [hue]
  (->> (col/hsva hue 1 1)
      col/as-rgba
      deref
      (map (partial * 255))))

(println (color-for-hue 0))
(println (color-for-hue 0.5))
(println (color-for-hue 1))

(defn- sketch-draw [{:keys [c1 c1-t centroids old-centroids]}]
  (let [f (q/frame-count)
        [fw fh] [(sketch-width) (sketch-height)]]
    (q/no-stroke) (q/fill 255) (q/rect 0 0 fw fh)
    (q/fill 255 0 0)
    (dotimes [i nb-points]
      (let [[ox oy] (nth old-centroids i)
            [nx ny] (nth centroids i)]
        (apply q/fill (color-for-hue (/ i nb-points)))
        (apply q/stroke (color-for-hue (/ i nb-points)))
        (q/ellipse ox oy 10 10)
        (q/ellipse nx ny 10 10)
        (q/line [ox oy] [nx ny])
        ))
    (dotimes [i nb-points]
      (let [w (/ fw nb-points)]
        (q/no-stroke)
        (apply q/fill (color-for-hue (/ i nb-points)))
        (q/rect (* i w) 0 w w)))
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
