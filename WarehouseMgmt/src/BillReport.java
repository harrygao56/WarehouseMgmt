import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

public class BillReport extends JPanel {
	private JPanel panel;
	private JLabel label;
	private JScrollPane pane;
	private JLabel selectAll;
	private JLabel deselectAll;
	private JLabel cancel;
	private JLabel continueToBill;
	private ArrayList<JCheckBox> checkboxes;
	private GUI gui;

	private String[] cLabels = { "Name", "Email", "Owed ($)", "Address", "Phone", "Rent ($)" };
	private ArrayList<String[]> table = SQLFunctions.getTenantReportInfo();

	public BillReport(GUI gui) {
		this.gui = gui;

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		panel = new JPanel();
		panel.setBackground(Color.white);
		
		Calendar cal = GregorianCalendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MMMM");

		Date currentMonth = new Date();
		cal.setTime(currentMonth);

		// Add next month
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		String nextString = df.format(cal.getTime());

		label = new JLabel("Select Tenants to Bill for " + nextString, SwingConstants.CENTER);
		label.setFont(new Font("Trebuchet MS Bold", Font.PLAIN, 20));

		// Create Select All Button
		selectAll = new JLabel("Select All", SwingConstants.CENTER);
		selectAll.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		selectAll.setOpaque(true);
		selectAll.setBackground(Color.cyan);
		selectAll.setPreferredSize(new Dimension(130, 30));
		selectAll.setForeground(Color.gray);
		// Creating border
		selectAll.setBorder(BorderFactory.createLineBorder(Color.gray));
		selectAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				for (int i = 0; i < checkboxes.size(); i++) {
					checkboxes.get(i).setSelected(true);
				}
			}

