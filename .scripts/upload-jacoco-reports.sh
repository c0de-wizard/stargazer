#!/bin/bash

featureDir=../features

for module in "$featureDir"/*
do
  for jacocoReportsDir in "$module/build/reports/jacoco/jacocoTestReport"/*
  do
    bash <(curl -s https://codecov.io/bash) "$jacocoReportsDir"
  done
done
