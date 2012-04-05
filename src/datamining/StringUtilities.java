package datamining;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author jwfergus
 *
 */
public class StringUtilities {

	/**
	 * @param inputString
	 * @return
	 */
	public static ArrayList<String> stripTextIntoArray(String inputString) {
		inputString = inputString.replaceAll("[#,()@\"]", " ").toLowerCase();
		ArrayList<String> inputStringArray = new ArrayList<String>(
				Arrays.asList(inputString.split("[(\\s++)\\u060C]")));

		for (int i = 0; i < inputStringArray.size(); i++) {

			/*
			 * Replace URLs with top level domain
			 */
			if (inputStringArray.get(i).startsWith("http")) {
				Pattern pattern = Pattern.compile(".*?([^.]+\\.[^.]+)");
				try {
					URI uri = new URI(inputStringArray.get(i));
					Matcher matcher = pattern.matcher(uri.getHost());
					if (matcher.matches()) {
						inputStringArray.set(i, matcher.group(1).toString());
					} else {
						inputStringArray.set(i, "MALFORMED_URL");
					}
				} catch (Exception exception) {
					inputStringArray.set(i, "MALFORMED_URL");
				}
			} else {
				inputStringArray.set(i,
						inputStringArray.get(i).replaceAll("\\p{Punct}", ""));
			}
			inputStringArray.set(i, (inputStringArray.get(i).replace(":", "")));
			if (inputStringArray.get(i).isEmpty()
					|| inputStringArray.get(i) == "."
					|| inputStringArray.get(i) == "RT") {
				inputStringArray.remove(i);
			}
		}

		return inputStringArray;

	}
	
	/**
	 * @param <E>
	 * @param list
	 * @return
	 */
	public static <E> List<List<E>> getPowerSet(Collection<E> list){
		List<List<E>> powerSet = new ArrayList<List<E>>();
		powerSet.add(new ArrayList<E>());
		for(E element: list){
			List<List<E>> newPowerSet = new ArrayList<List<E>>();
			for(List<E> subset: powerSet){
				newPowerSet.add(subset);
				List<E> newSubset = new ArrayList<E>(subset);
				newSubset.add(element);
				newPowerSet.add(newSubset);
			}
			powerSet = newPowerSet;
		}
		powerSet.remove(0);
		return powerSet;
	}
}
