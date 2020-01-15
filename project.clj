(defproject lambda-contract-checker "0.1.0"
  :description "AWS Lambda wrapper for clj-contract-checker."
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [org.clojure/data.json "0.2.7"]
                 [contract-checker "0.1.0"]]
  :aot :all)
