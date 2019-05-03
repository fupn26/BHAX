;iteratív faktoriális
(define (fakt_it n)
    (if (= 0 n) 
                (set! n 1)
    )
    (let loop ((variable (- n 1)))
        (if (> variable 1)                               
            (begin
                (set! n (* n variable))
                (loop (- variable 1) )
            )
        )
    )
    n
)

;rekurzív faktoriális
(define (fakt_re n)
    (
    if (< n 2) 
         1 
    (* n (fakt (- n 1))) ;else ag
    )
)

