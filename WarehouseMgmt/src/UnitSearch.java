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
import javax.swing.SwingConstants;

public class UnitSearch extends Search {
	public UnitSearch(GUI gui) {
		super(gui);

		ArrayList<String[]> table = SQLFunctions.getUnitInfo();
		load(table);
	}

	public void search() {
		// Calling sql function to obtain an arraylist of string arrays that contain all
		// tenant info
		ArrayList<String[]> results = SQLFunctions.getUnitInfo(searchBar.getText());
		load(results);
	}

	public void load(ArrayList<String[]> table) {
		panel.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());

		// creating first row of column labels
		String[] cLabels = { "Number", "Type", "Monthly Price ($)", "Size", "Tenant" };

		for (int i = 0; i < cLabels.length; i++) {
			JLabel label = new JLabel(cLabels[i], SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			if (i == 0) {
				label.setPreferredSize(new Dimension(60, 20));
			} else if (i == 2) {
				label.setPreferredSize(new Dimension(115, 20));
			}
			else {
				label.setPreferredSize(new Dimension(60, 20));
			}
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.insets = new Insets(2, 0, 2, 0);
			panel.add(label, gbc);
		}

		// creating each row of unit info
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).length; j++) {
				JLabel label = new JLabel(table.get(i)[j], SwingConstants.CENTER);
				
				if (j == 4 && table.get(i)[j] != null) {
					label = new JLabel(String.format("%1.10s", SQLFunctions.getTenantName(Integer.parseInt(table.get(i)[j]))), SwingConstants.CENTER);
				}
				
				if (table.get(i)[j] == null) {
					label = new JLabel("Empty", SwingConstants.CENTER);
				}
				
				label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				label.setOpaque(true);
				label.setBackground(Color.white);

				gbc.gridx = j;
				gbc.gridy = i + 1;
				gbc.insets = new Insets(2, 0, 2, 0);
				panel.add(label, gbc);
			}
		}

		// Creating Edit Info and Change Tenant and Remove Tenant buttons
		for (int i = 0; i < table.size(); i++) {
			JLabel label = new JLabel("Edit Info", SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			label.setOpaque(true);
			label.setBackground(Color.lightGray);
			label.setPreferredSize(new Dimension(80, 18));
			label.setForeground(Color.black);
			// Creating border
			label.setBorder(BorderFactory.createLineBorder(Color.gray));
			int pos = Integer.parseInt(table.get(i)[0]);
			UnitSearch u = this;
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent mevt) {
					UnitEditor t = new UnitEditor(SQLFunctions.getUnitInfo(pos), u);
				}

				public void mouseEntered(MouseEvent e) {
					label.setBackground(Color.white);
				}

				public void mouseExited(MouseEvent e) {
					label.setBackground(Color.lightGray);
				}
			});

			gbc.gridx = 5;
			gbc.gridy = i + 1;
			gbc.insets = new Insets(4, 5, 4, 5);
			panel.add(label, gbc);

			JLabel setTenant = new JLabel("Assign/Change Tenant", SwingConstants.CENTER);
			setTenant.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			setTenant.setOpaque(true);
			setTenant.setBackground(Color.lightGray);
			setTenant.setPreferredSize(new Dimension(160, 18));
			setTenant.setForeground(Color.black);
			// Creating border
			setTenant.setBorder(BorderFactory.createLineBorder(Color.gray));
			UnitSearch us = this;
			int unitNum = Integer.valueOf(table.get(i)[0]);
			setTenant.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent mevt) {
					TenantSearchSelector t = new TenantSearchSelector(gui);
					t.load(SQLFunctions.getTenantBasicInfo(), unitNum);
					gui.remove(us);
					t.display();
				}

				public void mouseEntered(MouseEvent e) {
					setTenant.setBackground(Color.white);
				}

				public void mouseExited(MouseEvent e) {
					setTenant.setBackground(Color.lightGray);
				}
			});

			gbc.gridx = 6;
			gbc.gridy = i + 1;
			gbc.insets = new Insets(4, 5, 4, 5);
			panel.add(setTenant, gbc);

			if (table.get(i)[4] != null) {
				JLabel removeTenant = new JLabel("Remove Tenant", SwingConstants.CENTER);
				removeTenant.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				removeTenant.setOpaque(true);
				removeTenant.setBackground(Color.lightGray);
				removeTenant.setPreferredSize(new Dimension(120, 18));
				removeTenant.setForeground(Color.black);
				// Creating border
				removeTenant.setBorder(BorderFactory.createLineBorder(Color.gray));
				int tenantID = Integer.valueOf(table.get(i)[4]);
				removeTenant.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(final MouseEvent mevt) {
						SQLFunctions.removeTenant(String.valueOf(tenantID), unitNum);
						ArrayList<String[]> table = SQLFunctions.getUnitInfo();
						load(table);
					}

					public void mouseEntered(MouseEvent e) {
						removeTenant.setBackground(Color.white);
					}

					public void mouseExited(MouseEvent e) {
						removeTenant.setBackground(Color.lightGray);
					}
				});

				gbc.gridx = 7;
				gbc.gridy = i + 1;
				gbc.insets = new Insets(4, 5, 4, 5);
				panel.add(removeTenant, gbc);
			}
		}

		gui.revalidate();
		gui.repaint();
	}
}