(ns maze.core
  (require [maze.misc :refer :all]))

(def empty-list-gen (comp vec repeat))
(defn- maze-factory [rows columns] (empty-list-gen rows (empty-list-gen columns 0)))

(def directions {:N 1, :S 2, :E 4, :W 8})
(def dx {:E 1, :W -1, :N 0, :S 0})
(def dy {:E 0, :W 0, :N -1, :S 1})
(def opposite {:E :W, :W :E, :N :S, :S :N})

(defn rand-directions [] (-> directions keys shuffle))

(defn add-queue [x y q]
  (into (map vector (repeat 4 [x, y]) (rand-directions)) q))

(defn y-length [m] (-> m count dec))
(def x-length (comp y-length first))

(defn- gen [x y maze]
  (loop [[[[x, y], d] & t] (add-queue x y []), m maze]
    (if (nil? x)
      m
      (let [nx (+ x (d dx))
            ny (+ y (d dy))]
        (if (and (between nx 0 (x-length m))
                 (between ny 0 (y-length m))
                 (= 0 (mget nx ny m)))
          (->> m
               (mset (d directions) x y)
               (mset (-> d opposite directions) nx ny)
               (recur (add-queue nx ny t)))
          (recur t m))))))

(defn create [rows columns]
  (->> (maze-factory rows columns) (gen 0 0)))
