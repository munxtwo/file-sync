/**
 * 
 */
package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import lib.GBC;
import controller.EventListeners;
import controller.EventListeners.BrowseSourceButtonListener;
import controller.EventListeners.BrowseTargetButtonListener;

/**
 * @author ktay
 * 
 */
public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JScrollPane sourceScrollPane;
	public JScrollPane targetScrollPane;
	public JButton browseSourceButton;
	public JButton browseTargetButton;
	public JButton syncSelectedButton;
	public JButton syncAllButton;
	public JFileChooser sourceFileChooser;
	public JFileChooser targetFileChooser;
	
	
	public Panel() {
		setLayout(new GridBagLayout());
		// Buttons
		createBrowseSourceButton();
		createBrowseTargetButton();
//		createSyncSelectedButton();
//		createSyncAllButton();
//		createPanelDivider();
	}
	
	public void createBrowseSourceButton() {
		String browseSourceButtonLabel = "Choose Source Folder";
		String browseSourceButtonToolTipText = "The source folder contains the files you would like to sync.";
		browseSourceButton = new JButton(browseSourceButtonLabel);
		browseSourceButton.setToolTipText(browseSourceButtonToolTipText);
		
		BrowseSourceButtonListener browseSourceButtonListener = new EventListeners(this).new BrowseSourceButtonListener();
		browseSourceButton.addActionListener(browseSourceButtonListener);
		
		add(browseSourceButton, new GBC(0, 0, 1, 1)
			.setAnchor(GridBagConstraints.NORTHWEST)
			.setWeight(1, 0.05)
			.setFill(GridBagConstraints.NONE)
			.setInsets(5));
	}
	
	public void createBrowseTargetButton() {
		String browseTargetButtonLabel = "Choose Target Folder";
		String browseTargetButtonToolTipText = "The target folder is the folder you would like to sync files to.";
		browseTargetButton = new JButton(browseTargetButtonLabel);
		browseTargetButton.setToolTipText(browseTargetButtonToolTipText);
		browseTargetButton.setEnabled(false);
		
		BrowseTargetButtonListener browseTargetButtonListener = new EventListeners(this).new BrowseTargetButtonListener();
		browseTargetButton.addActionListener(browseTargetButtonListener);
		
		add(browseTargetButton, new GBC(4, 0, 1, 1)
			.setAnchor(GridBagConstraints.NORTHWEST)
			.setWeight(1, 0.05)
			.setFill(GridBagConstraints.NONE)
			.setInsets(5));
	}
	
	public void createSyncSelectedButton() {
		String syncSelectedButtonLabel = ">";
		String syncSelectedButtonToolTipText = "Sync selected files only.";
		syncSelectedButton = new JButton(syncSelectedButtonLabel);
		syncSelectedButton.setToolTipText(syncSelectedButtonToolTipText);
		
		add(syncSelectedButton, new GBC(3, 4, 1, 1)
			.setAnchor(GridBagConstraints.LINE_END)
			.setWeight(0, 0)
			.setFill(GridBagConstraints.HORIZONTAL)
			.setInsets(5));
	}
	
	public void createSyncAllButton() {
		String syncAllButtonLabel = ">>";
		String syncAllButtonToolTipText = "Sync all files in folder.";
		syncAllButton = new JButton(syncAllButtonLabel);
		syncAllButton.setToolTipText(syncAllButtonToolTipText);
		
		add(syncAllButton, new GBC(3, 7, 1, 1)
			.setAnchor(GridBagConstraints.LINE_START)
			.setWeight(0, 0)
			.setFill(GridBagConstraints.HORIZONTAL)
			.setInsets(5));
	}
	
	public void createFillerButton(int gridx, int gridy, int widthx, int widthy) {
		JButton fillerButton = new JButton(" ");
		fillerButton.setBorder(null);
		fillerButton.setOpaque(false);
		fillerButton.setContentAreaFilled(false);
		fillerButton.setBorderPainted(false);
		
		add(fillerButton, new GBC(gridx, gridy, widthx, widthy)
			.setAnchor(GridBagConstraints.LINE_START)
			.setWeight(0, 0)
			.setFill(GridBagConstraints.HORIZONTAL)
			.setInsets(5));
	}
	
	public JFileChooser createFileChooser(String path) {
		JFileChooser fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setControlButtonsAreShown(false);
		fileChooser.setMultiSelectionEnabled(true);
		return fileChooser;
	}

	public void createSourcePane(String path) {
		sourceFileChooser = createFileChooser(path);
		sourceScrollPane = new JScrollPane(sourceFileChooser);
		
		add(sourceScrollPane, new GBC(0, 1, 3, 9)
			.setAnchor(GridBagConstraints.FIRST_LINE_START)
			.setFill(GridBagConstraints.BOTH)
			.setWeight(1, 1)
			.setInsets(0, 5, 5, 5));
	}
	
	public void createTargetPane(String path) {
		targetFileChooser = createFileChooser(path);
		targetScrollPane = new JScrollPane(targetFileChooser);
		
		add(targetScrollPane, new GBC(4, 1, 3, 9)
			.setAnchor(GridBagConstraints.FIRST_LINE_END)
			.setFill(GridBagConstraints.BOTH)
			.setWeight(1, 1)
			.setInsets(0, 5, 5, 5));
	}
	
	public void createPanelDivider() {
		add(new JSeparator(JSeparator.VERTICAL), new GBC(3, 0, 1, 10)
			.setAnchor(GridBagConstraints.CENTER)
			.setFill(GridBagConstraints.BOTH)
			.setInsets(0, 5, 5, 5));
	}

}
