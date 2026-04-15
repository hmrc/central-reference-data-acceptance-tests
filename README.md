# central-reference-data-acceptance-tests

Acceptance tests for the CRDL (Central Reference Data Library) API, written in Scala 3 using the HMRC api-test-runner framework.

## Pre-requisites
Start Mongo Docker container as follows:

Follow the Developer setup instructions in the MDTP Handbook for [MongoDB](https://docs.tax.service.gov.uk/mdtp-handbook/documentation/developer-set-up/set-up-mongodb.html).
Make sure you have MongoDB 7.x or later.

### Services

Start a MongoDB Docker container:

```bash
docker run --rm -d -p 27017:27017 --name mongo percona/percona-server-mongodb:5.0
```

Start all CRDL services via Service Manager 2:

```bash
sm2 --start CRDL_ALL
```

## Tests

### Running Tests

The `<environment>` argument must be one of: `local`, `dev`, `qa`, or `staging`. Defaults to `local` if omitted.

```bash
./run-tests.sh <environment>
```

### Test Coverage

#### Inbound POST API (`InboundPostAPIController`)

Tests for the `central-reference-data-inbound-orchestrator` endpoint:

| Scenario | Description |
|---|---|
| Reference data submission | Posts XML reference data with `x-files-included` header, expects `202 Accepted`, verifies message wrapper status via test-only endpoint |
| Subscription change request | Posts a subscription change request message and verifies correct processing |
| Subscription error report | Posts a subscription error report message and verifies correct processing |
| Error report | Posts an error report message and verifies correct processing |
| isAlive health check | Posts an isAlive message, expects `200 OK` with a valid XML response |
| isAlive invalid message | Posts a malformed isAlive message, expects `400 Bad Request` |
| Missing header validation | Omits the `x-files-included` header, expects `400 Bad Request` |
| Empty XML validation | Posts an empty XML body, expects `400 Bad Request` |

#### SDES Callback (`SdesCallbackController`)

Tests for SDES (Secure Data Exchange Service) callback notifications:

| Scenario | Description |
|---|---|
| File received | `FileReceived` notification for reference data; verifies message state transitions to `ScanPassed` |
| File processed | `FileProcessed` notification for reference data; verifies message state transitions to `Sent` |
| File scan failed | `FileProcessingFailure` notification for reference data; verifies message state transitions to `ScanFailed` |
| Error report — file received | `FileReceived` notification for error report messages |
| Error report — file processed | `FileProcessed` notification for error report messages |
| Subscription error report — file received | `FileReceived` notification for subscription error report messages |
| Subscription error report — file processed | `FileProcessed` notification for subscription error report messages |
| Subscription error report — scan failed | `FileProcessingFailure` notification for subscription error report messages |

## Scalafmt

Check all project files are formatted as expected:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files:

```bash
sbt scalafmtSbt
```

Format all project files:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
