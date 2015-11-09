package com.manaracekom.todo.rest

import akka.actor.Actor
import spray.routing.HttpService
import spray.http.MediaTypes
import com.manaracekom.todo.model.Note
import com.manaracekom.todo.model.ToDoNote

class RestServiceActor extends Actor with RestService {
  implicit def actorRefFactory = context

  def receive = runRoute(rest)
}

trait RestService extends HttpService {
  implicit val executionContext = actorRefFactory.dispatcher
  
  var notes = Note.notes
  
  val rest = respondWithMediaType(MediaTypes.`application/json`) {
    path("hello") {
      complete {
        "Hello World!"
      }
    } ~
    get {
      path("note" / "all") {
        complete {
          Note.toJson(notes)
        }
      }
    } ~
    post {
      path("note" / "add") {
        parameters("itemName") { (itemName) => 
          val newNote = ToDoNote(
            itemName,
            completed = false)
          
          notes = newNote :: notes
          
          complete {
            "OK"
          }
        }
      }
    } ~
    delete {
      path("note" / "delete" / IntNumber) { index =>
        notes = notes.drop(index)
        
        complete {
          "OK"
        }
      }
    }
  }
}