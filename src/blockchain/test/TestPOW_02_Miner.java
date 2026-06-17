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

import static blockchain.core.pow.Miner.mine;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 20/05/2026, 15:16:16
 *
 * @author manso - computer
 */
public class TestPOW_02_Miner {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String message = "Hello Blockchain secure world!";
        for (int dificulty = 1; dificulty < 10; dificulty++) {
            long time = System.currentTimeMillis();
            long nonce = mine(message, dificulty);
            time = System.currentTimeMillis() - time;
            System.out.printf("dificulty %2d   time %06d Nonce %d\n", dificulty, time, nonce);
        }

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605201516L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
