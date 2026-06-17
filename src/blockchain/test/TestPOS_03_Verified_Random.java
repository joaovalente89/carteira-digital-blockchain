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


package blockchain.test;

import static blockchain.core.pos.Validator.select;
import static blockchain.core.pos.Validator.select;

/**
 * Created on 18/05/2026, 17:11:51
 *
 * @author IPT - computer
 * @version 1.0
 */
public class TestPOS_03_Verified_Random {

   public static void main(String[] args) {
       String hash = "12234abc";
        int numSelects = 10000;
        double[] stake = {10, 2, 3, 4, 10};
        int[] total = new int[stake.length];
        for (int i = 0; i < numSelects; i++) {
            // int selected = selectValidator(stake);
            int selected = select(stake, hash.getBytes());
            //System.out.println("Selecionado " + selected);            
            total[selected]++;
        }
        for (int i = 0; i < total.length; i++) {
            System.out.println("Stake[" + i + "]=" + stake[i] + "\t" + total[i] / (double) numSelects * 100 + " %");
        }
    }


//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605181711L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
