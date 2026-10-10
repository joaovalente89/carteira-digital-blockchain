//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2026   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 //////////////////////////////////////////////////////////////////////////////


package blockchain.core.pos;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created on 18/05/2026, 15:01:58
 *  Classe que faz a seleção de validadores no algoritmo Proof of Stake
 * 
 * @author IPT - computer
 * @version 1.0
 */
public class Validator {

   

    public static int select(double[] stake) {
        //calcular total de stake
        double total = 0;
        for (double d : stake) {
            if (d > 0) { //considerar apenas os positivos
                total += d;
            }
        }
        // gerar um numero aletorio [0..total]
        double rnd = Math.random() * total;
        //identificar o index aleatorio
        double acum = 0;
        for (int i = 0; i < stake.length; i++) {
            if (stake[i] > 0) {//considerar apenas os positivos               
                acum += stake[i];
                if (acum > rnd) {
                    return i;
                }
            }
        }
        //nunca devia acontecer
        return stake.length - 1; //ultimo        
    }

    public static int select(double[] stake, byte[] seed) {
        //calcular total de stake
        double total = 0;
        for (double d : stake) {
            if (d > 0) {//considerar apenas os positivos
                total += d;
            }
        }
        // gerar um numero aletorio [0..total]
        double rnd = getVerifiableRandom(seed) * total;
        //identificar o index aleatorio
        double acum = 0;
        for (int i = 0; i < stake.length; i++) {
            if (stake[i] > 0) {//considerar apenas os positivos
                acum += stake[i];
                if (acum > rnd) {
                    return i;
                }
            }
        }
        //nunca devia acontecer
        return stake.length - 1; //ultimo        
    }

    /**
     * Gera um numero aleatorio verificavel
     * utiliza funcoes de hash para calcular um valor de 64 bits
     * 
     * @param seed semente
     * @return numero aleatorio [0,1[
     */
    public static double getVerifiableRandom(byte[] seed) {
        try {
            // 1. Gerar o hash determinístico (SHA-256)
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            byte[] hash = hasher.digest(seed);
            // 2. Extrair os primeiros 8 bytes (64 bits = long)
            long num64 = ByteBuffer.wrap(hash, 0, 8).getLong()&Long.MAX_VALUE;
            //3.  normalizar para o intervalo [0.0, 1.0[            
            return (double) num64 / Long.MAX_VALUE;
        } catch (Exception ex) {
            System.getLogger(Validator.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            // devolver o random da biblioteca Math
            return Math.random();
        }
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605181501L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
