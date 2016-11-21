(ns conways-gol.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn cell-state
  [cell]
  (let [live-neighbor-count (count (filter #(= 1 %) (cell :neighbors)))]
  (cond
    (< live-neighbor-count 2) 0
    (= live-neighbor-count 2) 1
    (= live-neighbor-count 3) 1
    (> live-neighbor-count 3) 0)))

(defn tick-cell
  [cell]
  (assoc cell :state (cell-state cell)))
