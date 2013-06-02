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

import org.apache.log4j.Logger;

import view.Panel;

/**
 * @author ktay
 * 
 */
public class EventListeners {
	
	private static Logger logger = Logger.getLogger(EventListeners.class);

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
	
	
	/*
	 * Panel listeners
	 */
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
			logger.info("Source folder: " + path);
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
				logger.error("Target path cannot be the same as source path!");
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
			logger.info("Target folder: " + path);
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
				logger.error("Target path cannot be the same as source path!");
				return;
			}
			else if (sourcePath.isEmpty() || targetPath.isEmpty()) {
				return;
			}
			
			File[] selectedFiles = panel.sourceFileChooser.getSelectedFiles();
			if (selectedFiles.length == 0) {
				panel.dialogMsg(
						"No files or directory selected!\n" +
						"Please select a file or directory first OR choose the '>>' (sync all) option",
						"ERROR!", JOptionPane.ERROR_MESSAGE);
				logger.error("No files or directory selected!");
				return;
			}
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
				logger.error("Target path cannot be the same as source path!");
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
//                    if (task.isCancelled()){
//                    	logger.info("Sync cancelled!\n");
//                    } 
//                    else if (task.isDone()) {
//                    }
//                    logger.info("Sync completed!\n");
                    panel.progressMonitor.close();
//                    setSyncButtonsEnabled(true);
	            }
	        }			
		}
		
	}

}
