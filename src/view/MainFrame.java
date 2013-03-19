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
	
//	private static MainFrame frame = new MainFrame();

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MainFrame() {
		super("File Syncer");
		createUI();
	}

//	public static MainFrame getMainFrame() {
//		return frame;
//	}

	/**
	 * Initialize frame
	 */
	public void createUI() {
		Panel panel = new Panel();
		Menu menu = new Menu();
		add(panel);
		setJMenuBar(menu);
	}
	
	public void setFrameSettings() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
