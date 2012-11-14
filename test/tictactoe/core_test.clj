(ns tictactoe.core-test
  (:use clojure.test
        tictactoe.core))


(def empty-board                      '( 0 0 0
                                         0 0 0
                                         0 0 0 ))

(def partially-complete-board         '( 0 2 0
                                         0 1 0
                                         0 0 0 ))

(def partially-complete-1wins-board   '( 1 0 2
                                         0 1 2
                                         0 0 1 ))

(def partially-complete-2wins-board   '( 1 0 2
                                         0 1 2
                                         0 1 2 ))

(def draw-board                       '( 1 2 2
                                         2 1 1
                                         1 1 2 ))

(deftest test-win-on-line
  (is (= 0 (win-on-line empty-board '(0 1 2))))
  (is (= 1 (win-on-line partially-complete-1wins-board '(0 4 8))))
  (is (= 2 (win-on-line partially-complete-2wins-board '(2 5 8)))))

(deftest test-is-board-solved?
  (is (= false (is-board-solved? empty-board)))
  (is (= false (is-board-solved? draw-board)))
  (is (= true (is-board-solved? partially-complete-1wins-board)))
  (is (= true (is-board-solved? partially-complete-2wins-board)))
  )
