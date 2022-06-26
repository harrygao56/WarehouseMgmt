import javax.swing.JFrame;
import javax.swing.JLabel;
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
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;

public class Report extends JFrame {
	private JPanel panel;
	private JScrollPane pane;
	
	public Report() {
		setTitle("Report");
		
		panel = new JPanel();
		panel.setBackground(Color.white);
		
		loadPanel();

		pane = new JScrollPane(panel);
		pane.setPreferredSize(new Dimension(1000, 550));
		
		add(pane);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void loadPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		
		ArrayList<String[]> table = SQLFunctions.getTenantReportInfo();
		
		// creating first row of column labels
		String[] cLabels = { "Name", "Email", "Owed ($)", "Address", "Phone", "Rent ($)" };
		
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
			for (int j = 1; j < table.get(i).length; j++) {
				JLabel label = new JLabel(table.get(i)[j], SwingConstants.CENTER);
				label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				label.setOpaque(true);
				label.setBackground(Color.white);
				
				if (j == 2 && (table.get(i)[j].equals("null") || table.get(i)[j].length() == 0)) {
					label.setBackground(Color.red);
					label.setText("");
					label.setPreferredSize(new Dimension(140, 18));
				}
				
				if (j == 3 && Integer.parseInt(table.get(i)[j]) > 0) {
					label.setBackground(Color.red);
					label.setPreferredSize(new Dimension(80, 18));
				}
				else if (j == 3 && Integer.parseInt(table.get(i)[j]) < 0) {
					label.setBackground(Color.green);
					label.setPreferredSize(new Dimension(80, 18));
				}

				gbc.gridx = j - 1;
				gbc.gridy = i + 1;
				gbc.insets = new Insets(4, 0, 4, 0);
				panel.add(label, gbc);
			}
		}
	}
}