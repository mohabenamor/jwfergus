package datamining;

import java.util.ArrayList;
import java.io.*;

public class OutputWriter {

	public static void writeToFile(ArrayList<String> line) {
		try {
			FileWriter fileWriter = new FileWriter("output.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < line.size(); i++) {
				bufferedWriter.write(line.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter
					.write("\n*****************************************************");
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (Exception exception) {

		}
	}

	public static void writeToFile(String line) {
		try {
			FileWriter fileWriter = new FileWriter("output.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(line);
			bufferedWriter.newLine();
			bufferedWriter
					.write("\n*****************************************************");
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (Exception exception) {

		}
	}
}
