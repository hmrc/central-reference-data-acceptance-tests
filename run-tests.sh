#!/usr/bin/env bash

ENVIRONMENT=$1

sbt clean -Denvironment="${ENVIRONMENT:=local}" -Dsecurity.assessment=false "testOnly uk.gov.hmrc.api.specs.*"
