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

	private JTextField amount;

	public LateFee(GUI gui) {
		this.gui = gui;

		setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(grid);
		
		JLabel title = new JLabel("<html>Fee will be applied to all tenants<br>who have outstanding balance</html>", SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 19));
		title.setPreferredSize(new Dimension(300, 45));

		JLabel perc = new JLabel("Fee Amount: ", SwingConstants.CENTER);
		perc.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		perc.setPreferredSize(new Dimension(200, 20));

		amount = new JTextField();
		amount.setPreferredSize(new Dimension(120, 25));
		amount.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					amount.setEditable(true);
				} else {
					amount.setEditable(false);
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
		add(amount, gbc);

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
		int val = Integer.valueOf(amount.getText());
		
		for (int i = 0; i < l.size(); i++) {
			int tenant = Integer.valueOf(l.get(i)[0]);
			
			if (Integer.valueOf(l.get(i)[3]) > 0) {
				SQLFunctions.applyFee(tenant, val);
				SQLFunctions.newHistory(tenant, val, "Late Fee", null);
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
