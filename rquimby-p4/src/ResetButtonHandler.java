import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Class to handle the Action Event of the reset race button.
 *
 * @author Randy Quimby
 * @version 1.0
 *
 *          COP4027 Project#: 4 File Name: ResetButtonHandler.java
 */
public class ResetButtonHandler implements EventHandler<ActionEvent> {
	/**
	 * The HorseRace object to access the horses ArrayList
	 */
	private HorseRace horseRace;
	/**
	 * The label for the status of the race application
	 */
	private Label status;
	/**
	 * The begin race button
	 */
	private Button race;

	/**
	 * Constructs a ResetButtonHandler object to access needed variables as part of
	 * resetting the field
	 *
	 * @param race the begin race button
	 * @param status the label for the status of the application
	 * @param horseRace the HorseRace object to access the horses (to reset them)
	 */
	public ResetButtonHandler(Button race, Label status, HorseRace horseRace) {
		this.horseRace = horseRace;
	    this.status = status;
		this.race = race;
	}

	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {
		race.setDisable(false);
		status.setText("Click the [run race] button to begin!");
		ArrayList<GraphicsContext> gc = horseRace.getHorses();
		for (GraphicsContext element : gc) {
			element.clearRect(0, 0, 1250, 180);
			element.drawImage(horseRace.getImage(), -40, 0, 200, 170);
			element.setStroke(Color.BLACK);
			element.setLineWidth(2);
			element.strokeRect(0, 0, 1250, 180);
		}
	}

}
