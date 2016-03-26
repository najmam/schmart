(ns schmart.util.sketch-size
  (:require clojure.set))

(defn is-supposed-to-be-fullscreen?
  []
  (let [body (.-body js/document)
        classes (clojure.string/split (.-className body) #" ")]
    (not (nil? (some #{"fullscreen"} classes)))))

(defn make-fullscreen!
  []
  (let [body (.-body js/document)
        classes (set (clojure.string/split (.-className body) #" "))
        newclasses (clojure.set/difference (clojure.set/union classes #{"fullscreen"}) #{"centered"})]
    (set! (.-className body) (clojure.string/join " " newclasses))))

(defn make-centered!
  []
  (let [body (.-body js/document)
        classes (set (clojure.string/split (.-className body) #" "))
        newclasses (clojure.set/difference (clojure.set/union classes #{"centered"}) #{"fullscreen"})]
    (set! (.-className body) (clojure.string/join " " newclasses))))

(defn get-window-size
  []
  (let [body (.-body js/document)
        w (.-offsetWidth body)
        h (.-offsetHeight body)]
    {:w w :h h}))

(defn swap-from-window-size!
  [at]
  (swap! at merge (get-window-size)))

(defn resize-canvas!
  "After this procedure is called, quil.core/width and quil.core/height will return outdated values."
  [w h]
  (let [canvas (.querySelector js/document "#sketch")]
    (set! (.-width canvas) w)
    (set! (.-height canvas) h)))

(defn swap-when-fullscreen-and-window-is-resized!
  [sketch-size-atom]
  (when (is-supposed-to-be-fullscreen?)
    (swap-from-window-size! sketch-size-atom)
    (set! (.-onresize js/window)
          (fn []
            (swap-from-window-size! sketch-size-atom)
            (resize-canvas! (:w @sketch-size-atom) (:h @sketch-size-atom))))))
