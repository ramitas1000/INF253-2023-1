#lang scheme

;1

;funcion encode
;codifica los bloques de una lista de bits
;Parametros:
;lista de bits a codificar
;Retorno:
;una lista de numeros con el tamano de todos los bloques que contienen los mismos valores de bits


(define (encode bits)
  (reverse 
  (let aux ((bits_aux bits) (contador 0) (ultimo 0) (respuesta '()))
    (if (null? bits_aux) (cons contador respuesta)
        (cond
          ((eqv? ultimo 0) (if (eqv? (car bits_aux) 0) (aux (cdr bits_aux) (+ contador 1) 0 respuesta)
             (aux bits_aux 0 1 (cons contador respuesta))
             )
                                   )
          ((eqv? ultimo 1) (if (eqv? (car bits_aux) 1) (aux (cdr bits_aux) (+ contador 1) 1 respuesta)
             (aux bits_aux 0 0 (cons contador respuesta))
             )
                                   )
          )
        )
    )
  )
  )
  
          
  
             
;casos de prueba de encode

(encode '(0 0 0 1 1 0 1 1 1 0 0 0))
(encode '(1 1 0 1 1 1 0 0 0))
(encode '(1 0 1 0))
(encode '(0 0 0 0))




;2

;funcion agregar
;crea una lista de un mismo numero (bit) y de tamano cant_numeros, utilizando recursion de cola
;funcion auxiliar para ambos decode
;Parametros:
;cant_numeros tamano de la lista
;bit numero a repetir en la lista (0 o 1)
;Retorno:
;una lista que contiene cant_numeros de ceros o unos

(define (agregar cant_numeros bit)
  (let aux ((numero cant_numeros)  (resultado '()) (bit_aux bit))
  (if (= numero 0) resultado
      (aux (- numero 1) (cons bit resultado) bit_aux)
      )
    )
  )




;funcion decode_simple
;decodifica una lista de numeros tomando cada numero y tranformandolo a un bloque de bits,
;utilizando recursion simple
;Parametros:
;lista lista a decodificar
;Retorno:
;la lista decodificada


(define (decode_simple lista) 
  (reverse 
  (let aux ((bits_aux lista) (pos 0) (respuesta '()))
  (if (= (modulo pos 2) 0)
      (if (null? bits_aux) respuesta
          (append (aux (cdr bits_aux) (+ pos 1) respuesta) (append respuesta (agregar (car bits_aux) 0)))
      )
      (if (null? bits_aux) respuesta
          (append (aux (cdr bits_aux) (+ pos 1) respuesta) (append respuesta (agregar (car bits_aux) 1)))
      )
      )
    )
  )
)


;funcion decode_cola
;contiene los mismos parametros que la funcion decode_simple pero utiliza recursion de cola


(define (decode_cola lista)
  (let aux((bits_aux lista) (pos 0) (respuesta '()))
    (if (= (modulo pos 2) 0)
        (if (null? bits_aux) respuesta
            (aux (cdr bits_aux) (+ pos 1) (append respuesta (agregar (car bits_aux) 0)))
        )
        (if (null? bits_aux) respuesta
            (aux (cdr bits_aux) (+ pos 1) (append respuesta (agregar (car bits_aux) 1)))
        )
        )
    )
  )
             

;casos de prueba de decode_simple

(decode_simple '(3 2 1 3 3))
(decode_simple '(0 2 1 3 3))
(decode_simple '(0 1 1 1 1))
(decode_simple '(4))


;casos de prueba de decode_cola

(decode_cola '(3 2 1 3 3))
(decode_cola '(0 2 1 3 3))
(decode_cola '(0 1 1 1 1))
(decode_cola '(4))




;3

;funcion sumatoria
;realiza la sumatoria de la formula de integral, utilizando recursion simple
;es usada solo por integrar_simple
;Parametros:
;a,b intervalo de la funcion
;n numero
;funcion lambda sobre la cual se quiere calcular la integral
;k numero util para la sumatoria
;Retorno:
;el resultado de la suma

(define (sumatoria a b n f k)
  (if (= k n) 0
      (+ (f (+ a (* k (/ (- b a) n)))) (sumatoria a b n f (+ k 1)))
      )
  )


;funcion integrar_simple
;calcula la proximacion de la integral escrita en el pdf de la tarea.
;la recursion simple se hace en la funcion sumatoria
;Parametros:
;a,b intervalo de la funcion
;n numero
;funcion lambda sobre la cual se quiere calcular la integral
;Retorno:
;el resultado de la suma junto con el resto del resultado de la formula

(define (integrar_simple a b n f)
  (* (/ (- b a) n) (+ (+ (/ (f a) 2) (/ (f b) 2)) (sumatoria a b n f 1)))
  )


;funcion integrar_cola
;calcula la misma formula anterior, utilizando recursion de cola,
;por lo  tanto no requiere una funcion auxiliar
;recibe los mismos parametros que integrar_simple y
;retorna el mismo resultado

(define (integrar_cola a b n f)
  (let aux ((a_aux a) (b_aux b) (n_aux n) (f_aux f) (k 1) (suma 0))
    (if (= k n) (*(+ (+ (/ (f a) 2) (/ (f b) 2)) suma) (/ (- b a) n))
        (aux a_aux b_aux n_aux f_aux (+ k 1) (+ suma (f (+ a (* k (/ (- b a) n))))))
    )
  )
  )
    

;casos de prueba de integrar_simple

(integrar_simple  0 1 4 (lambda (x) (* x x)))
(integrar_simple 1 10 100 (lambda (x) (/ (log x) (log 2))))


;casos de prueba de integrar_cola

(integrar_cola  0 1 4 (lambda (x) (* x x)))
(integrar_cola 1 10 100 (lambda (x) (/ (log x) (log 2))))




;4

;funcion map_arbol
;les aplica una funcion a todos los nodos de un arbol, dependiendo de un camino dado
;Parametros:
;arbol arbol sobre el cual se relizara map
;camino lista de ceros y unos la cual indica si se debe ir por el hijo derecho o izquierdo del arbol
;f funcion que se aplicara sobre los nodos
;Retorno:
;el arbol con los nodos modificados por f

;;---DETALLE IMPORTANTE---
;para el uso correcto de esta funcion se le debe repetir el ultimo numero
;por ejemplo, si el camino que sale en el pdf de la tarea es '(1), se le debe agregar otro 1 quedando '(1 1)
;otro ejemplo: '(1 0 0) pasa a ser '(1 0 0 0)
;si no se hace este cambio, no se le aplicara f a el ultimo numero del camino
;;------------------------

(define (map_arbol arbol camino f)
  (cond ((null? camino) arbol)
        ((= (car camino) 0) (list (f (car arbol))
                                  (map_arbol (cadr arbol) (cdr camino) f)
                                  (caddr arbol))
                            )
        ((= (car camino) 1) (list (f (car arbol))
                                  (cadr arbol)
                                  (map_arbol (caddr arbol) (cdr camino) f))
                            )
        )
  )



;casos de prueba de map_arbol
;los caminos estan modificados para obtener el mismo resultado que salen en los ejemplos

(map_arbol '(2 (3 () ()) (4 () ())) '(1 1) (lambda (x) (* x x)))
(map_arbol '(2 (3 () ()) (4 (3 (5 () ()) ()) (3 (8 () ()) ()))) '(1 0 0 0) (lambda (x) (* x x)))
(map_arbol '(2 (3 () ()) (4 (3 (5 () ()) ()) (3 (8 () ()) ()))) '(0 0) (lambda(x) (* x x)))
