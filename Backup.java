package com.example.demo;

import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * La classe Backup permet la gestion des sauvegardes et du menu principal du jeu Pocket Imperium.
 *
 * Cette classe gère l'interface utilisateur principale, la création de nouvelles parties,
 * le chargement des parties sauvegardées, et l'affichage des règles du jeu.
 * Elle implémente JavaFX Application pour gérer l'interface graphique.
 *
 * @author Anaelle Melo, Daria Avdeeva
 * @version 1.0
 */
public class Backup extends Application implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * Stage, sur lequel on effectue l'affichage graphique
     */
    private transient Stage primaryStage;

    /**
     * Ensemble des parties sauvegardées précédemment.
     */
    private ArrayList<Partie> parties=new ArrayList<>();

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier.
     *
     * @param fileName le nom du fichier où sauvegarder la partie
     */
    public void saveGames(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Parties sauvegardées avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge une partie sauvegardée à partir d'un fichier.
     *
     * @param fileName le nom du fichier contenant la sauvegarde
     * @return une instance de Backup contenant la partie sauvegardée
     */
    public static Backup loadGames(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Backup) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            return new Backup(); // au cas ou une erreur se produit, on crée un nouveau Backup
        }
    }

    /**
     * Démarre l'application JavaFX et affiche le menu principal.
     *
     * @param stage la fenêtre principale de l'application
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        Scanner sc = new Scanner(System.in);
        Scene mainMenu = createMainMenuScene(sc);

        // Configure the stage
        primaryStage.setTitle("Pocket Imperium");
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }

    /**
     * Crée la scène du menu principal avec les boutons pour les différentes options.
     *
     * @param sc le scanner pour la saisie utilisateur
     * @return la scène du menu principal
     */
    private Scene createMainMenuScene(Scanner sc) {
        // Create a vertical box to hold our components
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        // Create and style the title text
        Text titleText = new Text("Pocket Imperium");
        titleText.setFont(Font.font(24));
        titleText.setFill(Color.WHITE);

        // Create and style the buttons
        Button loadGameBtn = createStyledButton("Reprendre une partie");
        Button newGameBtn = createStyledButton("Nouvelle Partie");
        Button exitGameBtn = new Button("Sortir du jeu");
        exitGameBtn.setStyle("-fx-background-color: #5e0808; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px;");

        // Add hover effect
        exitGameBtn.setOnMouseEntered(e ->
                exitGameBtn.setStyle("-fx-background-color: #8a1919; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-min-width: 150px; " +
                        "-fx-min-height: 40px;"));

        exitGameBtn.setOnMouseExited(e ->
                exitGameBtn.setStyle("-fx-background-color: #5e0808; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-min-width: 150px; " +
                        "-fx-min-height: 40px;"));
        Button explainGameBtn = createStyledButton("Voir les règles du jeu");

        // Add button event handlers
        loadGameBtn.setOnAction(e -> showLoadGameScene(sc));
        newGameBtn.setOnAction(e -> showNewGameScene(sc));
        exitGameBtn.setOnAction(e -> {
            saveGames("parties.dat"); // Auto-save on exit
            sc.close();
            Platform.exit();
        });
        explainGameBtn.setOnAction(e -> showRules(sc));

        // Add all components to the VBox
        root.getChildren().addAll(titleText, loadGameBtn, newGameBtn, explainGameBtn, exitGameBtn);
        return new Scene(root, 900, 700);
    }

    /**
     * Affiche les règles du jeu dans une nouvelle scène.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    private void showRules(Scanner sc) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        double largeur = 975;
        double taillePolice = 13;
        root.setStyle("-fx-background-color: black;");

        // Create and style the title text
        Text titleText = new Text("Règles du jeu");
        titleText.setFont(Font.font(25));
        titleText.setFill(Color.WHITE);

        Text explicationRapide = new Text("COMMENT JOUER \n" +
                "Celui qui a le plus de points à la fin du jeu a gagné. Chaque tour, 3 phases sont exécutées dans cet ordre : \n" +
                "1. Planification: Choisir l'ordre des actions. \n" +
                "2. Exécution: Révélation et exécution des actions. \n" +
                "3. Exploitation: Vérification des vaisseaux et calcul des scores. ");
        explicationRapide.setFont(Font.font(15));
        explicationRapide.setFill(Color.WHITE);

        Text expandTitle = new Text("Expand");
        expandTitle.setFont(Font.font(20));
        expandTitle.setFill(Color.WHITE);

        Text expandExplanation = new Text("When you perform the Expand command, you may take ships of your colour from the " +
                "supply and add them to any system hexes you control. The number of ships you can add depends on how many other players chose the Expand command at the same time.controlled systems \n " +
                "Important! You may only add new ships to your  You cannot add ships to non system hexes you occupy or to unoccupied hexes. ");
        expandExplanation.setFont(Font.font(taillePolice));
        expandExplanation.setFill(Color.WHITE);
        expandExplanation.setWrappingWidth(largeur);

        Text exploreTitle = new Text("Explore");
        exploreTitle.setFont(Font.font(20));
        exploreTitle.setFill(Color.WHITE);

        Text exploreExplanation = new Text("When you perform the Explore command, you may move your fleets of ships from hex to hex. The number of fleet movements you can make depends on how many other players chose Explore at the same time. \n" +
                "All ships in the same hex may be moved together as a single fleet\n" +
                "Each time you move a fleet you may move it up to 2 hexes\n" +
                "• You cannot move your fleets into hexes occupied by another player. \n" +
                "• You cannot move a fleet through or through any the Tri-Prime system hex. If you move into this hex you must stop. \n" +
                "• If you move into and occupy an unoccupied system hex you immediately gain control of the system. \n" +
                "• Individual ships may be added to a fleet as it passes through a hex, or left behind in a hex while the fleet moves away to an adjacent hex. \n" +
                "• You cannot move into the ‘half hexes’ on the edges of the card layout. \n" +
                "\n" +
                "Important! Each ship may only be involved in one fleet movement in each round. ");
        exploreExplanation.setFont(Font.font(taillePolice));
        exploreExplanation.setFill(Color.WHITE);
        exploreExplanation.setWrappingWidth(largeur);

        Text exterminateTitle = new Text("Exterminate");
        exterminateTitle.setFont(Font.font(20));
        exterminateTitle.setFill(Color.WHITE);

        Text exterminateExplanation = new Text("When you perform the Exterminate command, you may use your ships to invade adjacent systems. The number of individual systems you can invade depends on how many other players chose Exterminate at the same time. \n" +
                "You can only invade systems that you do not control. \n" +
                "To do this you must move ships into the target system hex from your own fleets in directly adjacent hexes. \n" +
                "You may move in ships from any number of different adjacent hexes to create your invasion fleet. \n" +
                "When you invade, resolve the invasion as follows: \n" +
                "• You and the player who controls the target system must both remove a number of ships equal to the size of the smallest fleet now in the invaded system hex. Return all removed ships to the supply. \n" +
                "• Every invasion has one of two possible outcomes: either ships belonging to only one of the players will remain and that player will then control the system; or the system will be left unoccupied, which will happen if the two fleets were the same size. \n" +
                "For example, if an invading player moves 5 ships into a system hex containing 3 ships belonging to another player, both players must remove 3 ships. The invading player will have 2 ships remaining in the hex, and the other player will have none. \n" +
                "\n" +
                "• You can invade unoccupied systems, including systems that you moved your own ships out of during this round. \n" +
                "• You can invade a system using fewer ships than the player who controls it, in order to weaken that player. \n" +
                "• You cannot invade a system hex that you already control. \n" +
                "• If you are invading more than one system, you can choose the order in which you invade them.  \n" +
                "• A single system may be invaded by more than one player during a single round. \n" +
                "\n" +
                "Important! Each ship may only be involved in one invasion in each round. ");
        exterminateExplanation.setFont(Font.font(taillePolice));
        exterminateExplanation.setFill(Color.WHITE);
        exterminateExplanation.setWrappingWidth(largeur);

        // Bouton retour
        Button backBtn = createStyledButton("Back to Main Menu");

        // Add button event handlers
        backBtn.setOnAction(e -> primaryStage.setScene(createMainMenuScene(sc)));

        // Add all components to the VBox
        root.getChildren().addAll(titleText, explicationRapide, expandTitle, expandExplanation, exploreTitle, exploreExplanation, exterminateTitle, exterminateExplanation, backBtn);

        primaryStage.setScene(new Scene(root, 1100, 850));
    }

    /**
     * Crée la scène permettant le chargement d'une partie sauvegardée.
     *
     * Cette scène affiche la liste des parties disponibles si elles existent,
     * ou un message indiquant qu'aucune partie n'est sauvegardée dans le cas contraire.
     *
     * @param sc le scanner pour la saisie utilisateur
     * @return la scène de chargement de partie
     */
    private Scene createLoadGameScene(Scanner sc) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        // Create and style the title text
        Text titleText = new Text("Load Previous Game");
        titleText.setFont(Font.font(30));
        titleText.setFill(Color.WHITE);
        // Add component to the VBox
        root.getChildren().addAll(titleText);
        if (parties.isEmpty()) {
            Text affichage = new Text("Aucune partie sauvegardée");
            affichage.setFont(Font.font(20));
            affichage.setFill(Color.WHITE);
            root.getChildren().add(affichage);
            Button backBtn = createStyledButton("Back to Main Menu");

            // Add button event handlers
            backBtn.setOnAction(e -> primaryStage.setScene(createMainMenuScene(sc)));

            // Add component to the VBox
            root.getChildren().addAll(backBtn);

        }else{
            if(parties.size() <= 1){
                Button loadGame1Btn = createStyledButton("Load Game 1");
                loadGame1Btn.setOnAction(e -> {
                    Stage currentStage = (Stage) loadGame1Btn.getScene().getWindow();
                    currentStage.close();
                    handleLoadGameSlot(sc, 1);
                });
                root.getChildren().addAll(loadGame1Btn);
            }else if (parties.size() <= 2){
                Button loadGame2Btn = createStyledButton("Load Game 2");
                loadGame2Btn.setOnAction(e -> {
                    Stage currentStage = (Stage) loadGame2Btn.getScene().getWindow();
                    currentStage.close();
                    handleLoadGameSlot(sc, 2);
                });
                root.getChildren().addAll(loadGame2Btn);
            }else if (parties.size() <= 3){
                Button loadGame3Btn = createStyledButton("Load Game 3");
                loadGame3Btn.setOnAction(e -> {
                    Stage currentStage = (Stage) loadGame3Btn.getScene().getWindow();
                    currentStage.close();
                    handleLoadGameSlot(sc, 2);
                });
                root.getChildren().addAll(loadGame3Btn);
            }

            Button backBtn = createStyledButton("Back to Main Menu");

            // Add button event handlers
            backBtn.setOnAction(e -> primaryStage.setScene(createMainMenuScene(sc)));

            // Add component to the VBox
            root.getChildren().addAll(backBtn);
        }
        return new Scene(root, 900, 700);
    }

    /**
     * Crée un bouton stylisé avec des effets de survol.
     *
     * Le bouton utilise un style personnalisé avec une couleur de fond grise
     * et un texte blanc. L'apparence change lors du survol de la souris.
     *
     * @param text le texte à afficher sur le bouton
     * @return le bouton stylisé
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #404040; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px;");

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle("-fx-background-color: #606060; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-min-width: 150px; " +
                        "-fx-min-height: 40px;"));

        button.setOnMouseExited(e ->
                button.setStyle("-fx-background-color: #404040; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-min-width: 150px; " +
                        "-fx-min-height: 40px;"));

        return button;
    }

    /**
     * Affiche dans une nouvelle scène le choix des anciennes parties à reprendre.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    private void showLoadGameScene(Scanner sc){
        Scene loadGameScene = createLoadGameScene(sc);
        primaryStage.setScene(loadGameScene);
    }

    /**
     * Gère le chargement d'une partie sauvegardée spécifique.
     *
     * Récupère la partie sélectionnée et reprend son exécution.
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param slotNumber le numéro de l'emplacement de la sauvegarde à charger
     */
    private void handleLoadGameSlot(Scanner sc, int slotNumber) {
        Partie selectedGame = parties.get(slotNumber);
        selectedGame.resumeGame(sc);  // Use resumeGame instead of startGame
    }

    /**
     * Affiche la scène de création d'une nouvelle partie.
     *
     * Crée une nouvelle instance de Partie, l'ajoute à la liste des parties,
     * et affiche la scène de sélection des joueurs.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    private void showNewGameScene(Scanner sc) {
        this.parties.add(new Partie(sc, this));
        Scene newGameScene = createPlayerSelectionScene(sc, this.parties.getLast());
        primaryStage.setScene(newGameScene);
    }

    // Création des joueurs (en affichage graphique)
    /**
     * Crée la scène de sélection des joueurs pour une nouvelle partie.
     *
     * Permet de configurer jusqu'à trois joueurs avec leurs pseudos, couleurs,
     * et statut (humain ou virtuel). Vérifie la validité des choix avant
     * de lancer la partie.
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param p la partie en cours de création
     * @return la scène de sélection des joueurs
     */
    private Scene createPlayerSelectionScene(Scanner sc, Partie p) {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: black; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Choix des joueurs");
        title.setFont(Font.font(24));
        title.setStyle("-fx-text-fill: white;");

        List<String> availableColors = new ArrayList<>(Arrays.asList("Rouge", "Jaune", "Violet"));
        PlayerSetup[] players = new PlayerSetup[3];

        for (int i = 0; i < 3; i++) {
            players[i] = createPlayerSection(i + 1, availableColors);
        }

        Button validateButton = new Button("Valider");
        validateButton.setStyle("-fx-background-color: #404040; -fx-text-fill: white;");
        validateButton.setOnAction(e -> {
            if (validatePlayerChoices(players)) {
                // Start game with selected players
                ArrayList<Player> joueurs = new ArrayList<Player>();
                int playerId = 0; // Start player IDs from 0
                List<String> remainingColors = new ArrayList<>(Arrays.asList("Rouge", "Jaune", "Violet"));
                for (PlayerSetup player : players) {
                    if (!player.virtualCheck.isSelected()) {
                        remainingColors.remove(player.colorChoice.getValue());
                    }
                }

                int colorIndex = 0;
                for (PlayerSetup player : players) {
                    if (player.virtualCheck.isSelected()) {
                        player.colorChoice.setValue(remainingColors.get(colorIndex++));
                    }
                }

                for (PlayerSetup playerSetup : players) {
                    boolean isVirtual = playerSetup.virtualCheck.isSelected();
                    String nickname = playerSetup.nicknameField.getText().trim();
                    if (nickname.isEmpty()){
                        nickname="Bot " + playerId;
                    }
                    Couleur couleur = Couleur.ROUGE;
                    if (playerSetup.colorChoice.getValue().toUpperCase()=="ROUGE") {
                        couleur = Couleur.ROUGE;
                    }else if(playerSetup.colorChoice.getValue().toUpperCase()=="JAUNE") {
                        couleur = Couleur.JAUNE;
                    }else if (playerSetup.colorChoice.getValue().toUpperCase()=="VIOLET") {
                        couleur = Couleur.VIOLET;
                    }
                    // Create Player using the constructor
                    Player player = new Player(playerId, couleur, nickname, isVirtual);

                    // Add the player to the list
                    joueurs.add(player);
                    playerId++; // Increment the player ID
                }
                Stage currentStage = (Stage) validateButton.getScene().getWindow();
                currentStage.close();
                p.startGame(joueurs, sc);

            }
        });

        root.getChildren().addAll(title);
        root.getChildren().addAll(Arrays.stream(players).map(pl -> pl.container).toArray(Node[]::new));
        root.getChildren().add(validateButton);

        return new Scene(root, 900, 700);
    }

    /**
     * Cette classe permet de gérer les différents choix que doit faire un joueur dans l'affichage graphique.
     */
    private static class PlayerSetup {
        VBox container;
        CheckBox virtualCheck;
        TextField nicknameField;
        ComboBox<String> colorChoice;
        boolean isVirtual;
    }

    /**
     * Crée la scène de sélection d'un seul joueur pour une nouvelle partie.
     *
     * Permet de configurer 1 joueur avec son pseudo, sa couleur,
     * et statut (humain ou virtuel).
     *
     * @param playerNum le numéro du joueur
     * @param availableColors les couleurs encore disponibles
     * @return l'affichage graphiqiue d'un joueur
     */
    private PlayerSetup createPlayerSection(int playerNum, List<String> availableColors) {
        PlayerSetup setup = new PlayerSetup();

        setup.container = new VBox(10);
        setup.container.setStyle("-fx-border-color: white; -fx-border-width: 1; -fx-padding: 10;");

        Label playerLabel = new Label("Joueur " + playerNum);
        playerLabel.setStyle("-fx-text-fill: white;");
        playerLabel.setFont(Font.font(18));

        setup.virtualCheck = new CheckBox("Joueur virtuel");
        setup.virtualCheck.setStyle("-fx-text-fill: white;");

        setup.nicknameField = new TextField();
        setup.nicknameField.setPromptText("Pseudo");
        setup.nicknameField.setStyle("-fx-max-width: 200");
        //setup.nicknameField.setStyle("-fx-text-fill: #3e3e3e;");

        setup.colorChoice = new ComboBox<>();
        setup.colorChoice.getItems().addAll(availableColors);
        setup.colorChoice.setPromptText("Choisir une couleur");

        setup.virtualCheck.setOnAction(e -> {
            boolean isVirtual = setup.virtualCheck.isSelected();
            setup.nicknameField.setDisable(isVirtual);
            setup.colorChoice.setDisable(isVirtual);

            if (isVirtual) {
                setup.nicknameField.setStyle("-fx-opacity: 0.5;");
                setup.colorChoice.setStyle("-fx-opacity: 0.5;");
            } else {
                setup.nicknameField.setStyle("");
                setup.colorChoice.setStyle("");
            }
            setup.nicknameField.setStyle("-fx-max-width: 200");
        });

        setup.container.getChildren().addAll(playerLabel, setup.virtualCheck,
                setup.nicknameField, setup.colorChoice);
        return setup;
    }

    /**
     * Vérifie la validité des choix des joueurs.
     *
     * Contrôle que chaque joueur humain a un pseudo et une couleur unique.
     * Affiche une alerte en cas d'erreur.
     *
     * @param players tableau des configurations des joueurs à valider
     * @return true si les choix sont valides, false sinon
     */
    private boolean validatePlayerChoices(PlayerSetup[] players) {
        Set<String> usedColors = new HashSet<>();
        // potentiellement ne pas mettre des alertes comme ça, c'est pas très beau TODO
        for (PlayerSetup player : players) {
            if (!player.virtualCheck.isSelected()) {
                if (player.nicknameField.getText().trim().isEmpty()) {
                    showAlert("Erreur", "Veuillez entrer un pseudo pour chaque joueur humain.");
                    return false;
                }
                String selectedColor = player.colorChoice.getValue();
                if (selectedColor == null) {
                    showAlert("Erreur", "Veuillez choisir une couleur pour chaque joueur humain.");
                    return false;
                }
                if (usedColors.contains(selectedColor)) {
                    showAlert("Erreur", "Chaque joueur doit avoir une couleur différente.");
                    return false;
                }
                usedColors.add(selectedColor);
            }
        }
        return true;
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec un titre et un message.
     *
     * @param title le titre de la boîte de dialogue
     * @param content le message d'erreur à afficher
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Point d'entrée principal de l'application.
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Backup jeu = loadGames("parties.dat");
        jeu.launch();
    }
}
