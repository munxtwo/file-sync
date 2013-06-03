package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import view.Panel;

public class MenuEventListeners {
	
	private static Logger logger = Logger.getLogger(MenuEventListeners.class);
	
	private Panel panel;
	
	public MenuEventListeners(Panel panel) {
		this.panel = panel;
	}

	/*
	 * Menu Listeners
	 */
	public class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
			logger.info("Exiting application");
		}

	}
	
	public class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String msg = "FileSyncer\n> Allows files to be synced between two folders\n" +
					"> Copy files from source folder to target folder\n" +
					"\nVersion: 1.1\n"
					+ "(c) Copyright munxtwo 2013\n";
			panel.dialogMsg(msg, "About FileSyncer", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
