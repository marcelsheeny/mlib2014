/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class Viterbi {
    
    private double[][] trans;
    private double[][] emit;
    private double[] init;
    
    private int[] input;
    private int[] states;
    private int[] hiddenStates;
    
    private double likelihood = 0;
    private ArrayList<Integer> predict;
    private Integer[] predict2;
    private String predictString;
    
    
    
    public static Integer[] conv (String stri,HashMap<Character, Integer> dic)
    {
        Integer[] arr = new Integer[stri.length()];
        for (int i = 0; i < arr.length; i++) 
        {
            arr[i] = dic.get(stri.charAt(i));
        }
        return arr;
    }
    
    
    private static void initvec(int[] v)
    {
        for (int i = 0; i < v.length; i++)
        {
            v[i] = i;
        }   
    }

    public String getPredictString() {
        return predictString;
    }
    
    public static String read_fasta(String name) 
    {
        BufferedReader br = null;
        String everything = "";
        try {
            br = new BufferedReader(new FileReader(name));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            everything = sb.toString();
        }
        catch (FileNotFoundException fnfe)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, fnfe);
        }
        catch (IOException ioex)
        {
            System.out.println("LOL");
        }
        finally
        {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return everything;
    }
    
    void run (String input, String states, String hidden, double[][] trans, double[][] emit, double[] init, double[][] conf_matrix)
    {
        this.trans = trans;
        this.emit = emit;
        this.init = init;
        this.states = new int[states.length()];
        initvec(this.states);
        this.hiddenStates = new int[hidden.length()];
        initvec(this.hiddenStates);
        this.input = new int[input.length()];
        Map<Character, Integer> inputHash = new HashMap<>();
        for (int i = 0; i < this.states.length; i++) {
            inputHash.put(states.charAt(i), this.states[i]);
        }
        
        Map<Integer, Character> predHash = new HashMap<>();
        for (int i = 0; i < this.hiddenStates.length; i++)
        {
            predHash.put(this.hiddenStates[i],hidden.charAt(i));
        }
        
        for (int i = 0; i < this.input.length; i++) {
            this.input[i] = inputHash.get(input.charAt(i));
        }
        
        viterbi_logspace2();
        
        System.out.println(likelihood);
//        System.out.println(predict.size());
//        predictString = "";
//        for (int i = 0; i < predict.size(); i++)
//        {
//            predictString += predHash.get(predict.get(i)); 
//        }
        
        System.out.println(predict2.length);
        predictString = "";
        BufferedWriter bw = null;
        BufferedWriter bw2 = null;
        try
        {
            bw = new BufferedWriter(new FileWriter("asdasd.fa"));
            bw2 = new BufferedWriter(new FileWriter("asdasd2.fa"));
            System.out.println(predict2.length);
            bw.write(">NC_002737.1 gene annotation Streptococcus pyogenes M1 GAS, complete genome.\n");
            for (int i = 0; i < predict2.length; i++)
            {
                bw.write(predHash.get(predict2[i])); 
                bw2.write(Integer.toString(predict2[i])); 
                //predictString += predHash.get(predict2[i]);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                bw.close();
                bw2.close();
            }
            catch(Exception ex2)
            {
                ex2.printStackTrace();
            }
        }
        predictString = read_fasta("asdasd.fa");
        
        
//        evaluate(read_fasta("annotation1.fa"), predictString, conf_matrix);   
//        System.out.println("CONF MATRIX");
//        printMatrix(conf_matrix);
//        
//        double tp = conf_matrix[0][0];
//        double tn = conf_matrix[1][1];
//        double fp = conf_matrix[0][1];
//        double fn = conf_matrix[1][0];
//        
//        double sn = (double)tp / (double)(tp + fn);
//        double sp = (double)(tp) / (double)(tp + fp);
//        double cc = (double)((tp*tn - fp*fn)) / Math.sqrt((double)((tp+fn)*(tn+fp)*(tp+fp)*(tn+fn)));
//        double acp = 0.25 * ((double)(tp)/(tp+fn) + (double)(tp)/(tp+fp) + (double)(tn)/(tn+fp) + (double)(tn)/(tn+fn));
//        double ac = (acp - 0.5) * 2;
//        double accu = (double)(tp+fp)/(double)(tp+fp+tn+fn);
//        
//        System.out.println("Sn\tSp\tCC\tAC\tAccu");
//        System.out.println(sn+"\t"+sp+"\t"+cc+"\t"+ac+"\t"+accu);
    }
    
    void runCV (String input, String states, String hidden, double[][] trans, double[][] emit, double[] init, double[][] conf_matrix, String test_ann)
    {
        this.trans = trans;
        this.emit = emit;
        this.init = init;
        this.states = new int[states.length()];
        initvec(this.states);
        this.hiddenStates = new int[hidden.length()];
        initvec(this.hiddenStates);
        this.input = new int[input.length()];
        Map<Character, Integer> inputHash = new HashMap<>();
        for (int i = 0; i < this.states.length; i++) {
            inputHash.put(states.charAt(i), this.states[i]);
        }
        
        Map<Integer, Character> predHash = new HashMap<>();
        for (int i = 0; i < this.hiddenStates.length; i++) {
            predHash.put(this.hiddenStates[i],hidden.charAt(i));
        }
        
        for (int i = 0; i < this.input.length; i++) {
            this.input[i] = inputHash.get(input.charAt(i));
        }
        
        viterbi_logspace2();
        
        System.out.println(likelihood);
//        System.out.println(predict.size());
//        predictString = "";
//        for (int i = 0; i < predict.size(); i++)
//        {
//            predictString += predHash.get(predict.get(i)); 
//        }
        
        System.out.println(predict2.length);
        predictString = "";
        BufferedWriter bw = null;
        BufferedWriter bw2 = null;
        try
        {
            bw = new BufferedWriter(new FileWriter("asdasd.fa"));
            bw2 = new BufferedWriter(new FileWriter("asdasd2.fa"));
            System.out.println(predict2.length);
            bw.write(">NC_002737.1 gene annotation Streptococcus pyogenes M1 GAS, complete genome.\n");
            for (int i = 0; i < predict2.length; i++)
            {
                bw.write(predHash.get(predict2[i]));
                bw2.write(predict2[i]);
                //predictString += predHash.get(predict2[i]);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                bw.close();
            }
            catch(Exception ex2)
            {
                ex2.printStackTrace();
            }
        }
        String tempPredictString = read_fasta("asdasd.fa");
        
        System.out.println("ASD");
        
        evaluate(test_ann, tempPredictString, conf_matrix);        
    }
    
    public void viterbi_logspace()
    {
        double[][] w = new double[input.length][hiddenStates.length];
        Map<Integer,ArrayList<Integer>> path = new HashMap<>();
        long startTime = System.currentTimeMillis();
        for (int y : hiddenStates) 
        {
            w[0][y] = Double.NEGATIVE_INFINITY;
            double val = Math.log(init[y]) + Math.log(emit[y][input[0]]);
            w[0][y] = val;
            ArrayList<Integer> ini = new ArrayList<>();
            ini.add(y);
            path.put(y, ini);
        }
        
        for (int t = 1; t < input.length; t++)
        {
            Map<Integer,ArrayList<Integer>> newpath = new HashMap<>();
            for (int y : hiddenStates) 
            {
                double[] tempList = new double[hiddenStates.length];
                for (int y0 : hiddenStates)
                {
                    tempList[y0] = Double.NEGATIVE_INFINITY;
                    double val = (w[t-1][y0] + Math.log(trans[y0][y]) + Math.log(emit[y][input[t]]));
                     tempList[y0] = val;
                }
                GetMaxMin gmm = new GetMaxMin(tempList);
                gmm.max();
                w[t][y] = gmm.getMaxNumber();
                newpath.put(y,new ArrayList<>(path.get(gmm.getIndexMax())));
                newpath.get(y).add(y);
            }
            path = newpath;
            if (t%1000 == 0)
            {
                long estimatedTime = System.currentTimeMillis() - startTime;
                System.out.println((float)estimatedTime/1000.f+"s");
                System.out.println("\r"+(float)(t)/(float)(input.length)+"%");
            }
        }
        //temporary list that store the last probabilities
        double[] temp_lis = new double[hiddenStates.length];
        for (int y : hiddenStates)
        {
            temp_lis[y] = w[input.length-1][y];
        }

        //return max probability and best path

        GetMaxMin gmm = new GetMaxMin(temp_lis);
        gmm.max();
        predict = path.get(gmm.getIndexMax());
        likelihood = gmm.getMaxNumber();
    }
    
     public void viterbi_logspace2()
    {
        double[][] w = new double[hiddenStates.length][input.length];
        Integer[][] path = new Integer[hiddenStates.length][input.length];

        for (int y : hiddenStates) 
        {
            w[y][0] = Math.log(init[y]) + Math.log(emit[y][input[0]]);
            path[y][0] = 0;
        }
        
        for (int t = 1; t < input.length; t++)
        {
            for (int y : hiddenStates) 
            {
                double[] tempList = new double[hiddenStates.length];
                for (int y0 : hiddenStates)
                {
                    double val = (w[y0][t-1] + Math.log(trans[y0][y]) + Math.log(emit[y][input[t]]));
                    tempList[y0] = val;
                }
                GetMaxMin gmm = new GetMaxMin(tempList);
                gmm.max();
                w[y][t] = gmm.getMaxNumber();
                path[y][t] = gmm.getIndexMax();
            }
        }
        //temporary list that store the last probabilities
        double[] temp_lis = new double[hiddenStates.length];
        for (int y : hiddenStates)
        {
            temp_lis[y] = w[y][input.length-1];
        }

        //return max probability and best path

        GetMaxMin gmm = new GetMaxMin(temp_lis);
        gmm.max();
        //predict2 = path[gmm.getIndexMax()];
        likelihood = gmm.getMaxNumber();
        
        Integer[] z = new Integer[input.length];
        predict2 = new Integer[input.length];
        
        z[input.length-1] = gmm.getIndexMax();
        predict2[input.length-1] = hiddenStates[z[input.length-1]];
        for (int i = input.length-1; i > 0; i--) {
            z[i-1] = path[z[i]][i];
            predict2[i-1] = hiddenStates[z[i-1]];
        }
    
    }
     
    static void printMatrix(double[][] x)
    {
        for (int i = 0; i < x.length; i++) {
            System.out.print("[");
            for (int j = 0; j < x[0].length-1; j++) {
                System.out.print(" "+x[i][j]+", ");  
            }
            System.out.println(" "+x[i][x[0].length-1]+"]");
        }
    }
    static void printVector(double[] x)
    {
        System.out.print("[");
        for (int i = 0; i < x.length-1; i++) {
            System.out.print(" "+x[i]+", ");  
        }
        System.out.println(" "+x[x.length-1]+"]");
    }
    
    public static void initPseudoCountMatrix(double[][] m)
    {
        for (int i = 0; i < m.length; i++)
        {
            for (int j = 0; j < m[0].length; j++)
            {
                m[i][j] = 1;    
            }
        }
    }
    public static void initPseudoCountVector(double[] m, double value)
    {
        for (int i = 0; i < m.length; i++)
        {
            m[i] = value;
        }
    }
    
