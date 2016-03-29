(ns schmart.util.dimension)

; ar(short and fat) > ar(tall and thin)

(defn stretch-to-fit
  "Return a map with keys [x y w h] describing a rect to draw into so that
  a rect of width/height-ratio ar is stretched to fit a sketch of size fw by fh."
  [fw fh ar]
  (let [A (> fw fh)
        B (> ar 1)
        C (> (/ fw fh) ar)
        min-edge (if (or (and A B (not C))
                         (and (not A) B)
                         (and (not A) (not B) (not C)))
                   :horizontal
                   :vertical)
        [w h] (if (= min-edge :horizontal)
                [fw (/ fw ar)]
                [(* ar fh) fh])]
    {:x (- (/ fw 2) (/ w 2))
     :y (- (/ fh 2) (/ h 2))
     :w w
     :h h}))
