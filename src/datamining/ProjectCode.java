package datamining;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.*;
import com.google.common.collect.Multiset.Entry;

/**
 * 
 * @author Joshua Ferguson 4/1/2012
 * 
 */
public class ProjectCode {

	/**
	 * @param args
	 *            the command line arguments - None now.
	 */

	public static void main(String[] args) {

		// Main Dictionary
		HashMultiset<String> dictionaryHashMultiset = HashMultiset.create();

		String inputFileName = System.getProperty("user.dir")
				+ "\\src\\datamining\\samples\\yemen.tsv";
		System.out.println("Looking for file at: " + inputFileName);
		BufferedReader bufferedReader = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(inputFileName);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
		} catch (Exception exception) {
			System.out.println("Exception:\n" + exception.getMessage());
			exception.printStackTrace();
		}

		try {
			String stringReader;
			while ((stringReader = bufferedReader.readLine()) != null) {
				/*
				 * Reads in the information from an individual Tweet
				 */
				ArrayList<String> lineAsArray = new ArrayList<String>(
						Arrays.asList(stringReader.split("\t")));
				String tweetID = lineAsArray.get(0);
				String text = lineAsArray.get(1);
				String user = lineAsArray.get(2);
				String publicationTime = lineAsArray.get(3);
				String latitude = lineAsArray.get(4);
				String longitude = lineAsArray.get(5);
				String location = lineAsArray.get(6);
				String geotag = lineAsArray.get(7);
				String publishMode = lineAsArray.get(8);
				String country = lineAsArray.get(9);

				/*
				 * Splits the tweet's text into individual words
				 */
				ArrayList<String> textAsArray = StringUtilities
						.stripTextIntoArray(text);
				/*
				 * stores the line into the dictionary
				 */
				dictionaryHashMultiset.addAll(textAsArray);

				/*
				 * Prints to examine data
				 */
				/*
				 * System.out.println("******** Tweet ID: " + tweetID);
				 * System.out.print("User: "); System.out.println(user);
				 * 
				 * //Temporary for looking through input BufferedReader
				 * userInput = new BufferedReader(new
				 * InputStreamReader(System.in)); String name = null; try { name
				 * = userInput.readLine(); } catch (IOException e) {
				 * System.out.println("Error!"); System.exit(1); }
				 */

			}
		} catch (IOException exception) {
			System.out.println("IO Exception:\n" + exception.getMessage());
		}
		HashMultisetHandler hashMultisetHandler = new HashMultisetHandler();
		OutputWriter.writeToFile(hashMultisetHandler.getSortedListFromHashMultiset(dictionaryHashMultiset, HashMultisetComparator.DESCENDING).toString());
		//OutputWriter.writeToFile(dictionaryHashMultiset.toString());

	}

}
