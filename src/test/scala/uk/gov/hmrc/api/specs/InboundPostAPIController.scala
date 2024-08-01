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
import play.api.libs.ws.DefaultBodyReadables.readableAsString

class InboundPostAPIController extends BaseSpec, HttpClient:

  Feature("User can test the inbound post api public-soap-proxy") {
    Scenario("Central-reference-data-inbound-orchestrator endpoint works") {
      Given("The endpoint is accessed")
      val url    = s"$host"
      val result = await(
        post(
          url,
          "<body/>",
          "content-type"     -> "application/xml",
          "x-files-included" -> "true"
        )
      )
      result.status shouldBe 202
    }
    Scenario("Return Bad Request if the x-files-included header is not present") {
      Given("The endpoint is not accessed")
      val url    = s"$host"
      val result = await(
        post(
          url,
          "<body/>",
          "content-type" -> "application/xml"
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
