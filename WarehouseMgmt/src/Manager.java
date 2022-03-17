import javax.swing.JPanel;

public abstract class Manager {
	private GUI gui;
	public JPanel panel;
	
	public Manager(GUI gui)
	{
		this.gui = gui;
	}
	
	public void display()
	{
		gui.add(panel);
		gui.revalidate();
		gui.repaint();
	}
	
	public void returnToMenu()
	{
		gui.remove(panel);
		gui.add(gui.getMenu());
		gui.revalidate();
		gui.repaint();
	}
}
