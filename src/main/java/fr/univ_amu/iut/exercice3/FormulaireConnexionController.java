package fr.univ_amu.iut.exercice3;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Contrôleur de la vue {@code FormulaireConnexionView.fxml}.
 *
 * <p>Concepts introduits :
 *
 * <ul>
 *   <li>injection de plusieurs types de contrôles via {@code @FXML} ({@link TextField}, {@link
 *       PasswordField}, {@link Button}, {@link Label})
 *   <li>plusieurs handlers reliés au FXML par {@code onAction="#..."}
 *   <li>mise en place des bindings de validation dans {@link #initialize()} (l'équivalent en FXML
 *       du {@code createBindings()} du TP2 exercice 6)
 *   <li>utilisation d'un {@link BooleanBinding} bas niveau pour exprimer une règle de validation
 *       qui ne se factorise pas avec les opérateurs {@link Bindings} de haut niveau
 * </ul>
 *
 * <p>Règles de validation (identiques au TP2 exercice 6) :
 *
 * <ul>
 *   <li>Le champ mot de passe n'est éditable que si l'identifiant contient au moins 6 caractères
 *   <li>Le bouton OK n'est actif que si le mot de passe est valide (>= 8 caractères, contient au
 *       moins une majuscule et au moins un chiffre)
 *   <li>Le bouton Annuler est désactivé si les deux champs sont vides
 * </ul>
 */
public class FormulaireConnexionController {

  @FXML private TextField champIdentifiant;

  @FXML private PasswordField champMotDePasse;

  @FXML private Button boutonOk;

  @FXML private Button boutonAnnuler;

  @FXML private Label labelMessage;

  /**
   * Méthode invoquée automatiquement par {@link javafx.fxml.FXMLLoader} une fois que tous les
   * champs annotés {@code @FXML} ont été injectés. C'est ici qu'on installe les bindings de
   * validation.
   */
  @FXML
  private void initialize() {
    // TODO exercice 3 : installer les bindings de validation.
    //
    // 1. Le mot de passe n'est éditable que si l'identifiant contient au moins 6
    // caractères :
    // champMotDePasse.editableProperty().bind(
    // Bindings.greaterThanOrEqual(champIdentifiant.textProperty().length(), 6));
    champMotDePasse
        .editableProperty()
        .bind(Bindings.greaterThanOrEqual(champIdentifiant.textProperty().length(), 6));

    // 2. Le bouton Annuler est désactivé si les deux champs sont vides :
    // boutonAnnuler.disableProperty().bind(
    // Bindings.and(
    // Bindings.equal(0, champIdentifiant.textProperty().length()),
    // Bindings.equal(0, champMotDePasse.textProperty().length())));
    boutonAnnuler
        .disableProperty()
        .bind(
            Bindings.and(
                Bindings.equal(0, champIdentifiant.textProperty().length()),
                Bindings.equal(0, champMotDePasse.textProperty().length())));

    // 3. Le bouton OK est désactivé tant que le mot de passe n'est pas valide.
    // On crée une classe interne anonyme `new BooleanBinding() { ... }` :
    // - bloc d'initialisation : super.bind(champMotDePasse.textProperty())
    // - computeValue() : retourne true si le mot de passe est trop court (< 8)
    // OU ne contient pas de majuscule OU ne contient pas de chiffre.
    // Puis : boutonOk.disableProperty().bind(motDePasseInvalide);
    BooleanBinding motDePasseInvalide =
        new BooleanBinding() {
          {
            super.bind(champMotDePasse.textProperty());
          }

          @Override
          protected boolean computeValue() {
            String motdepasse = champMotDePasse.getText();
            return motdepasse.length() < 8
                || motdepasse.equals(motdepasse.toLowerCase())
                || !motdepasse.matches(".*\\d.*");
          }
        };

    boutonOk.disableProperty().bind(motDePasseInvalide);
  }

  /**
   * Action du bouton OK. Affiche dans {@link #labelMessage} l'identifiant suivi du mot de passe
   * masqué (autant d'étoiles que de caractères saisis).
   */
  @FXML
  private void valider() {
    // TODO exercice 3 : afficher dans labelMessage l'identifiant suivi du mot
    // de passe masqué par autant d'étoiles que de caractères saisis.
    // Exemple : "alice ********" pour identifiant "alice" et mot de passe de 8
    // caractères.
    String motDePasse = champMotDePasse.getText();
    String masque = "";
    for (int i = 0; i < motDePasse.length(); i++) {
      masque += "*";
    }
    labelMessage.setText(champIdentifiant.getText() + " " + masque);
  }

  /** Action du bouton Annuler. Vide les deux champs et le label de message. */
  @FXML
  private void annuler() {
    // TODO exercice 3 : vider les deux champs et le label message.
    champMotDePasse.setText("");
    labelMessage.setText("");
    champIdentifiant.setText("");
  }
}
