package com.tarashor.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.*;

/**
 * Created by Taras on 05.01.2017.
 */
public class FileUtils {
    public static void countWordsInFile(String fileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileName));

        // count occurrences
        Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
        while (input.hasNext()) {
            String next = input.next().toLowerCase();
            next = next.replaceAll("^[^\\p{L}^\\p{N}^\\p{M}\\s%]+|[^\\p{L}^\\p{N}^\\p{M}\\s%]+$", "");
            if (!wordCounts.containsKey(next)) {
                wordCounts.put(next, 1);
            } else {
                wordCounts.put(next, wordCounts.get(next) + 1);
            }
        }

        List<Entry<String, Integer>> statistic = sortMapByIntegerValue(wordCounts);

        // get cutoff and report frequencies
        System.out.println("Total words = " + wordCounts.size());
        for (Entry<String, Integer> item : statistic) {
            int count = item.getValue();
            if (count >= 2)
                System.out.println(count + "\t" + item.getKey());
        }
    }


    static final Comparator<Entry<String, Integer>> DOUBLE_VALUE_COMPARATOR =
            new Comparator<Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            };

    static final List<Entry<String, Integer>> sortMapByIntegerValue(Map<String, Integer> temp)
    {
        Set<Entry<String, Integer>> entryOfMap = temp.entrySet();
        List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(entryOfMap);
        Collections.sort(entries, DOUBLE_VALUE_COMPARATOR);
        return entries;
    }
}
