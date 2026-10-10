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

package blockchain.core;

import blockchain.core.pos.BlockPOS;

/**
 * Created on 29/05/2026, 14:52:53
 *  class que faz extensão da classe BlockChain para suportar a Proof of Stake,
 * a class redefine a criação da blockchain inicial 
 * utilizando um bloco génesis específico do Proof of Stake
 * @author manso - computer
 */
public class BlockChainPOS extends BlockChain {
    
    /**
     * construtor da blockchain com Proof of Stake
     * @param fileName nome do ficheiro onde a blockchain será guardada e carregada posteriormente
     */
    public BlockChainPOS(String fileName) {
        super(fileName);
    }
    
    /**
     * inicia a blockchain com um block genesis do Proof of Stake.
     */
    @Override
    public void initBlockChain() {
        initBlockChain(BlockPOS.createGenesis());
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605291452L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
