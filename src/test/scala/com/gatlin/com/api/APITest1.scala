package com.gatlin.com.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class APITest1 extends Simulation{

  //protocol
  val httpProtocol = http
    .baseUrl("https://reqres.in/api/users")

  //scenario
  val scn = scenario(scenarioName = "Get API Request Demo")
    .exec(
      http(requestName = "Get Single User")
        .get("/2")
        .check(
          status.is(200),
          jsonPath(path = "$.data.first_name").is("Janet")
        )
    )
    .pause(duration=1)

  //setUp
  setUp(
    scn.inject(rampUsers(users = 10).during(5))
      .protocols(httpProtocol)
  )
}
