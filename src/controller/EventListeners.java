/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import view.Panel;

/**
 * @author ktay
 * 
 */
public class EventListeners {

	private Panel panel;

	public EventListeners(Panel panel) {
		this.panel = panel;
	}

	public String getFolderPath() {
		String chosenDirPath = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = fileChooser.showOpenDialog(panel);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File chosenDir = fileChooser.getSelectedFile();
			chosenDirPath = chosenDir.getAbsolutePath();
		}
		return chosenDirPath;
	}

	public class BrowseSourceButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = getFolderPath();
			panel.createSourcePane(path);
			panel.browseTargetButton.setEnabled(true);
			panel.updateUI();
		}
	}

	public class BrowseTargetButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = getFolderPath();
			String sourcePath = panel.sourceFileChooser.getCurrentDirectory()
					.getAbsolutePath();
			if (path.equals(sourcePath)) {
				panel.dialogMsg(
						"Target path cannot be the same as source path!",
						"ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			panel.createTargetPane(path);
			panel.createSyncSelectedButton();
			panel.createSyncAllButton();
			panel.createFillerButton(3, 2, 1, 2);
			panel.createFillerButton(3, 5, 1, 2);
			panel.updateUI();
		}
	}
	
	public class SyncSelectedButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class SyncAllButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
