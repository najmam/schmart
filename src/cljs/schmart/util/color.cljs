(ns schmart.util.color
  (:require [thi.ng.color.core :as col]))

;http://androidarts.com/palette/16pal.htm
(def arne
  (->>
    [0 0 0       ; 0  ; void       ; black
    157 157 157  ; 1  ; ash        ; grey
    255 255 255  ; 2  ; blind      ; white
    190 38 51    ; 3  ; bloodred   ; red
    224 111 139  ; 4  ; pigmeat    ; pink
    73 60 43     ; 5  ; oldpoop    ; greybrown
    164 100 34   ; 6  ; newpoop    ; dark brown
    235 137 49   ; 7  ; blaze      ; light brown / orange
    247 226 107  ; 8  ; zornskin   ; yellow
    47 72 78     ; 9  ; shadegreen ; lime blue
    68 137 26    ; 10 ; leafgreen  ; dark green
    163 206 39   ; 11 ; slimegreen ; lime green
    27 38 50     ; 12 ; nightblue  ; dark blue
    0 87 132     ; 13 ; seablue    ; less dark blue
    49 162 242   ; 14 ; skyblue    ; blue blue
    178 220 239] ; 15 ; cloudblue  ; light blue
    (partition 3)
    (map (fn [[r g b]] (col/rgba r g b 255)))))

(def palettes
  {:arne arne})

(defn color-in-palette
  [palette-id color-id]
  (let [palette (get palettes palette-id (first palettes)) ; only one for now
        real-color-id color-id]
    (nth palette real-color-id)))

(defn as-argb
  [tc]
  (let [r (col/red tc)
        g (col/green tc)
        b (col/blue tc)
        a (col/alpha tc)]
    (->> [b g r a]
         (map-indexed (fn [idx c] (bit-shift-left c (* idx 8))))
         (reduce + 0))))
