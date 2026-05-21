package fr.univ_amu.iut.exercice4;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
 * Tests de l'exercice 4 - Coquille principale en FXML.
 *
 * <p>Pour cliquer sur un item de menu sans manipuler la souris physique, on récupère le {@link
 * MenuItem} en parcourant le {@link MenuBar} et on appelle directement {@code item.fire()} dans
 * {@code robot.interact(...)}.
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoquilleAccueilControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null);
    new CoquilleAccueil().start(stage);
  }

  private MenuItem itemDuMenu(FxRobot robot, String menuLabel, String itemLabel) {
    MenuBar menuBar = robot.lookup(".menu-bar").queryAs(MenuBar.class);
    return menuBar.getMenus().stream()
        .filter(m -> menuLabel.equals(m.getText()))
        .flatMap(m -> m.getItems().stream())
        .filter(i -> itemLabel.equals(i.getText()))
        .findFirst()
        .orElse(null);
  }

  @Test
  @Order(1)
  void le_titre_central_affiche_mes_sites_de_suivi_au_demarrage(FxRobot robot) {
    Label labelTitre = robot.lookup("#labelTitre").queryAs(Label.class);
    assertThat(labelTitre)
        .as("un Label avec fx:id=\"labelTitre\" doit être présent au centre de la coquille")
        .isNotNull();
    assertThat(labelTitre.getText())
        .as("au démarrage, le titre central doit afficher \"Mes sites de suivi\"")
        .isEqualTo("Mes sites de suivi");
  }

  @Test
  @Order(2)
  void le_label_de_statut_est_present_dans_la_barre_de_statut(FxRobot robot) {
    Label labelStatut = robot.lookup("#labelStatut").queryAs(Label.class);
    assertThat(labelStatut)
        .as("un Label avec fx:id=\"labelStatut\" doit être présent dans la barre de statut")
        .isNotNull();
  }

  @Test
  @Order(3)
  void le_menu_bar_contient_les_menus_fichier_et_affichage(FxRobot robot) {
    MenuBar menuBar = robot.lookup(".menu-bar").queryAs(MenuBar.class);
    assertThat(menuBar)
        .as("un MenuBar doit être placé en haut de la coquille (BorderPane.top)")
        .isNotNull();
    assertThat(menuBar.getMenus())
        .as("le MenuBar doit contenir au minimum les menus \"Fichier\" et \"Affichage\"")
        .extracting("text")
        .contains("Fichier", "Affichage");
  }

  @Test
  @Order(4)
  void cliquer_sur_le_menu_mes_sites_met_le_titre_central_a_mes_sites_de_suivi(FxRobot robot) {
    MenuItem item = itemDuMenu(robot, "Affichage", "Mes sites");
    assertThat(item).as("l'item \"Mes sites\" doit exister dans le menu Affichage").isNotNull();
    robot.interact(item::fire);
    Label labelTitre = robot.lookup("#labelTitre").queryAs(Label.class);
    assertThat(labelTitre.getText())
        .as("après clic sur \"Mes sites\", le titre central doit être \"Mes sites de suivi\"")
        .isEqualTo("Mes sites de suivi");
  }

  @Test
  @Order(5)
  void cliquer_sur_le_menu_importer_met_le_titre_central_a_importer_une_nuit(FxRobot robot) {
    MenuItem item = itemDuMenu(robot, "Affichage", "Importer une nuit");
    assertThat(item)
        .as("l'item \"Importer une nuit\" doit exister dans le menu Affichage")
        .isNotNull();
    robot.interact(item::fire);
    Label labelTitre = robot.lookup("#labelTitre").queryAs(Label.class);
    assertThat(labelTitre.getText())
        .as("après clic sur \"Importer une nuit\", le titre doit être \"Importer une nuit\"")
        .isEqualTo("Importer une nuit");
  }

  @Test
  @Order(6)
  void cliquer_sur_le_menu_vue_tabulaire_met_le_titre_central_a_vue_tabulaire_des_passages(
      FxRobot robot) {
    MenuItem item = itemDuMenu(robot, "Affichage", "Vue tabulaire");
    assertThat(item).as("l'item \"Vue tabulaire\" doit exister dans le menu Affichage").isNotNull();
    robot.interact(item::fire);
    Label labelTitre = robot.lookup("#labelTitre").queryAs(Label.class);
    assertThat(labelTitre.getText())
        .as("après clic sur \"Vue tabulaire\", le titre doit être \"Vue tabulaire des passages\"")
        .isEqualTo("Vue tabulaire des passages");
  }

  @Test
  @Order(7)
  void cliquer_sur_le_menu_parametres_met_le_titre_central_a_parametres_de_l_application(
      FxRobot robot) {
    MenuItem item = itemDuMenu(robot, "Affichage", "Paramètres");
    assertThat(item).as("l'item \"Paramètres\" doit exister dans le menu Affichage").isNotNull();
    robot.interact(item::fire);
    Label labelTitre = robot.lookup("#labelTitre").queryAs(Label.class);
    assertThat(labelTitre.getText())
        .as("après clic sur \"Paramètres\", le titre doit être \"Paramètres de l'application\"")
        .isEqualTo("Paramètres de l'application");
  }

  @Test
  @Order(8)
  void cliquer_sur_un_menu_met_a_jour_le_label_de_la_barre_de_statut(FxRobot robot) {
    MenuItem item = itemDuMenu(robot, "Affichage", "Importer une nuit");
    robot.interact(item::fire);
    Label labelStatut = robot.lookup("#labelStatut").queryAs(Label.class);
    assertThat(labelStatut.getText())
        .as("la barre de statut doit refléter la dernière rubrique cliquée")
        .isEqualTo("Rubrique active : Importer une nuit");
  }
}
