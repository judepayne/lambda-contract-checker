#!/bin/bash

lein clean

lein uberjar

aws lambda update-function-code \
--function-name DrawGraph \
--zip-file fileb://target/lambda-draw-graph-0.1.0-standalone.jar


aws lambda update-function-code \
--function-name DrawGraph \
--region us-east-1 \
--zip-file fileb://target/lambda-draw-graph-0.1.0-standalone.jar