public static void count_c(String true_ann,String pred_ann, double[][] conf_matrix)
{
    double tp = conf_matrix[0][0];
    double tn = conf_matrix[1][1];
    double fp = conf_matrix[0][1];
    double fn = conf_matrix[1][0];
    double total = 0;
    for (int i = 0; i < true_ann.length(); i++)
    {
        try 
        {
        if (pred_ann.charAt(i) == 'C' || pred_ann.charAt(i) == 'c')
        {
            total = total + 1;
            if (true_ann.charAt(i) == 'C' || true_ann.charAt(i) == 'c')
            {
                tp = tp + 1;
            }
            else
            {
                fp = fp + 1;
            }
        
        }
        if (pred_ann.charAt(i) == 'N' || pred_ann.charAt(i) == 'n')
        {
            if (true_ann.charAt(i) == 'N' || true_ann.charAt(i) == 'n' || true_ann.charAt(i) == 'R' || true_ann.charAt(i) == 'r')
            {    
                tn = tn + 1;
            }
            else
            {
                fn = fn + 1;
            }
        }
        }
                 catch(Exception e)
        {
        
        }
    }
    conf_matrix[0][0] = tp;
    conf_matrix[1][1] = tn;
    conf_matrix[0][1] = fp;
    conf_matrix[1][0] = fn;
}

