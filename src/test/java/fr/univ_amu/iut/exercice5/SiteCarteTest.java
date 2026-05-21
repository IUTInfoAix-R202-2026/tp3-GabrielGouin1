package fr.univ_amu.iut.exercice5;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.Scene;
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
 * Tests de l'exercice 5 - composant SiteCarte (pattern fx:root).
 *
 * <p>On affiche une seule {@link SiteCarte} dans la Scene et on modifie ses propriétés depuis le
 * test pour vérifier que les labels et la classe CSS du badge réagissent comme attendu.
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SiteCarteTest {

  private SiteCarte carte;

  @Start
  void start(Stage stage) {
    stage.setScene(null);
    carte = new SiteCarte();
    stage.setScene(new Scene(carte, 700, 120));
    stage.show();
  }

  @Test
  @Order(1)
  void le_numero_de_carre_est_affiche_dans_la_carte(FxRobot robot) {
    robot.interact(() -> carte.setNumeroCarre("Carré 640380"));
    Label labelCarre = robot.from(carte).lookup(".carre-no").queryAs(Label.class);
    assertThat(labelCarre.getText())
        .as("le label .carre-no doit afficher le numéro de carré transmis à la propriété")
        .isEqualTo("Carré 640380");
  }

  @Test
  @Order(2)
  void le_nom_convivial_est_affiche_dans_la_carte(FxRobot robot) {
    robot.interact(() -> carte.setNomConvivial("📍 Étang de la Tuilière"));
    Label labelNom = robot.from(carte).lookup(".carre-nom").queryAs(Label.class);
    assertThat(labelNom.getText())
        .as("le label .carre-nom doit afficher le nom convivial transmis à la propriété")
        .isEqualTo("📍 Étang de la Tuilière");
  }

  @Test
  @Order(3)
  void les_compteurs_de_points_d_ecoute_et_de_passages_sont_affiches(FxRobot robot) {
    robot.interact(
        () -> {
          carte.setNombrePoints(3);
          carte.setNombrePassages(7);
        });
    String labelsConcat =
        robot.from(carte).lookup(".stat-num").queryAllAs(Label.class).stream()
            .map(Label::getText)
            .reduce("", (a, b) -> a + " | " + b);
    assertThat(labelsConcat)
        .as("les deux labels statistiques doivent inclure le nombre de points et de passages")
        .contains("3 points d'écoute")
        .contains("7 passages");
  }

  @Test
  @Order(4)
  void la_carte_avec_moins_de_sept_jours_affiche_un_badge_vert(FxRobot robot) {
    robot.interact(() -> carte.setJoursDepuisDernierPassage(2));
    Label badge = robot.from(carte).lookup(".badge").queryAs(Label.class);
    assertThat(badge.getStyleClass())
        .as("avec un dernier passage < 7 jours, la classe CSS doit être badge-fresh")
        .contains("badge-fresh")
        .doesNotContain("badge-stale", "badge-cold");
    assertThat(badge.getText())
        .as("le texte du badge doit refléter le nombre de jours")
        .isEqualTo("Il y a 2j");
  }

  @Test
  @Order(5)
  void la_carte_entre_sept_et_trente_jours_affiche_un_badge_orange(FxRobot robot) {
    robot.interact(() -> carte.setJoursDepuisDernierPassage(8));
    Label badge = robot.from(carte).lookup(".badge").queryAs(Label.class);
    assertThat(badge.getStyleClass())
        .as("avec un dernier passage entre 7 et 30 jours, la classe CSS doit être badge-stale")
        .contains("badge-stale")
        .doesNotContain("badge-fresh", "badge-cold");
  }

  @Test
  @Order(6)
  void la_carte_au_dela_de_trente_jours_affiche_un_badge_gris(FxRobot robot) {
    robot.interact(() -> carte.setJoursDepuisDernierPassage(45));
    Label badge = robot.from(carte).lookup(".badge").queryAs(Label.class);
    assertThat(badge.getStyleClass())
        .as("avec un dernier passage > 30 jours, la classe CSS doit être badge-cold")
        .contains("badge-cold")
        .doesNotContain("badge-fresh", "badge-stale");
  }

  @Test
  @Order(7)
  void la_carte_sans_aucun_passage_affiche_un_badge_gris_avec_le_texte_jamais_utilise(
      FxRobot robot) {
    robot.interact(() -> carte.setJoursDepuisDernierPassage(-1));
    Label badge = robot.from(carte).lookup(".badge").queryAs(Label.class);
    assertThat(badge.getStyleClass())
        .as("avec joursDepuisDernierPassage = -1, la classe CSS doit être badge-cold")
        .contains("badge-cold");
    assertThat(badge.getText())
        .as("avec joursDepuisDernierPassage = -1, le texte doit être \"Jamais utilisé\"")
        .isEqualTo("Jamais utilisé");
  }

  @Test
  @Order(8)
  void changer_le_nombre_de_jours_met_a_jour_la_classe_css_du_badge(FxRobot robot) {
    robot.interact(
        () -> {
          carte.setJoursDepuisDernierPassage(2);
          carte.setJoursDepuisDernierPassage(45);
        });
    Label badge = robot.from(carte).lookup(".badge").queryAs(Label.class);
    assertThat(badge.getStyleClass())
        .as("après changement 2 -> 45, l'ancienne classe badge-fresh doit avoir été retirée")
        .doesNotContain("badge-fresh")
        .contains("badge-cold");
  }
}
