package com.manaracekom.todo.model

import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.ShortTypeHints

trait Note
  
case class ToDoNote(itemName: String, completed: Boolean) extends Note

object Note {
  val notes = List[Note](
    ToDoNote(itemName = "Buy milk", completed = false),
    ToDoNote(itemName = "Buy eggs", completed = false),
    ToDoNote(itemName = "Read a book", completed = false)
  )
  
  private implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Note])))
  def toJson(notes: List[Note]): String = writePretty(notes)
  def toJson(note: Note): String = writePretty(note)
}