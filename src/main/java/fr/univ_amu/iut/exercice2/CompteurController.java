package fr.univ_amu.iut.exercice2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Contrôleur de la vue {@code CompteurView.fxml}.
 *
 * <p>Concepts introduits :
 *
 * <ul>
 *   <li>Injection des composants via {@code @FXML} (les attributs portent le même nom que les
 *       {@code fx:id} du FXML)
 *   <li>Méthodes {@code @FXML} appelées par {@code onAction="#..."} dans le FXML
 *   <li>Méthode {@code initialize()} invoquée automatiquement après l'injection des composants
 *   <li>Pont avec le TP2 : le compteur est une {@link IntegerProperty} et le texte du label s'y lie
 *       via un binding
 * </ul>
 */
public class CompteurController {

  /** Le modèle : la valeur courante du compteur, observable. */
  private final IntegerProperty compteur = new SimpleIntegerProperty(0);

  @FXML private Label labelCompteur;

  @FXML private Button boutonIncrementer;

  @FXML private Button boutonDecrementer;

  @FXML private Button boutonReinitialiser;

  /**
   * Méthode invoquée automatiquement par {@link javafx.fxml.FXMLLoader} une fois que tous les
   * champs annotés {@code @FXML} ont été injectés.
   *
   * <p>C'est ici que l'on branche le modèle (la propriété {@code compteur}) à la vue (le {@code
   * Label}) via un binding unidirectionnel.
   */
  @FXML
  private void initialize() {
    // TODO exercice 2 : lier le texte du label à la valeur du compteur.
    //
    // Utiliser Bindings.convert(...) ou compteur.asString() pour obtenir un
    // StringBinding,
    // puis labelCompteur.textProperty().bind(...).
    //
    // Ainsi, chaque fois que la valeur de `compteur` change (via
    // incrementer/decrementer/reinit),
    // le label se met à jour automatiquement - aucun setText() à appeler !
    labelCompteur.textProperty().bind(compteur.asString());
  }

  /**
   * Action du bouton +. Incrémente le compteur.
   *
   * <p>Grâce au binding mis en place dans {@link #initialize()}, aucune mise à jour de label n'est
   * nécessaire ici : la modification de la propriété suffit.
   */
  @FXML
  private void incrementer() {
    // TODO exercice 2 : incrémenter la valeur de la propriété compteur.
    compteur.set(compteur.get() + 1);
  }

  /** Action du bouton −. Décrémente le compteur. */
  @FXML
  private void decrementer() {
    // TODO exercice 2 : décrémenter la valeur de la propriété compteur.
    compteur.set(compteur.get() - 1);
  }

  /** Action du bouton Réinitialiser. Remet le compteur à zéro. */
  @FXML
  private void reinitialiser() {
    // TODO exercice 2 : remettre la propriété compteur à 0.
    compteur.set(0);
  }

  /**
   * Accesseur exposant la valeur courante du compteur. Utile pour les tests.
   *
   * @return la valeur courante du compteur
   */
  public int getCompteur() {
    return compteur.get();
  }
}
