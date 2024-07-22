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

import scala.concurrent.Await
import scala.concurrent.duration._

class HelloWorld extends BaseSpec with HttpClient {

  Feature("User can test the template service") {
    Scenario("hello world endpoint works") {
      Given("the endpoint is accessed")
      val result =
        Await.result(get("http://localhost:7250/central-reference-data-inbound-orchestrator/hello-world"), 10.seconds)
      result.body shouldBe "Hello world"
    }
  }
}