			public void mouseEntered(MouseEvent e) {
				selectAll.setBackground(Color.white);
				selectAll.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				selectAll.setBackground(Color.cyan);
				selectAll.setForeground(Color.gray);
			}
		});

		// Create Deselect All Button
		deselectAll = new JLabel("Deselect All", SwingConstants.CENTER);
		deselectAll.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		deselectAll.setOpaque(true);
		deselectAll.setBackground(Color.cyan);
		deselectAll.setPreferredSize(new Dimension(130, 30));
		deselectAll.setForeground(Color.gray);
		// Creating border
		deselectAll.setBorder(BorderFactory.createLineBorder(Color.gray));
		deselectAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				for (int i = 0; i < checkboxes.size(); i++) {
					checkboxes.get(i).setSelected(false);
				}
			}

			public void mouseEntered(MouseEvent e) {
				deselectAll.setBackground(Color.white);
				deselectAll.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				deselectAll.setBackground(Color.cyan);
				deselectAll.setForeground(Color.gray);
			}
		});

		// Create Cancel Button
		cancel = new JLabel("Cancel", SwingConstants.CENTER);
		cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		cancel.setOpaque(true);
		cancel.setBackground(Color.cyan);
		cancel.setPreferredSize(new Dimension(130, 30));
		cancel.setForeground(Color.gray);
		// Creating border
		cancel.setBorder(BorderFactory.createLineBorder(Color.gray));
		BillReport b = this;
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				gui.remove(b);
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

		// Create Continue To Bill Button
		continueToBill = new JLabel("Continue", SwingConstants.CENTER);
		continueToBill.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		continueToBill.setOpaque(true);
		continueToBill.setBackground(Color.cyan);
		continueToBill.setPreferredSize(new Dimension(130, 30));
		continueToBill.setForeground(Color.gray);
		// Creating border
		continueToBill.setBorder(BorderFactory.createLineBorder(Color.gray));
		continueToBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mevt) {
				int input = JOptionPane.showConfirmDialog(null, "Bill and email selected tenants?", null,
						JOptionPane.YES_NO_OPTION);

				if (input == 0) {
					ArrayList<Integer> billedTenants = new ArrayList<Integer>();
					for (int i = 0; i < checkboxes.size(); i++) {
						if (checkboxes.get(i).isSelected()) {
							billedTenants.add(Integer.parseInt(table.get(i)[0]));
						}
					}

					SQLFunctions.bill(billedTenants);

					ArrayList<String[]> table = SQLFunctions.getTenantStatementInfo();
					for (String[] row : table) {
						if (billedTenants.contains(Integer.parseInt(row[5])) && !(row[1].equals("null") || row[1] == null)) {
							try (FileInputStream f = new FileInputStream("db.properties")) {

								// load the properties file
								Properties pros = new Properties();
								pros.load(f);

								// assign db parameters
								String name = pros.getProperty("company_name");
								String street = pros.getProperty("company_staddress");
								String city = pros.getProperty("company_citystatezip");

								String m1 = name + "\n" + street + "\n" + city + "\n\n--------------------------------------------------------------------------" 
								+ "\n\nTenant:          " + row[0]
										+ "\nEmail:           " + row[1] + "\nAddress:         " + row[3]
										+ "\nMonthly Rent:    $" + row[4] + "\nBalance Due:     $" + row[2]
										+ "\nPrior Balance:   $" + (Integer.valueOf(row[2]) - Integer.valueOf(row[4])) + "\n\n--------------------------------------------------------------------------";
								
								
								Calendar cal = GregorianCalendar.getInstance();
								SimpleDateFormat df = new SimpleDateFormat("MMMM");

								Date currentMonth = new Date();
								cal.setTime(currentMonth);

								// Add next month
								cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
								String nextString = df.format(cal.getTime());

								String m2 = "\n\nDear " + row[0]
										+ ",\n\nThank you for your business. This is your invoice for the month of "
										+ nextString
										+ ". Please submit payment prior to the due date to avoid fees. A copy of your statement is attached.\n\nThank you,\n"
										+ name;

								String message = m1.concat(m2);
								try {
									Mail.send(row[1], "INVOICE FOR " + nextString, message,
											HistoryFile.createFile(Integer.valueOf(row[5])), "history.txt");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (IOException e) {
								System.out.println(e.getMessage());
							}
						}
					}

					JOptionPane.showMessageDialog(new JFrame(), "Billing finished");

					gui.remove(b);
					gui.add(gui.getMenu());
					gui.revalidate();
					gui.repaint();
				}
			}

			public void mouseEntered(MouseEvent e) {
				continueToBill.setBackground(Color.white);
				continueToBill.setForeground(Color.cyan);
			}

			public void mouseExited(MouseEvent e) {
				continueToBill.setBackground(Color.cyan);
				continueToBill.setForeground(Color.gray);
			}
		});

		loadPanel();

		pane = new JScrollPane(panel);
		pane.setPreferredSize(new Dimension(1200, 600));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		add(label, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 10, 10);
		add(selectAll, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 10, 10);
		add(deselectAll, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 450, 10, 10);
		add(cancel, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 10, 10, 10);
		add(continueToBill, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.WEST;
		add(pane, gbc);

		setSize(1150, 630);
		setVisible(true);
	}

	public void loadPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());

		// creating first row of column labels
		for (int i = 0; i < cLabels.length; i++) {
			JLabel label = new JLabel(cLabels[i], SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			if (i == 0) {
				label.setPreferredSize(new Dimension(60, 20));
			} else {
				label.setPreferredSize(new Dimension(100, 20));
			}
			gbc.gridx = i + 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(2, 0, 2, 0);
			panel.add(label, gbc);
		}

		// Creating Check Boxes
		checkboxes = new ArrayList<JCheckBox>();
		for (int i = 0; i < table.size(); i++) {
			JCheckBox c = new JCheckBox();
			checkboxes.add(c);

			gbc.gridx = 0;
			gbc.gridy = i + 1;
			gbc.insets = new Insets(6, 6, 6, 6);
			panel.add(c, gbc);
		}

		for (int i = 0; i < table.size(); i++) {
			for (int j = 1; j < table.get(i).length; j++) {
				JLabel label = new JLabel(table.get(i)[j], SwingConstants.CENTER);
				label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				label.setOpaque(true);
				label.setBackground(Color.white);

				if (j == 2 && (table.get(i)[j].equals("null") || table.get(i)[j].length() == 0)) {
					label.setBackground(Color.red);
					label.setText("");
					label.setPreferredSize(new Dimension(100, 18));
				}

				if (j == 3 && Integer.parseInt(table.get(i)[j]) > 0) {
					label.setBackground(Color.red);
					label.setPreferredSize(new Dimension(80, 18));
				} else if (j == 3 && Integer.parseInt(table.get(i)[j]) < 0) {
					label.setBackground(Color.green);
					label.setPreferredSize(new Dimension(80, 18));
				}

				gbc.gridx = j;
				gbc.gridy = i + 1;
				gbc.insets = new Insets(4, 0, 4, 0);
				panel.add(label, gbc);
			}
		}
	}

	public void display() {
		gui.add(this);
		gui.revalidate();
		gui.repaint();
	}
}