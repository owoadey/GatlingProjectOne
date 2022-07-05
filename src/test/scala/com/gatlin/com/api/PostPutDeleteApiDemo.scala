package com.gatlin.com.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class PostPutDeleteApiDemo extends Simulation{

  //protocol

  //scenario
  val createUserScn = scenario(scenarioName = "create users")
    .exec(
      http(requestName = "create user request")
        .post("https://reqres.in/api/users")
        .header(name="content-type", value = "application/json")
        .asJson
        .body(RawFileBody(filePath = "data/user.json")).asJson
//        .body(StringBody(
//          """
//            |{
//            |    "name": "John",
//            |    "job": "leader"
//            |}
//            |""".stripMargin)).asJson
        .check(
          status.is(201),
          jsonPath(path = "$.name") is "Tony"
        )
    )
    .pause(duration=1)

  val updateUserScn = scenario(scenarioName = "Update User")
  .exec(
    http(requestName = "update user")
      .put("https://reqres.in/api/users/2")
      .body(RawFileBody("data/user.json")).asJson
      .check(
        status is 200,
        jsonPath(path = "$.name") is "Tony"
      )
  )
    .pause(duration = 1)

  val deleteUserScn = scenario(scenarioName = "Delete Users")
    .exec(
      http(requestName = "delete user req")
      .delete(url = "https://reqres.in/api/users/2")
        .check(status is 204)
    )
    .pause(1)

  //Setup
  setUp(
    createUserScn.inject(rampUsers(users = 10).during(5)),
    updateUserScn.inject(rampUsers(users = 5).during(3)),
    deleteUserScn.inject(rampUsers(users = 3).during(2))
  )
}
