/**
 * 
 */
package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lib.GBC;

/**
 * @author ktay
 * 
 */
public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Panel() {
		setLayout(new GridBagLayout());
		createSourceFolder();
		createDestFolder();
	}

	public void createSourceFolder() {
		JScrollPane scrollPaneSource = new JScrollPane(new JFileChooser("C:/"));
		add(scrollPaneSource, new GBC(0, 5, 3, 1)
			.setAnchor(GridBagConstraints.LAST_LINE_START)
			.setFill(GridBagConstraints.BOTH).setWeight(0.5, 0.5)
			.setInsets(0, 5, 5, 5));
	}
	
	public void createDestFolder() {
		JScrollPane scrollPaneDest = new JScrollPane(new JFileChooser("C:/"));
		add(scrollPaneDest, new GBC(3, 5, 3, 1)
			.setAnchor(GridBagConstraints.LAST_LINE_END)
			.setFill(GridBagConstraints.BOTH).setWeight(0.5, 0.5)
			.setInsets(0, 5, 5, 5));
	}
	
	

}
