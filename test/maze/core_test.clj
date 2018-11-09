(ns maze.core-test
  (:require [clojure.test :refer :all]
            [maze.core :refer :all]))

(deftest maze-creation
  (testing "A valid random maze is generated"
    (is (->> (create 10) flatten (every? pos?))))
  (testing "The maze size is respected"
    (is (->> (create 5) count (= 5)))))
