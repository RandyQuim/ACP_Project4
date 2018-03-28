import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 * Class to handle to Action Event of the reset race button.
 *
 * @author Randy Quimby
 * @version 1.0
 *
 *          COP4027 Project#: 4 File Name: ResetButtonHandler.java
 */
public class ResetButtonHandler implements EventHandler<ActionEvent> {
	/**
	 * The RaceButtonHandler object to access the horses ArrayList
	 */
	private RaceButtonHandler horses;
	/**
	 * The horse image
	 */
	private Image image;
	/**
	 * The begin race button
	 */
	private Button race;

	/**
	 * Constructs a ResetButtonHandler object to access needed variables as part of
	 * resetting the field
	 *
	 * @param image the horse image
	 * @param horses the RaceButtonHandler object to access the horses (to reset them)
	 * @param race the begin race button
	 */
	public ResetButtonHandler(Image image, RaceButtonHandler horses, Button race){
		this.horses = horses;
		this.image = image;
		this.race = race;
	}

	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {
		race.setDisable(false);
		ArrayList<GraphicsContext> gc = horses.getHorses();
		for (GraphicsContext element : gc) {
			element.clearRect(0, 0, 1250, 180);
			element.drawImage(image, -40, 0, 200, 170);
		}
	}

}
