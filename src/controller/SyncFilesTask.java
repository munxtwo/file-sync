package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import view.Panel;

public class SyncFilesTask extends SwingWorker<Boolean, Double> {
	
	private Panel panel;
	private File[] files;
	
	public SyncFilesTask(Panel panel, File[] files) {
		this.panel = panel;
		this.files = files;
	}

	@Override
	public Boolean doInBackground() throws Exception {
		int progress = 0;
		int count = 0;
		File targetDir = panel.targetFileChooser.getCurrentDirectory();
		setProgress(1);
		
		for (File file : files) {
			@SuppressWarnings("static-access")
			File targetFile = new File(targetDir + file.separator + file.getName());
			count++;
			if (targetFile.exists()) {
				int option = panel.confirmDialog("The file '" + targetFile + "' already exists.\nDo you wish to overwrite this file?", 
						"File exists!", JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.NO_OPTION) {
					continue;
				}
			}
			if (!isCancelled()) {
				try {
					if (file.isDirectory()) {
						FileUtils.copyDirectoryToDirectory(file, targetDir);
					} else {
						FileUtils.copyFileToDirectory(file, targetDir);
					}
					progress = (int) (count/(double) files.length * 100);
					setProgress(progress);
//					System.out.println("count: " + count + "; length: " + files.length + "; progress: " + progress);
				} catch (IOException e1) {
					panel.dialogMsg(
							"An error occurred when copying '"
									+ file.getAbsolutePath() + "'", "ERROR!",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
		panel.targetFileChooser.rescanCurrentDirectory();
		return null;
	}
	
	@Override
	public void done() {
		setProgress(100);
		panel.dialogMsg(
				"All files successfully synced!", "Sync Complete!",	JOptionPane.INFORMATION_MESSAGE);
		panel.syncAllButton.setEnabled(true);
		panel.syncSelectedButton.setEnabled(true);
		panel.sourceFileChooser.setSelectedFiles(new File[]{new File("")});
	}

}
