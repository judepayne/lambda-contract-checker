(ns clojurehandler.plain_clojure_handler
  (:require [clojure.data.json      :as json]
            [contract-checker.core  :as cc]
            [clojure.java.io        :as io]
            [contract-checker.rules :as rules]))


(defn conform-contract
  [js]
  (let [in (json/read-str js :key-fn keyword)
        consumer (:consumer in)
        producer (:producer in)]
    (if (and consumer producer)
      (json/write-str {:errors (cc/check-contract (:consumer in) (:producer in) :rules rules/rules)})
      (json/write-str {:errors "You must specify both a consumer and producer contract!"}))))


;; gen-class and how to use it

;; 1. Compiling
;; lein-clean will remove the target/ directory

;; a) cider-jack-in will compile eveything
;; (probably if :aot :all is set in defproject)
;;
;; b) from REPL, call (compile 'this-is.my-namespace)
;; but ensure that the target/classes/ dir exists already
;;
;; c) lein compile will also do it, if :aot :all is set
;;

;; 2. Importing
;; Now you have the classes but they're not yet in your namespace
;; They have to be imported!
;;
;; Ususally the best way is to quit emacs, do a lein clean & lein compile
;; (with :aot :all set) and then in a new emacs/ fresh repl
;; (import clojurehandler.hello)
;; then it will be possible to do: (with the example code below)
;; (def a (hello.))
;; (.toString a) => "I'm a B!"
;; (.showMessage "Jude") => "Jude"
;;
;; TODO look for ways to do this without restarting emacs
;;
;;
;; 3. Generating (using gen-class)
;;
;; Overridden methods (e.g. toString) can just be defined, but custom
;; methods must be declared and then defined as shown below.
;; 
;;
;; 4. Inspecting/ debugging
;; in the repl:
;; (use 'clojure.reflect 'clojure.pprint)
;; (pprint (reflect clojurehandler.hello))
;; You should see your classes there - or something has gone wrong!

(import com.amazonaws.services.lambda.runtime.Context)

(gen-class
 :name clojurehandler.Handler
 :prefix pch-
 :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler])


(defn pch-handleRequest [this input output ctx]
  (let [out (-> (slurp input)
                 conform-contract)]
    (with-open [o (io/writer output)]
      (.write o out))))

