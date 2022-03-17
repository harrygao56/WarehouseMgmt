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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LateFee extends JPanel {
	private GUI gui;

	private JTextField percField;
	private JTextField minField;
	private JTextField maxField;

	public LateFee(GUI gui) {
		this.gui = gui;

		setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(grid);
		
		JLabel title = new JLabel("<html>Fee will be applied to all tenants<br>who have outstanding balance</html>", SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 19));
		title.setPreferredSize(new Dimension(300, 45));

		JLabel perc = new JLabel("Percentage of Balance: ", SwingConstants.CENTER);
		perc.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		perc.setPreferredSize(new Dimension(200, 20));

		percField = new JTextField();
		percField.setPreferredSize(new Dimension(120, 25));
		percField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					percField.setEditable(true);
				} else {
					percField.setEditable(false);
				}
			}
		});
		
		JLabel min = new JLabel("Fee Minimum: ", SwingConstants.CENTER);
		min.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		min.setPreferredSize(new Dimension(170, 20));
		
		minField = new JTextField();
		minField.setPreferredSize(new Dimension(120, 25));
		minField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					minField.setEditable(true);
				} else {
					minField.setEditable(false);
				}
			}
		});
		
		JLabel max = new JLabel("Fee Maximum: ", SwingConstants.CENTER);
		max.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		max.setPreferredSize(new Dimension(170, 20));
		
		maxField = new JTextField();
		maxField.setPreferredSize(new Dimension(120, 25));
		maxField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					maxField.setEditable(true);
				} else {
					maxField.setEditable(false);
				}
			}
		});

		JLabel button = new JLabel("Submit", SwingConstants.CENTER);
		button.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		button.setPreferredSize(new Dimension(160, 30));
		button.setBackground(Color.cyan);
		button.setForeground(Color.gray);
		button.setOpaque(true);
		// Creating border
		button.setBorder(BorderFactory.createLineBorder(Color.gray));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				submit();
			}

			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.white);
				button.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(Color.cyan);
				button.setForeground(Color.gray);
			}
		});

		JLabel cancel = new JLabel("Cancel", SwingConstants.CENTER);
		cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		cancel.setPreferredSize(new Dimension(120, 30));
		cancel.setBackground(Color.cyan);
		cancel.setForeground(Color.gray);
		cancel.setOpaque(true);
		// Creating border
		cancel.setBorder(BorderFactory.createLineBorder(Color.gray));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				goBack();
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

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(20, 20, 20, 20);
		gbc.gridwidth = 2;
		add(title, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		add(perc, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(percField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		add(min, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		add(minField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		add(max, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		add(maxField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(20, 0, 0, 0);
		add(button, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(20, 10, 0, 0);
		add(cancel, gbc);

		gui.add(this);
		gui.revalidate();
		gui.repaint();
	}

	public void submit() {
		ArrayList<String[]> l = SQLFunctions.getTenantBasicInfoAndBalance();
		double percent = Integer.valueOf(percField.getText())/100.0;
		int min = Integer.valueOf(minField.getText());
		int max = Integer.valueOf(maxField.getText());
		
		for (int i = 0; i < l.size(); i++) {
			int tenant = Integer.valueOf(l.get(i)[0]);
			
			if (Integer.valueOf(l.get(i)[3]) > 0) {
				int fee = (int)(percent*Integer.valueOf(l.get(i)[3]));
				if (fee < min) {
					SQLFunctions.applyFee(tenant, min);
					SQLFunctions.newHistory(tenant, min, "Late Fee", null);
				}
				else if (fee > max) {
					SQLFunctions.applyFee(tenant, max);
					SQLFunctions.newHistory(tenant, max, "Late Fee", null);
				}
				else {
					SQLFunctions.applyFee(tenant, fee);
					SQLFunctions.newHistory(tenant, fee, "Late Fee", null);
				}
			}
		}
		
		goBack();
		JOptionPane.showMessageDialog(new JFrame(),
				"Fees applied successfully.");
	}

	public void goBack() {
		gui.remove(this);
		gui.add(gui.getMenu());
		gui.revalidate();
		gui.repaint();
	}
}
