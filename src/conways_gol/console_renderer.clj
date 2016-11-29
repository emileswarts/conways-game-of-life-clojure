(ns conways-gol.console-renderer)

(defn on-boundary?
  [cell world-dimensions]
  (= (- (world-dimensions :x) 1) (cell :x)))

(defn rendered-cell
  [cell world-dimensions] 
  (cond 
    (= (cell :state) 0)
      (if (on-boundary? cell world-dimensions)
        ". \n"
        ". ")
    (= (cell :state) 1)
    (if (on-boundary? cell world-dimensions)
      "🔥 \n"
      "🔥 ")))

(defn render
  [world world-dimensions]
  (str (clojure.string/join "" (map (fn [cell] 
                                 (rendered-cell cell world-dimensions))
                               world)) "\n"))
