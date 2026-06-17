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


package blockchain.core.pow;

import blockchain.core.Block;
import blockchain.core.BlockChain;
import blockchain.core.Transaction;
import blockchain.helpers.Security;
import blockchain.helpers.Utils;

/**
 * Created on 18/05/2026, 16:38:46
 *
 * @author IPT - computer
 * @version 1.0
 */
public class BlockPow extends Block {

    int dificulty = 4;      // dificulty of mining (zeros)
    private long nonce = 0; // nonce that produces zeros

    /**
     * Create a genesis block to POW
     *
     * @return
     */
    public static BlockPow createGenesis() {
        return new BlockPow();
    }

    /**
     * private constructor to genesis block
     */
    private BlockPow() {
        this("00000000000", "Genesis Block", 4);
    }

    /**
     * Creates a new block with pow consensus
     *
     * @param prevHash hash os previous block (hexFormat)
     * @param data data of the block (base64 format)
     * @param dificulty dificulty of mining
     */
    public BlockPow(String prevHash, String data, int dificulty) {
        //creates a basic block
        super(prevHash, data);
        this.dificulty = dificulty;

        //concatenar os dados do bloco
        String totalData = prevHash + data + dificulty;
        //calcular o nonce através do mineiro
        this.nonce = Miner.mine(totalData, dificulty);
        //calcular o hash
        this.blockHash = Security.getHashHex(totalData + nonce);

    }

    /**
     * Creates a new block with pow consensus
     *
     * @param bc blockchain of 
     * @param data data of the block (base64 format)
     */
    public BlockPow(BlockChain bc, String data) {
        //creates a basic block
        this(bc.getLastBlock().getBlockHash(), data, ((BlockPow) bc.getLastBlock()).dificulty);
    }

    @Override
    public boolean isValid() {
        //calculate Hash do bloco
        String totalData = previousHash + data + dificulty + nonce;
        String trueHash = Security.getHashHex(totalData);
        //prefixo do hash
        String zeros = "0".repeat(dificulty);
        //verificar o prefixo e se o hash é igual
        return this.blockHash.startsWith(zeros) && trueHash.equals(this.blockHash);
    }

    @Override
    public String toString() {
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //try to extract transactions from data
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::
        String trueData = data;
        try {
            Transaction t = Utils.fromBase64(data);
            trueData = t.toString();
        } catch (Exception e) {
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::

        return String.format("%-10s[%-40s] <%2d %8d> %-10s: %b",
                Utils.cutString(previousHash, 10),
                Utils.cutString(trueData, 40),
                dificulty,
                nonce,
                Utils.cutString(blockHash, 10),
                isValid());
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605181638L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
