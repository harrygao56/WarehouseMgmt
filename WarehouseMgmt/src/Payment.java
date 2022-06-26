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
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Payment extends JPanel {
	private JTextField field;
	private JTextField type;
	private int tenant;
	private GUI gui;

	public Payment(GUI gui, int tenant) {
		this.gui = gui;
		this.tenant = tenant;

		setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(grid);

		JLabel title = new JLabel("Payment for " + SQLFunctions.getName(tenant), SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		title.setPreferredSize(new Dimension(300, 24));

		JLabel amt = new JLabel("Payment Amount ($):", SwingConstants.CENTER);
		amt.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		amt.setPreferredSize(new Dimension(200, 20));

		field = new JTextField();
		field.setPreferredSize(new Dimension(120, 25));
		field.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					field.setEditable(true);
				} else {
					field.setEditable(false);
				}
			}
		});

		JLabel payType = new JLabel("Payment Method:", SwingConstants.CENTER);
		payType.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		payType.setPreferredSize(new Dimension(170, 20));

		type = new JTextField();
		type.setPreferredSize(new Dimension(120, 25));

		JLabel button = new JLabel("Submit Payment", SwingConstants.CENTER);
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
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 10, 20, 20);
		gbc.anchor = GridBagConstraints.EAST;
		add(title, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.EAST;
		add(amt, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		add(payType, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		add(type, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		add(button, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 10, 0, 0);
		add(cancel, gbc);

		gui.add(this);
		gui.revalidate();
		gui.repaint();
	}

	public void submit() {
		int amt = Integer.valueOf(field.getText());

		SQLFunctions.applyPayment(tenant, amt);
		SQLFunctions.newHistory(tenant, -amt, "Payment", type.getText());

		try {
			Mail.sendReceipt(SQLFunctions.getEmail(tenant), "Payment Received",
					"Your payment of $" + amt + " was received. Updated Balance: $" + SQLFunctions.getBalance(tenant)
							+ "\n\nThank you, and have a nice day,\nA Best Self Storage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		goBack();
		JOptionPane.showMessageDialog(new JFrame(),
				"Payment successfully applied. Updated Balance: " + SQLFunctions.getBalance(tenant));
	}

	public void goBack() {
		gui.remove(this);
		gui.add(gui.getMenu());
		gui.revalidate();
		gui.repaint();
	}
}
