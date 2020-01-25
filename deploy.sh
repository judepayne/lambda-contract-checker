#!/bin/bash

cd ..
cd clj-contract-checker
lein install
cd ..
cd lambda-contract-checker

lein clean

lein uberjar

aws lambda update-function-code \
--function-name ContractChecker \
--zip-file fileb://target/lambda-contract-checker-0.1.0-standalone.jar
