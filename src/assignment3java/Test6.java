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
public class Test6 {
    private static double[][] count_training_trans (String genome, String annotation)
    {
        double[][] trans = new double[31][31];
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
        trans[16][0] = 1;
        trans[17][16] = 1;
        trans[18][17] = 1;
        trans[30][29] = 1;
        trans[29][28] = 1;
        trans[19][20] = 1;
        trans[20][21] = 1;
        trans[21][30] = 1;
        trans[22][23] = 1;
        trans[23][24] = 1;
        trans[24][30] = 1;
        trans[25][26] = 1;
        trans[26][27] = 1;
        trans[27][30] = 1;
        
        int totalTrans1 = 0;
        int totalTrans2 = 0;
        int totalTrans3 = 0;

        
        for (int i = 0; i < genome.length() - 1; i++) 
        {
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
                    System.out.println("jhkjhfghj");
                    trans[15][13]++;
                }
            }
            //Enter R
            else if (annotation.charAt(i) != 'R' && annotation.charAt(i + 1) == 'R') {
                totalTrans1++;
                String temp = genome.substring(i + 1, i + 4);
                if (temp.equals("TTA"))
                {
                    trans[0][19]++;
                }
                else if (temp.equals("CTA"))
                {
                    trans[0][22]++;
                }
                else if (temp.equals("TCA"))
                {
                    trans[0][25]++;
                }
                else
                {
                    trans[0][0]++;
                }
            }
            //Leaving R
            else if (annotation.charAt(i) == 'R' && annotation.charAt(i + 1) != 'R')
            {
                totalTrans3++;
                String temp = genome.substring(i - 2, i + 1);
                if (temp.equals("CAT"))
                {
                    trans[28][18]++;
                }
                else
                {
                    trans[28][30]++;
                }
            }
            else if (annotation.charAt(i) == 'N')
            {
                totalTrans1++;
                trans[0][0]++;
            }
            else if (annotation.charAt(i) == 'C' && annotation.charAt(i-1) == 'C' && annotation.charAt(i-2) == 'C')
            {
                totalTrans2++;
                trans[15][13]++;
            }
            else if (annotation.charAt(i) == 'R' && annotation.charAt(i-1) == 'R' && annotation.charAt(i-2) == 'R')
            {
                totalTrans3++;
                trans[28][30]++;
            }

        }
        trans[0][1] /= totalTrans1;
        trans[0][0] /= totalTrans1;
        trans[0][19] /= totalTrans1;
        trans[0][22] /= totalTrans1;
        trans[0][25] /= totalTrans1;
        trans[15][6] /= totalTrans2;
        trans[15][9] /= totalTrans2;
        trans[15][12] /= totalTrans2;
        trans[15][13] /= totalTrans2;
        trans[28][18] /= totalTrans3;
        trans[28][30] /= totalTrans3;
        
        return trans;
    }

    private static double[] count_training_pi (String genome, String annotation)
    {
        double[] pi = new double[31];
        pi[0] = 1;

        return pi;
    }
    
    private static double[][] count_training_emi (Integer[] genome, String annotation)
    {
        double[][] emi = new double[31][4];
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
        emi[16][3] = 1;
        emi[17][0] = 1;
        emi[18][1] = 1;
        emi[19][3] = 1;
        emi[20][3] = 1;
        emi[21][0] = 1;
        emi[22][1] = 1;
        emi[23][3] = 1;
        emi[24][0] = 1;
        emi[25][3] = 1;
        emi[26][1] = 1;
        emi[27][0] = 1;
        int totalN = 0;
        int total13 = 0;
        int total14 = 0;
        int total15 = 0;
        int total28 = 0;
        int total29 = 0;
        int total30 = 0;
        for (int i = 0; i < annotation.length(); i++ )
        {
            if (annotation.charAt(i) == 'N')
            {
                try
                {
                totalN++;
                emi[0][genome[i]]++;
                }
                catch (Exception e)
                {
                    
                }
            }
            else if (annotation.charAt(i) != 'C' && annotation.charAt(i + 1) == 'C')
            {
                i = i + 3;
            }
            else if (annotation.charAt(i) == 'C' && annotation.charAt(i + 1) != 'C')
            {
                i++;
            }
             else if (annotation.charAt(i) != 'R' && annotation.charAt(i + 1) == 'R')
            {
                i = i + 3;
            }
            else if (annotation.charAt(i) == 'R' && annotation.charAt(i + 1) != 'R')
            {
                i++;
            }
            else if (annotation.charAt(i) == 'C')
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
            else if (annotation.charAt(i) == 'R')
            {
                emi[28][genome[i]]++;
                total28++;
                i++;
                emi[29][genome[i]]++;
                total29++;
                i++;
                emi[30][genome[i]]++;
                total30++;
                i++;
            }
        }
        for (int i = 0; i < 4; i++) {
            emi[0][i] /= totalN;
            emi[13][i] /= total13;
            emi[14][i] /= total14;
            emi[15][i] /= total15;
            emi[28][i] /= total28;
            emi[29][i] /= total29;
            emi[30][i] /= total30;
        }
        return emi;
    }
    
    
    
    public static void run()
    {
        Viterbi v = new Viterbi();
        
        double[][] confusionMatrix = new double[2][2];
        
        String genomeTrain = Viterbi.read_fasta("genome1.fa");
        String genomeTest = Viterbi.read_fasta("genome11.fa");
        String annotation = Viterbi.read_fasta("annotation1.fa");
        
        HashMap<Character, Integer> dic_x = new HashMap<>();
        HashMap<Character, Integer> dic_z = new HashMap<>();
        
        dic_x.put('A', 0);dic_x.put('C', 1);dic_x.put('G', 2);dic_x.put('T', 3);
        dic_z.put('N', 0);dic_z.put('C', 1);dic_z.put('R', 2);
        
        
        Integer[] gen = Viterbi.conv(genomeTrain, dic_x);
        Integer[] genTest = Viterbi.conv(genomeTest, dic_x);
        Integer[] ann = Viterbi.conv(annotation, dic_z);
        
        double[][] trans_prob = count_training_trans(genomeTrain, annotation);
        double[][] emit_prob = count_training_emi(gen, annotation);
        double[] init_prob = count_training_pi(genomeTrain, annotation);
        
        System.out.println("TRANS");
        Viterbi.printMatrix(trans_prob);
        System.out.println("EMIT");
        Viterbi.printMatrix(emit_prob);
        System.out.println("INIT");
        Viterbi.printVector(init_prob);
        
        v.run(genomeTest, "ACGT", "NCCCCCCCCCCCCCCCRRRRRRRRRRRRRRR", trans_prob, emit_prob, init_prob, confusionMatrix);
        
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
        dic_z.put('N', 0);dic_z.put('C', 1);dic_z.put('R', 2);
        
        
        Integer[] gen = Viterbi.conv(genome, dic_x);
        Integer[] ann = Viterbi.conv(annotation, dic_z);
        
        for (int i = 0; i < 5; i++) {
            Integer[] test_gen = new Integer[gen.length/5];
            //Integer[] test_ann = new Integer[gen.length/5];
            String  test_ann_str = "";
            String  test_gen_str = "";
            String  train_ann_str = "";
            String  train_gen_str = "";
            
            test_ann_str = annotation.substring( i*(ann.length/5),i*(gen.length/5)+(gen.length/5));
            test_gen_str = genome.substring( i*(ann.length/5),i*(gen.length/5)+(gen.length/5));
            
            train_ann_str = annotation.substring( 0,i*(gen.length/5)+1);
            train_ann_str += annotation.substring( (i+1)*(gen.length/5), gen.length);
            
            train_gen_str = genome.substring( 0,i*(gen.length/5)+1);
            train_gen_str += genome.substring( (i+1)*(gen.length/5), gen.length);
            
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
        
            double[][] trans_prob = count_training_trans(train_gen_str, train_ann_str);
            double[][] emit_prob = count_training_emi(train_gen, train_ann_str);
            double[] init_prob = count_training_pi(train_gen_str, train_ann_str);

            System.out.println("TRANS");
            Viterbi.printMatrix(trans_prob);
            System.out.println("EMIT");
            Viterbi.printMatrix(emit_prob);
            System.out.println("INIT");
            Viterbi.printVector(init_prob);

            v.runCV(genome, "ACGT", "NC", trans_prob, emit_prob, init_prob, conf_matrix,test_ann_str);
            
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
