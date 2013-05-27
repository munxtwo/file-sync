/**
 * 
 */
package view;

import javax.swing.JFrame;

/**
 * @author ktay
 *
 */
public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MainFrame() {
		super("File Syncer");
		createUI();
	}

	/**
	 * Initialize frame
	 */
	public void createUI() {
		Panel panel = new Panel();
		Menu menu = new Menu();
		add(panel);
		setJMenuBar(menu);
		menu.addListeners(panel);
	}
	
	public void setFrameSettings() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
