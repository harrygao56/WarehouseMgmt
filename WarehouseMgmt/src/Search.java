import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public abstract class Search extends JPanel {
	public HintTextField searchBar;
	public GUI gui;
	private JScrollPane pane;
	public JPanel panel;
	private JLabel searchButton;
	private JLabel cancel;

	public Search(GUI gui) {
		this.gui = gui;

		setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(grid);

		// Initialize search bar that says "Search" in the background
		searchBar = new HintTextField("Search");
		searchBar.setPreferredSize(new Dimension(584, 30));

		// Initialize Search Button
		searchButton = new JLabel("Search", SwingConstants.CENTER);
		searchButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		searchButton.setOpaque(true);
		searchButton.setBackground(Color.cyan);
		searchButton.setPreferredSize(new Dimension(85, 30));
		searchButton.setForeground(Color.gray);
		// Creating border
		searchButton.setBorder(BorderFactory.createLineBorder(Color.gray));
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				search();
			}

			public void mouseEntered(MouseEvent e) {
				searchButton.setBackground(Color.white);
				searchButton.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				searchButton.setBackground(Color.cyan);
				searchButton.setForeground(Color.gray);
			}
		});

		// Initialize Cancel Button
		cancel = new JLabel("Cancel", SwingConstants.CENTER);
		cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		cancel.setOpaque(true);
		cancel.setBackground(Color.cyan);
		cancel.setPreferredSize(new Dimension(85, 30));
		cancel.setForeground(Color.gray);
		// Creating border
		cancel.setBorder(BorderFactory.createLineBorder(Color.gray));
		Search s = this;
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(s);
				gui.add(gui.getMenu());
				gui.revalidate();
				gui.repaint();
			}

			public void mouseEntered(MouseEvent e) {
				cancel.setBackground(Color.white);
				cancel.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				cancel.setBackground(Color.cyan);
				cancel.setForeground(Color.gray);
			}
		});

		panel = new JPanel();
		panel.setBackground(Color.white);

		pane = new JScrollPane(panel);
		pane.setPreferredSize(new Dimension(775, 450));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 10, 10);
		add(searchBar, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		add(searchButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		add(cancel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		add(pane, gbc);
	}
	
	public abstract void search();
	
	public GUI getGUI() {
		return gui;
	}

	public void display() {
		gui.add(this);
		gui.revalidate();
		gui.repaint();
	}
}
