package fr.univ_amu.iut.exercice3;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
 * Tests de l'exercice 3 - Formulaire de connexion en FXML.
 *
 * <p>On vérifie : la présence des contrôles avec les bons {@code fx:id}, l'état initial des boutons
 * (OK désactivé, Annuler désactivé), les bindings de validation (mot de passe non éditable tant que
 * l'identifiant est trop court, OK activé seulement si le mot de passe est valide), et les actions
 * des deux actions de boutons.
 *
 * <p>Les saisies sont faites via {@code robot.interact(() -> field.setText(...))} pour ne pas
 * prendre le contrôle du clavier physique pendant les tests. Les clics utilisent {@code
 * button.fire()} pour la même raison.
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FormulaireConnexionControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null); // évite la fuite de Scene entre tests
    new FormulaireConnexionFXML().start(stage);
  }

  // --- Étape 1 : présence des contrôles ---

  @Test
  @Order(1)
  void les_champs_identifiant_et_mot_de_passe_sont_presents(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    assertThat(identifiant)
        .as("un TextField avec fx:id=\"champIdentifiant\" doit être présent dans le FXML")
        .isNotNull();
    assertThat(motDePasse)
        .as("un PasswordField avec fx:id=\"champMotDePasse\" doit être présent dans le FXML")
        .isNotNull();
  }

  @Test
  @Order(2)
  void les_boutons_ok_et_annuler_sont_presents(FxRobot robot) {
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    Button annuler = robot.lookup("#boutonAnnuler").queryAs(Button.class);
    assertThat(ok)
        .as("un Button avec fx:id=\"boutonOk\" doit être présent dans le FXML")
        .isNotNull();
    assertThat(annuler)
        .as("un Button avec fx:id=\"boutonAnnuler\" doit être présent dans le FXML")
        .isNotNull();
  }

  // --- Étape 2 : état initial ---

  @Test
  @Order(3)
  void le_mot_de_passe_est_non_editable_au_demarrage(FxRobot robot) {
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    assertThat(motDePasse.isEditable())
        .as(
            "au démarrage l'identifiant est vide, donc le champ mot de passe ne doit pas être"
                + " éditable")
        .isFalse();
  }

  @Test
  @Order(4)
  void le_bouton_ok_est_desactive_au_demarrage(FxRobot robot) {
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    assertThat(ok.isDisabled())
        .as("le bouton OK doit être désactivé au démarrage (mot de passe vide)")
        .isTrue();
  }

  @Test
  @Order(5)
  void le_bouton_annuler_est_desactive_si_les_deux_champs_sont_vides(FxRobot robot) {
    Button annuler = robot.lookup("#boutonAnnuler").queryAs(Button.class);
    assertThat(annuler.isDisabled())
        .as("le bouton Annuler doit être désactivé quand les deux champs sont vides")
        .isTrue();
  }

  // --- Étape 3 : binding sur l'éditabilité du mot de passe ---

  @Test
  @Order(6)
  void le_mot_de_passe_devient_editable_quand_l_identifiant_atteint_six_caracteres(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    robot.interact(() -> identifiant.setText("alice"));
    assertThat(motDePasse.isEditable())
        .as("avec 5 caractères dans l'identifiant, le mot de passe ne doit pas être éditable")
        .isFalse();
    robot.interact(() -> identifiant.setText("alice1"));
    assertThat(motDePasse.isEditable())
        .as("avec 6 caractères dans l'identifiant, le mot de passe doit devenir éditable")
        .isTrue();
  }

  // --- Étape 4 : binding sur la validité du mot de passe ---

  @Test
  @Order(7)
  void le_bouton_ok_reste_desactive_si_le_mot_de_passe_est_trop_court(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    robot.interact(() -> identifiant.setText("alice1"));
    robot.interact(() -> motDePasse.setText("Ab1"));
    assertThat(ok.isDisabled())
        .as("avec un mot de passe < 8 caractères, OK doit rester désactivé")
        .isTrue();
  }

  @Test
  @Order(8)
  void le_bouton_ok_reste_desactive_sans_majuscule_dans_le_mot_de_passe(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    robot.interact(() -> identifiant.setText("alice1"));
    robot.interact(() -> motDePasse.setText("abcdef12"));
    assertThat(ok.isDisabled())
        .as("sans majuscule, OK doit rester désactivé même si le mot de passe fait 8 caractères")
        .isTrue();
  }

  @Test
  @Order(9)
  void le_bouton_ok_reste_desactive_sans_chiffre_dans_le_mot_de_passe(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    robot.interact(() -> identifiant.setText("alice1"));
    robot.interact(() -> motDePasse.setText("AbcdefGh"));
    assertThat(ok.isDisabled())
        .as(
            "sans chiffre, OK doit rester désactivé même si le mot de passe est long et a une"
                + " majuscule")
        .isTrue();
  }

  @Test
  @Order(10)
  void le_bouton_ok_s_active_quand_le_mot_de_passe_respecte_les_trois_regles(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    robot.interact(() -> identifiant.setText("alice1"));
    robot.interact(() -> motDePasse.setText("Abcdef12"));
    assertThat(ok.isDisabled())
        .as("avec un mot de passe valide (8+ chars, 1 majuscule, 1 chiffre), OK doit s'activer")
        .isFalse();
  }

  // --- Étape 5 : actions ---

  @Test
  @Order(11)
  void cliquer_sur_valider_affiche_l_identifiant_et_le_mot_de_passe_masque(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    Label message = robot.lookup("#labelMessage").queryAs(Label.class);
    robot.interact(() -> identifiant.setText("alice1"));
    robot.interact(() -> motDePasse.setText("Abcdef12"));
    robot.interact(ok::fire);
    assertThat(message.getText())
        .as(
            "après clic sur OK, le label doit afficher l'identifiant suivi d'autant d'étoiles que"
                + " de caractères dans le mot de passe")
        .isEqualTo("alice1 ********");
  }

  @Test
  @Order(12)
  void cliquer_sur_annuler_vide_les_deux_champs_et_le_label_message(FxRobot robot) {
    TextField identifiant = robot.lookup("#champIdentifiant").queryAs(TextField.class);
    PasswordField motDePasse = robot.lookup("#champMotDePasse").queryAs(PasswordField.class);
    Button ok = robot.lookup("#boutonOk").queryAs(Button.class);
    Button annuler = robot.lookup("#boutonAnnuler").queryAs(Button.class);
    Label message = robot.lookup("#labelMessage").queryAs(Label.class);

    robot.interact(() -> identifiant.setText("alice1"));
    robot.interact(() -> motDePasse.setText("Abcdef12"));
    robot.interact(ok::fire);

    robot.interact(annuler::fire);

    assertThat(identifiant.getText()).as("Annuler doit vider l'identifiant").isEmpty();
    assertThat(motDePasse.getText()).as("Annuler doit vider le mot de passe").isEmpty();
    assertThat(message.getText()).as("Annuler doit vider le label de message").isEmpty();
  }
}
