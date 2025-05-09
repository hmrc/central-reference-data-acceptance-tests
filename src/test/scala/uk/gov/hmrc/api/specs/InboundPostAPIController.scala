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
import uk.gov.hmrc.api.utils.InboundSoapMessage.{xmlFullMessage, xmlFullMessageErrorReport, xmlFullMessageIsAlive, xmlFullMessageIsAliveWithInvalidMessage}

import scala.xml.{Elem, XML}
import play.api.libs.ws.XMLBodyReadables.*
import uk.gov.hmrc.api.utils.OutboundSoapMessage.xmlOutboundResponseMessageIsAlive

class InboundPostAPIController extends BaseSpec, HttpClient:

  Feature("User can test the inbound post api public-soap-proxy") {
    Scenario("Central-reference-data-inbound-orchestrator endpoint works") {
      Given("The endpoint is accessed")
      val url            = s"$host"
      val body           = xmlFullMessage
      val result         = await(
        post(
          url,
          body,
          "content-type"     -> "application/xml",
          "x-files-included" -> "true"
        )
      )
      result.status shouldBe 202
      val id             = (XML.loadString(body) \\ "ReceiveReferenceDataRequestResult").text.trim
      val testOnlyUrl    = s"$testOnlyHost/message-wrappers/$id"
      val wrapper_status = await(
        get(
          testOnlyUrl
        )
      )
      wrapper_status.status        shouldBe 202
      wrapper_status.body.toString shouldBe "Received"
    }
    Scenario("Return Bad Request if the x-files-included header is not present") {
      Given("The endpoint is not accessed")
      val url            = s"$host"
      val body           = xmlFullMessage
      val result         = await(
        post(
          url,
          body,
          "content-type" -> "application/xml"
        )
      )
      result.status shouldBe 400
      val id             = (XML.loadString(body) \\ "ReceiveReferenceDataRequestResult").text.trim
      val testOnlyUrl    = s"$testOnlyHost/message-wrappers/$id"
      val wrapper_status = await(
        get(
          testOnlyUrl
        )
      )
      wrapper_status.status        shouldBe 404
      wrapper_status.body.toString shouldBe ""
    }

    Scenario("Central-reference-data-inbound-orchestrator endpoint works for ErrorReport Case") {
      Given("The endpoint is accessed")
      val url            = s"$host"
      val body           = xmlFullMessageErrorReport
      val result         = await(
        post(
          url,
          body,
          "content-type"     -> "application/xml",
          "x-files-included" -> "true"
        )
      )
      result.status shouldBe 202
      val id             = (XML.loadString(body) \\ "ErrorReport").text.trim
      val testOnlyUrl    = s"$testOnlyHost/message-wrappers/$id"
      val wrapper_status = await(
        get(
          testOnlyUrl
        )
      )
      wrapper_status.status        shouldBe 202
      wrapper_status.body.toString shouldBe "Received"
    }

    Scenario("isAlive Health Check works successfully") {
      Given("The endpoint is accessed")
      val url    = s"$host"
      val body   = xmlFullMessageIsAlive
      val result = await(
        post(
          url,
          body
        )
      )
      result.status     shouldBe 200
      result.body[Elem] shouldBe XML.loadString(xmlOutboundResponseMessageIsAlive)
    }

    Scenario("isAlive Health Check returns 400 with invalid message") {
      Given("The endpoint is accessed")
      val url    = s"$host"
      val body   = xmlFullMessageIsAliveWithInvalidMessage
      val result = await(
        post(
          url,
          body
        )
      )
      result.status shouldBe 400
    }

    Scenario("Return Bad Request if there is no XML content") {
      Given("The endpoint is not accessed")
      val url    = s"$host"
      val result = await(
        post(
          url,
          "",
          "x-files-included" -> "true",
          "content-type"     -> "application/xml"
        )
      )
      result.status shouldBe 400
    }
  }
