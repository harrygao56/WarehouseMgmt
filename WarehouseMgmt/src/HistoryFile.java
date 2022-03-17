import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HistoryFile {
	public static File createFile(int tenant) {
		try {
			ArrayList<String[]> history = SQLFunctions.getHistory(tenant);
			
			File file = new File("history.txt");
			FileWriter myWriter = new FileWriter(file);
			
			myWriter.write("Date      New Balance   Transaction   Type      Desc.\n");
			
			for (String[] r : history) {
				for (String s : r) {
					if (s == null) {
						myWriter.write("    ");
					}
					else {
						myWriter.write(s);
					}
					myWriter.write("     ");
				}
				myWriter.write("\n");
			}
			
			myWriter.close();
			
			return file;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}