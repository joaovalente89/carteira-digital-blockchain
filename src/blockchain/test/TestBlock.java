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

import blockchain.core.Block;

/**
 * Created on 24/04/2026, 08:23:39
 *
 * @author IPT - computer
 * @version 1.0
 */
public class TestBlock {

    public static void main(String[] args) {
        Block b1 = new Block("000000000", "Ana 10 maria");
        System.out.println(b1);
        Block b2 = new Block(b1.getBlockHash(), " Maria 20 pedro");
        System.out.println("\n" + b2);
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604240823L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
