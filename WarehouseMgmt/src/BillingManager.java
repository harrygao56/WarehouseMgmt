import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BillingManager extends Manager {
	public BillingManager(GUI gui) {
		super(gui);

		super.panel = new JPanel();
		panel.setBackground(Color.white);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(grid);

		// Creating and configuring "Bill & Send Invoices" button
		JLabel bill = new JLabel("Bill & Send Invoices", SwingConstants.CENTER);
		bill.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		bill.setOpaque(true);
		bill.setBackground(Color.cyan);
		bill.setPreferredSize(new Dimension(200, 40));
		bill.setForeground(Color.gray);
		// Creating border
		bill.setBorder(BorderFactory.createLineBorder(Color.gray));
		bill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(panel);
				BillReport b = new BillReport(gui);
				b.display();
			}

			public void mouseEntered(MouseEvent e) {
				bill.setBackground(Color.white);
				bill.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				bill.setBackground(Color.cyan);
				bill.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Apply Payment" button
		JLabel payment = new JLabel("Apply Payment", SwingConstants.CENTER);
		payment.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		payment.setOpaque(true);
		payment.setBackground(Color.cyan);
		payment.setPreferredSize(new Dimension(200, 40));
		payment.setForeground(Color.gray);
		// Creating border
		payment.setBorder(BorderFactory.createLineBorder(Color.gray));
		payment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(panel);
				TenantSearchBiller t = new TenantSearchBiller(gui);
				t.load(SQLFunctions.getTenantBasicInfoAndBalance());
				t.display();
			}

			public void mouseEntered(MouseEvent e) {
				payment.setBackground(Color.white);
				payment.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				payment.setBackground(Color.cyan);
				payment.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Apply Late Fees" button
		JLabel late = new JLabel("Apply Late Fees", SwingConstants.CENTER);
		late.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		late.setOpaque(true);
		late.setBackground(Color.cyan);
		late.setPreferredSize(new Dimension(200, 40));
		late.setForeground(Color.gray);
		// Creating border
		late.setBorder(BorderFactory.createLineBorder(Color.gray));
		late.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(panel);
				LateFee l = new LateFee(gui);
			}

			public void mouseEntered(MouseEvent e) {
				late.setBackground(Color.white);
				late.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				late.setBackground(Color.cyan);
				late.setForeground(Color.gray);
			}
		});

		// Creating and configuring "Apply Misc Fees" button
		JLabel misc = new JLabel("Apply Misc. Fees", SwingConstants.CENTER);
		misc.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		misc.setOpaque(true);
		misc.setBackground(Color.cyan);
		misc.setPreferredSize(new Dimension(200, 40));
		misc.setForeground(Color.gray);
		// Creating border
		misc.setBorder(BorderFactory.createLineBorder(Color.gray));
		misc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(panel);
				TenantSearchFee t = new TenantSearchFee(gui);
				t.load(SQLFunctions.getTenantBasicInfoAndBalance());
				t.display();
			}

			public void mouseEntered(MouseEvent e) {
				misc.setBackground(Color.white);
				misc.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				misc.setBackground(Color.cyan);
				misc.setForeground(Color.gray);
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
		JLabel blank1 = new JLabel();
		blank1.setPreferredSize(new Dimension(200, 20));
		JLabel blank2 = new JLabel();
		blank2.setPreferredSize(new Dimension(200, 20));
		JLabel blank3 = new JLabel();
		blank3.setPreferredSize(new Dimension(200, 20));
		JLabel blank4 = new JLabel();
		blank4.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(bill, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(blank1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panel.add(payment, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		panel.add(blank2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		panel.add(late, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		panel.add(blank3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 7;
		panel.add(misc, gbc);

		gbc.gridx = 1;
		gbc.gridy = 8;
		panel.add(blank4, gbc);

		gbc.gridx = 1;
		gbc.gridy = 9;
		panel.add(back, gbc);
	}
}