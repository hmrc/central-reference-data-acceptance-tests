package uk.gov.hmrc.api.utils

object InboundSdesMessage:
  val successJsonBody: String =
    """
      |{
      |"notification": "FileReceived",
      |"filename": "32f2c4f7-c635-45e0-bee2-0bdd97a4a70d.zip",
      |"checksumAlgorithm": "md5",
      |"checksum": "894bed34007114b82fa39e05197f9eec",
      |"correlationID": "32f2c4f7-c635-45e0-bee2-0bdd97a4a70d",
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

  val failureJsonBody: String =
    """{
      |"notification": "FileProcessingFailure",
      |"filename": "32f2c4f7-c635-45e0-bee2-0bdd97a4a70d.zip",
      |"checksumAlgorithm": "md5",
      |"checksum": "894bed34007114b82fa39e05197f9eec",
      |"correlationID": "32f2c4f7-c635-45e0-bee2-0bdd97a4a70d",
      |"dateTime": "2020-11-09T16:48:21.659Z",
      |"failureReason": "Virus Detected",
      |"actionRequired": "ADDRESS-FAILURE-THEN-RETRY"
      |}
      |""".stripMargin
