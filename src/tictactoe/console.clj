(ns tictactoe.console
  (:gen-class)
  (:use tictactoe.core
        [clojure.string :only [join]]))

(def printfln (comp println format))

(defn print-board [board]
  (doseq [line (partition 3 board)]
    (printfln "+---+---+---+")
    (printfln "| %s |" (join " | " line)))
  (printfln "+---+---+---+"))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn player-name [player-num]
  (str "Player " (if (= player-num 1) "X" "O")))

(defn play-console [board player]
  (print-board board)
  (if (is-board-solved? board)
    board
    (let []
      (printfln "%s's turn: " (player-name player))
      (let [input (parse-int (read-line))]
        (printfln "Player picked: %s" input)
        (let [result (play-turn board [input] player)]
          (recur (first result) (second result)))))))


(defn -main [& args]
  (let [final-board (play-console empty-board 1)
        winner (who-won-board final-board)]
    (printfln "*** %s won! ***" (player-name winner))))
