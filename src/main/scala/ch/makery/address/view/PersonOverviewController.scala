package ch.makery.address.view

import ch.makery.address.model.Person
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty} 
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType

@sfxml
class PersonOverviewController(
  
    private val personTable : TableView[Person],
      
    private val firstNameColumn : TableColumn[Person, String],
  
    private val studentIdColumn : TableColumn[Person, Int],

    private val firstNameLabel : Label,
    
    private val programmeLabel : Label,

    private val studentIdLabel : Label,
  
    private val streetLabel : Label,

    private val postalCodeLabel : Label,

    private val cityLabel :  Label,

    private val  birthdayLabel : Label  
    
    ) {
  // initialize Table View display contents model
  personTable.items = MainApp.personData 
  // initialize columns's cell values
  firstNameColumn.cellValueFactory = {_.value.firstName}
  studentIdColumn.cellValueFactory  = {_.value.studentId} 


  //Binds the person details to the text field to be shown in the display.
  private def showPersonDetails (person : Option[Person]) = {
    person match {
      case Some(person) =>
      firstNameLabel.text <== person.firstName
      programmeLabel.text  <== person.programme
      studentIdLabel.text   = person.studentId.value.toString
      streetLabel.text    <== person.street
      cityLabel.text      <== person.city;
      postalCodeLabel.text = person.postalCode.value.toString
      import ch.makery.address.util.DateUtil._
      
      birthdayLabel.text  = person.date.value.asString


      case None =>
        // Person is null, remove all the text.
      firstNameLabel.text.unbind()
      programmeLabel.text.unbind()
      studentIdLabel.text.unbind()
      streetLabel.text.unbind()
      cityLabel.text.unbind()
      postalCodeLabel.text.unbind()
      firstNameLabel.text = ""
      programmeLabel.text  = ""
      streetLabel.text    = ""
      postalCodeLabel.text= ""
      cityLabel.text      = ""
      birthdayLabel.text  = ""
    }  
  }
  
  showPersonDetails(None);
  
  personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue))
  )
  /**
 * Called when the user clicks on the delete button.
 */
  def handleDeletePerson(action : ActionEvent) = {
    val selectedIndex = personTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) {
        personTable.items().remove(selectedIndex);
    }else {
        // Nothing selected.
        new Alert(AlertType.Warning){
          initOwner(MainApp.stage)
          title       = "No Selection"
          headerText  = "No Person Selected"
          contentText = "Please select a person in the table."
        }.showAndWait()
    } 
  }
  //Called when user clicks new person button
  def handleNewPerson(action : ActionEvent) = {
    val person = new Person("", 1234)
    val okClicked = MainApp.showPersonEditDialog(person);
        if (okClicked) {
            MainApp.personData += person
        }
  }

  //Called when user clicks edit person button
  def handleEditPerson(action : ActionEvent) = {
    val selectedPerson = personTable.selectionModel().selectedItem.value
    if (selectedPerson != null) {
        val okClicked = MainApp.showPersonEditDialog(selectedPerson)

        if (okClicked) showPersonDetails(Some(selectedPerson))

    } else {
        // Nothing selected.
        val alert = new Alert(Alert.AlertType.Warning){
          initOwner(MainApp.stage)
          title       = "No Selection"
          headerText  = "No Person Selected"
          contentText = "Please select a person in the table."
        }.showAndWait()
    }
  }
    
}



