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
 * Created on 20/05/2026, 15:19:19
 *
 * @author manso - computer
 */
public class TestPOS_01_Function {

    public static void main(String[] args) {
        int numSelects = 100;
        double[] stake = {1, 2, 3, 4, 5};
        for (int i = 0; i < numSelects; i++) {
            int selected = select(stake);
            System.out.println(i + "\t Selected " + selected + " \tvalue " + stake[selected]);

        }

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605201519L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
