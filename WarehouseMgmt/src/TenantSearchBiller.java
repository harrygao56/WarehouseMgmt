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
import javax.swing.SwingConstants;

public class TenantSearchBiller extends TenantSearch {
	public int tenant;
	
	public TenantSearchBiller(GUI gui) {
		super(gui);
	}
	
	public void search() {
		// Calling sql function to obtain an arraylist of string arrays that contain basic
		// tenant info
		ArrayList<String[]> results = SQLFunctions.getTenantBasicInfoAndBalance(searchBar.getText());
		load(results);
	}
	
	public void load(ArrayList<String[]> table) {
		panel.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		
		JLabel title = new JLabel("Choose Tenant", SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		title.setBackground(Color.white);
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(360, 35));
		title.setForeground(Color.black);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 10;
		gbc.gridwidth = 4;
		panel.add(title, gbc);

		// creating first row of column labels
		String[] cLabels = { "ID", "Name", "Address" , "Balance" };

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
			gbc.ipady = 2;
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
				gbc.gridy = i + 2;
				gbc.insets = new Insets(4, 0, 4, 0);
				panel.add(label, gbc);
			}
		}

		// Creating Select Tenant Button
		for (int i = 0; i < table.size(); i++) {
			JLabel label = new JLabel("Select Tenant", SwingConstants.CENTER);
			label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			label.setOpaque(true);
			label.setBackground(Color.lightGray);
			label.setPreferredSize(new Dimension(110, 16));
			label.setForeground(Color.black);
			// Creating border
			label.setBorder(BorderFactory.createLineBorder(Color.gray));
			int pos = Integer.parseInt(table.get(i)[0]);
			TenantSearch s = this;
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent mevt) {
					gui.remove(s);
					Payment p = new Payment(gui, pos);
				}

				public void mouseEntered(MouseEvent e) {
					label.setBackground(Color.white);
				}

				public void mouseExited(MouseEvent e) {
					label.setBackground(Color.lightGray);
				}
			});
			
			gbc.gridx = 4;
			gbc.gridy = i + 2;
			gbc.insets = new Insets(4, 20, 4, 20);
			panel.add(label, gbc);
		}
			
			gui.revalidate();
			gui.repaint();
	}
}
