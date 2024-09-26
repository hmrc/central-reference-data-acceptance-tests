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

object InboundSoapMessage {
  def xmlBody: String                         = xmlBodyFromID(UUID.randomUUID().toString)
  def xmlBodyFromID(identify: String): String =
    s"""<MainMessage>
       |      <Body>
       |        <TaskIdentifier>780912</TaskIdentifier>
       |        <AttributeName>ReferenceData</AttributeName>
       |        <MessageType>gZip</MessageType>
       |        <IncludedBinaryObject>$identify</IncludedBinaryObject>
       |        <MessageSender>CS/RD2</MessageSender>
       |      </Body>
       |    </MainMessage>""".stripMargin

  def xmlFullMessage: String                         = xmlFullMessageFromID(UUID.randomUUID().toString)
  def xmlFullMessageFromID(identify: String): String =
    s"""<S:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope" xmlns:S="http://www.w3.org/2003/05/soap-envelope">
       |      <S:Header>
       |        <Action xmlns="http://www.w3.org/2005/08/addressing">CCN2.Service.Customs.Default.CSRD.ReferenceDataSubmissionResultReceiverCBS/ReceiveReferenceDataSubmissionResult</Action>
       |      </S:Header>
       |      <S:Body>
       |        <ReceiveReferenceDataSubmissionResult>
       |          <MessageHeader>
       |            <messageID>testMessageId123</messageID>
       |            <messageName>test message name</messageName>
       |            <sender>CS/RD2</sender>
       |            <recipient>DPS</recipient>
       |            <timeCreation>2023-10-03T16:00:00</timeCreation>
       |          </MessageHeader>
       |          <TaskIdentifier>TASKID12345</TaskIdentifier>
       |          <IncludedBinaryObject>$identify</IncludedBinaryObject>
       |        </ReceiveReferenceDataSubmissionResult>
       |      </S:Body>
       |    </S:Envelope>""".stripMargin
}
