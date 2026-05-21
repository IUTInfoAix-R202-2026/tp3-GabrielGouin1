package fr.univ_amu.iut.exercice7;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
 * Tests de l'exercice 7 - Pierre angulaire MVC (parcours P3 du brief SAÉ 2.01).
 *
 * <p>Ces tests vérifient en bout-en-bout que le modèle {@link NuitVerification}, la vue FXML et le
 * contrôleur {@link QualificationController} sont câblés correctement. Les modifications du modèle
 * se propagent à la vue ; les actions de la vue mettent à jour le modèle.
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QualificationControllerTest {

  private QualificationController controller;

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null);
    FXMLLoader loader =
        new FXMLLoader(QualificationController.class.getResource("QualificationView.fxml"));
    Parent racine = loader.load();
    controller = loader.getController();
    stage.setScene(new Scene(racine, 880, 520));
    stage.show();
  }

  @SuppressWarnings("unchecked")
  private TableView<Sequence> table(FxRobot robot) {
    return robot.lookup("#tableView").queryAs(TableView.class);
  }

  @SuppressWarnings("unchecked")
  private ChoiceBox<String> choice(FxRobot robot) {
    return robot.lookup("#choiceBoxVerdict").queryAs(ChoiceBox.class);
  }

  @Test
  @Order(1)
  void le_tableview_est_alimente_par_les_sequences_de_la_nuit(FxRobot robot) {
    TableView<Sequence> tv = table(robot);
    assertThat(tv)
        .as("la TableView fx:id=\"tableView\" doit être présente dans la vue FXML")
        .isNotNull();
    assertThat(tv.getItems())
        .as("la TableView doit être alimentée par les séquences du modèle (10 par défaut)")
        .hasSize(10);
  }

  @Test
  @Order(2)
  void le_panneau_de_detail_invite_a_selectionner_une_sequence_au_demarrage(FxRobot robot) {
    Label labelSelection = robot.lookup("#labelSelection").queryAs(Label.class);
    assertThat(labelSelection.getText())
        .as("au démarrage, le panneau de droite indique qu'aucune séquence n'est sélectionnée")
        .isEqualTo("(sélectionnez une séquence dans le tableau)");
  }

  @Test
  @Order(3)
  void le_bouton_ecouter_est_desactive_sans_selection_dans_le_tableau(FxRobot robot) {
    Button boutonEcouter = robot.lookup("#boutonEcouter").queryAs(Button.class);
    assertThat(boutonEcouter.isDisabled())
        .as("sans sélection, le bouton Écouter doit être désactivé via un binding")
        .isTrue();
  }

  @Test
  @Order(4)
  void selectionner_une_sequence_active_le_bouton_ecouter_et_met_a_jour_le_panneau_de_detail(
      FxRobot robot) {
    TableView<Sequence> tv = table(robot);
    robot.interact(() -> tv.getSelectionModel().select(0));
    Button boutonEcouter = robot.lookup("#boutonEcouter").queryAs(Button.class);
    Label labelSelection = robot.lookup("#labelSelection").queryAs(Label.class);
    assertThat(boutonEcouter.isDisabled())
        .as("avec une séquence sélectionnée, le bouton Écouter doit être activé")
        .isFalse();
    assertThat(labelSelection.getText())
        .as("le label de sélection doit commencer par \"Séquence\" et contenir \"kHz\"")
        .startsWith("Séquence ")
        .contains("kHz");
  }

  @Test
  @Order(5)
  void cliquer_sur_ecouter_passe_le_statut_de_la_sequence_selectionnee_a_ecoutee(FxRobot robot) {
    TableView<Sequence> tv = table(robot);
    robot.interact(() -> tv.getSelectionModel().select(0));
    Sequence selectionnee = tv.getSelectionModel().getSelectedItem();
    assertThat(selectionnee.getStatut())
        .as("avant clic, le statut initial de la séquence doit être \"À écouter\"")
        .isEqualTo("À écouter");
    Button boutonEcouter = robot.lookup("#boutonEcouter").queryAs(Button.class);
    robot.interact(boutonEcouter::fire);
    assertThat(selectionnee.getStatut())
        .as("après clic sur Écouter, le statut doit passer à \"Écoutée\"")
        .isEqualTo("Écoutée");
  }

  @Test
  @Order(6)
  void le_choicebox_de_verdict_propose_ok_douteux_et_a_jeter(FxRobot robot) {
    ChoiceBox<String> cb = choice(robot);
    assertThat(cb.getItems())
        .as("la ChoiceBox de verdict doit proposer exactement \"OK\", \"Douteux\", \"À jeter\"")
        .containsExactly("OK", "Douteux", "À jeter");
  }

  @Test
  @Order(7)
  void le_label_de_verdict_global_affiche_a_saisir_au_demarrage(FxRobot robot) {
    Label labelVerdict = robot.lookup("#labelVerdictGlobal").queryAs(Label.class);
    assertThat(labelVerdict.getText())
        .as("tant qu'aucun verdict n'a été enregistré, le label affiche \"(à saisir)\"")
        .isEqualTo("Verdict global : (à saisir)");
  }

  @Test
  @Order(8)
  void cliquer_sur_enregistrer_verdict_alimente_le_modele_et_met_a_jour_le_label(FxRobot robot) {
    ChoiceBox<String> cb = choice(robot);
    robot.interact(() -> cb.setValue("Douteux"));
    Button boutonEnregistrer = robot.lookup("#boutonEnregistrer").queryAs(Button.class);
    robot.interact(boutonEnregistrer::fire);
    Label labelVerdict = robot.lookup("#labelVerdictGlobal").queryAs(Label.class);
    assertThat(labelVerdict.getText())
        .as("le label doit refléter le verdict choisi via le binding when/then/otherwise")
        .isEqualTo("Verdict global : Douteux");
  }

  @Test
  @Order(9)
  void la_zone_de_commentaire_est_liee_au_modele_de_maniere_bidirectionnelle(FxRobot robot) {
    TextArea zone = robot.lookup("#zoneCommentaire").queryAs(TextArea.class);
    // Vue -> modèle
    robot.interact(() -> zone.setText("vent fort vers 02:00"));
    assertThat(controller.getNuit().getCommentaire())
        .as("modifier la TextArea doit propager la valeur dans le modèle (bindBidirectional)")
        .isEqualTo("vent fort vers 02:00");
    // Modèle -> vue
    robot.interact(() -> controller.getNuit().setCommentaire("modifié côté modèle"));
    assertThat(zone.getText())
        .as("modifier le modèle doit propager la valeur dans la TextArea (bindBidirectional)")
        .isEqualTo("modifié côté modèle");
  }
}
