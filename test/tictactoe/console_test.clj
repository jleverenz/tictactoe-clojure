(ns tictactoe.console-test
  (:use clojure.test
        tictactoe.console))

;; Returns answer + function for determining next answer
(defn myfunc [state]
  (if (empty? state)
    (list nil #(do nil))
    (list (first state) #(myfunc (next state)))))

(defn go [generator f momento]
  (let [[value func] (generator)]
    (if (nil? value)
      momento
      (recur func f (f value momento)))))

(deftest test-recursive-mover
  (is (= '(1 2 3) (go #(myfunc '(1 2 3))
                      #(concat %2 (list %1))
                      '())))
  (is (= '(1 4 9) (go #(myfunc '(1 2 3))
                      #(concat %2 (list (* %1 %1)))
                      '()))))

  ;; (is (= '(4) (go #(myfunc '(4)) '())))
  ;; (is (= '(123 456) (go #(myfunc '(123 456)) '())))
  ;; (let [gen (repeatedly 10 #(rand-int 100))
  ;;       out (go #(myfunc gen) '())]
  ;;   (is (= (count out) 10))
  ;;   (doseq [x out] (is (< x 100)))))
