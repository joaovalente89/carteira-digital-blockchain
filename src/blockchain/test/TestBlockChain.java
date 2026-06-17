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

import blockchain.core.BlockChain;

/**
 * Created on 24/04/2026, 08:26:37
 *
 * @author IPT - computer
 * @version 1.0
 */
public class TestBlockChain {

    public static void main(String[] args) {
        //starts a new blockchain
        BlockChain bc = new BlockChain();
        bc.initBlockChain();

        bc.add("Ana 10 Maria");
        bc.add("Maria 5 Pedro");
        bc.add("Ana 10 Joana");
        bc.add("Pedro 2 Ana");
        System.out.println(bc);

    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604240826L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
