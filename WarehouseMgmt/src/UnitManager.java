import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UnitManager extends Manager {
	public UnitManager(GUI gui)
	{
		super(gui);
		
		super.panel = new JPanel();
		panel.setBackground(Color.white);
		
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(grid);
		
		//Creating and configuring "New Unit" button
		JLabel newUnit = new JLabel("New Unit", SwingConstants.CENTER);
		newUnit.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		newUnit.setOpaque(true);
		newUnit.setBackground(Color.cyan);
		newUnit.setPreferredSize(new Dimension(200,40));
		newUnit.setForeground(Color.gray);
		//Creating border
		newUnit.setBorder(BorderFactory.createLineBorder(Color.gray));
		newUnit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent mevt) {
                UnitAdder u = new UnitAdder();
            }
		            
            public void mouseEntered(MouseEvent e) {
            	newUnit.setBackground(Color.white);
            	newUnit.setForeground(Color.cyan);
             }

             public void mouseExited(MouseEvent e) {
            	 newUnit.setBackground(Color.cyan);
            	 newUnit.setForeground(Color.gray);
             }
		});
		
		//Creating and configuring "Unit Lookup" button
		JLabel search = new JLabel("Unit Lookup", SwingConstants.CENTER);
		search.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		search.setOpaque(true);
		search.setBackground(Color.cyan);
		search.setPreferredSize(new Dimension(200,40));
		search.setForeground(Color.gray);
		//Creating border
		search.setBorder(BorderFactory.createLineBorder(Color.gray));
		search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent mevt) {
            	UnitSearch search = new UnitSearch(gui);
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
		
		//Creating and configuring "Go Back" button
		JLabel back = new JLabel("Go Back", SwingConstants.CENTER);
		back.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		back.setOpaque(true);
		back.setBackground(Color.cyan);
		back.setPreferredSize(new Dimension(200,40));
		back.setForeground(Color.gray);
		//Creating border
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
		
		//Creating blank filler labels
		JLabel blank2 = new JLabel();
		blank2.setPreferredSize(new Dimension(200,20));
		JLabel blank3 = new JLabel();
		blank3.setPreferredSize(new Dimension(200,20));
		
        gbc.gridx = 1;
		gbc.gridy=1;
        panel.add(newUnit,gbc);
        
        gbc.gridx = 1;
		gbc.gridy=2;
        panel.add(blank2,gbc);
        
        gbc.gridx = 1;
		gbc.gridy=3;
        panel.add(search,gbc);
        
        gbc.gridx = 1;
		gbc.gridy=4;
        panel.add(blank3,gbc);
        
        gbc.gridx = 1;
		gbc.gridy=5;
        panel.add(back,gbc);
	}
}
