import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * A class to handle Action Events related to the [run race] button.
 *
 * @author Randy Quimby
 * @version 1.0
 *
 *          COP4027 Project#: 4 File Name: RaceButtonHandler.java
 */
public class RaceButtonHandler implements EventHandler<ActionEvent> {
	/**
	 * The HorseRace object to start the animation
	 */
	private HorseRace horseRace;

	/**
	 * Constructs a RaceButtonHandler object to access the HorseRace class
	 *
	 * @param horseRace the horseRace object to start the animation
	 */
	public RaceButtonHandler(HorseRace horseRace) {
		this.horseRace = horseRace;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {
		horseRace.initRace();
	}

}