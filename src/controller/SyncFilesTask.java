package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import view.Panel;

public class SyncFilesTask extends SwingWorker<Boolean, Double> {
	
	private static Logger logger = Logger.getLogger(SyncFilesTask.class);

	private Panel panel;
	private File[] files;
	private int progress;
	private File targetDir;
	private int sourceDirNameLength;
	private int numFiles;
	private int count;

	public SyncFilesTask(Panel panel, File[] files) {
		this.panel = panel;
		this.files = files;
		this.progress = 0;
		this.targetDir = panel.targetFileChooser.getCurrentDirectory();
		this.sourceDirNameLength = panel.sourceFileChooser.getCurrentDirectory().getAbsolutePath().length();
		this.numFiles = files.length;
		this.count = 0;
	}

	@Override
	public Boolean doInBackground() throws Exception {
		logger.info("Starting sync ...");
		progress = 1;
		setProgress(progress);

		for (File file : files) {
			count++;
			if (!isCancelled()) {
				syncFile(file);
				progress = (int) (count / (double) numFiles * 100);
				setProgress(progress);
				// System.out.println("count: " + count + "; length: " +
				// files.length + "; progress: " + progress);
			}
		}
		panel.targetFileChooser.rescanCurrentDirectory();
		return null;
	}

	@Override
	public void done() {
		if (!isCancelled()) {
			progress = 100;
			setProgress(progress);
			panel.dialogMsg("All files successfully synced!\n" +
					"Logs can be found at: " + System.getProperty("user.dir") + File.separator + "FileSyncer.log", "Sync Complete!",
					JOptionPane.INFORMATION_MESSAGE);
			logger.info("Sync completed!\n");
		}
		else {
			logger.info("Sync cancelled!\n");
		}
		panel.syncAllButton.setEnabled(true);
		panel.syncSelectedButton.setEnabled(true);
		panel.sourceFileChooser.setSelectedFiles(new File[] { new File("") });
	}
	
	private void syncFile(File sourceFile) {
		String sourceFileName = sourceFile.getAbsolutePath().substring(sourceDirNameLength);
		File targetFile = new File(targetDir + File.separator
				+ sourceFileName);
		if (!isCancelled()) {
			if (targetFile.exists()) {
				if (sourceFile.isDirectory()) {
					File[] sourceFileList = sourceFile.listFiles();
					numFiles = numFiles + sourceFileList.length;
					for (File file : sourceFileList) {
						count++;
						syncFile(file);
						progress = (int) (count / (double) numFiles * 100);
						setProgress(progress);
					}
				} else {
					syncExistingFile(sourceFile, targetFile);
				}
			} else {
				syncNewFile(sourceFile, targetFile);
			}
		}
	}

	private void syncExistingFile(File file, File targetFile) {
		if (targetFile.exists() && !isCancelled()) {
			if (FileUtils.isFileNewer(targetFile, file)) {
				int option = panel.confirmDialog("The file '" + targetFile
						+ "' already exists"
						+ "\nand is newer than the file you wish to sync with."
						+ "\nDo you wish to overwrite this file?",
						"Target File Is Newer!", JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.NO_OPTION) {
					logger.info("Skipping '" + file + "' because it already exists and is newer");
					return;
				}
			} else
				try {
					if (FileUtils.contentEquals(file, targetFile)) {
						logger.info("Skipping '" + file + "' because it already exists and is the same");
						return;
					}
				} catch (IOException e) {
					panel.dialogMsg(
							"An error occurred when copying '" + file.getAbsolutePath()
									+ "'", "ERROR!", JOptionPane.ERROR_MESSAGE);
					logger.error("Could not copy '" + file.getAbsolutePath() + "'");
					e.printStackTrace();
				}
		}
		syncNewFile(file, targetFile);
	}

	private void syncNewFile(File file, File targetFile) {
		try {
			if (file.isDirectory()) {
				FileUtils.copyDirectory(file, targetFile);
			} else {
				FileUtils.copyFile(file, targetFile);
			}
		} catch (IOException e) {
			panel.dialogMsg(
					"An error occurred when copying '" + file.getAbsolutePath()
							+ "'", "ERROR!", JOptionPane.ERROR_MESSAGE);
			logger.error("Could not copy '" + file.getAbsolutePath() + "'");
			e.printStackTrace();
		}
		logger.info("Copied '" + file + "' to '" + targetFile + "'");
	}

}
