package com.manaracekom.todo.boot

import com.manaracekom.todo.config.Configuration
import akka.actor.ActorSystem
import akka.actor.Props
import com.manaracekom.todo.rest.RestServiceActor
import akka.io.IO
import spray.can.Http

object Boot extends App with Configuration {
  
  implicit val system = ActorSystem("todo-scala-rest")
  
  val restService = system.actorOf(Props[RestServiceActor], "rest-endpoint")
  
  IO(Http) ! Http.Bind(restService, serviceHost, servicePort)
}