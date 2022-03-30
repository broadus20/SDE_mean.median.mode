package com.stephen.stats;
import org.stats.AnalyzerConfigurationException;
import org.stats.RatingAnalyzer;

import java.util.*;

public class RatingAnalyzerImpl
        implements RatingAnalyzer {
    private int[] ratings;

    public RatingAnalyzerImpl(int[] ratings) {
        this.setRatings(ratings);
    }

    // BUSINESS CODE
    @Override
    public double mean() {
        double result = 0.0;
        for (double num : ratings){
            result += num;
        }
        result = result/ratings.length;
        return result;
    }

    @Override
    public double median() {
        double median = 0.0;
        Arrays.sort(ratings);

        //Cast int Array to doubles
        double[] numbers = new double[ratings.length];
        for(int i=0; i<ratings.length; i++) {
            numbers[i] = ratings[i];
        }

        // identify median
        if (numbers.length % 2 == 0){
            median = (numbers[numbers.length/2] + numbers[numbers.length/2 - 1])/2;
        }
        else {
            median = numbers[numbers.length / 2];
        }
        return median;
    }

    @Override
    public int[] mode() {
        Arrays.sort(ratings); //sorts numbers from lowest to highest
        Map<Integer, Integer> vals = new HashMap<Integer, Integer>(); //Number, Occurance

        // Create Map for number and frequencies
        int i = 0;
        //get MAIN number, will match MAIN v. TEMP to confirm frequency
        for (int num : ratings) { //num ratings[0,0,0,0,0] go through 1 by 1//start at 0 count up for each occurance
            int count = 0; // there is 1 of that number
            for (int temp : ratings) { // get TEMPORARY_NUMBER
                if (vals.containsKey(num)) { //If we already have this number
                    if (num == temp) { //and the number is what we're looking for MAIN = TEMP
                        count += 1;
                        vals.replace(num, count);//Increase count by 1
                    }
                }
                if (i == 0) { //The first number enters map with count of 1
                    i += 1;
                    count += 1;
                    vals.put(num, count); //num[i] _ count=1
                } else { // !first arg, count = 0 first iteration
                    vals.put(num, count); //num[i] _ count=1
                }
            }
        }
        // convert obj array to int Array
        Object[] objArray = vals.values().toArray();
        Object[] keyArray = vals.keySet().toArray();

        Integer[] valuesArray = new Integer[objArray.length];

        int maxFreq = 0;
        // Identify max Freq
        for (Object num : objArray) {
            if ((Integer) num > maxFreq) {
                maxFreq = (Integer) num;
            }
        }
        List locations = new ArrayList();

        // Identify locations with max freq
        i = -1;
        for (Object key : keyArray) {
            i+=1;
            if ((Integer)objArray[i] == maxFreq) {
                locations.add(i);
            }
        }

        int[] mode = new int[locations.size()];

         // get int[] with number @ max freq locations
        i=-1;
        for (Object location : locations){
            i+=1;
            mode[i] = (Integer)keyArray[(Integer)location];;
        }

        if (noMode(mode, ratings)){
            System.out.println("There is no mode");
            return new int[0];
        }
        return mode;
    }

    public boolean noMode(int[] a, int[] b){
        if(a.length == b.length){
            return true;
        }
       return false;
    }


    // Getters Setters
    public void setRatings(int[] numbers) {
        this.ratings = numbers;
    }
}
