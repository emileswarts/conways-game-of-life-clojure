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
    (< live-neighbor-count 2) :dead
    (= live-neighbor-count 2) :alive
    (= live-neighbor-count 3) :alive
    (> live-neighbor-count 3) :dead)))
