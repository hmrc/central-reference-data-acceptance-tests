/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.utils

import java.util.UUID

object InboundSdesMessage:
  def successJsonBody: String                             = successJsonBodyFromString(UUID.randomUUID().toString)
  def successJsonBodyFromString(identify: String): String =
    s"""
      |{
      |"notification": "FileReceived",
      |"filename": "32f2c4f7-c635-45e0-bee2-0bdd97a4a70d.zip",
      |"checksumAlgorithm": "md5",
      |"checksum": "894bed34007114b82fa39e05197f9eec",
      |"correlationID": "$identify",
      |"dateTime": "2020-11-09T16:48:21.659Z",
      |"properties": [
      |{
      |"name": "name1",
      |"value": "value1"
      |},
      |{
      |"name": "name2",
      |"value": "value2"
      |}
      |]
      |}
      |""".stripMargin

  def failureJsonBody: String                             = failureJsonBodyFromString(UUID.randomUUID().toString)
  def failureJsonBodyFromString(identify: String): String =
    s"""{
      |"notification": "FileProcessingFailure",
      |"filename": "32f2c4f7-c635-45e0-bee2-0bdd97a4a70d.zip",
      |"checksumAlgorithm": "md5",
      |"checksum": "894bed34007114b82fa39e05197f9eec",
      |"correlationID": "$identify",
      |"dateTime": "2020-11-09T16:48:21.659Z",
      |"failureReason": "Virus Detected",
      |"actionRequired": "ADDRESS-FAILURE-THEN-RETRY"
      |}
      |""".stripMargin
