/**
 * 
 */
package driver;

import java.awt.EventQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import view.MainFrame;

/**
 * @author ktay
 *
 */
public class Driver {
	
	private static Logger logger = Logger.getLogger(Driver.class);
	
	public static void showGUI() {
		MainFrame frame = new MainFrame();
		frame.pack();
		frame.setFrameSettings();
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log.properties");
		logger.info("Starting application");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				showGUI();
			}
		});
	}

}
