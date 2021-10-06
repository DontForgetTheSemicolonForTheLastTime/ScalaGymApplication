package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate;
import scalafx.collections.ObservableBuffer

case class Person ( firstNameS : String, studentID : Int ){
    
  var firstName  = new StringProperty(firstNameS)
  var programme   = new StringProperty("Enter Programme Name")
  var studentId  = ObjectProperty[Int](studentID)
  var street     = new StringProperty("Enter Street Name")
  //var postalCode = IntegerProperty(1234)
  var postalCode = ObjectProperty[Int](1234)
  var city       = new StringProperty("Enter your city Name")
  var date       = ObjectProperty[LocalDate](LocalDate.of(1999, 2, 21))
}

