package fr.univ_amu.iut.exercice6;

import javafx.fxml.FXML;

/**
 * Contrôleur parent de la vue {@code VueAccueilView.fxml}.
 *
 * <p>Démontre le motif d'<b>injection automatique des sous-contrôleurs</b> : pour chaque {@code
 * <fx:include source="..." fx:id="xxx"/>}, FXMLLoader injecte deux champs dans le contrôleur parent
 * :
 *
 * <ul>
 *   <li>{@code @FXML private Node xxx;} - le nœud racine de la sous-vue
 *   <li>{@code @FXML private XxxController xxxController;} - le contrôleur associé
 * </ul>
 *
 * <p>Le suffixe {@code Controller} (figé par convention dans FXMLLoader) est obligatoire pour que
 * l'injection fonctionne. Cela permet au contrôleur parent d'orchestrer les sous-contrôleurs sans
 * qu'ils aient besoin de se connaître entre eux.
 */
public class VueAccueilController {

  @FXML private EnTeteController enTeteController;

  @FXML private ListeSitesController listeSitesController;

  /**
   * {@link javafx.fxml.FXMLLoader} appelle {@code initialize()} sur le parent <b>après</b> avoir
   * initialisé tous les sous-contrôleurs. C'est donc ici qu'on les fait dialoguer.
   */
  @FXML
  private void initialize() {
    // TODO exercice 6 : faire dialoguer les deux sous-contrôleurs via celui-ci.
    //
    // 1. Brancher l'action du bouton "+ Nouveau site" de l'en-tête sur l'ajout
    // d'une carte :
    // enTeteController.setActionNouveauSite(() -> {
    // int total = listeSitesController.ajouterSiteDemo();
    // enTeteController.mettreAJourCompteur(total);
    // });
    // 2. Initialiser le compteur de l'en-tête au nombre courant de cartes (zéro au
    // démarrage).
    enTeteController.setActionNouveauSite(
        () -> {
          int total = listeSitesController.ajouterSiteDemo();
          enTeteController.mettreAJourCompteur(total);
        });

    enTeteController.mettreAJourCompteur(0);
  }

  /** Exposé pour les tests : permet de récupérer le sous-contrôleur en-tête. */
  public EnTeteController getEnTeteController() {
    return enTeteController;
  }

  /** Exposé pour les tests : permet de récupérer le sous-contrôleur liste. */
  public ListeSitesController getListeSitesController() {
    return listeSitesController;
  }
}
