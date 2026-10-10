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

import blockchain.core.Transaction;
import blockchain.core.Block;
import blockchain.core.BlockChain;
import blockchain.helpers.Security;
import blockchain.helpers.Utils;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.swing.JOptionPane;
import moedas.BitCoin;
import moedas.Currency;

/**
 * Created on 18/05/2026, 16:44:51
 *
 * Implementação de um bloco utilizando o mecanismo de consenso Proof of Stake "pos"
 * Cada bloco é validado por um utilizador selecionado pelo Proof of Stake
 * 
 * 
 * @author IPT - computer
 * @version 1.0
 */
public class BlockPOS extends Block {

    protected String validator; // name of validator
    protected String signature;

    public static BlockPOS createGenesis() {
        return new BlockPOS();
    }
   private final Currency moedaGenesis = new BitCoin();

    private BlockPOS() {
        super("00000000000", "Genesis Block");
        this.validator = "SYSTEM";
        //creates first block
        Transaction genesis = new Transaction("SYSTEM", "SYSTEM", 0, moedaGenesis);
        //Load credentials
        PrivateKey privateKey = Security.loadPrivateKey(validator, "admin".toCharArray());
        //sign trannsaction
        genesis.sign(privateKey);
        //sign block
        this.data = Utils.toBase64(genesis);
        this.signature = Security.sign((data + validator).getBytes(), privateKey);
        //calculate hash
        this.blockHash = Security.getHashHex(previousHash + data + validator + signature);
    }
    
    /**
     * constroi um block pos
     * @param bc    blockchain onde sera inserido o block
     * @param data      dados a armazenar no block
     * @param password  password do validador
     */
    public BlockPOS(BlockChain bc, String data, String password) {
        super(bc, data);
        //calculate validator com toda a blockchain
        this.validator = ProofOfStake.selectValidator(bc);
        password = JOptionPane.showInputDialog( "password da " + validator);
        //CONSENSUS : sign block
        PrivateKey privateKey = Security.loadPrivateKey(validator, password.toCharArray());
        this.signature = Security.sign((data + validator).getBytes(), privateKey);
        //calculate hash
        this.blockHash = Security.getHashHex(previousHash + data + validator + signature);
    }

    @Override
    public boolean isValid() {
        PublicKey pubKey = Security.loadPublicKey(this.validator);
        boolean signValid = Security.verify((data + validator).getBytes(), signature, pubKey);
        boolean hashValid = Security.getHashHex(previousHash + data + validator + signature).equals(blockHash);
        return signValid && hashValid;
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
            //not  base64 - do nothing
        }
        return String.format("%-10s... [%-40s] <%-10s %-10s>  %-10s... : %b",
                Utils.cutString(previousHash, 10),
                Utils.cutString(trueData, 40),
                Utils.cutString(validator, 10),
                Utils.cutString(signature, 10),
                Utils.cutString(blockHash, 10),
                isValid());
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605181644L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
