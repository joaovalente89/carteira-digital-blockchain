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
import blockchain.core.pow.BlockPow;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 20/05/2026, 15:16:16
 *
 * @author manso - computer
 */
public class TestPOW_03_BlockChain {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //criar uma nova blockchain com o consenso POW 
        BlockChain bc = new BlockChain();
        bc.initBlockChain(BlockPow.createGenesis());
        //create blocks                
        String message = "Block with POW consensus";
        int numberOfBlocks = 20;
        int dificulty = 4;
        for (int i = 0; i <numberOfBlocks; i++) {
            //create a pow block
            BlockPow block = new BlockPow(bc.getLastBlock().getBlockHash(), message + " " + i, dificulty);
            //add block to blockchain
            bc.add(block);
            
        }
        System.out.println("Blockchain\n" + bc);
          
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605201516L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
