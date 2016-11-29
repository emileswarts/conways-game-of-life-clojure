(ns conways-gol.start-patterns) 

(def glider1 [{:x 3 :y 2 :state 1}
              {:x 4 :y 3 :state 1}
              {:x 4 :y 4 :state 1}
              {:x 3 :y 4 :state 1}
              {:x 2 :y 4 :state 1}])

(def glider2 [{:x 1 :y 0 :state 1}
              {:x 3 :y 0 :state 1}
              {:x 3 :y 1 :state 1}
              {:x 2 :y 1 :state 1}
              {:x 2 :y 2 :state 1}])

(def glider3 [{:x 3 :y 0 :state 1}
              {:x 3 :y 1 :state 1}
              {:x 3 :y 2 :state 1}
              {:x 2 :y 2 :state 1}
              {:x 1 :y 1 :state 1}])

(def blinker1 [{:x 1 :y 0 :state 1}
               {:x 1 :y 1 :state 1}
               {:x 1 :y 2 :state 1}])

(def boat [{ :x 1 :y 1 :state 1 }
           { :x 2 :y 1 :state 1 }
           { :x 1 :y 2 :state 1 }
           { :x 3 :y 2 :state 1 }
           { :x 2 :y 3 :state 1 }])
