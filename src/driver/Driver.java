/**
 * 
 */
package driver;

import java.awt.EventQueue;

import view.MainFrame;

/**
 * @author ktay
 *
 */
public class Driver {
	
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				showGUI();
			}
		});
	}

}
