import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	/**
	 * The width of the race lane border
	 */
	private static final int LINE_WIDTH = 1;

	@Override
	public void start(Stage stage) throws Exception {
		Button race = new Button("[run race]");
		Button exit = new Button("[quit program]");
		Button reset = new Button("[reset race]");
		race.setStyle("-fx-base: #f6e7c5;");
		exit.setStyle("-fx-base: #f6e7c5;");
		reset.setStyle("-fx-base: #f6e7c5;");
		InnerShadow isEffect = new InnerShadow();
		isEffect.setOffsetX(3);
		isEffect.setOffsetY(3);
		isEffect.setColor(Color.GRAY);
		race.setEffect(isEffect);
		reset.setEffect(isEffect);
		exit.setEffect(isEffect);

		Group root = new Group();
		GridPane gridPane = new GridPane();

		Label label = new Label("Click the [run race] button to begin!");
		label.setFont(new Font(26.0));
		Image image = new Image("horse.gif");
		HorseRace horseRace = new HorseRace(image, reset, race, label);
		exit.setOnAction(new ExitButtonHandler());
		race.setOnAction(new RaceButtonHandler(horseRace));
		reset.setOnAction(new ResetButtonHandler(race, label, horseRace));

		FlowPane flowPane = new FlowPane();
		flowPane.getChildren().addAll(race, reset, exit, label);
		flowPane.setHgap(45);
		gridPane.getChildren().add(flowPane);

		for (int i = 1; i <= NUM_HORSES; i++) {
			Canvas canvas = new Canvas(1250, 180);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			image = new Image("horse.gif");
			gc.drawImage(image, -40, 0, 200, 170);

			horseRace.addHorse(gc);
			horseRace.drawLines(i - 1, Color.BLACK, LINE_WIDTH);

			GridPane.setRowIndex(canvas, i);
			GridPane.setColumnIndex(canvas, 0);
			gridPane.getChildren().add(canvas);
		}

		root.getChildren().add(gridPane);

		Scene scene = new Scene(root, 1250, 950);
		stage.setTitle("Horse Race");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
