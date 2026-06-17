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

import blockchain.helpers.Security;
import blockchain.helpers.Utils;
import java.io.Serializable;

/**
 * Created on 24/04/2026, 08:22:14
 *
 * @author IPT - computer
 * @version 1.0
 */
public class Block implements Serializable, Validable {

    //previous hash in HEX
    protected String previousHash;
    //data in base64 to store objects
    protected String data;
    //actualHash in HEX
    protected String blockHash;

    public Block(String prevHash, String data) {
        this.previousHash = prevHash;
        this.data = data;
        this.blockHash = Security.getHashHex(this.previousHash + this.data);
    }

    public Block(BlockChain bc, String data) {
        this(bc.getChain().getLast().blockHash,data);
    }

    @Override
    public boolean isValid() {
        //hash do bloco é igual ao hash do prevHash
        return Security.getHashHex(this.previousHash + this.data).equals(this.blockHash);
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }

    public String getBlockHash() {
        return blockHash;
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

        return String.format("%-10s... [%-40s...] %-10s... : %b",
                Utils.cutString(previousHash, 10),
                Utils.cutString(trueData, 40),
                Utils.cutString(blockHash, 10),
                isValid());
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604240822L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
