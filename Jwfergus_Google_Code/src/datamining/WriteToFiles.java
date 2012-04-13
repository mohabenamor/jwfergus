package datamining;

import java.util.ArrayList;
import java.io.*;

public class WriteToFiles {

	/**
	 * @param line - ArrayList<String> to write to file
	 * @param fileName - the name of the file to write to
	 * @param append TODO
	 */
	public static void writeToFile(ArrayList<String> line, String fileName, Boolean append) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, append);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < line.size(); i++) {
				bufferedWriter.write(line.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (Exception exception) {

		}
	}

	/**
	 * @param line String to write to file
	 * @param fileName the name of the file to write to
	 */
	public static void writeToFile(String line, String fileName, Boolean append) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, append);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(line);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (Exception exception) {

		}
	}

	/*
	 * @param object - the object to be serialized and written
	 * @param fileName - the name of the file to write to
	 */
	public static void writeToFile(Object object, String fileName) {
		FileOutputStream fileOutputStream;
		try {
			OutputStream file = new FileOutputStream(fileName, false);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(object);
			output.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
