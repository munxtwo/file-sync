/**
 * 
 */
package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.MenuEventListeners;
import controller.MenuEventListeners.AboutListener;
import controller.MenuEventListeners.ExitListener;

/**
 * @author ktay
 *
 */
public class Menu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	private JMenu fileMenu;
	private JMenuItem exit;
	private JMenu helpMenu;
	private JMenuItem about;

	public Menu() {
		createFileMenu();
		createHelpMenu();
	}

	public void createFileMenu() {
		fileMenu = new JMenu("File");
		add(fileMenu);

		exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		
	}

	public void createHelpMenu() {
		helpMenu = new JMenu("Help");
		add(helpMenu);

		about = new JMenuItem("About FileSyncer");
		helpMenu.add(about);

	}
	
	public void addListeners(Panel panel) {
		ExitListener exitListener = new MenuEventListeners(panel).new ExitListener();
		exit.addActionListener(exitListener);
		
		AboutListener aboutListener = new MenuEventListeners(panel).new AboutListener();
		about.addActionListener(aboutListener);
	}
}
