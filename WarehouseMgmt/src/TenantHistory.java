import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class TenantHistory extends JPanel {
	private JPanel panel;
	private JScrollPane pane;

	public TenantHistory(GUI gui, int tenant) {
		setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(grid);

		panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new GridBagLayout());

		pane = new JScrollPane(panel);
		pane.setPreferredSize(new Dimension(1200, 600));

		JLabel title = new JLabel("Payment History for " + SQLFunctions.getName(tenant), SwingConstants.LEFT);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		title.setPreferredSize(new Dimension(400, 20));

		// Initialize Back Button
		JLabel cancel = new JLabel("Back", SwingConstants.CENTER);
		cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		cancel.setOpaque(true);
		cancel.setBackground(Color.cyan);
		cancel.setPreferredSize(new Dimension(90, 35));
		cancel.setForeground(Color.gray);
		// Creating border
		cancel.setBorder(BorderFactory.createLineBorder(Color.gray));
		TenantHistory t = this;
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(t);
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

		// Initialize Download Button
		JLabel download = new JLabel("Download", SwingConstants.CENTER);
		download.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		download.setOpaque(true);
		download.setBackground(Color.cyan);
		download.setPreferredSize(new Dimension(105, 35));
		download.setForeground(Color.gray);
		// Creating border
		download.setBorder(BorderFactory.createLineBorder(Color.gray));
		download.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				panel.paint(g);
				try {
					String home = System.getProperty("user.home");
					ImageIO.write(image, "png", new File(home + "\\Downloads\\history_" + tenant + ".png"));
					JOptionPane.showMessageDialog(new JFrame(), "File Saved to Downloads Folder");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Error Downloading File");
					System.out.println(e.getMessage());
				}
			}

			public void mouseEntered(MouseEvent e) {
				download.setBackground(Color.white);
				download.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				download.setBackground(Color.cyan);
				download.setForeground(Color.gray);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		add(title, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		add(pane, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 4;
		gbc.anchor = GridBagConstraints.WEST;
		add(cancel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.ipady = 4;
		gbc.anchor = GridBagConstraints.EAST;
		add(download, gbc);

		// Creating first row of column labels
		String[] cLabels = { "Date", "New Balance", "Transaction", "Type", "Description" };

		for (int i = 0; i < cLabels.length; i++) {
			JLabel label = new JLabel(cLabels[i], SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			if (i == 0) {
				label.setPreferredSize(new Dimension(60, 20));
			} else {
				label.setPreferredSize(new Dimension(100, 20));
			}
			gbc.gridx = i;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.ipady = 4;
			gbc.insets = new Insets(2, 0, 2, 0);
			panel.add(label, gbc);
		}

		ArrayList<String[]> table = SQLFunctions.getHistory(tenant);

		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).length; j++) {
				JLabel label = new JLabel(table.get(i)[j], SwingConstants.CENTER);
				label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				label.setOpaque(true);
				label.setBackground(Color.white);
				if (j == 0) {
					label.setPreferredSize(new Dimension(140, 20));
				}

				gbc.gridx = j;
				gbc.gridy = i + 2;
				gbc.insets = new Insets(0, 10, 0, 10);
				panel.add(label, gbc);
			}
		}

		gui.add(this);
		gui.revalidate();
		gui.repaint();
	}
}
