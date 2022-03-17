import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TenantManager extends Manager {
	public TenantManager(GUI gui) {
		super(gui);

		super.panel = new JPanel();
		panel.setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(grid);

		// Creating and configuring "New Tenant" button
		JLabel newTenant = new JLabel("New Tenant", SwingConstants.CENTER);
		newTenant.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		newTenant.setOpaque(true);
		newTenant.setBackground(Color.cyan);
		newTenant.setPreferredSize(new Dimension(200, 40));
		newTenant.setForeground(Color.gray);
		// Creating border
		newTenant.setBorder(BorderFactory.createLineBorder(Color.gray));
		newTenant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				TenantAdder t = new TenantAdder();
			}

			public void mouseEntered(MouseEvent e) {
				newTenant.setBackground(Color.white);
				newTenant.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				newTenant.setBackground(Color.cyan);
				newTenant.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Tenant Lookup" button
		JLabel search = new JLabel("Tenant Lookup", SwingConstants.CENTER);
		search.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		search.setOpaque(true);
		search.setBackground(Color.cyan);
		search.setPreferredSize(new Dimension(200, 40));
		search.setForeground(Color.gray);
		// Creating border
		search.setBorder(BorderFactory.createLineBorder(Color.gray));
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				TenantSearch search = new TenantSearch(gui);
				search.load(SQLFunctions.getTenantBasicInfo());
				gui.remove(panel);
				search.display();
			}
			
			public void mouseEntered(MouseEvent e) {
				search.setBackground(Color.white);
				search.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				search.setBackground(Color.cyan);
				search.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Go Back" button
		JLabel back = new JLabel("Go Back", SwingConstants.CENTER);
		back.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		back.setOpaque(true);
		back.setBackground(Color.cyan);
		back.setPreferredSize(new Dimension(200, 40));
		back.setForeground(Color.gray);
		// Creating border
		back.setBorder(BorderFactory.createLineBorder(Color.gray));
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				returnToMenu();
			}

			public void mouseEntered(MouseEvent e) {
				back.setBackground(Color.white);
				back.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				back.setBackground(Color.cyan);
				back.setForeground(Color.gray);
			}
		});

		// Creating blank filler labels
		JLabel blank2 = new JLabel();
		blank2.setPreferredSize(new Dimension(200, 20));
		JLabel blank3 = new JLabel();
		blank3.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(newTenant, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(blank2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panel.add(search, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		panel.add(blank3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		panel.add(back, gbc);
	}
}
