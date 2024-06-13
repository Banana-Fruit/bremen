package resources;

import control.scenes.MainMenuController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.userInterface.TransparentButton;
import resources.constants.Constants_MainMenu;


public interface GameMenuBar
{

    static MenuBar createMenuBarWithTwoPoints(Stage stage, String point1, String point2,
                                              String menuItem1, String menuItem2)
    {
        MenuBar menuBar = new MenuBar();
        // define points of the menu bar
        Menu menuGame = new Menu(point1);
        Menu menuSettings = new Menu(point2);
        // add the points to the menu bar
        menuBar.getMenus().addAll(menuGame, menuSettings);

        // define menuItems for the point menuGame and add them
        MenuItem finishItem = new MenuItem(menuItem1);
        MenuItem loadItem = new MenuItem(menuItem2);
        menuGame.getItems().addAll(finishItem, loadItem);

        // close the game
        closeTheGame(finishItem);
        // load the game
        loadTheGame(stage, loadItem);


        return menuBar;
    }

    //_______________________________close Button________________________________________
    private static void closeTheGame(MenuItem finish)
    {
        finish.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                closeGame();
            }
        });
    }


    /**
     * Methode zum Abfragen und Beenden des Spiels
     *
     * @author Jonas Helfer
     */
    static void closeGame()
    {
        // Todo: Dialog nicht doppelt anzeigen ohne dieses boolean?
        final boolean[] dialogShown = {false};
        if (dialogShown[0])
        {
            return;
        }
        Stage dialogStage = new Stage();
        VBox dialogVbox = new VBox();
        dialogVbox.setSpacing(Constants_MainMenu.VBOX_SPACE_BETWEEN_CHOICE_AND_TEXT);
        HBox buttonBox = new HBox();
        Scene dialogScene = new Scene(dialogVbox, Constants_MainMenu.DIALOG_SCENE_WIDTH,
                Constants_MainMenu.DIALOG_SCENE_HEIGHT);
        // set a dialog window with a text message
        setDialogWindow(dialogStage, dialogVbox);

        // Text message whether to close the game
        Text message = new Text(Constants_MainMenu.MESSAGE_CLOSE_GAME);
        message.setFill(Color.WHITE);

        // Yes-Button to commit the text message
        TransparentButton yesButton = new TransparentButton(Constants_MainMenu.YES_BUTTON, () -> {
            Platform.exit();
            dialogStage.close();
        },
                Constants_MainMenu.RC_WIDTH, Constants_MainMenu.RC_HEIGHT, Constants_MainMenu.LINEAR_GRADIENT_OPACITY, Constants_MainMenu.LINEAR_GRADIENT_OPACITY_W);

        // No-Button to reject the text message
        TransparentButton noButton = new TransparentButton(Constants_MainMenu.NO_BUTTON, () -> {
            dialogStage.close();
            dialogShown[0] = false;
        },
                Constants_MainMenu.RC_WIDTH, Constants_MainMenu.RC_HEIGHT, Constants_MainMenu.LINEAR_GRADIENT_OPACITY, Constants_MainMenu.LINEAR_GRADIENT_OPACITY_W);

        // HBox with yes and no button
        arrangeTwoButtonsHorizontal(buttonBox, yesButton, noButton, Constants_MainMenu.SPACE_BETWEEN_YES_NO_BOXES);

        // Add message and buttons to the VBox
        dialogVbox.getChildren().addAll(message, buttonBox);



        // Set the dialog Background black
        Background background = new Background(new BackgroundFill(Color.rgb(Constants_MainMenu.RGB_SCHWARZ, Constants_MainMenu.RGB_SCHWARZ, Constants_MainMenu.RGB_SCHWARZ, Constants_MainMenu.LINEAR_GRADIENT_OPACITY), CornerRadii.EMPTY, Insets.EMPTY));
        // Set the dialog background with an image
        //Background background = createBackground(Constants_MenuSetting.PATH_BACKGROUND_IMAGE, dialogStage.getWidth(), dialogStage.getHeight());
        dialogVbox.setBackground(background);

        showSceneOnStage(dialogScene, dialogStage);
        dialogShown[0] = true;
    }
    private static Background createBackground (String path, double sceneWidth, double sceneHeight) throws IllegalArgumentException
    {
        Image imgBackground = new Image(path);
        BackgroundSize backgroundSize = new BackgroundSize(sceneWidth, sceneHeight, false, false, true, false);
        return new Background(new BackgroundImage(
                imgBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                backgroundSize));
    }




    private static void setDialogWindow(Stage dialogStage, VBox dialogVbox)
    {
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setTitle(Constants_MainMenu.STAGE_TITLE);
        //set position of the vbox
        dialogVbox.setAlignment(Pos.CENTER);
    }


    private static void arrangeTwoButtonsHorizontal(HBox buttonBox, TransparentButton button1, TransparentButton button2, int space)
    {
        //Position of the HBox
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(space);
        //add the MenuItems
        buttonBox.getChildren().addAll(button1, button2);
    }

    private static void showSceneOnStage(Scene dialogScene, Stage dialogStage)
    {
        dialogScene.setFill(Color.TRANSPARENT);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }


    //_________________________________load Button___________________________________________


    private static void loadTheGame(Stage stage, MenuItem loadItem)
    {
        loadItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                loadGame(stage);
            }
        });
    }

    static void loadGame(Stage stage)
    {
        Pane root = new Pane();
        Scene scene = new Scene(root, Constants_MainMenu.SCENE_WIDTH, Constants_MainMenu.SCENE_HEIGHT);

        Background background = createBackground(Constants_MainMenu.PATH_BACKGROUND_IMAGE, stage.getWidth(), stage.getHeight());
        root.setBackground(background);

        // creates a gridpane
        GridPane gridPane = createGridPaneForLoadGame(Constants_MainMenu.GRIDPANE_WIDTH, Constants_MainMenu.GRIDPANE_HEIGHT,
                Constants_MainMenu.GRIDPANE_TRANSLATE_Y, Constants_MainMenu.GRIDPANE_GAP);

        // creates four items in the gridpane for the different game loads
        createMenuItemsForGameLoads(gridPane);

        // creates a TilePane with the option to go back
        createTilePaneToGoBack(root, gridPane);


        stage.setScene(scene);
        stage.show();
    }


    static GridPane createGridPaneForLoadGame (int width, int height, int translateY, int gap)
    {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPrefSize(width, height);
        gridPane.setTranslateY(translateY);
        gridPane.setHgap(gap);
        gridPane.setVgap(gap);
        return gridPane;
    }


    static void createMenuItemsForGameLoads (GridPane gridPane)
    {
        TransparentButton[] saveGameItems = createGameLoadsItems(Constants_MainMenu.NUMBER_OF_GAMES);

        addItemsToGridpane(saveGameItems, gridPane);
    }


    private static TransparentButton[] createGameLoadsItems(int numberOfGames)
    {
        TransparentButton[] saveGameItems = new TransparentButton[numberOfGames];
        
        //Todo: Namensanpassung der Spielstände, sodass statt Spielstand 1 dort ein Eigenname steht
        for (int i = Constants_MainMenu.START_LOOP; i < numberOfGames; i++)
        {
            String saveGameName = Constants_MainMenu.SAVE_GAMES + (i + Constants_MainMenu.ONE); // name of the game load
            TransparentButton saveGameItem = new TransparentButton(saveGameName, () ->
            {
            }, Constants_MainMenu.GAME_LOAD_ITEM_WIDTH,
                    Constants_MainMenu.GAME_LOAD_ITEM_HEIGHT, Constants_MainMenu.LINEAR_GRADIENT_OPACITY, Constants_MainMenu.LINEAR_GRADIENT_OPACITY_W);

            saveGameItems[i] = saveGameItem;
        }
        
        return saveGameItems;
    }


    private static void addItemsToGridpane (TransparentButton[] saveGameItems, GridPane gridPane)
    {
        for (int i = Constants_MainMenu.START_LOOP; i < Constants_MainMenu.NUMBER_OF_GAMES; i++)
        {
            GridPane.setConstraints(saveGameItems[i], i % Constants_MainMenu.TWO, i / Constants_MainMenu.TWO); // set position on the GridPane
            gridPane.getChildren().add(saveGameItems[i]);
        }
    }


    static void createTilePaneToGoBack (Pane root, GridPane gridPane)
    {
        // org.example.bremen.Tile-Pane for the GoBack-Button
        TilePane goBackPane = new TilePane();

        // defines position and size of the tilePane
        defineTilePane(goBackPane);

        // creates goBack button
        TransparentButton backButton = new TransparentButton(Constants_MainMenu.BACK_BUTTON_NAME, () ->
        {
            // Define the action for the goBack button
            try {
                // Assuming MainMenuController has a method to start the main menu scene
                MainMenuController.getInstance().addButtons();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }, Constants_MainMenu.BACK_BUTTON_WIDTH, Constants_MainMenu.BACK_BUTTON_HEIGHT, Constants_MainMenu.LINEAR_GRADIENT_OPACITY, Constants_MainMenu.LINEAR_GRADIENT_OPACITY_W);
        //adds button to the TilePane
        goBackPane.getChildren().addAll(backButton);
        //adds TilePane and GridPane to the Pane root
        root.getChildren().addAll(gridPane, goBackPane);
    }


    private static void defineTilePane(TilePane tilePane)
    {
        tilePane.setAlignment(Pos.BOTTOM_CENTER);
        tilePane.setTranslateY(Constants_MainMenu.TILEPANE_TRANSLATE_Y);
        tilePane.setPrefSize(Constants_MainMenu.TILEPANE_WIDTH, Constants_MainMenu.TILEPANE_HEIGHT);
    }
}
