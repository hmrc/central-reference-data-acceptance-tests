package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.utils.InboundSdesMessage.*

class SdesCallbackController extends BaseSpec, HttpClient:
  Feature("Inbound POST API handles successful SDES notification") {
    Scenario("Central-reference-data-inbound-orchestrator endpoint works") {
      Given("The endpoint is accessed")
      val url    = s"$host/services/crdl/callback"
      val result = await(
        post(
          url,
          successJsonBody,
          "content-type" -> "application/json"
        )
      )
      result.status shouldBe 202
    }
  }

  Feature("Inbound POST API handles failure SDES notification") {
    Scenario("Central-reference-data-inbound-orchestrator endpoint fails") {
      Given("The endpoint is accessed")
      val url    = s"$host/services/crdl/callback"
      val result = await(
        post(
          url,
          failureJsonBody,
          "content-type" -> "application/json"
        )
      )
      result.status shouldBe 202
    }
  }
