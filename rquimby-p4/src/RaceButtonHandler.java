import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

/**
 * A class to handle Action Events related to the [run race] button. Uses
 * threads to animate five horses in an inner class and provides global (shared)
 * variables to the threads. Holds the graphics context of each horse image.
 *
 * @author Randy Quimby
 * @version 1.0
 *
 *          COP4027 Project#: 4 File Name: RaceButtonHandler.java
 */
public class RaceButtonHandler implements EventHandler<ActionEvent> {
	/**
	 * A array of graphic context horse images
	 */
	private ArrayList<GraphicsContext> horses;
	/**
	 * The horse image
	 */
	private Image image;
	/**
	 * Generates random x coordinate changes to imitate movement and sleep times
	 * for threads
	 */
	private Random rand;
	/**
	 * Determines whether the horses should keep racing or come to a stop
	 */
	private boolean keepRacing;
	/**
	 * The lock for threads in a section of code (for race conditions)
	 */
	private ReentrantLock lock;
	/**
	 * The reset race button
	 */
	private Button reset;
	/**
	 * The begin race button
	 */
	private Button race;
	/**
	 * The StopWatch object to time the race
	 */
	private StopWatch stopWatch;

	/**
	 * Constructs a RaceButtonHandler object to set default values to instance
	 * fields, instantiate objects and access needed variables as a part of the
	 * race
	 *
	 * @param image the horse image
	 * @param reset the reset race button
	 * @param race the begin race button
	 */
	public RaceButtonHandler(Image image, Button reset, Button race) {
		this.horses = new ArrayList<GraphicsContext>();
		this.image = image;
		this.rand = new Random();
		this.keepRacing = true;
		this.lock = new ReentrantLock();
		this.reset = reset;
		this.race = race;
		this.stopWatch = new StopWatch();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {
		reset.setDisable(true);
		race.setDisable(true);
		int i = 0;
		while (i < horses.size()) {
			startAnimation(i);
			i++;
		}

		setKeepRacing(true);
	}

	/**
	 * Method to start the animation with use of an inner class for the horse
	 * race and use of threads
	 *
	 * @param i the counter to count the horses and ID them
	 */
	private void startAnimation(int i) {
		/**
		 * Inner class implements runnable to run several threads representing
		 * horses
		 *
		 * @author Randy Quimby
		 * @version 1.0
		 *
		 *          COP4027 Project#: 4 File Name: HorseRace
		 */
		class HorseRace implements Runnable {
			/**
			 * The x coordinate of the finish line
			 */
			private final int FINISH_LINE = 1070;

			/*
			 * (non-Javadoc)
			 *
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				int xCoordinate = -40;
				while (isKeepRacing()) {
					try {
						lock.lock();
						xCoordinate = moveHorse(i, xCoordinate);
						if (xCoordinate >= FINISH_LINE) {
							// Lambda needed to run an FX thread (an Alert)
							// after instantiated threads finish
							Platform.runLater(() -> winnerDialog(i + 1));
							setKeepRacing(false);
							stopWatch.stop();
							Thread.currentThread().interrupt();
						}

						lock.unlock();
						Thread.sleep(rand.nextInt(300) + 10);
						if (!isKeepRacing()) {
							Thread.currentThread().interrupt();
						}

					} catch (InterruptedException e) {
						System.out.println("Thread terminated");
					}
				}

				reset.setDisable(false);
			}

			/**
			 * Method to move the horses by changing their x coordinates on the
			 * GUI a random amount
			 *
			 * @param i the counter to count the horses and ID them
			 * @param xCoordinate the x coordinate of the horses
			 * @return the x coordinate of the horses
			 */
			private int moveHorse(int i, int xCoordinate) {
				horses.get(i).clearRect(xCoordinate, 0, image.getWidth(), image.getHeight());
				xCoordinate += rand.nextInt(30) + 5;
				horses.get(i).drawImage(image, xCoordinate, 0, 200, 170);
				return xCoordinate;
			}

		}

		Runnable r = new HorseRace();
		Thread t = new Thread(r);
		stopWatch.start();
		t.start();
	}

	/**
	 * An Alert dialog to notify the user of the winning horse and its race time
	 *
	 * @param horseNumber the horse ID
	 */
	private void winnerDialog(int horseNumber) {
		BigDecimal timeInSeconds = new BigDecimal(((double) (stopWatch.getElapsedTime()) / 1000));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.DECORATED);
		alert.setTitle("Announcing the winner");
		alert.setHeaderText(null);
		alert.setContentText("Horse number " + horseNumber + " is the winner!!\nTotal Time: "
				+ timeInSeconds.setScale(2, RoundingMode.HALF_UP) + " seconds.");
		alert.showAndWait();
		stopWatch.reset();
	}

	/**
	 * Method to add the horses' graphics context to an ArrayList
	 *
	 * @param gc the graphics context of the horse
	 */
	public void addHorse(GraphicsContext gc) {
		horses.add(gc);
	}

	/**
	 * Returns an array of graphic context horse images
	 *
	 * @return the array of graphic context horse images
	 */
	public ArrayList<GraphicsContext> getHorses() {
		return horses;
	}

	/**
	 * Returns the boolean value of whether the horses should keep racing or
	 * come to a stop
	 *
	 * @return the boolean value of whether the horses should keep racing or
	 *         come to a stop
	 */
	private boolean isKeepRacing() {
		return keepRacing;
	}

	/**
	 * Sets the boolean value of whether the horses should keep racing
	 *
	 * @param flag the boolean value of whether the horses should keep racing
	 */
	private void setKeepRacing(boolean flag) {
		this.keepRacing = flag;
	}

}