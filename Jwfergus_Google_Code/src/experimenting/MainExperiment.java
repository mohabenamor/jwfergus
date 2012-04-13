package experimenting;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;

import datamining.StringUtilities;
import datamining.WriteToFiles;

public class MainExperiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {


		ArrayList<String> list = new ArrayList();
		list.add("one");
		list.add("two");
		list.add("three");
		System.out.println(list.toString());
		
		
	}
}
