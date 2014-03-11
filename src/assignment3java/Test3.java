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
public class Test3 {
    private static double[][] count_training_trans (String genome, String annotation)
    {
//        double[][] trans = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //0
//                            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0}, //1
//                            {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, //2
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0}, //3
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //4
//                            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0}, //5
//                            {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0}, //6
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //7
//                            {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}, //8
//                            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0}, //9
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //10
//                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0}, //11
//                            {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0}, //12
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0}, //13
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, //14
//                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};//15
        double[][] trans = new double[16][16];
        trans[1][2] = 1;
        trans[2][3] = 1;
        trans[3][13] = 1;
        trans[13][14] = 1;
        trans[14][15] = 1;
        trans[4][0] = 1;
        trans[7][0] = 1;
        trans[10][0] = 1;
        trans[6][5] = 1;
        trans[5][4] = 1;
        trans[9][8] = 1;
        trans[8][7] = 1;
        trans[12][11] = 1;
        trans[11][10] = 1;
        
        int totalTrans1 = 0;
        int totalTrans2 = 0;

        
        for (int i = 0; i < genome.length() - 1; i++) {
            //Enter C
            if (annotation.charAt(i) != 'C' && annotation.charAt(i + 1) == 'C') {
                totalTrans1++;
                String temp = genome.substring(i + 1, i + 4);
                if (temp.equals("ATG"))
                {
                    trans[0][1]++;
                }
                else
                {
                    trans[0][0]++;
                }
            } //Leaving C
            else if (annotation.charAt(i) == 'C' && annotation.charAt(i + 1) != 'C')
            {
                totalTrans2++;
                String temp = genome.substring(i - 2, i + 1);
                if (temp.equals("TAA"))
                {
                    trans[15][6]++;
                }
                else if (temp.equals("TAG"))
                {
                    trans[15][9]++;
                }
                else if (temp.equals("TGA"))
                {
                    trans[15][12]++;
                }
                else
                {
                    trans[15][13]++;
                }
            } 

        }
        trans[0][1] /= totalTrans1;
        trans[0][0] /= totalTrans1;
        trans[15][6] /= totalTrans2;
        trans[15][9] /= totalTrans2;
        trans[15][12] /= totalTrans2;
        trans[15][13] /= totalTrans2;
        
        return trans;
    }

    private static double[] count_training_pi (String genome, String annotation)
    {
        double[] pi = new double[16];
//        Viterbi.initPseudoCountVector(pi, 1);
//        pi[annotation[0]] += 1;
//        for (int i = 0; i < pi.length; i++)
//        {
//            pi[i] /= pi.length+1;
//        }
        pi[0] = 1;

        return pi;
    }
    
    private static double[][] count_training_emi (Integer[] genome, String annotation)
    {
        double[][] emi = new double[16][4];
        emi[1][0] = 1;
        emi[2][3] = 1;
        emi[3][2] = 1;
        emi[4][0] = 1;
        emi[5][0] = 1;
        emi[6][3] = 1;
        emi[7][2] = 1;
        emi[8][0] = 1;
        emi[9][3] = 1;
        emi[10][0] = 1;
        emi[11][2] = 1;
        emi[12][3] = 1;
        int totalN = 0;
        int total13 = 0;
        int total14 = 0;
        int total15 = 0;
        for (int i = 0; i < annotation.length(); )
        {
            if (annotation.charAt(i) == 'N')
            {
                totalN++;
                emi[0][genome[i]]++;
                i++;
            }
            else
            {
                emi[13][genome[i]]++;
                total13++;
                i++;
                emi[14][genome[i]]++;
                total14++;
                i++;
                emi[15][genome[i]]++;
                total15++;
                i++;
            }
        }
        for (int i = 0; i < 4; i++) {
            emi[0][i] /= totalN;
            emi[13][i] /= total13;
            emi[14][i] /= total14;
            emi[15][i] /= total15;
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
        
        double[][] trans_prob = count_training_trans(genome, annotation);
        double[][] emit_prob = count_training_emi(gen, annotation);
        double[] init_prob = count_training_pi(genome, annotation);
        
        System.out.println("TRANS");
        Viterbi.printMatrix(trans_prob);
        System.out.println("EMIT");
        Viterbi.printMatrix(emit_prob);
        System.out.println("INIT");
        Viterbi.printVector(init_prob);
        
        v.run(genome, "ACGT", "NCCCCCCCCCCCCCCC", trans_prob, emit_prob, init_prob, confusionMatrix);
        
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
            String  test_ann_str = "";
            String  train_ann_str = "";
            String  train_gen_str = "";
            train_ann_str = annotation.substring( i*(ann.length/5),i*(gen.length/5)+(gen.length/5));
            test_ann_str = annotation.substring( i*(ann.length/5),i*(gen.length/5)+(gen.length/5));
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
        
            //double[][] trans_prob = count_training_trans(train_gen, train_ann);
            //double[][] emit_prob = count_training_emi(train_gen, train_ann);
            //double[] init_prob = count_training_pi(train_gen, train_ann);

//            System.out.println("TRANS");
//            Viterbi.printMatrix(trans_prob);
//            System.out.println("EMIT");
//            Viterbi.printMatrix(emit_prob);
//            System.out.println("INIT");
//            Viterbi.printVector(init_prob);
//
//            v.runCV(genome, "ACGT", "NC", trans_prob, emit_prob, init_prob, conf_matrix,test_ann_str);
            
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
