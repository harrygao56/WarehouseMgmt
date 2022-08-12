import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI extends JFrame {
	private JPanel menu;
	private JLabel manageTenants;
	private JLabel manageUnits;
	private JLabel billing;
	private JLabel report;
	private JLabel title;
	private JLabel icon;

	public GUI() {
		setTitle("Warehouse Manager");

		menu = new JPanel();
		menu.setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		menu.setLayout(grid);

		// Creating Title Label
		title = new JLabel("Warehouse Manager", SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.PLAIN, 40));
		title.setOpaque(true);
		title.setBackground(Color.white);

		// Creating warehouse icon
		ImageIcon i = new ImageIcon("icon.png");
		Image img = i.getImage();
		Image newImg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		i = new ImageIcon(newImg);
		icon = new JLabel(i);

		// Creating and configuring "Manage Tenants" button
		manageTenants = new JLabel("Manage Tenants", SwingConstants.CENTER);
		manageTenants.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		manageTenants.setOpaque(true);
		manageTenants.setBackground(Color.cyan);
		manageTenants.setPreferredSize(new Dimension(200, 40));
		manageTenants.setForeground(Color.gray);
		// Creating border
		Border border = BorderFactory.createLineBorder(Color.gray);
		manageTenants.setBorder(border);
		manageTenants.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				manageTenants.setBackground(Color.cyan);
				manageTenants.setForeground(Color.gray);
				openTenantManager();
			}

			public void mouseEntered(MouseEvent e) {
				manageTenants.setBackground(Color.white);
				manageTenants.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				manageTenants.setBackground(Color.cyan);
				manageTenants.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Manage Units" button
		manageUnits = new JLabel("Manage Units", SwingConstants.CENTER);
		manageUnits.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		manageUnits.setOpaque(true);
		manageUnits.setBackground(Color.cyan);
		manageUnits.setPreferredSize(new Dimension(200, 40));
		manageUnits.setForeground(Color.gray);
		// Creating border
		border = BorderFactory.createLineBorder(Color.gray);
		manageUnits.setBorder(border);
		manageUnits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				manageUnits.setBackground(Color.cyan);
				manageUnits.setForeground(Color.gray);
				openUnitManager();
			}

			public void mouseEntered(MouseEvent e) {
				manageUnits.setBackground(Color.white);
				manageUnits.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				manageUnits.setBackground(Color.cyan);
				manageUnits.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Billing" button
		billing = new JLabel("Manage Billing", SwingConstants.CENTER);
		billing.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		billing.setOpaque(true);
		billing.setBackground(Color.cyan);
		billing.setPreferredSize(new Dimension(200, 40));
		billing.setForeground(Color.gray);
		// Creating border
		border = BorderFactory.createLineBorder(Color.gray);
		billing.setBorder(border);
		billing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				billing.setBackground(Color.cyan);
				billing.setForeground(Color.gray);
				openBilling();
			}

			public void mouseEntered(MouseEvent e) {
				billing.setBackground(Color.white);
				billing.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				billing.setBackground(Color.cyan);
				billing.setForeground(Color.gray);
			}
		});

		// Creating and configuring "View Report" button
		report = new JLabel("View Report", SwingConstants.CENTER);
		report.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		report.setOpaque(true);
		report.setBackground(Color.cyan);
		report.setPreferredSize(new Dimension(200, 40));
		report.setForeground(Color.gray);
		// Creating border
		border = BorderFactory.createLineBorder(Color.gray);
		report.setBorder(border);
		report.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				report.setBackground(Color.cyan);
				report.setForeground(Color.gray);
				openReport();
			}

			public void mouseEntered(MouseEvent e) {
				report.setBackground(Color.white);
				report.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				report.setBackground(Color.cyan);
				report.setForeground(Color.gray);
			}
		});

		// Creating blank filler labels
		JLabel blank = new JLabel();
		blank.setPreferredSize(new Dimension(200, 10));
		JLabel blank1 = new JLabel();
		blank1.setPreferredSize(new Dimension(200, 35));
		JLabel blank2 = new JLabel();
		blank2.setPreferredSize(new Dimension(200, 20));
		JLabel blank3 = new JLabel();
		blank3.setPreferredSize(new Dimension(200, 20));
		JLabel blank4 = new JLabel();
		blank4.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 1;
		gbc.gridy = 1;
		menu.add(title, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		menu.add(blank, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		menu.add(icon, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		menu.add(blank1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		menu.add(manageTenants, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		menu.add(blank2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 7;
		menu.add(manageUnits, gbc);

		gbc.gridx = 1;
		gbc.gridy = 8;
		menu.add(blank3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 9;
		menu.add(billing, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 10;
		menu.add(blank4, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 11;
		menu.add(report, gbc);

		add(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void openTenantManager() {
		remove(menu);
		TenantManager tM = new TenantManager(this);
		tM.display();
	}

	public void openUnitManager() {
		remove(menu);
		UnitManager uM = new UnitManager(this);
		uM.display();
	}

	public void openBilling() {
		remove(menu);
		BillingManager bM = new BillingManager(this);
		bM.display();
	}
	
	public void openReport() {
		Report r = new Report();
	}

	public JPanel getMenu() {
		return menu;
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
	}
}