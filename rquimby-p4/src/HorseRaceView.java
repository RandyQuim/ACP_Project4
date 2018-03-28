import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class Creates a stage and a scene for the horse race and related buttons.
 * Launches the application.
 *
 * @author Randy Quimby
 * @version 1.0
 *
 *          COP4027 Project#: 4 File Name: HorseRaceView.java
 */
public class HorseRaceView extends Application {
	/**
	 * The number of horses
	 */
	private static final int NUM_HORSES = 5;

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		GridPane gridPane = new GridPane();
		Button race = new Button("[run race]");
		Button exit = new Button("[quit program]");
		Button reset = new Button("[reset race]");
		Image image = new Image("horse.gif");
		RaceButtonHandler raceAction = new RaceButtonHandler(image, reset, race);
		exit.setOnAction(new ExitButtonHandler());
		race.setOnAction(raceAction);
		reset.setOnAction(new ResetButtonHandler(image, raceAction, race));

		FlowPane flowPane = new FlowPane();
		flowPane.getChildren().addAll(race, reset, exit);
		flowPane.setHgap(50);
		gridPane.getChildren().add(flowPane);
		root.getChildren().add(gridPane);

		for (int i = 1; i <= NUM_HORSES; i++) {
			Canvas canvas = new Canvas(1250, 180);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			image = new Image("horse.gif");
			gc.drawImage(image, -40, 0, 200, 170);

			raceAction.addHorse(gc);

			GridPane.setRowIndex(canvas, i);
			GridPane.setColumnIndex(canvas, 0);
			gridPane.getChildren().add(canvas);
		}

		Scene scene = new Scene(root, 1250, 950);
		stage.setTitle("Horse Race");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
