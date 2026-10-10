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
 * Cada bloco contém o hash do block anterior, os dados armazenados e o hash do próprio block, 
 * garantindo integridade através de funções de hash criptográficas
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
    
    /**
     * constroi um block com o hash anterior e os dados fornecidos
     * @param prevHash hash do block anterior
     * @param data do block com a base 64
     */
    public Block(String prevHash, String data) {
        this.previousHash = prevHash;
        this.data = data;
        this.blockHash = Security.getHashHex(this.previousHash + this.data);
    }
    
    /**
     * constrói um block a partir de uma blockchain existente.
     * @param bc blockchain onde se introduz o block
     * @param data do block com a base 64
     */
    public Block(BlockChain bc, String data) {
        this(bc.getChain().getLast().blockHash,data);
    }
    
    /**
     * verifica se o block é válido
     * @return boolean
     */
    @Override
    public boolean isValid() {
        //hash do bloco é igual ao hash do prevHash
        return Security.getHashHex(this.previousHash + this.data).equals(this.blockHash);
    }
    
    /**
     * devolve o hash anterior
     * @return string do hash anterior
     */
    public String getPreviousHash() {
        return previousHash;
    }
    
    /**
     * devolve os dados do block
     * @return string de dados
     */
    public String getData() {
        return data;
    }
    
    /**
     * devolve o hash do block
     * @return string da hash do block
     */
    public String getBlockHash() {
        return blockHash;
    }
    
    /**
     * representa o block em string
     * @return devolve o block em string
     */
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
