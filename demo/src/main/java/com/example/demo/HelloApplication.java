package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloApplication extends Application {
    private Stage primaryStage;  // We need to store the stage as a field to access it from other methods
    // Hex dimensions
    private static final double HEX_WIDTH = 90;
    private static final double HEX_HEIGHT = 78 ;//69.3; // approximately 0.866 * HEX_WIDTH
    // importation des systèmes
    private static final Image SYSTEM1_IMAGE = new Image("system1.png", 45, 45, true, true);
    private static final Image SYSTEM2_IMAGE = new Image("system2.png", 60, 60, true, true);
    private static final Image TRIPRIME_IMAGE = new Image("triprime.png", 75, 75, true, true);

    // Importation des images de vaisseaux
    private static final Image redShip = new Image("redRocket.png", 30, 30, true, true);
    private static final Image blueShip = new Image("blueRocket.png", 30, 30, true, true);
    private static final Image greenShip = new Image("greenRocket.png", 30, 30, true, true);


    // Grid configuration
    private static final int[] ROW_LENGTHS = {6, 5, 6, 5, 6, 5, 6, 5, 6};
    private static boolean dejaCree=false;
    private List<List<Hex>> gameBoard = new ArrayList<>();
    private SectorCard[][] board = new SectorCard[3][3];
    private List<List<Polygon>> interfaceGameBoard = new ArrayList<>();
    private boolean isChoosingHex=false;
    private ArrayList<Player> players = new ArrayList<Player>();



    // SERA SUPPRIME :
    private static int numeroJoueur=0;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        Scene mainMenu = createMainMenuScene();

        // Configure the stage
        primaryStage.setTitle("Pocket Imperium");
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }

    private Scene createMainMenuScene() {
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
        loadGameBtn.setOnAction(e -> showLoadGameScene());
        newGameBtn.setOnAction(e -> showNewGameScene());
        exitGameBtn.setOnAction(e -> Platform.exit());
        explainGameBtn.setOnAction(e -> showRules());

        // Add all components to the VBox
        root.getChildren().addAll(titleText, loadGameBtn, newGameBtn, explainGameBtn, exitGameBtn);
        return new Scene(root, 900, 700);
    }

    private void showRules() {
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
        backBtn.setOnAction(e -> primaryStage.setScene(createMainMenuScene()));

        // Add all components to the VBox
        root.getChildren().addAll(titleText, explicationRapide, expandTitle, expandExplanation, exploreTitle, exploreExplanation, exterminateTitle, exterminateExplanation, backBtn);

        primaryStage.setScene(new Scene(root, 1100, 850));
    }

    private Scene createLoadGameScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        // Create and style the title text
        Text titleText = new Text("Load Previous Game");
        titleText.setFont(Font.font(30));
        titleText.setFill(Color.WHITE);

        // Create and style the save game buttons
        Button loadGame1Btn = createStyledButton("Load Game 1");
        Button loadGame2Btn = createStyledButton("Load Game 2");
        Button loadGame3Btn = createStyledButton("Load Game 3");
        Button backBtn = createStyledButton("Back to Main Menu");

        // Add button event handlers
        loadGame1Btn.setOnAction(e -> handleLoadGameSlot(1));
        loadGame2Btn.setOnAction(e -> handleLoadGameSlot(2));
        loadGame3Btn.setOnAction(e -> handleLoadGameSlot(3));
        backBtn.setOnAction(e -> primaryStage.setScene(createMainMenuScene()));

        // Add all components to the VBox
        root.getChildren().addAll(titleText, loadGame1Btn, loadGame2Btn, loadGame3Btn, backBtn);

        return new Scene(root, 900, 700);
    }

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

    private void showLoadGameScene() {
        Scene loadGameScene = createLoadGameScene();
        primaryStage.setScene(loadGameScene);
    }

    private void handleLoadGameSlot(int slotNumber) {
        // Add your load game logic here
        System.out.println("Loading game from slot " + slotNumber);
    }

    private void showNewGameScene() {
        Scene newGameScene = createPlayerSelectionScene();
        primaryStage.setScene(newGameScene);
    }

    private Scene createNewGameScene() {
        Pane root = new Pane();

        // Create the game board
        //TODO ne sera pas comme ça dans le vrai programme
        if(!dejaCree){
            createGameBoard();
        }

        // Render the hexagonal grid
        renderHexGrid(root);
        setupSidebar(root, primaryStage);

        Scene scene = new Scene(root, 900, 700);
        return scene;
    }

    private void createGameBoard() {
        // CREATION DES JOUEURS
        this.players.add(new Player("r"));
        this.players.add(new Player("g"));
        this.players.add(new Player("b"));

        //TODO va partir, remplace la génération du plateau
        for (int row = 0; row < ROW_LENGTHS.length; row++) {
            List<Hex> hexRow = new ArrayList<>();
            for (int col = 0; col < ROW_LENGTHS[row]; col++) {
                // Example: alternating special hex types for demonstration
                boolean triPrime = (row + col) % 3 == 0;
                boolean systemHex1 = (row + col) % 5 == 0;
                boolean systemHex2 = (row + col) % 7 == 0;
                if (row==0 && col==0){
                    triPrime = false;
                    systemHex1 = false;
                    systemHex2 = false;
                }
                Hex hex = new Hex(row, col, triPrime, systemHex1, systemHex2);
                hexRow.add(hex);
            }
            gameBoard.add(hexRow);

        }
        dejaCree=true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j]= new SectorCard();
            }
        }

        this.board[0][0].addHex(gameBoard.get(0).get(0));
        this.board[0][0].addHex(gameBoard.get(0).get(1));
        this.board[0][0].addHex(gameBoard.get(1).get(0));
        this.board[0][0].addHex(gameBoard.get(2).get(0));
        this.board[0][0].addHex(gameBoard.get(2).get(1));

        this.board[0][1].addHex(gameBoard.get(0).get(2));
        this.board[0][1].addHex(gameBoard.get(0).get(3));
        this.board[0][1].addHex(gameBoard.get(1).get(1));
        this.board[0][1].addHex(gameBoard.get(1).get(2));
        this.board[0][1].addHex(gameBoard.get(1).get(3));
        this.board[0][1].addHex(gameBoard.get(2).get(2));
        this.board[0][1].addHex(gameBoard.get(2).get(3));
    }

    private void renderHexGrid(Pane root) {

        //mettre fond en noir
        root.setBackground(new Background(new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY)));
        createTriPrimeArea(root);

        for (int row = 0; row < gameBoard.size(); row++) {
            List<Hex> currentRow = gameBoard.get(row);
            List<Polygon> hexInterfaceRow = new ArrayList<>();
            for (int col = 0; col < currentRow.size(); col++) {

                Hex hex = currentRow.get(col);
                Polygon hexagon = createHexagon(row, col);

                // Add click event to each hexagon
                hexagon.setOnMouseClicked(event -> handleHexClick(root, hex));

                hexInterfaceRow.add(hexagon);

                if ((row == 3 && col == 2) || (row == 4 && col == 2) ||
                        (row == 4 && col == 3) || (row == 5 && col == 2)) {
                    continue;
                }

                root.getChildren().add(hexagon);

                // Calculate center position for the systems (same as in createHexagon)
                double xOffset = (row % 2 == 0) ? 0 : HEX_WIDTH / 2;
                double centerX = 50 + col * HEX_WIDTH * 0.95 + xOffset;
                double centerY = 50 + row * HEX_HEIGHT * 0.95;

                // Ajout des vaisseaux (notamment si c'est une sauvegarde)
                addShipsToHex(root, hex, centerX, centerY);


                // Add system images when the hex is a system
                if (hex.isSystemHex1()) {
                    ImageView systemView = new ImageView(SYSTEM1_IMAGE);
                    systemView.setX(centerX - SYSTEM1_IMAGE.getWidth()/2);
                    systemView.setY(centerY - SYSTEM1_IMAGE.getHeight()/2);
                    root.getChildren().add(systemView);
                } else if (hex.isSystemHex2()) {
                    ImageView systemView = new ImageView(SYSTEM2_IMAGE);
                    systemView.setX(centerX - SYSTEM2_IMAGE.getWidth()/2);
                    systemView.setY(centerY - SYSTEM2_IMAGE.getHeight()/2);
                    root.getChildren().add(systemView);
                }
            }
            interfaceGameBoard.add(hexInterfaceRow);
        }

        for (int row = 0; row < gameBoard.size(); row++) {
            List<Hex> currentRow = gameBoard.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                int finalRow = row;
                int finalCol = col;


                interfaceGameBoard.get(row).get(col).setOnMouseEntered(e -> {
                    if (isChoosingHex) {
                        interfaceGameBoard.get(finalRow).get(finalCol).setFill(Color.web("#606060"));
                    } else{

                        for (int i=0; i<3; i++) {
                            for (int j=0; j<3; j++) {
                                if (board[i][j].getHexes().contains(gameBoard.get(finalRow).get(finalCol))) {
                                    for (int taille = 0; taille <board[i][j].getHexes().size(); taille++){
                                        int xHex=board[i][j].getHexes().get(taille).getxPosition();
                                        int yHex=board[i][j].getHexes().get(taille).getyPosition();
                                        interfaceGameBoard.get(xHex).get(yHex).setFill(Color.web("#6c6345"));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    System.out.println("Carte Non trouvé");
                });

                interfaceGameBoard.get(row).get(col).setOnMouseExited(e -> {
                    if (isChoosingHex) {
                        interfaceGameBoard.get(finalRow).get(finalCol).setFill(Color.web("#404040"));
                    }else{
                        for (int i=0; i<3; i++) {
                            for (int j=0; j<3; j++) {
                                if (board[i][j].getHexes().contains(gameBoard.get(finalRow).get(finalCol))) {
                                    for (int taille = 0; taille <board[i][j].getHexes().size(); taille++){
                                        int xHex=board[i][j].getHexes().get(taille).getxPosition();
                                        int yHex=board[i][j].getHexes().get(taille).getyPosition();
                                        interfaceGameBoard.get(xHex).get(yHex).setFill(Color.web("#404040"));
                                    }
                                    break;
                                }
                            }
                        }
                    }

                });
            }
            }

    }

    private void addShipsToHex(Pane root, Hex hex, double centerX, double centerY) {
        //TODO soit on fait comme ça soit on crée un fonction qui supprime et rajoute un ship
        // Au lieu de tout revérifier à chaque fois

        List<Ship> shipsOnHex = new ArrayList<>();

        // Collect all ships on this hex
        for (Player player : players) {
            for (Ship ship : player.getShips()) {
                if (ship.getxPosition() == hex.getxPosition() && ship.getyPosition() == hex.getyPosition()) {
                    shipsOnHex.add(ship);
                }
            }
        }

        // Position ships in a circle around center
        int totalShips = shipsOnHex.size();
        System.out.println(hex + "vaisseaux" + totalShips);
        for (int i = 0; i < totalShips; i++) {
            Ship ship = shipsOnHex.get(i);
            // TODO clear le hex avant d'en rajouter, il se re ecrit dessus la
            // Calculate position in circle
            double angle = (2 * Math.PI * i) / totalShips;
            double radius = 20; // Adjust this value to change circle size
            double shipX = centerX + radius * Math.cos(angle) - 15; // -15 for half image width
            double shipY = centerY + radius * Math.sin(angle) - 15; // -15 for half image height

            ImageView shipView = new ImageView();
            switch( ship.getOwner().getColor()) {
                case RED: shipView.setImage(redShip); break;
                case BLUE: shipView.setImage(blueShip); break;
                case GREEN: shipView.setImage(greenShip); break;
            }

            shipView.setX(shipX);
            shipView.setY(shipY);
            root.getChildren().add(shipView);
        }
    }

    private void createTriPrimeArea(Pane root) {
        List<Double> points = new ArrayList<>();

        // Calculate positions for the four hexes
        double x1 = 51 + 2 * HEX_WIDTH * 0.95;  // col 2
        double y1 = 50 + 3 * HEX_HEIGHT * 0.95; // row 3

        // Add points clockwise from top hex
        addHexPoints(points, x1 + HEX_WIDTH * 0.95/2, y1, new int[]{1, 0, 5, 4, 3});  // Top (3,2)
        addHexPoints(points, x1, y1 + HEX_HEIGHT * 0.95, new int[]{5, 4, 3, 2});  // Middle-left (4,2)
        addHexPoints(points, x1 + HEX_WIDTH * 0.95/2, y1 + 2 * HEX_HEIGHT * 0.95, new int[]{4, 3, 2, 1, 0});  // Bottom (5,2)
        addHexPoints(points, x1 + HEX_WIDTH * 0.95, y1 + HEX_HEIGHT * 0.95, new int[]{2, 1, 0, 5});  // Middle-right (4,3)

        Polygon triPrime = new Polygon();
        triPrime.getPoints().addAll(points);
        triPrime.setFill(Color.web("#404040"));
        triPrime.setStroke(javafx.scene.paint.Color.BLACK);
        triPrime.setOnMouseEntered(e -> triPrime.setFill(Color.web("#606060")));
        triPrime.setOnMouseExited(e -> triPrime.setFill(Color.web("#404040")));

        root.getChildren().add(triPrime);

        ImageView systemView = new ImageView(TRIPRIME_IMAGE);
        systemView.setX(x1 + HEX_WIDTH * 0.95/2 - TRIPRIME_IMAGE.getWidth()/2);
        systemView.setY(y1 + HEX_WIDTH * 0.95 /1.25 - TRIPRIME_IMAGE.getHeight()/2);
        root.getChildren().add(systemView);
    }

    private void addHexPoints(List<Double> points, double centerX, double centerY, int[] indices) {
        for (int i : indices) {
            double angle = Math.PI / 180 * (60 * i - 30);
            points.add(centerX + HEX_WIDTH / 2 * Math.cos(angle));
            points.add(centerY + HEX_WIDTH / 2 * Math.sin(angle));
        }
    }

    private Polygon createHexagon(int row, int col) {
        Polygon hexagon = new Polygon();

        // Offset for alternating rows
        double xOffset = (row % 2 == 0) ? 0 : HEX_WIDTH / 2;

        // Hex vertex coordinates, calculate center position
        double centerX = 50 + col * HEX_WIDTH * 0.95 + xOffset;
        double centerY = 50 + row * HEX_HEIGHT * 0.95 ; // 0.75 avant

        List<Double> points = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 180 * (60 * i - 30);
            points.add(centerX + HEX_WIDTH / 2 * Math.cos(angle));
            points.add(centerY + HEX_WIDTH / 2 * Math.sin(angle));
        }
        hexagon.getPoints().addAll(points);

        // Styling
        hexagon.setFill(Color.web("#404040"));
        hexagon.setStroke(javafx.scene.paint.Color.BLACK);
        return hexagon;
    }

    //TODO sera peut etre utile à un autre moment, je garde en attendant
    private javafx.scene.paint.Color getHexColor(int row, int col) {
        Hex hex = gameBoard.get(row).get(col);

        if (hex.isTriPrime()) {
            return javafx.scene.paint.Color.BLUE;
        } else if (hex.isSystemHex2()) {
            return javafx.scene.paint.Color.GREEN;
        } else if (hex.isSystemHex1()) {
            return javafx.scene.paint.Color.YELLOW;
        }

        return javafx.scene.paint.Color.LIGHTGRAY;
    }

    private void handleHexClick(Pane root, Hex hex) {
        System.out.println("Clicked Hex at (" + hex.getxPosition() + ", " + hex.getyPosition() + ")");
        System.out.println("Hex Value: " + hex.getValue());
        System.out.println("Max Ships: " + hex.getNbMaxShips());

        double xOffset = (hex.getxPosition() % 2 == 0) ? 0 : HEX_WIDTH / 2;
        double centerX = 50 + hex.getyPosition() * HEX_WIDTH * 0.95 + xOffset;
        double centerY = 50 + hex.getxPosition() * HEX_HEIGHT * 0.95;

        //TODO ici yaura un grand if else if else pour chaque cas ou on peut etre (choix de rajouter des vaisseaux, choix de explore...,
        // choix de secteur de score..)
        if(numeroJoueur%3==0){
            players.get(0).setShip(hex);
        }else if (numeroJoueur%3==1){
            players.get(1).setShip(hex);
        }else{
            players.get(2).setShip(hex);
        }
        numeroJoueur++;
        addShipsToHex(root, hex, centerX, centerY);

    }

    private void setupSidebar(Pane root, Stage primaryStage) {
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 20;");
        sidebar.setPrefWidth(250);
        sidebar.setLayoutX(600);
        sidebar.setLayoutY(0);

        // Game State Label
        Label gameState = new Label("Initialisation");
        gameState.setFont(Font.font(24));
        gameState.setStyle("-fx-text-fill: #FFFFFF;");

        // Current Step Label
        Label stepLabel = new Label("Choosing actions");
        stepLabel.setFont(Font.font(18));
        stepLabel.setStyle("-fx-text-fill: #cccccc;");

        // Player Label
        Label playerLabel = new Label("Player 1");
        playerLabel.setFont(Font.font(16));
        playerLabel.setStyle("-fx-text-fill: #999999;");

        // Action Selection
        VBox actionBox = new VBox(10);
        actionBox.setVisible(true); // Only visible during action selection

        ComboBox<String>[] actionSelectors = new ComboBox[3];
        String[] actions = {"Expand", "Exterminate", "Explore"};

        for (int i = 0; i < 3; i++) {
            actionSelectors[i] = new ComboBox<>();
            actionSelectors[i].getItems().addAll(actions);
            actionSelectors[i].setPromptText("Select Action " + (i + 1));
            actionSelectors[i].setStyle("-fx-background-color: #999898; -fx-text-fill: white;");
        }

        Button confirmButton = new Button("Confirm Actions");
        confirmButton.setStyle("-fx-background-color: #404040; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> {
            String[] selectedActions = new String[3];
            for (int i = 0; i < 3; i++) {
                selectedActions[i] = actionSelectors[i].getValue();
            }
            handleSelection(selectedActions);
        });

        actionBox.getChildren().addAll(actionSelectors);
        actionBox.getChildren().add(confirmButton);

        // Back to Main Menu Button
        Button mainMenuButton = new Button("Back to Main Menu");
        mainMenuButton.setStyle("-fx-background-color: #8b0000; -fx-text-fill: white;");
        mainMenuButton.setPrefWidth(200);

        boolean canReturnToMenu = true; // Replace with your condition
        mainMenuButton.setDisable(!canReturnToMenu);
        mainMenuButton.setOpacity(canReturnToMenu ? 1.0 : 0.5);

        mainMenuButton.setOnAction(e -> {
            // Save game logic here
            root.getChildren().clear();
            primaryStage.setScene(createMainMenuScene());
        });

        VBox.setMargin(mainMenuButton, new Insets(50, 0, 0, 0));

        sidebar.getChildren().addAll(gameState, stepLabel, playerLabel, actionBox, mainMenuButton);
        root.getChildren().add(sidebar);
    }

    private void handleSelection(String[] selectedActions) {
        System.out.println("Selected actions: " + Arrays.toString(selectedActions));
        boolean valid = true;
        if (selectedActions[0]==null || selectedActions[1]==null || selectedActions[2]==null) {
            valid=false;
            System.out.println("Veuillez choisir 3 actions");
        }
        else if (selectedActions[0].equals(selectedActions[1]) || selectedActions[0].equals(selectedActions[2]) || selectedActions[2].equals(selectedActions[1])) {
            valid=false;
            System.out.println("Ce choix n'est pas valide, veuillez choisir 3 actions différentes");
        }
        if (valid==true){
            System.out.println("Le choix est valide ! ");
            //TODO relier au truc qui commence les actions
        }

    }


    // CREATION DES JOUEURS
    private Scene createPlayerSelectionScene() {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: black; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Choix des joueurs");
        title.setFont(Font.font(24));
        title.setStyle("-fx-text-fill: white;");

        List<String> availableColors = new ArrayList<>(Arrays.asList("Rouge", "Vert", "Bleu"));
        PlayerSetup[] players = new PlayerSetup[3];

        for (int i = 0; i < 3; i++) {
            players[i] = createPlayerSection(i + 1, availableColors);
        }

        Button validateButton = new Button("Valider");
        validateButton.setStyle("-fx-background-color: #404040; -fx-text-fill: white;");
        validateButton.setOnAction(e -> {
            if (validatePlayerChoices(players)) {
                // Start game with selected players
                startGame(primaryStage, players);
            }
        });

        root.getChildren().addAll(title);
        root.getChildren().addAll(Arrays.stream(players).map(p -> p.container).toArray(Node[]::new));
        root.getChildren().add(validateButton);

        return new Scene(root, 800, 600);
    }

    private static class PlayerSetup {
        VBox container;
        CheckBox virtualCheck;
        TextField nicknameField;
        ComboBox<String> colorChoice;
        boolean isVirtual;
    }

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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void startGame(Stage primaryStage, PlayerSetup[] players) {
        // Assign remaining colors to virtual players
        List<String> remainingColors = new ArrayList<>(Arrays.asList("Rouge", "Vert", "Bleu"));
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

        // Create and show game scene
        Scene gameScene = createNewGameScene();
        primaryStage.setScene(gameScene);
    }


    public static void main(String[] args) {
        launch();
    }
}