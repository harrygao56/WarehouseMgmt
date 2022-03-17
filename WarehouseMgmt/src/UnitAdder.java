import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;

public class UnitAdder extends JFrame {
	private JPanel panel;
	private JTextField fields[];
	private String descriptions[];

	public UnitAdder() {
		setTitle("New Unit");

		panel = new JPanel();
		panel.setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(grid);

		// Initializing fields and descriptions
		fields = new JTextField[4];
		descriptions = new String[4];

		// Make exlusive to ints
		fields[0] = new JFormattedTextField();
		fields[0].setPreferredSize(new Dimension(140, 30));
		descriptions[0] = "Unit Number";
		fields[0].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					fields[0].setEditable(true);
				} else {
					fields[0].setEditable(false);
				}
			}
		});
		
		fields[1] = new JFormattedTextField();
		fields[1].setPreferredSize(new Dimension(140, 30));
		descriptions[1] = "Unit Type";

		// Make exlusive to ints
		fields[2] = new JFormattedTextField();
		fields[2].setPreferredSize(new Dimension(140, 30));
		descriptions[2] = "Unit Price";
		fields[2].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					fields[2].setEditable(true);
				} else {
					fields[2].setEditable(false);
				}
			}
		});

		fields[3] = new JFormattedTextField();
		fields[3].setPreferredSize(new Dimension(140, 30));
		descriptions[3] = "Unit Size";

		// Add components to panel
		for (int i = 0; i < fields.length; i++) {
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.ipady = 5;
			gbc.ipadx = 30;

			JLabel label = new JLabel(descriptions[i], SwingConstants.LEFT);
			label.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
			label.setBackground(Color.white);

			panel.add(label, gbc);

			gbc.gridx = 1;
			gbc.gridy = i;
			gbc.insets = new Insets(0, 0, 10, 10);
			panel.add(fields[i], gbc);
		}

		// Creating and configuring "Add" button
		JLabel add = new JLabel("Add", SwingConstants.CENTER);
		add.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		add.setOpaque(true);
		add.setBackground(Color.cyan);
		add.setPreferredSize(new Dimension(100, 30));
		add.setForeground(Color.gray);
		// Creating border
		add.setBorder(BorderFactory.createLineBorder(Color.gray));
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				add();
			}

			public void mouseEntered(MouseEvent e) {
				add.setBackground(Color.white);
				add.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				add.setBackground(Color.cyan);
				add.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Cancel" button
		JLabel cancel = new JLabel("Cancel", SwingConstants.CENTER);
		cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		cancel.setOpaque(true);
		cancel.setBackground(Color.cyan);
		cancel.setPreferredSize(new Dimension(100, 30));
		cancel.setForeground(Color.gray);
		// Creating border
		cancel.setBorder(BorderFactory.createLineBorder(Color.gray));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				cancel();
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

		// Adding buttons to panel
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(add, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(cancel, gbc);

		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void add() {
		SQLFunctions.insertUnit(Integer.parseInt(fields[0].getText()), fields[1].getText(), Integer.parseInt(fields[2].getText()),
				fields[3].getText());
		setVisible(false);
		dispose();
	}

	public void cancel() {
		setVisible(false);
		dispose();
	}
}
