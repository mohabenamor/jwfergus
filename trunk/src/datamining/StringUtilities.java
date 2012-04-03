package datamining;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;

public class StringUtilities {

	public static ArrayList<String> stripTextIntoArray(String inputString) {
		inputString = inputString.replaceAll("[#,()\"]", " ");
		ArrayList<String> inputStringArray = new ArrayList<String>(Arrays.asList(inputString.split("(\\s++)")));

		try {
			for (int i = 0; i < inputStringArray.size(); i++) {

				/*
				 * Replace URLs with top level domain
				 */
				if (inputStringArray.get(i).startsWith("http")) {
					Pattern pattern = Pattern.compile(".*?([^.]+\\.[^.]+)");
					URI uri = new URI(inputStringArray.get(i));
					try{
					Matcher matcher = pattern.matcher(uri.getHost());
					if (matcher.matches()) {
						inputStringArray.set(i,matcher.group(1).toString());
					} else {
						inputStringArray.set(i, "MALFORMED_URL");
					}
					}catch(NullPointerException exception){
						inputStringArray.set(i, "MALFORMED_URL");
					}
				} else {
					inputString = inputString.replaceAll(".", "");
				}
				inputStringArray.set(i, (inputStringArray.get(i).replace(":", ""))) ;
				if(inputStringArray.get(i).isEmpty()|| inputStringArray.get(i) == "." || inputStringArray.get(i) == "RT"){
					inputStringArray.remove(i);
				}
			}
		} catch (URISyntaxException e) {
			System.out.println("*** URI Syntax Exception ***\n"
					+ e.getMessage());
			e.printStackTrace();
		}
		return inputStringArray;

	}
}
