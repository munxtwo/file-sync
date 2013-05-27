package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.Panel;

public class MenuEventListeners {
	
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
		}

	}
	
	public class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String msg = "FileSyncer\n> Allows files to be synced between two folders\n" +
					"> Copy files from source folder to target folder\n" +
					"\nVersion: 1.0\n"
					+ "(c) Copyright munxtwo 2013\n";
			panel.dialogMsg(msg, "About FileSyncer", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
