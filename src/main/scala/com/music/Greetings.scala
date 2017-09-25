package com.music

import javax.inject.Named

import com.google.api.server.spi.config.{Api, ApiMethod}
import com.google.api.server.spi.response.NotFoundException
import com.google.appengine.api.users.User


@Api(
  name = "helloworldScala",
  version = "v1",
  scopes = Array("https://www.googleapis.com/auth/userinfo.email"),
  clientIds = Array(""),
  audiences = Array(""))
class Greetings {
  val greetings = Array(
    new HelloGreeting("hello world!"),
    new HelloGreeting("goodbye world!")
  )


  @throws[NotFoundException]
  def getGreeting(@Named("id") id: Integer): HelloGreeting = try {
    greetings(id)
  }
  catch {
    case e: IndexOutOfBoundsException =>
      throw new NotFoundException("Greeting not found with an index: " + id)
  }

  def listGreeting: Array[HelloGreeting] = greetings

  @ApiMethod(name = "greetings.multiply", httpMethod = "post")
  def insertGreeting(@Named("times") times: Integer, greeting: HelloGreeting): HelloGreeting = {
    val responseBuilder = new StringBuilder
    var i = 0
    while ( {
      i < times
    }) {
      responseBuilder.append(greeting.message)

      {
        i += 1; i - 1
      }
    }
  new HelloGreeting(responseBuilder.toString)
  }

  @ApiMethod(name = "greetings.authed", path = "hellogreeting/authed") def authedGreeting(user: User): HelloGreeting = {
    val response = new HelloGreeting("hello " + user.getEmail)
    response
  }
}
