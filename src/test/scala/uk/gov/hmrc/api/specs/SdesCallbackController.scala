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

package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.utils.InboundSdesMessage.*
import uk.gov.hmrc.api.utils.InboundSoapMessage.*

import java.util.UUID

class SdesCallbackController extends BaseSpec, HttpClient:
  Feature("User can test Inbound POST API for SDES notification") {
    Scenario("Inbound POST API handles successful SDES notification") {
      Given("The endpoint is accessed")
      val id             = UUID.randomUUID().toString
      val _              = await(
        post(
          host,
          xmlFullMessageFromID(id),
          "content-type"     -> "application/xml",
          "x-files-included" -> "true"
        )
      )
      val url            = s"$host/services/crdl/callback"
      val result         = await(
        post(
          url,
          successJsonBodyFromString(id),
          "content-type" -> "application/json"
        )
      )
      result.status shouldBe 202
      // The code being run is asynchronous,we need to sleep to make sure that it has finished processing.otherwise we do not see anything different when we check the state.
      Thread.sleep(5500)
      val testOnlyUrl    = s"$testOnlyHost/message-wrappers/$id"
      val wrapper_status = await(
        get(
          testOnlyUrl
        )
      )
      wrapper_status.status        shouldBe 202
      wrapper_status.body.toString shouldBe "ScanPassed"
    }

    Scenario("Inbound POST API handles sent SDES notification") {
      Given("The endpoint is accessed")
      val id             = UUID.randomUUID().toString
      val _              = await(
        post(
          host,
          xmlFullMessageFromID(id),
          "content-type"     -> "application/xml",
          "x-files-included" -> "true"
        )
      )
      val url            = s"$host/services/crdl/callback"
      val result         = await(
        post(
          url,
          fileProcessedJsonBodyFromString(id),
          "content-type" -> "application/json"
        )
      )
      result.status shouldBe 202
      // The code being run is asynchronous,we need to sleep to make sure that it has finished processing.otherwise we do not see anything different when we check the state.
      Thread.sleep(5500)
      val testOnlyUrl    = s"$testOnlyHost/message-wrappers/$id"
      val wrapper_status = await(
        get(
          testOnlyUrl
        )
      )
      wrapper_status.status        shouldBe 202
      wrapper_status.body.toString shouldBe "Sent"
    }

    Scenario("Inbound POST API handles failure SDES notification") {
      Given("The endpoint is accessed")
      val id             = UUID.randomUUID().toString
      val _              = await(
        post(
          host,
          xmlFullMessageFromID(id),
          "content-type"     -> "application/xml",
          "x-files-included" -> "true"
        )
      )
      val url            = s"$host/services/crdl/callback"
      val body           = failureJsonBodyFromString(id)
      val result         = await(
        post(
          url,
          body,
          "content-type" -> "application/json"
        )
      )
      result.status shouldBe 202
      val testOnlyUrl    = s"$testOnlyHost/message-wrappers/$id"
      val wrapper_status = await(
        get(
          testOnlyUrl
        )
      )
      wrapper_status.status        shouldBe 202
      wrapper_status.body.toString shouldBe "ScanFailed"
    }
  }
