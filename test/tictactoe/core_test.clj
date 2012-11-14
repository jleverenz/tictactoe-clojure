(ns tictactoe.core-test
  (:use clojure.test
        tictactoe.core))


(def empty-board                      '( 0 0 0
                                         0 0 0
                                         0 0 0 ))

(def partially-complete-board         '( 0 2 0
                                         0 1 0
                                         0 0 0 ))

(def draw-board                       '( 1 2 2
                                         2 1 1
                                         1 1 2 ))

(def partially-complete-1wins-board   '( 1 0 2
                                         0 1 2
                                         0 0 1 ))

(def partially-complete-2wins-board   '( 1 0 2
                                         0 1 2
                                         0 1 2 ))

(def complete-1wins-board             '( 1 1 1
                                         2 1 2
                                         2 1 2 ))

(def complete-2wins-board             '( 1 1 2
                                         2 2 2
                                         1 2 1 ))



(deftest test-win-on-line
  (is (= 0 (win-on-line empty-board '(0 1 2))))
  (is (= 1 (win-on-line partially-complete-1wins-board '(0 4 8))))
  (is (= 2 (win-on-line partially-complete-2wins-board '(2 5 8)))))

(deftest test-is-board-solved?
  (is (= false (is-board-solved? empty-board)))
  (is (= false (is-board-solved? draw-board)))
  (is (= false (is-board-solved? partially-complete-board)))
  (is (= true (is-board-solved? partially-complete-1wins-board)))
  (is (= true (is-board-solved? partially-complete-2wins-board)))
  (is (= true (is-board-solved? complete-1wins-board)))
  (is (= true (is-board-solved? complete-2wins-board)))
  )


(deftest test-is-board-full?
  (is (= false (is-board-full? empty-board)))
  (is (= true (is-board-full? draw-board)))
  (is (= false (is-board-full? partially-complete-board)))
  (is (= false (is-board-full? partially-complete-1wins-board)))
  (is (= false (is-board-full? partially-complete-2wins-board)))
  (is (= true (is-board-full? complete-1wins-board)))
  (is (= true (is-board-full? complete-2wins-board)))
  )

(deftest test-is-board-complete?
  (is (= false (is-board-complete? empty-board)))
  (is (= true (is-board-complete? draw-board)))
  (is (= false (is-board-complete? partially-complete-board)))
  (is (= true (is-board-complete? partially-complete-1wins-board)))
  (is (= true (is-board-complete? partially-complete-2wins-board)))
  (is (= true (is-board-complete? complete-1wins-board)))
  (is (= true (is-board-complete? complete-2wins-board)))
  )

(deftest test-who-won-board
  (is (= 0 (who-won-board empty-board)))
  (is (= 0 (who-won-board draw-board)))
  (is (= 0 (who-won-board partially-complete-board)))
  (is (= 1 (who-won-board partially-complete-1wins-board)))
  (is (= 2 (who-won-board partially-complete-2wins-board)))
  (is (= 1 (who-won-board complete-1wins-board)))
  (is (= 2 (who-won-board complete-2wins-board)))
  )

(deftest test-play-move-exceptions
  (is (thrown? IllegalArgumentException (play-move empty-board -1 1)))
  (is (thrown? IllegalArgumentException (play-move empty-board 9 1)))
  (is (thrown? IllegalArgumentException (play-move empty-board 0 0)))
  (is (thrown? IllegalArgumentException (play-move empty-board 0 3))))

(deftest test-play-move
  (is (= '(1 0 0 0 0 0 0 0 0) (play-move empty-board 0 1)))
  (is (= '(0 0 0 0 0 0 0 0 2) (play-move empty-board 8 2)))
  (is (= '(1 2 1 2 1 2 1 2 1) (play-move '(1 2 1 2 1 0 1 2 1) 5 2)))
  )

(deftest test-play-game
  (let [check-play-game-result
        (fn [moves exp-winner exp-complete]
          (let [outcome (play-game empty-board moves)]
            (is (= 2 (count outcome))) ;; we have a board and turn state left
            (is (= exp-winner (who-won-board (first outcome))))
            (is (= exp-complete (is-board-complete? (first outcome))))
            outcome))]
    (check-play-game-result [] 0 false)
    (check-play-game-result [4] 0 false)
    (check-play-game-result [4 0 1 6 7] 1 true)
    (check-play-game-result [2 4 0 1 6 7] 2 true)
    ))
