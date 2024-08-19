package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.utils.InboundSdesMessage.*

class SdesCallbackController extends BaseSpec, HttpClient:
  Feature("User can test Inbound POST API for SDES notification") {
    Scenario("Inbound POST API handles successful SDES notification") {
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

    Scenario("Inbound POST API handles failure SDES notification") {
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
