/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Marcel
 */
public class AnalyseInput {

    String genome = Viterbi.read_fasta("genome1.fa");
    String annotation = Viterbi.read_fasta("annotation1.fa");
    HashMap<String, Integer> startCoccurences = new HashMap<>();
    HashMap<String, Integer> leavingCoccurences = new HashMap<>();
    HashMap<String, Integer> startRoccurences = new HashMap<>();
    HashMap<String, Integer> leavingRoccurences = new HashMap<>();

    void run() {
        for (int i = 0; i < genome.length() - 1; i++) {
            //Enter C
            if (annotation.charAt(i) != 'C' && annotation.charAt(i + 1) == 'C') {
                String temp = genome.substring(i + 1, i + 4);
                if (startCoccurences.get(temp) == null) {
                    startCoccurences.put(temp, 1);
                } else {
                    startCoccurences.put(temp, startCoccurences.get(temp) + 1);
                }
            } //Leaving C
            else if (annotation.charAt(i) == 'C' && annotation.charAt(i + 1) != 'C') {
                String temp = genome.substring(i - 2, i + 1);
                if (leavingCoccurences.get(temp) == null) {
                    leavingCoccurences.put(temp, 1);
                } else {
                    leavingCoccurences.put(temp, leavingCoccurences.get(temp) + 1);
                }
            } //Enter R
            else if (annotation.charAt(i) != 'R' && annotation.charAt(i + 1) == 'R') {
                String temp = genome.substring(i + 1, i + 4);
                if (startRoccurences.get(temp) == null) {
                    startRoccurences.put(temp, 1);
                } else {
                    startRoccurences.put(temp, startRoccurences.get(temp) + 1);
                }
            } //Leaving R
            else if (annotation.charAt(i) == 'R' && annotation.charAt(i + 1) != 'R') {
                String temp = genome.substring(i - 2, i + 1);
                if (leavingRoccurences.get(temp) == null) {
                    leavingRoccurences.put(temp, 1);
                } else {
                    leavingRoccurences.put(temp, leavingRoccurences.get(temp) + 1);
                }
            }
        }
        System.out.println("Start C");
        Iterator it = startCoccurences.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("Leaving C");
        it = leavingCoccurences.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("Start R");
        it = startRoccurences.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("Leaving R");
        it = leavingRoccurences.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
