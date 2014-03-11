/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcel
 */
public class Assignment3java {

    
    public static void oi(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("test1.txt"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String everything = sb.toString();
        
        String[] resNumbers = everything.split(", ");
        String[] resLetters = new String[resNumbers.length];
        
        for (int i = 0; i < resLetters.length; i++) {
            resLetters[i] = (resNumbers[i].equals("0")) ? "N" : "C"; 
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter("test1letters.fa"));
        System.out.println(resLetters.length);
        bw.write(">NC_002737.1 gene annotation Streptococcus pyogenes M1 GAS, complete genome.\n");
        for (int i = 0; i < resLetters.length;i++) {
            bw.write(resLetters[i]);
        }
        
        BufferedReader br2 = new BufferedReader(new FileReader("annotation1.fa"));
        StringBuilder sb2 = new StringBuilder();
        String line2 = br2.readLine();
        line2 = br2.readLine();

        while (line2 != null) {
            sb2.append(line);
            sb2.append(System.lineSeparator());
            line2 = br2.readLine();
        }
        String everything2 = sb2.toString();
        
        String[] ress = everything2.split("");
        System.out.println(ress.length);
    }
}
