(ns sketches.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [sketches.delaunay :refer [triangulate]]))
(enable-console-print!)

(def frame-rate 30)
(def canvas-side 500)
(def canvas-width canvas-side)
(def canvas-height canvas-side)
(def canvas-dim [canvas-width canvas-height])
(def w canvas-width)
(def h canvas-height)

(defn random
  "A random number between 0 (included) and 1 (excluded)."
  []
  (. js/Math random))

(defn random-in
  [min max]
  (+ min (* (- max min) (random))))

(defn random-points
  []
  (->> (repeatedly random)
      (take 400)
      (partition 2)
      (map (fn [[x y]] [(* w x) (* h y)]))
      ))

; (def t q/with-translation)
(def e q/ellipse)

(defn lin-scale
  [min val max]
  (+ min (* val (- max min))))

(defn square [x] (* x x))


(defn *sqrt2 [x] (* (.sqrt js/Math 2) x))
(defn sqrt [x] (.sqrt js/Math x))


(defn square-centered-at
  [x y size]
  (q/rect (- x (/ size 2))
          (- y (/ size 2))
          size size))

(defn msin
  [frame period]
  (let [t (* frame (/ (* 2 q/PI) period))]
    (/ (+ 1 (q/sin t)) 2)))

(def lerp lin-scale)

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
  [bpm]
  (let [fps frame-rate
        seconds-in-a-beat (/ bpm 60)]
    (* seconds-in-a-beat fps)))

(def point-radius 3)
(defn dot [x y]
  (q/ellipse x y point-radius point-radius))

(defn norm [x y] (sqrt (+ (square x) (square y))))

(defn rect2 [x y w h] (q/rect (- x (/ w 2)) (- y (/ h 2)) w h))

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

(defn stripe
  "Direction is the angle formed by the stripe irt the X-axis pointing right.
  d is the distance from the stripe to a parallel stripe passing through (0,0)."
  [direction thickness d]
  (let [dc (rlerp -1 1 0 1 d)
        cx (* dc w)
        cy (* dc h)]
  (q/with-translation
    [cx cy]
    (q/with-rotation
      [(* direction (/ q/PI -4))]
      (rect2 0 0 (* 1.5 w) thickness)))))

(def alley-width 10)

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

(defn shrink-trig
  [bary scale x1 y1 x2 y2 x3 y3]
  (let [[bx by] bary
        v1 [(- x1 bx) (- y1 by)]
        v2 [(- x2 bx) (- y2 by)]
        v3 [(- x3 bx) (- y3 by)]
        sv1 (scale-vec v1 scale)
        sv2 (scale-vec v2 scale)
        sv3 (scale-vec v3 scale)
        newpts (map #(add-vec bary %) [sv1 sv2 sv3])]
    (apply concat newpts)))

(defn erode-vec
  [v px]
  (let [;[[x1 y1] [x2 y2]] v
        norm 20 ;(q/dist x1 y1 x2 y2)
        newnorm (- norm px)
        scale (/ newnorm norm)]
    (if (< newnorm 0)
      v
      (scale-vec v scale))))

(defn erode-trig
  [bary erosion x1 y1 x2 y2 x3 y3]
  (let [[bx by] bary
        v1 [(- x1 bx) (- y1 by)]
        v2 [(- x2 bx) (- y2 by)]
        v3 [(- x3 bx) (- y3 by)]
        sv1 (erode-vec v1 erosion)
        sv2 (erode-vec v2 erosion)
        sv3 (erode-vec v3 erosion)
        newpts (map #(add-vec bary %) [sv1 sv2 sv3])]
    (apply concat newpts)))

(defn draw-state [{:keys [points triangulation]}]
  (let [f (q/frame-count)]
  (q/background 0)
  #_ (doseq [[x y] points]
    (dot x y))
  (q/no-stroke)
  (let [{:keys [triangles]} triangulation]
    (doseq [[[x1 y1] [x2 y2] [x3 y3]] triangles]
      (let [bary (barycentre x1 y1 x2 y2 x3 y3)
            shrunk (shrink-trig bary 0.8 x1 y1 x2 y2 x3 y3)
            eroded (erode-trig bary 5 x1 y1 x2 y2 x3 y3)
            [ex1 ey1 ex2 ey2 ex3 ey3] eroded
            [sx1 sy1 sx2 sy2 sx3 sy3] shrunk]
        (q/fill 255) (q/triangle ex1 ey1 ex2 ey2 ex3 ey3) ; eroded
        ; (q/fill 0) (q/triangle x1 y1 x2 y2 x3 y3) ; original triangle
        ; (q/fill 0) (q/triangle sx1 sy1 sx2 sy2 sx3 sy3) ; scaled down
        ; (q/fill 255 0 0) (dot (first bary) (second bary)) ; barycentre
        )))
  (q/stroke 0 255 0)
  (let [{:keys [edges]} triangulation]
    (doseq [[p1 p2] edges]
      #_ (q/line p1 p2)
      )
    )
  
  
  ))



(defn setup []
  (q/frame-rate frame-rate)
  (q/color-mode :rgb)
  (let [pts (random-points)
        with-corners (conj pts [0 0] [0 h] [w h] [w 0])]
    {:points pts
     :triangulation (triangulate with-corners)}))

(defn update-state [state]
  state)

(q/defsketch sketches
  :host "canvas"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
