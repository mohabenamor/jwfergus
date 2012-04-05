package experimenting;

import java.util.ArrayList;
import java.util.List;

import datamining.StringUtilities;

public class MainExperiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("one");
		testList.add("two");
		testList.add("three");
		testList.add("four");
		
		List<List<String>> list = StringUtilities.getPowerSet(testList);
		System.out.println(list.toString());
		
	}
}
