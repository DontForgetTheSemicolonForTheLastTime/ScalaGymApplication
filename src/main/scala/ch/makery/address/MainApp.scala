package ch.makery.address
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import ch.makery.address.model.Person
import ch.makery.address.view.PersonEditDialogController
import scalafx.scene.image.Image
import scalafx.stage.{Stage, Modality}
import scalafx.event.ActionEvent

object MainApp extends JFXApp {
  import scalafx.beans.property.IntegerProperty



  // Class called from person.scala
  val personData = new ObservableBuffer[Person]()
  personData += new Person("Hans", 18093369)
  personData += new Person("Ruth", 1723349)
  personData += new Person("Heinz", 1721123)
  personData += new Person("Cornelia", 1823321)
  personData += new Person("Werner", 19022121)
  personData += new Person("Lydia", 18093321)
  personData += new Person("Anna", 17231123)
  personData += new Person("Stefan", 16201192)
  personData += new Person("Martin", 17022123)



  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML 
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  val cssResource = getClass.getResource("view/NewDesign.css")
  roots.stylesheets = List(cssResource.toExternalForm)

  // initialize stage
  stage = new PrimaryStage {
    title = "StudentDetailApp"
    icons += new Image(getClass.getResourceAsStream("view/images/book_image.png"))
    scene = new Scene {
      root = roots
    } 
  }
  // actions for display person overview window 
  def showPersonOverview() = {
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  } 

  //actions to edit details of object person
  def showPersonEditDialog(person: Person): Boolean = {
    val resource = getClass.getResourceAsStream("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController#Controller]
    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked
  } 



  // call to display PersonOverview when app start
  showPersonOverview()
}
