#!/usr/bin/env bash

ENVIRONMENT=$1

sbt clean -Denvironment="${ENVIRONMENT:=local}" -Dzap.proxy=true -Dsecurity.assessment=true "testOnly uk.gov.hmrc.api.specs.*"