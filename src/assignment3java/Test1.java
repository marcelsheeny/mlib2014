/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3java;

import java.util.HashMap;

/**
 *
 * @author Marcel
 */
public class Test1 {
    
    private static double[][] count_training_trans (Integer[] genome, Integer[] annotation)
    {
        double[][] trans = new double[2][2];
        double[] div = new double[2];
        for (int i = 1; i < annotation.length; i++) {
            trans[annotation[i]][annotation[i-1]]++;
            div[annotation[i-1]] += 1;
            
        }
        for (int i = 0; i < trans.length; i++)
        {
            for (int j = 0; j < trans[0].length; j++)
            {
                trans[i][j] /= div[i];
            }
        }
        return trans;
    }

    private static double[] count_training_pi (Integer[] genome, Integer[] annotation)
    {
        double[] pi = new double[2];
        pi[annotation[0]] += 1;
        return pi;
    }
    
    private static double[][] count_training_emi (Integer[] genome, Integer[] annotation)
    {
        double[][] emi = new double[2][4];
        double[] div = new double[2];
        for (int i = 0; i < annotation.length; i++)
        {
            emi[annotation[i]][genome[i]] += 1;
            div[annotation[i]] += 1;
        }
        
        for (int i = 0; i < emi.length; i++)
        {
            for (int j = 0; j < emi[0].length; j++)
            {
                emi[i][j] /= div[i];
            }
        }
        return emi;
    }
    
    
    
    public static void run()
    {
        Viterbi v = new Viterbi();
        
        double[][] confusionMatrix = new double[2][2];
        
        String genome = Viterbi.read_fasta("genome1.fa");
        String annotation = Viterbi.read_fasta("annotation1.fa");
        
        HashMap<Character, Integer> dic_x = new HashMap<>();
        HashMap<Character, Integer> dic_z = new HashMap<>();
        
        dic_x.put('A', 0);dic_x.put('C', 1);dic_x.put('G', 2);dic_x.put('T', 3);
        dic_z.put('N', 0);dic_z.put('C', 1);dic_z.put('R', 1);
        
        
        Integer[] gen = Viterbi.conv(genome, dic_x);
        Integer[] ann = Viterbi.conv(annotation, dic_z);
        
        for (int i = 0; i < 5; i++) {
            Integer integer = ann[i];
            Integer[] test_gen = new Integer[gen.length/5];
            Integer[] test_ann = new Integer[gen.length/5];
            Integer[] train_gen = new Integer[gen.length - gen.length/5];
            Integer[] train_ann = new Integer[gen.length - gen.length/5];
            System.arraycopy(gen, i*(gen.length/5), test_gen, 0, i*(gen.length/5)+(gen.length/5));
            System.arraycopy(ann, i*(ann.length/5), test_ann, 0, i*(ann.length/5)+(ann.length/5));
            System.arraycopy(gen, 0, train_gen, 0, i*(gen.length/5));
            System.arraycopy(gen, (i+1)*(gen.length/5), train_gen, (i+1)*(gen.length/5), (i+1)*(gen.length/5)+(gen.length/5));
            System.arraycopy(ann, 0, train_ann, 0, i*(ann.length/5));
            System.arraycopy(ann, (i+1)*(ann.length/5), train_ann, (i+1)*(ann.length/5), (i+1)*(ann.length/5)+(ann.length/5));
        
            
            
            
//            double[][] trans_prob = count_training_trans(gen, ann);
//            double[][] emit_prob = count_training_emi(gen, ann);
//            double[] init_prob = count_training_pi(gen, ann);
            
            double[][] trans_prob = count_training_trans(train_gen, train_ann);
            double[][] emit_prob = count_training_emi(train_gen, train_ann);
            double[] init_prob = count_training_pi(train_gen, train_ann);

            System.out.println("TRANS");
            Viterbi.printMatrix(trans_prob);
            System.out.println("EMIT");
            Viterbi.printMatrix(emit_prob);
            System.out.println("INIT");
            Viterbi.printVector(init_prob);

            v.run(genome, "ACGT", "NC", trans_prob, emit_prob, init_prob, confusionMatrix);
        }
    }
    