public static void count_r(String true_ann,String pred_ann, double[][] conf_matrix)
{
    double tp = conf_matrix[0][0];
    double tn = conf_matrix[1][1];
    double fp = conf_matrix[0][1];
    double fn = conf_matrix[1][0];
    double total = 0;
    for (int i = 0; i < true_ann.length(); i++)
    {
        if (pred_ann.charAt(i) == 'R' || pred_ann.charAt(i) == 'r')
        {
            total = total + 1;
            if (true_ann.charAt(i) == 'R' || true_ann.charAt(i) == 'r')
            {
                tp = tp + 1;
            }
            else
            {
                fp = fp + 1;
            }
        }
        if (pred_ann.charAt(i) == 'N' || pred_ann.charAt(i) == 'n')
        {
            if (true_ann.charAt(i) == 'N' || true_ann.charAt(i) == 'n' || true_ann.charAt(i) == 'C' || true_ann.charAt(i) == 'c')
            {    
                tn = tn + 1;
            }
            else
            {
                fn = fn + 1;
            }
        }
    }
    conf_matrix[0][0] = tp;
    conf_matrix[1][1] = tn;
    conf_matrix[0][1] = fp;
    conf_matrix[1][0] = fn;
}

public static void count_cr(String true_ann,String pred_ann, double[][] conf_matrix)
{
    double tp = conf_matrix[0][0];
    double tn = conf_matrix[1][1];
    double fp = conf_matrix[0][1];
    double fn = conf_matrix[1][0];
    double total = 0;
    for (int i = 0; i < true_ann.length(); i++)
    {
        if (pred_ann.charAt(i) == 'C' || pred_ann.charAt(i) == 'c' || pred_ann.charAt(i) == 'R' || pred_ann.charAt(i) == 'r')
        {
            total = total + 1;
            if ((true_ann.charAt(i) == 'C' || true_ann.charAt(i) == 'c') &&((true_ann.charAt(i) == 'C' || true_ann.charAt(i) == 'c')))
            {
                tp = tp + 1;
            }
            else if ((pred_ann.charAt(i) == 'R' || pred_ann.charAt(i) == 'r') && (true_ann.charAt(i) == 'R' || true_ann.charAt(i) == 'r'))
            {
                tp = tp + 1;
            }
            else
            {
                fp = fp + 1;
            }
        }
        if (pred_ann.charAt(i) == 'N' || pred_ann.charAt(i) == 'n')
        {
            if (true_ann.charAt(i) == 'N' || true_ann.charAt(i) == 'n' )
            {    
                tn = tn + 1;
            }
            else
            {
                fn = fn + 1;
            }
        }
    }
    conf_matrix[0][0] = tp;
    conf_matrix[1][1] = tn;
    conf_matrix[0][1] = fp;
    conf_matrix[1][0] = fn;
}
    
    
    public static void evaluate (String true_ann, String pred_ann, double[][] conf_matrix)
    {
        count_c(true_ann, pred_ann, conf_matrix);
//        {
//        double tp = conf_matrix[0][0];
//        double tn = conf_matrix[1][1];
//        double fp = conf_matrix[0][1];
//        double fn = conf_matrix[1][0];
//
//        double sn = (double)tp / (double)(tp + fn);
//        double sp = (double)(tp) / (double)(tp + fp);
//        double cc = (double)((tp*tn - fp*fn)) / Math.sqrt((double)((tp+fn)*(tn+fp)*(tp+fp)*(tn+fn)));
//        double acp = 0.25 * ((double)(tp)/(tp+fn) + (double)(tp)/(tp+fp) + (double)(tn)/(tn+fp) + (double)(tn)/(tn+fn));
//        double ac = (acp - 0.5) * 2;
//        double accu = (double)(tp+fp)/(double)(tp+fp+tn+fn);
//        System.out.println("Only C");
//        System.out.println("Sn\tSp\tCC\tAC\tAccu");
//        System.out.println(sn+"\t"+sp+"\t"+cc+"\t"+ac+"\t"+accu);
//        }
//        
//        count_r(true_ann, pred_ann, conf_matrix);
//        {
//        double tp = conf_matrix[0][0];
//        double tn = conf_matrix[1][1];
//        double fp = conf_matrix[0][1];
//        double fn = conf_matrix[1][0];
//
//        double sn = (double)tp / (double)(tp + fn);
//        double sp = (double)(tp) / (double)(tp + fp);
//        double cc = (double)((tp*tn - fp*fn)) / Math.sqrt((double)((tp+fn)*(tn+fp)*(tp+fp)*(tn+fn)));
//        double acp = 0.25 * ((double)(tp)/(tp+fn) + (double)(tp)/(tp+fp) + (double)(tn)/(tn+fp) + (double)(tn)/(tn+fn));
//        double ac = (acp - 0.5) * 2;
//        double accu = (double)(tp+fp)/(double)(tp+fp+tn+fn);
//        System.out.println("Only R");
//        System.out.println("Sn\tSp\tCC\tAC\tAccu");
//        System.out.println(sn+"\t"+sp+"\t"+cc+"\t"+ac+"\t"+accu);
//        }
//        
//        count_cr(true_ann, pred_ann, conf_matrix);
//        {
//        double tp = conf_matrix[0][0];
//        double tn = conf_matrix[1][1];
//        double fp = conf_matrix[0][1];
//        double fn = conf_matrix[1][0];
//
//        double sn = (double)tp / (double)(tp + fn);
//        double sp = (double)(tp) / (double)(tp + fp);
//        double cc = (double)((tp*tn - fp*fn)) / Math.sqrt((double)((tp+fn)*(tn+fp)*(tp+fp)*(tn+fn)));
//        double acp = 0.25 * ((double)(tp)/(tp+fn) + (double)(tp)/(tp+fp) + (double)(tn)/(tn+fp) + (double)(tn)/(tn+fn));
//        double ac = (acp - 0.5) * 2;
//        double accu = (double)(tp+fp)/(double)(tp+fp+tn+fn);
//        System.out.println("CR");
//        System.out.println("Sn\tSp\tCC\tAC\tAccu");
//        System.out.println(sn+"\t"+sp+"\t"+cc+"\t"+ac+"\t"+accu);
        //}
    }
   
    
}
