package fr.univ_amu.iut.exercice4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Contrôleur de la vue {@code CoquilleAccueilView.fxml}.
 *
 * <p>Concepts mis en pratique :
 *
 * <ul>
 *   <li>injection de {@link Label} via {@code @FXML} (titre central + statut en bas de fenêtre)
 *   <li>plusieurs handlers reliés au FXML par {@code onAction="#..."} sur les items d'un {@code
 *       MenuBar}
 *   <li>séparation propre : la <b>structure</b> (BorderPane + MenuBar + center + bottom) est dans
 *       le FXML, la <b>logique</b> (changement d'écran) est dans le contrôleur
 * </ul>
 *
 * <p>Aucun écran réel n'est implémenté ici : on se contente d'afficher le nom de la rubrique au
 * centre et de tracer le dernier clic dans la barre de statut. C'est suffisant pour vérifier que la
 * navigation principale fonctionne, sans détourner l'exercice de son objectif (FXML déclaratif).
 */
public class CoquilleAccueilController {

  @FXML private Label labelTitre;

  @FXML private Label labelStatut;

  /** Action du menu « Mes sites ». Affiche le titre correspondant au centre de la coquille. */
  @FXML
  private void onMesSites() {
    // TODO exercice 4 : afficher le titre "Mes sites de suivi" au centre,
    // puis tracer dans la barre de statut : "Rubrique active : Mes sites".
    labelTitre.setText("Mes sites de suivi");
    labelStatut.setText("Rubrique active : Mes sites");
  }

  /** Action du menu « Importer une nuit ». */
  @FXML
  private void onImporter() {
    // TODO exercice 4 : titre "Importer une nuit", statut "Rubrique active :
    // Importer une nuit".
    labelTitre.setText("Importer une nuit");
    labelStatut.setText("Rubrique active : Importer une nuit");
  }

  /** Action du menu « Vue tabulaire ». */
  @FXML
  private void onVueTabulaire() {
    // TODO exercice 4 : titre "Vue tabulaire des passages", statut "Rubrique active
    // : Vue
    // tabulaire".
    labelTitre.setText("Vue tabulaire des passages");
    labelStatut.setText("Rubrique active : Vue tabulaire");
  }

  /** Action du menu « Paramètres ». */
  @FXML
  private void onParametres() {
    // TODO exercice 4 : titre "Paramètres de l'application", statut "Rubrique
    // active : Paramètres".
    labelTitre.setText("Paramètres de l'application");
    labelStatut.setText("Rubrique active : Paramètres");
  }

  /** Action du menu « Fichier > Quitter ». Ferme la fenêtre courante via la scène du label. */
  @FXML
  private void onQuitter() {
    Stage stage = (Stage) labelTitre.getScene().getWindow();
    stage.close();
  }
}
