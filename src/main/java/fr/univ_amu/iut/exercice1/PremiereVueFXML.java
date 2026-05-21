package fr.univ_amu.iut.exercice1;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Exercice 1 : première vue décrite en FXML.
 *
 * <p>Aucun code Java ne construit l'interface ici : tout est dans {@code PremiereVueFXML.fxml}.
 * Cette classe se contente de <b>charger</b> le fichier FXML et de l'afficher dans une {@link
 * Stage}.
 *
 * <p>Concepts introduits :
 *
 * <ul>
 *   <li>{@link FXMLLoader#load(URL)} : le pont entre XML et objets Java
 *   <li>{@code getClass().getResource("...")} : chemin vers la ressource FXML
 *   <li>Racine unique ({@code BorderPane}) retournée par {@code FXMLLoader.load()}
 * </ul>
 */
public class PremiereVueFXML extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO exercice 1 : charger la vue FXML et l'afficher.
    //
    // 1. Construire l'URL vers la ressource "PremiereVueFXML.fxml".
    // Le fichier vit à côté de cette classe (même paquetage
    // fr.univ_amu.iut.exercice1).
    // Utiliser getClass().getResource("PremiereVueFXML.fxml") - JavaFX trouvera le
    // fichier en cherchant dans le paquetage courant.
    URL url = getClass().getResource("PremiereVueFXML.fxml");
    //
    // 2. Appeler FXMLLoader.load(url) qui retourne un Parent (ici un BorderPane).
    Parent root = FXMLLoader.load(url);
    Scene scene = new Scene(root);
    // 3. Créer une Scene avec ce Parent et la donner à primaryStage.
    primaryStage.setScene(scene);
    primaryStage.setTitle("Première vue FXML");
    primaryStage.show();
    // 4. Ajouter un titre et show().
  }
}
