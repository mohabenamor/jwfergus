package datamining;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import weka.attributeSelection.PrincipalComponents;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.RemoveUseless;

import com.google.common.collect.*;

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

        long startTime = System.currentTimeMillis();
        long endTime = 0;

        String country = "libya";
        String inputFileName = System.getProperty("user.dir")
                + "\\src\\datamining\\samples\\" + country + ".tsv";
        IDFValues idfValues = idfCalculation(inputFileName);

        endTime = System.currentTimeMillis();
        System.out.println("Time to calculate IDF (seconds): "
                + ((endTime - startTime) / 1000));

        Hashtable<String, Double> termToTFIDFTable = generateTFIDFHashtable(
                idfValues.getCount(), inputFileName, idfValues);
        
        endTime = System.currentTimeMillis();
        System.out.println("Time to calculate TF*IDF Tables (seconds): "
                + ((endTime - startTime) / 1000));
        
          HashMultisetHandler hashMultisetHandler = new HashMultisetHandler();
          //WriteToFiles.writeToFile(dictionaryHashMultiset, "serialized.1");
          //dictionaryHashMultiset = null; 
          //dictionaryHashMultiset = ReadFromFiles.readFromFile("serialized.1");
          WriteToFiles.writeToFile(hashMultisetHandler.getSortedListFromHashMultiset(idfValues.getUniqueWordsInDocumentCount(),
          HashMultisetComparator.DESCENDING).toString(), "uniquewords.txt", false);
         

        /*
         * This section creates a mapping between an attribute (the term) and
         * that attributes index in the instance vector.
         * Hashtable->attributeNumbering contains this mapping.
         */
        createARFFFile(country, inputFileName, idfValues, termToTFIDFTable);
        endTime = System.currentTimeMillis();
        System.out.println("Time to create ARFF file (seconds): "
                + ((endTime - startTime) / 1000));

        // Using Weka now!
        DataSource source;
        try {
            source = new DataSource(country + ".arff");
            Instances data = source.getDataSet();
            String[] options = weka.core.Utils.splitOptions("-P .5");

            // PrincipalComponents pc = new PrincipalComponents();
            // pc.buildEvaluator(data);
            // pc.transformedData(data);
            // System.out.print(data.numAttributes());
            //
            // options = weka.core.Utils.splitOptions("-N 10 -I 50 -O");
            // EM em = new EM();
            // em.setOptions(options);
            // em.buildClusterer(data);
            DistanceFunction distanceFunction = new weka.core.MinkowskiDistance();
            distanceFunction.setOptions(options); 
            SimpleKMeans simpleKMeans = new SimpleKMeans();
            options = weka.core.Utils.splitOptions("-N 10");
            simpleKMeans.setOptions(options);
            //simpleKMeans.setDistanceFunction(distanceFunction);
            simpleKMeans.buildClusterer(data);
            // DBScan dbscan = new DBScan();
            // dbscan.setOptions(options);
            // dbscan.buildClusterer(data);

            WriteToFiles.writeToFile(simpleKMeans.toString(),
                    country + "-output.txt", false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //
        // PRINTING TIME
        //
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time (seconds): "
                + ((endTime - startTime) / 1000));
    }

    /**
     * @param country
     * @param inputFileName
     * @param idfValues
     * @param termToTFIDFTable
     */
    private static void createARFFFile(String country, String inputFileName,
            IDFValues idfValues, Hashtable<String, Double> termToTFIDFTable) {
        WriteToFiles.writeToFile("@RELATION " + country, country + ".arff",
                false);
        ArrayList<String> instances = new ArrayList<String>();
        ArrayList<String> attributeList = new ArrayList<String>();
        Hashtable<String, Integer> attributeNumbering = new Hashtable<String, Integer>();

        Iterator<String> iterator = idfValues.getUniqueWordsInDocumentCount()
                .elementSet().iterator();
        int iterationCount = 0;
        while (iterator.hasNext()) {
            String term = iterator.next();
            attributeNumbering.put(term, iterationCount);
            attributeList.add("@ATTRIBUTE \"" + term + "\" numeric");
            iterationCount++;
        }

        WriteToFiles.writeToFile(attributeList, country + ".arff", true);
        WriteToFiles.writeToFile("@DATA", country + ".arff", true);

        /*
         * This section iterates through the tweets and creates an arrayList of
         * the instances, with attributes correctly indexed and valued by their
         * TF*IDF.
         */
        Iterator<ArrayList<String>> dataIterator = idfValues.getOverallList().iterator();
        while (dataIterator.hasNext()) {

                ArrayList<String> textAsArray = dataIterator.next();

                // Hash the strings to create a unique set

                HashSet<String> uniqueHash = new HashSet<String>(textAsArray);
                Iterator<String> iteratorOfUniqueHash = uniqueHash.iterator();
                TreeMap<Integer, Double> attributeLocationToTFIDF = new TreeMap<Integer, Double>();
                while (iteratorOfUniqueHash.hasNext()) {
                    String term = iteratorOfUniqueHash.next();
                    attributeLocationToTFIDF.put(attributeNumbering.get(term),
                            termToTFIDFTable.get(term));
                }
                // System.out.println(attributeLocationToTFIDF.toString());
                Iterator<Integer> attributeLocationIterator = attributeLocationToTFIDF
                        .keySet().iterator();

                String thisInstance = "{";
                Boolean instanceWritten = false;
                while (attributeLocationIterator.hasNext()) {

                    Integer attributeLocation = attributeLocationIterator
                            .next();
                    if (attributeLocationToTFIDF.get(attributeLocation) > 2 && attributeLocationToTFIDF.get(attributeLocation)<6) {
                        instanceWritten = true;
                        thisInstance += attributeLocation
                                + " "
                                + attributeLocationToTFIDF.get(attributeLocation).toString() + ",";
                                //+"1,";
                    }

                }
                if (instanceWritten == true) {
                    thisInstance = thisInstance.substring(0,
                            thisInstance.length() - 1)
                            + "}";

                    instances.add(thisInstance);
                }
            }

        WriteToFiles.writeToFile(instances, country + ".arff", true);
    }

    /**
     * @param count
     * @param inputFileName
     * @param idfValues
     * @return
     */
    private static Hashtable<String, Double> generateTFIDFHashtable(long count,
            String inputFileName, IDFValues idfValues) {
        Hashtable<String, Double> termToTFIDFTable = new Hashtable<String, Double>();
        Iterator<ArrayList<String>> dataIterator = idfValues.getOverallList().iterator();
        while (dataIterator.hasNext()) {
                count++;
               

                /*
                 * Splits the tweet's text into individual words
                 */
                ArrayList<String> textAsArray = dataIterator.next();
                /*
                 * stores the line into the dictionary
                 */
                HashMultiset<String> wordCountsInTweet = HashMultiset.create();
                wordCountsInTweet.addAll(textAsArray);
                Iterator<String> iterator = wordCountsInTweet.iterator();

                while (iterator.hasNext()) {
                    String term = iterator.next();
                    int countOfTermInTweet = wordCountsInTweet.count(term);
                    double tfidf = Math
                            .log((double) (countOfTermInTweet * (idfValues
                                    .getCount() / idfValues
                                    .getUniqueWordsInDocumentCount()
                                    .count(term))));
                    termToTFIDFTable.put(term, tfidf);
                }
            }

        return termToTFIDFTable;
    }

    /**
     * @param count
     * @param uniqueWordsInDocumentCount
     * @param inputFileName
     * @return
     */
    private static IDFValues idfCalculation(String inputFileName) {
        ArrayList<ArrayList<String>> summarizedListFile = new ArrayList<ArrayList<String>>();
        HashMultiset<String> uniqueWordsInDocumentCount = HashMultiset.create();
        long count = 0;
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
                count++;
                /*
                 * Reads in the information from an individual Tweet
                 */
                ArrayList<String> lineAsArray = new ArrayList<String>(
                        Arrays.asList(stringReader.split("\t")));
                String text = lineAsArray.get(1);

                /*
                 * Splits the tweet's text into individual words
                 */
                ArrayList<String> textAsArray = StringUtilities
                        .stripTextIntoArray(text);
                summarizedListFile.add(textAsArray);
                /*
                 * stores the line into the dictionary
                 */
                HashSet<String> uniqueHash = new HashSet<String>(textAsArray);
                uniqueWordsInDocumentCount.addAll(uniqueHash);

            }
        } catch (IOException exception) {
            System.out.println("IO Exception:\n" + exception.getMessage());
        }
        

        return new IDFValues(count, uniqueWordsInDocumentCount, summarizedListFile);
    }

}