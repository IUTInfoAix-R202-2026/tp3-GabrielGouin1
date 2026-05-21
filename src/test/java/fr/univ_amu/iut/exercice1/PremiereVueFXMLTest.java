package fr.univ_amu.iut.exercice1;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/** Tests de l'exercice 1 - PremiereVueFXML. */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PremiereVueFXMLTest {

  private Stage stage;

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null); // évite la fuite de Scene entre tests (TestFX réutilise le Stage)
    this.stage = stage;
    new PremiereVueFXML().start(stage);
  }

  // --- Étape 1 : la fenêtre est affichée ---

  @Test
  @Order(1)
  void la_fenetre_de_la_vue_fxml_est_visible(FxRobot robot) {
    assertThat(stage.isShowing())
        .as("le Stage doit être affiché - appeler show() à la fin de start()")
        .isTrue();
  }

  @Test
  @Order(2)
  void la_scene_du_stage_contient_la_racine_fxml(FxRobot robot) {
    assertThat(stage.getScene())
        .as("le Stage doit avoir une Scene attachée via setScene()")
        .isNotNull();
  }

  // --- Étape 2 : la racine provient bien du FXML ---

  @Test
  @Order(3)
  void la_racine_de_la_vue_fxml_est_un_borderpane(FxRobot robot) {
    assertThat(stage.getScene().getRoot())
        .as("la racine chargée depuis PremiereVueFXML.fxml doit être un BorderPane")
        .isInstanceOf(BorderPane.class);
  }

  @Test
  @Order(4)
  void le_stage_porte_le_titre_attendu(FxRobot robot) {
    assertThat(stage.getTitle())
        .as("le titre du Stage doit être défini via primaryStage.setTitle(...)")
        .isEqualTo("Première vue FXML");
  }

  // --- Étape 3 : les composants du FXML sont bien présents ---

  @Test
  @Order(5)
  void le_label_de_bienvenue_est_affiche_dans_la_vue(FxRobot robot) {
    Label titre =
        (Label)
            robot
                .lookup(
                    node ->
                        node instanceof Label labelNode
                            && labelNode.getText() != null
                            && labelNode.getText().contains("Bienvenue"))
                .queryAll()
                .stream()
                .findFirst()
                .orElse(null);

    assertThat(titre)
        .as("un Label contenant 'Bienvenue' doit être présent dans la vue FXML")
        .isNotNull();
  }

  @Test
  @Order(6)
  void le_bouton_est_affiche_dans_la_vue(FxRobot robot) {
    Button bouton = robot.lookup(".button").queryButton();

    assertThat(bouton).as("un Button doit être présent dans la vue FXML (zone bottom)").isNotNull();
    assertThat(bouton.getText()).as("le texte du bouton doit être non vide").isNotEmpty();
  }
}
