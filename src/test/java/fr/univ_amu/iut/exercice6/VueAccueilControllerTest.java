package fr.univ_amu.iut.exercice6;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univ_amu.iut.exercice5.SiteCarte;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
 * Tests de l'exercice 6 - vue d'accueil composée par {@code fx:include}.
 *
 * <p>On vérifie à la fois que la structure FXML est correctement assemblée (deux sous-vues
 * présentes), que l'en-tête expose son bouton et son sous-titre, et que la communication entre les
 * deux sous-contrôleurs fonctionne (clic sur le bouton → carte ajoutée + sous-titre mis à jour).
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VueAccueilControllerTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null);
    new VueAccueil().start(stage);
  }

  @Test
  @Order(1)
  void l_entete_avec_titre_et_sous_titre_est_affichee(FxRobot robot) {
    Label sousTitre = robot.lookup("#labelSousTitre").queryAs(Label.class);
    assertThat(sousTitre)
        .as("le Label fx:id=\"labelSousTitre\" de l'en-tête doit être présent")
        .isNotNull();
  }

  @Test
  @Order(2)
  void le_bouton_nouveau_site_est_present_dans_l_entete(FxRobot robot) {
    Button bouton = robot.lookup("#boutonNouveauSite").queryAs(Button.class);
    assertThat(bouton)
        .as("le Button fx:id=\"boutonNouveauSite\" de l'en-tête doit être présent")
        .isNotNull();
    assertThat(bouton.getText())
        .as("le bouton doit afficher \"+ Nouveau site\"")
        .isEqualTo("+ Nouveau site");
  }

  @Test
  @Order(3)
  void la_liste_des_cartes_est_vide_au_demarrage(FxRobot robot) {
    VBox conteneur = robot.lookup("#conteneurCartes").queryAs(VBox.class);
    assertThat(conteneur)
        .as("le VBox fx:id=\"conteneurCartes\" doit être présent dans la liste")
        .isNotNull();
    assertThat(conteneur.getChildren())
        .as("au démarrage, aucune carte n'a encore été ajoutée à la liste")
        .isEmpty();
  }

  @Test
  @Order(4)
  void le_sous_titre_indique_aucun_site_declare_au_demarrage(FxRobot robot) {
    Label sousTitre = robot.lookup("#labelSousTitre").queryAs(Label.class);
    assertThat(sousTitre.getText())
        .as("au démarrage, le sous-titre doit indiquer qu'aucun site n'est encore déclaré")
        .isEqualTo("Aucun site déclaré");
  }

  @Test
  @Order(5)
  void cliquer_sur_nouveau_site_ajoute_une_carte_dans_la_liste(FxRobot robot) {
    Button bouton = robot.lookup("#boutonNouveauSite").queryAs(Button.class);
    robot.interact(bouton::fire);
    VBox conteneur = robot.lookup("#conteneurCartes").queryAs(VBox.class);
    assertThat(conteneur.getChildren())
        .as("après un clic sur \"+ Nouveau site\", une carte doit être présente dans la liste")
        .hasSize(1);
    assertThat(conteneur.getChildren().get(0))
        .as("la carte ajoutée doit être une instance du composant SiteCarte de l'exercice 5")
        .isInstanceOf(SiteCarte.class);
  }

  @Test
  @Order(6)
  void cliquer_sur_nouveau_site_met_le_sous_titre_a_un_site_declare(FxRobot robot) {
    Button bouton = robot.lookup("#boutonNouveauSite").queryAs(Button.class);
    robot.interact(bouton::fire);
    Label sousTitre = robot.lookup("#labelSousTitre").queryAs(Label.class);
    assertThat(sousTitre.getText())
        .as("après le premier clic, le sous-titre doit indiquer \"1 site déclaré\"")
        .isEqualTo("1 site déclaré");
  }

  @Test
  @Order(7)
  void trois_clics_sur_nouveau_site_ajoutent_trois_cartes_et_passent_le_sous_titre_au_pluriel(
      FxRobot robot) {
    Button bouton = robot.lookup("#boutonNouveauSite").queryAs(Button.class);
    robot.interact(bouton::fire);
    robot.interact(bouton::fire);
    robot.interact(bouton::fire);
    VBox conteneur = robot.lookup("#conteneurCartes").queryAs(VBox.class);
    Label sousTitre = robot.lookup("#labelSousTitre").queryAs(Label.class);
    assertThat(conteneur.getChildren()).as("3 clics doivent ajouter 3 cartes").hasSize(3);
    assertThat(sousTitre.getText())
        .as("le sous-titre doit indiquer \"3 sites déclarés\" (pluriel)")
        .isEqualTo("3 sites déclarés");
  }
}
