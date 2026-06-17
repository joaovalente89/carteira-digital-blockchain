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

import blockchain.core.pow.BlockPow;

/**
 * Created on 15/05/2026, 15:26:38
 *
 * @author manso - computer
 */
public class TestPOW_01_Block {

    public static void main(String[] args) {

        BlockPow b = new BlockPow("000000", "block 1", 4);
        System.out.println("Block DATA 1 " + b);
        for (int i = 0; i < 10; i++) {
            b = new BlockPow(b.getBlockHash(), " BLOCK POW DATA" + i, 4);
            System.out.println(b);
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605151526L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
