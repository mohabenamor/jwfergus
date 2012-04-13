package datamining;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import com.google.common.collect.HashMultiset;

public class ReadFromFiles {

	public static HashMultiset<String> readFromFile(String fileName){
		HashMultiset<String> dictionaryHashMultiset = null;
		try{
		      //use buffering
		      InputStream file = new FileInputStream( fileName );
		      InputStream buffer = new BufferedInputStream( file );
		      ObjectInput input = new ObjectInputStream ( buffer );
		      try{
		    	  dictionaryHashMultiset = (HashMultiset<String>)input.readObject();
		       // System.out.println(dictionaryHashMultiset.toString());
		      }
		      finally{
		        input.close();
		      }
		    }
		    catch(ClassNotFoundException ex){
		      ex.printStackTrace();
		    }
		    catch(IOException ex){
		      ex.printStackTrace();
		    }
		    return dictionaryHashMultiset;
	}
}
