package ch.makery.address.view

import ch.makery.address.model.Person
import ch.makery.address.MainApp
import scalafx.scene.control.{TextField, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import ch.makery.address.util.DateUtil._
import scalafx.event.ActionEvent

@sfxml
class PersonEditDialogController (

    private val  firstNameField : TextField,
    private val   programmeField : TextField,
    private val   studentIdField: TextField,
    private val     streetField : TextField,
    private val postalCodeField : TextField,
    private val       cityField : TextField,
    private val   birthdayField : TextField

){
  var         dialogStage : Stage  = null
  private var _person     : Person = null
  var         okClicked            = false

  def person = _person
  def person_=(x : Person) {
        _person = x

        firstNameField.text = _person.firstName.value
        programmeField.text  = _person.programme.value
        studentIdField.text = _person.studentId.value.toString
        streetField.text    = _person.street.value
        postalCodeField.text= _person.postalCode.value.toString
        cityField.text      = _person.city.value
        birthdayField.text  = _person.date.value.asString
        birthdayField.setPromptText("dd.mm.yyyy");
  }

  //Called when user clicks the ok button 
  def handleOk(action :ActionEvent){

     if (isInputValid()) {
        _person.firstName.value = firstNameField.text()
        _person.programme.value = programmeField.text()
        _person.studentId.value = studentIdField.text().toInt
        _person.street.value = streetField.text()
        _person.city.value = cityField.text()
        _person.postalCode.value = postalCodeField.text().toInt
        _person.date.value       = birthdayField.text().parseLocalDate;

        okClicked = true;
        dialogStage.close()
    }
  }

  //Called when user clicks the cancel button 
  def handleCancel(action :ActionEvent) {
        dialogStage.close();
  }
  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(firstNameField.text.value))
      errorMessage += "No valid first name!\n"
    if (nullChecking(programmeField.text.value))
      errorMessage += "No valid last name!\n"    
    if (nullChecking(studentIdField.text.value))
      errorMessage += "No valid student Id!\n"
    if (nullChecking(streetField.text.value))
      errorMessage += "No valid street!\n"
    if (nullChecking(postalCodeField.text.value))
      errorMessage += "No valid postal code!\n"
    else {
      try {
        Integer.parseInt(postalCodeField.getText());
        Integer.parseInt(studentIdField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "No valid postal code or student Id (must be an integer)!\n"
      }
    }
    if (nullChecking(cityField.text.value))
      errorMessage += "No valid city!\n"
    if (nullChecking(birthdayField.text.value))
      errorMessage += "No valid birtday!\n"
    else {
      if (!birthdayField.text.value.isValid) {
          errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
      }
    }

    if (errorMessage.length() == 0) {
        return true;
    } else {
        // Show the error message.
        val alert = new Alert(Alert.AlertType.Error){
          initOwner(dialogStage)
          title = "Invalid Fields"
          headerText = "Please correct invalid fields"
          contentText = errorMessage
        }.showAndWait()

        return false;
    }
   }
}