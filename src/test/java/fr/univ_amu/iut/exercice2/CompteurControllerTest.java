package fr.univ_amu.iut.exercice2;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Tests de l'exercice 2 - Compteur FXML.
 *
 * <p>Les clics sur les boutons sont réalisés via {@code button.fire()} dans {@code
 * robot.interact(...)} plutôt que {@code robot.clickOn(...)} pour éviter de prendre le contrôle
 * physique de la souris pendant les tests.
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompteurControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null); // évite la fuite de Scene entre tests (TestFX réutilise le Stage)
    new CompteurFXML().start(stage);
  }

  // --- Étape 1 : le label affiche 0 au démarrage ---

  @Test
  @Order(1)
  void le_compteur_affiche_zero_au_demarrage(FxRobot robot) {
    Label labelCompteur = robot.lookup("#labelCompteur").queryAs(Label.class);
    assertThat(labelCompteur)
        .as("un Label avec fx:id=\"labelCompteur\" doit être présent dans la vue FXML")
        .isNotNull();
    assertThat(labelCompteur.getText())
        .as("le label doit afficher \"0\" au démarrage (via binding sur compteur)")
        .isEqualTo("0");
  }

  // --- Étape 2 : le bouton + incrémente ---

  @Test
  @Order(2)
  void le_bouton_plus_est_present_et_libelle_avec_plus(FxRobot robot) {
    Button boutonPlus = robot.lookup("#boutonIncrementer").queryAs(Button.class);
    assertThat(boutonPlus)
        .as("un Button avec fx:id=\"boutonIncrementer\" doit être présent dans la vue FXML")
        .isNotNull();
    assertThat(boutonPlus.getText()).as("le bouton incrémenter doit afficher \"+\"").contains("+");
  }

  @Test
  @Order(3)
  void cliquer_sur_le_bouton_plus_incremente_le_compteur_de_un(FxRobot robot) {
    Button boutonPlus = robot.lookup("#boutonIncrementer").queryAs(Button.class);
    robot.interact(boutonPlus::fire);
    Label labelCompteur = robot.lookup("#labelCompteur").queryAs(Label.class);
    assertThat(labelCompteur.getText())
        .as("après un clic sur +, le label doit afficher \"1\"")
        .isEqualTo("1");
  }

  @Test
  @Order(4)
  void trois_clics_sur_plus_portent_le_compteur_a_trois(FxRobot robot) {
    Button boutonPlus = robot.lookup("#boutonIncrementer").queryAs(Button.class);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonPlus::fire);
    Label labelCompteur = robot.lookup("#labelCompteur").queryAs(Label.class);
    assertThat(labelCompteur.getText())
        .as("après 3 clics sur +, le label doit afficher \"3\"")
        .isEqualTo("3");
  }

  // --- Étape 3 : le bouton − décrémente ---

  @Test
  @Order(5)
  void cliquer_sur_le_bouton_moins_decremente_le_compteur_de_un(FxRobot robot) {
    Button boutonMoins = robot.lookup("#boutonDecrementer").queryAs(Button.class);
    robot.interact(boutonMoins::fire);
    Label labelCompteur = robot.lookup("#labelCompteur").queryAs(Label.class);
    assertThat(labelCompteur.getText())
        .as("après un clic sur −, le label doit afficher \"-1\"")
        .isEqualTo("-1");
  }

  @Test
  @Order(6)
  void deux_increments_puis_deux_decrements_ramenent_le_compteur_a_zero(FxRobot robot) {
    Button boutonPlus = robot.lookup("#boutonIncrementer").queryAs(Button.class);
    Button boutonMoins = robot.lookup("#boutonDecrementer").queryAs(Button.class);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonMoins::fire);
    robot.interact(boutonMoins::fire);
    Label labelCompteur = robot.lookup("#labelCompteur").queryAs(Label.class);
    assertThat(labelCompteur.getText()).as("+ + − − doit revenir à \"0\"").isEqualTo("0");
  }

  // --- Étape 4 : le bouton Réinitialiser ---

  @Test
  @Order(7)
  void cliquer_sur_reinitialiser_remet_le_compteur_a_zero(FxRobot robot) {
    Button boutonPlus = robot.lookup("#boutonIncrementer").queryAs(Button.class);
    Button boutonReinit = robot.lookup("#boutonReinitialiser").queryAs(Button.class);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonPlus::fire);
    robot.interact(boutonReinit::fire);
    Label labelCompteur = robot.lookup("#labelCompteur").queryAs(Label.class);
    assertThat(labelCompteur.getText())
        .as("après clic sur Réinitialiser, le label doit afficher \"0\"")
        .isEqualTo("0");
  }
}
