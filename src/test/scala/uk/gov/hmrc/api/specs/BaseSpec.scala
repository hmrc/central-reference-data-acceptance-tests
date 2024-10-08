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

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen
import uk.gov.hmrc.api.conf.TestConfiguration

import scala.concurrent.{Await, Awaitable, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.duration.*

trait BaseSpec extends AnyFeatureSpec, GivenWhenThen, Matchers:
  val host: String         = TestConfiguration.url("central-reference-data-inbound-orchestrator")
  val testOnlyHost: String = TestConfiguration.testOnlyUrl("central-reference-data-inbound-orchestrator")

  def await[T](f: Awaitable[T], timeout: Duration = 10.seconds): T =
    Await.result(f, timeout)
