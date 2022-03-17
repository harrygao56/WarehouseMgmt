import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class TenantSearch extends Search {
	public TenantSearch(GUI gui) {
		super(gui);
	}

	public void search() {
		// Calling sql function to obtain an arraylist of string arrays that contain basic info of all tenants
		ArrayList<String[]> results = SQLFunctions.getTenantBasicInfo(searchBar.getText());
		load(results);
	}

	// Loads the given tenant info onto the screen along with edit button and payment history button
	public void load(ArrayList<String[]> table) {
		panel.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());

		// creating first row of column labels
		String[] cLabels = { "ID", "Name", "Address" };

		for (int i = 0; i < cLabels.length; i++) {
			JLabel label = new JLabel(cLabels[i], SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			if (i == 0) {
				label.setPreferredSize(new Dimension(60, 20));
			} else {
				label.setPreferredSize(new Dimension(100, 20));
			}
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.insets = new Insets(2, 0, 2, 0);
			panel.add(label, gbc);
		}

		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).length; j++) {
				JLabel label = new JLabel(table.get(i)[j], SwingConstants.CENTER);
				label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				label.setOpaque(true);
				label.setBackground(Color.white);

				gbc.gridx = j;
				gbc.gridy = i + 1;
				gbc.insets = new Insets(4, 0, 4, 0);
				panel.add(label, gbc);
			}
		}

		// Creating Edit and Payment History buttons
		for (int i = 0; i < table.size(); i++) {
			JLabel label = new JLabel("Edit/View Info", SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			label.setOpaque(true);
			label.setBackground(Color.lightGray);
			label.setPreferredSize(new Dimension(130, 18));
			label.setForeground(Color.black);
			// Creating border
			label.setBorder(BorderFactory.createLineBorder(Color.gray));
			int pos = Integer.parseInt(table.get(i)[0]);
			TenantSearch s = this;
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent mevt) {
					TenantEditor t = new TenantEditor(SQLFunctions.getTenantInfo(pos), s);
				}

				public void mouseEntered(MouseEvent e) {
					label.setBackground(Color.white);
				}

				public void mouseExited(MouseEvent e) {
					label.setBackground(Color.lightGray);
				}
			});

			gbc.gridx = 3;
			gbc.gridy = i + 1;
			// gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(4, 20, 4, 20);
			panel.add(label, gbc);

			// Creating Payment History button
			JLabel label1 = new JLabel("Payment History", SwingConstants.CENTER);
			label1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			label1.setOpaque(true);
			label1.setBackground(Color.lightGray);
			label1.setPreferredSize(new Dimension(130, 18));
			label1.setForeground(Color.black);
			// Creating border
			label1.setBorder(BorderFactory.createLineBorder(Color.gray));
			label1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent mevt) {
					gui.remove(s);
					TenantHistory th = new TenantHistory(gui, pos);
				}

				public void mouseEntered(MouseEvent e) {
					label1.setBackground(Color.white);
				}

				public void mouseExited(MouseEvent e) {
					label1.setBackground(Color.lightGray);
				}
			});

			gbc.gridx = 4;
			gbc.gridy = i + 1;
			// gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(4, 20, 4, 20);
			panel.add(label1, gbc);
		}

		gui.revalidate();
		gui.repaint();
	}
}