    public static void runCV()
    {
        Viterbi v = new Viterbi();
        
        double[][] conf_matrix = new double[2][2];
        
        String genome = Viterbi.read_fasta("genome1.fa");
        String annotation = Viterbi.read_fasta("annotation1.fa");
        
        HashMap<Character, Integer> dic_x = new HashMap<>();
        HashMap<Character, Integer> dic_z = new HashMap<>();
        
        dic_x.put('A', 0);dic_x.put('C', 1);dic_x.put('G', 2);dic_x.put('T', 3);
        dic_z.put('N', 0);dic_z.put('C', 1);dic_z.put('R', 1);
        
        
        Integer[] gen = Viterbi.conv(genome, dic_x);
        Integer[] ann = Viterbi.conv(annotation, dic_z);
        
        for (int i = 0; i < 5; i++) {
            Integer[] test_gen = new Integer[gen.length/5];
            //Integer[] test_ann = new Integer[gen.length/5];
            String  test_ann_ = "";
            test_ann_ = annotation.substring( i*(ann.length/5),i*(gen.length/5)+(gen.length/5));
            //test_ann_ = annotation.substring( i*(ann.length/5),i*(gen.length/5)+(gen.length/5));
            Integer[] train_gen = new Integer[gen.length - gen.length/5];
            Integer[] train_ann = new Integer[gen.length - gen.length/5];
            System.arraycopy(gen, i*(gen.length/5), test_gen, 0, (gen.length/5));
            //System.arraycopy(ann, i*(ann.length/5), test_ann, 0, i*(ann.length/5)+(ann.length/5));
            System.out.println((i+1)*(gen.length/5));
            System.out.println(i*(gen.length/5));
            System.out.println((5-i)*(gen.length/5));
            System.arraycopy(gen, 0, train_gen, 0, i*(gen.length/5));
            System.arraycopy(gen, (i+1)*(gen.length/5), train_gen, i*(gen.length/5),(int)( (4-i)*(gen.length/5.f))+1);
            System.arraycopy(ann, 0, train_ann, 0, i*(ann.length/5));
            System.arraycopy(ann, (i+1)*(ann.length/5), train_ann, i*(ann.length/5),(int)( (4-i)*(ann.length/5.f))+1);
        
            double[][] trans_prob = count_training_trans(train_gen, train_ann);
            double[][] emit_prob = count_training_emi(train_gen, train_ann);
            double[] init_prob = count_training_pi(train_gen, train_ann);

            System.out.println("TRANS");
            Viterbi.printMatrix(trans_prob);
            System.out.println("EMIT");
            Viterbi.printMatrix(emit_prob);
            System.out.println("INIT");
            Viterbi.printVector(init_prob);

            v.runCV(genome, "ACGT", "NC", trans_prob, emit_prob, init_prob, conf_matrix,test_ann_);
            
        }
        double tp = conf_matrix[0][0];
        double tn = conf_matrix[1][1];
        double fp = conf_matrix[0][1];
        double fn = conf_matrix[1][0];

        double sn = (double)tp / (double)(tp + fn);
        double sp = (double)(tp) / (double)(tp + fp);
        double cc = (double)((tp*tn - fp*fn)) / Math.sqrt((double)((tp+fn)*(tn+fp)*(tp+fp)*(tn+fn)));
        double acp = 0.25 * ((double)(tp)/(tp+fn) + (double)(tp)/(tp+fp) + (double)(tn)/(tn+fp) + (double)(tn)/(tn+fn));
        double ac = (acp - 0.5) * 2;
        double accu = (double)(tp+fp)/(double)(tp+fp+tn+fn);

        System.out.println("Sn\tSp\tCC\tAC\tAccu");
        System.out.println(sn+"\t"+sp+"\t"+cc+"\t"+ac+"\t"+accu);
        
    }
    
}
