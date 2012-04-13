package datamining;

import java.util.ArrayList;

import com.google.common.collect.HashMultiset;

public class IDFValues {
    long count;
    HashMultiset<String> uniqueWordsInDocumentCount;
    ArrayList<ArrayList<String>> overallList;
    IDFValues(long count, HashMultiset<String> uniqueWordsInDocumentCount, ArrayList<ArrayList<String>> overallList){
        this.count = count;
        this.uniqueWordsInDocumentCount = uniqueWordsInDocumentCount;
        this.overallList = overallList;
    }

    public long getCount() {
        return count;
    }

    public HashMultiset<String> getUniqueWordsInDocumentCount() {
        return uniqueWordsInDocumentCount;
    }
    public void setUniqueWordsInDocumentCount(HashMultiset<String> uniqueWordsInDocumentCount) {
        this.uniqueWordsInDocumentCount = uniqueWordsInDocumentCount;
    }

    public ArrayList<ArrayList<String>> getOverallList() {
        return overallList;
    }
}
