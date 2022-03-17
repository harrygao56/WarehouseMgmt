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

public class MiscFee extends JPanel {
	private GUI gui;
	private int tenant;
	
	private JTextField amtField;
	private JTextField desField;
	
	public MiscFee(GUI gui, int tenant) {
		this.gui = gui;
		this.tenant = tenant;

		setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(grid);
		
		JLabel title = new JLabel("Fee for " + SQLFunctions.getName(tenant), SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		title.setPreferredSize(new Dimension(300, 24));

		JLabel amt = new JLabel("Fee Amount: ", SwingConstants.CENTER);
		amt.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		amt.setPreferredSize(new Dimension(170, 20));

		amtField = new JTextField();
		amtField.setPreferredSize(new Dimension(120, 25));
		amtField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\u0008') {
					amtField.setEditable(true);
				} else {
					amtField.setEditable(false);
				}
			}
		});
		
		JLabel des = new JLabel("Fee Description: ", SwingConstants.CENTER);
		des.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		des.setPreferredSize(new Dimension(170, 20));
		
		desField = new JTextField();
		desField.setPreferredSize(new Dimension(120, 25));

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
		add(amt, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(amtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		add(des, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		add(desField, gbc);

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
		SQLFunctions.applyFee(tenant, Integer.valueOf(amtField.getText()));
		SQLFunctions.newHistory(tenant, Integer.valueOf(amtField.getText()), "Fee", desField.getText());
		goBack();
		JOptionPane.showMessageDialog(new JFrame(), "Fee sucessfully applied. Updated Balance: " + SQLFunctions.getBalance(tenant));
	}
	
	public void goBack() {
		gui.remove(this);
		gui.add(gui.getMenu());
		gui.revalidate();
		gui.repaint();
	}
}
