(ns tictactoe.core-test
  (:use clojure.test
        tictactoe.core))


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

;; TODO -- not used yet, but a sample of a random AI mover
(defn rand-mover [player board]
  (list
   (rand-nth (map first (filter #(= 0 (second %)) (map-indexed vector board))))
   #(rand-mover %1 %2)))

(defn scripted-mover [player board moves]
  (list
   (first moves)
   #(scripted-mover %1 %2 (rest moves))))

(deftest test-play-game
  (let [check-play-game-result
        (fn [moves exp-winner]
          (let [outcome-board (play-game empty-board
                                         1
                                         #(scripted-mover %1 %2 moves)
                                         nil)]
            (is (= 9 (count outcome-board))) ;; we have a board
            (is (= exp-winner (who-won-board outcome-board)))
            (is (is-board-complete? outcome-board))
            ))]
    (check-play-game-result [4 0 1 6 7] 1)
    (check-play-game-result [2 4 0 1 6 7] 2)
    ))
