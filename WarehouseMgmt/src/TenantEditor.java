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
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TenantEditor extends JFrame {
	private JPanel panel;
	private JTextField[] fields;
	private String[] labels = { "Name", "Address", "City/State", "Zipcode", "Email", "Phone", "Current Balance", "Monthly Rent",
			"Security Deposit", "Notes"};
	private int id;
	private TenantSearch tSearch;

	public TenantEditor(String[] entries, TenantSearch tSearch) {
		id = Integer.valueOf(entries[0]);
		this.tSearch = tSearch;
		
		setTitle("View/Edit Tenant Info");

		panel = new JPanel();
		panel.setBackground(Color.white);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(layout);

		JLabel title = new JLabel("View/Edit Info for " + SQLFunctions.getName(Integer.valueOf(entries[0])), SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		title.setBackground(Color.white);
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(330, 35));
		title.setForeground(Color.black);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 10;
		gbc.gridwidth = 2;
		panel.add(title, gbc);
		

		for (int i = 0; i < labels.length; i++) {
			JLabel label = new JLabel(labels[i], SwingConstants.LEFT);
			label.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
			label.setBackground(Color.white);
			label.setOpaque(true);
			label.setPreferredSize(new Dimension(160, 22));
			label.setForeground(Color.black);

			gbc.gridx = 0;
			gbc.gridy = i + 1;
			gbc.gridwidth = 1;
			panel.add(label, gbc);
		}

		fields = new JTextField[entries.length - 1];

		for (int i = 1; i < entries.length; i++) {
			int curr = i - 1;

			fields[curr] = new JTextField(entries[i]);
			fields[curr].setPreferredSize(new Dimension(200, 20));
			
			// Making Zipcode, Current Balance, and Security deposit explusive to ints
			if (labels[curr].equals("Zipcode") || labels[curr].equals("Current Balance")
					|| labels[curr].equals("Security Deposit") || labels[curr].equals("Phone")) {
				// Making the text field exclusive to numbers
				fields[curr].addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke) {
						if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
							fields[curr].setEditable(true);
						} else {
							fields[curr].setEditable(false);
						}
					}
				});
			}
			
			// Make Monthly Rent uneditable
			if (labels[curr].equals("Monthly Rent")) {
				fields[curr].setEditable(false);
			}
			
			gbc.gridx = 1;
			gbc.gridy = i;
			gbc.anchor = GridBagConstraints.WEST;
			panel.add(fields[curr], gbc);
		}
		
		// Creating Submit button
		JLabel submit = new JLabel("Submit", SwingConstants.CENTER);
		submit.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		submit.setBackground(Color.CYAN);
		submit.setOpaque(true);
		submit.setPreferredSize(new Dimension(160, 24));
		submit.setBorder(BorderFactory.createLineBorder(Color.gray));
		submit.setForeground(Color.gray);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				update();
				setVisible(false);
				dispose();
			}

			public void mouseEntered(MouseEvent e) {
				submit.setBackground(Color.white);
			}

			public void mouseExited(MouseEvent e) {
				submit.setBackground(Color.cyan);
			}
		});

		gbc.gridy = entries.length;
		gbc.gridx = 0;
		gbc.ipadx = 20;
		gbc.ipady = 10;
		gbc.insets = new Insets(10,10,10,10);
		panel.add(submit, gbc);
		
		// Creating cancel button
		JLabel cancel = new JLabel("Cancel", SwingConstants.CENTER);
		cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		cancel.setBackground(Color.CYAN);
		cancel.setOpaque(true);
		cancel.setPreferredSize(new Dimension(160, 24));
		cancel.setBorder(BorderFactory.createLineBorder(Color.gray));
		cancel.setForeground(Color.gray);
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				setVisible(false);
				dispose();
			}

			public void mouseEntered(MouseEvent e) {
				cancel.setBackground(Color.white);
			}

			public void mouseExited(MouseEvent e) {
				cancel.setBackground(Color.cyan);
			}
		});

		gbc.gridy = entries.length;
		gbc.gridx = 1;
		panel.add(cancel, gbc);
		
		
		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		title.setPreferredSize(new Dimension(200, 24));
		title.setForeground(Color.black);
	}
	
	public void update() {
		String[] entries = new String[11];
		entries[0] = String.valueOf(id);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getText().equals("")) {
				entries[i+1] = null;
			}
			else {
				entries[i+1] = fields[i].getText();
			}
		}
		
		SQLFunctions.editTenant(entries);
		
		ArrayList<String[]> table = SQLFunctions.getTenantBasicInfo();
		tSearch.load(table);
	}
}