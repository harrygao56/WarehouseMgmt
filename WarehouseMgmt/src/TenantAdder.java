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

public class TenantAdder extends JFrame {
	private JPanel panel;
	private JTextField fields[];
	private String descriptions[];

	public TenantAdder() {
		setTitle("Add Tenant");

		panel = new JPanel();
		panel.setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(grid);

		// Initializing fields and descriptions
		fields = new JTextField[7];
		descriptions = new String[7];

		fields[0] = new JTextField();
		fields[0].setPreferredSize(new Dimension(140, 30));
		descriptions[0] = "Name";

		fields[1] = new JTextField();
		fields[1].setPreferredSize(new Dimension(140, 30));
		descriptions[1] = "Address";

		fields[2] = new JTextField();
		fields[2].setPreferredSize(new Dimension(140, 30));
		descriptions[2] = "City, State";

		// Creating NumberFormatter to limit input to int
		fields[3] = new JTextField();
		fields[3].setPreferredSize(new Dimension(140, 30));
		descriptions[3] = "Zipcode";
		fields[3].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					fields[3].setEditable(true);
				} else {
					fields[3].setEditable(false);
				}
			}
		});

		fields[4] = new JTextField();
		fields[4].setPreferredSize(new Dimension(140, 30));
		descriptions[4] = "Email";
		
		fields[5] = new JTextField();
		fields[5].setPreferredSize(new Dimension(140, 30));
		descriptions[5] = "Current Balance";
		fields[5].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					fields[5].setEditable(true);
				} else {
					fields[5].setEditable(false);
				}
			}
		});
		
		
		fields[6] = new JTextField();
		fields[6].setPreferredSize(new Dimension(140, 30));
		descriptions[6] = "Security Deposit";
		fields[6].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					fields[6].setEditable(true);
				} else {
					fields[6].setEditable(false);
				}
			}
		});

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
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(add, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(cancel, gbc);

		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void add() {
		SQLFunctions.insertTenant(fields[0].getText(), fields[1].getText(), fields[2].getText(), Integer.parseInt(fields[3].getText()), fields[4].getText(), Integer.parseInt(fields[5].getText()), Integer.parseInt(fields[6]. getText()));
		setVisible(false);
		dispose();
	}

	public void cancel() {
		setVisible(false);
		dispose();
	}
}