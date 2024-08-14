package uk.gov.hmrc.api.utils

import java.util.UUID

object InboundSoapMessage {
  val xmlBody: String =
    s"""<MainMessage>
       |      <Body>
       |        <TaskIdentifier>780912</TaskIdentifier>
       |        <AttributeName>ReferenceData</AttributeName>
       |        <MessageType>gZip</MessageType>
       |        <IncludedBinaryObject>${UUID.randomUUID()}</IncludedBinaryObject>
       |        <MessageSender>CS/RD2</MessageSender>
       |      </Body>
       |    </MainMessage>""".stripMargin
}
