package com.gatlin.com

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FeederDemo extends Simulation{

  //Protocol
  val httpProtocol = http.baseUrl("https://computer-database.gatling.io/")

  //Scenario
  val feeder = csv(fileName = "data/data1.csv").circular

  val scn = scenario(scenarioName = "Feeders Demo")
    .repeat(1){
      feed(feeder)
        .exec { session =>
          println("Name: "+session("name").as[String])
          println("Job: "+session("job").as[String])
          session
        }
        .pause(1)
      .exec(http(requestName = "Goto Home Page")
        .get("/computers")
      )

    }

  //Setup
  setUp(scn.inject(atOnceUsers(users = 3)).protocols(httpProtocol))

}
