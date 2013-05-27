/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
	private SyncFilesTask task;

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
			if (path.isEmpty()) {
				return;
			}
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
			if (isSourceDirEqualsTargetDir(sourcePath, path)) {
				panel.dialogMsg(
						"Target path cannot be the same as source path!",
						"ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if (path.isEmpty()) {
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
			String sourcePath = panel.sourceFileChooser.getCurrentDirectory()
					.getAbsolutePath();
			String targetPath = panel.targetFileChooser.getCurrentDirectory()
					.getAbsolutePath();
			if (isSourceDirEqualsTargetDir(sourcePath, targetPath)) {
				panel.dialogMsg(
						"Target path cannot be the same as source path!",
						"ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if (sourcePath.isEmpty() || targetPath.isEmpty()) {
				return;
			}
			
			File[] selectedFiles = panel.sourceFileChooser.getSelectedFiles();
			syncFiles(selectedFiles);
		}

	}

	public class SyncAllButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String sourcePath = panel.sourceFileChooser.getCurrentDirectory()
					.getAbsolutePath();
			String targetPath = panel.targetFileChooser.getCurrentDirectory()
					.getAbsolutePath();
			if (isSourceDirEqualsTargetDir(sourcePath, targetPath)) {
				panel.dialogMsg(
						"Target path cannot be the same as source path!",
						"ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			} 
			else if (sourcePath.isEmpty() || targetPath.isEmpty()) {
				return;
			}
			
			File[] allFiles = panel.sourceFileChooser.getCurrentDirectory().listFiles();
			syncFiles(allFiles);
		}

	}
	
	public class SyncFilesProgressListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName() ) {
	            int progress = (Integer) evt.getNewValue();
	            panel.progressMonitor.setProgress(progress);
	            String message = String.format("Completed %d%%.\n", progress);
	            panel.progressMonitor.setNote(message);
	            
	            if (panel.progressMonitor.isCanceled() || task.isDone()) {
	                if (panel.progressMonitor.isCanceled()) {
	                    task.cancel(true);
	                } 
	                setSyncButtonsEnabled(true);
	            }
	        }			
		}
		
	}

	private boolean isSourceDirEqualsTargetDir(String sourcePath,
			String targetPath) {
		if (sourcePath.equals(targetPath)) {
			return true;
		}
		return false;
	}

	private void setSyncButtonsEnabled(boolean isEnabled) {
		panel.syncSelectedButton.setEnabled(isEnabled);
		panel.syncAllButton.setEnabled(isEnabled);
	}
	
	private void syncFiles(File[] files) {
		panel.createProgressMonitor();
		task = new SyncFilesTask(panel, files);
		task.addPropertyChangeListener(new EventListeners.SyncFilesProgressListener());
		task.execute();
		setSyncButtonsEnabled(false);
	}
}
