/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Marcel
 */
public class GenerateString {

    List<String> allStrings;
    char[] c;
    int n;
    
    GenerateString(char[] c, int n)
    {
        this.c = c;
        this.n = n;
        allStrings = new ArrayList<>();
        printAll(c,n,"");
        
    }

    public List<String> getAllStrings() {
        return allStrings;
    }
    
    
            
    public void printAll(char[] c, int n, String start) {
        if (start.length() >= n) {
            allStrings.add(start);
        } else {
            for (char x : c) { // not a valid syntax in Java
                printAll(c, n, start + x);
            }
        }
    }
}
