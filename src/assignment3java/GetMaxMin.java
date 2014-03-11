/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3java;

/**
 *
 * @author Marcel
 */
public class GetMaxMin {
    private double[] vec;
    private double maxNumber;
    private double minNumber;
    private int indexMin;
    private int indexMax;

    public GetMaxMin(double[] vec) {
        this.vec = vec;
    }

    public double getMaxNumber() {
        return maxNumber;
    }

    public double getMinNumber() {
        return minNumber;
    }

    public int getIndexMin() {
        return indexMin;
    }

    public int getIndexMax() {
        return indexMax;
    }
    
    
    
    void min ()
    {
        minNumber = vec[0];
        indexMin = 0;
        for (int i = 0; i < vec.length; i++) {
            if (vec[i] < minNumber) {
                minNumber = vec[i];
                indexMin = i;
            }
        }
    }
    
    void max ()
    {
        maxNumber = vec[0];
        indexMax = 0;
        for (int i = 0; i < vec.length; i++) {
            if (vec[i] > maxNumber) {
                maxNumber = vec[i];
                indexMax = i;
            }
        }
    }
}
