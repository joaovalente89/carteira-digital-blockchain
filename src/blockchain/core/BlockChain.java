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

import blockchain.helpers.Utils;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created on 24/04/2026, 08:25:11
 *
 * @author IPT - computer
 * @version 1.0
 */
public class BlockChain implements Validable {

    private   String fileName ;

    //list of blocks
    private ArrayList<Block> chain;
    
    public BlockChain(){
        this("blockchain.blck");
    }

    /**
     * init blockchain from the file <fileName>
     * if fileName is not avaiable create a new with a genesis block
     */
    public BlockChain(String fileName ) {
        try {
            this.fileName =fileName;
            //try to load chain from file
            chain = Utils.load(fileName);
        } catch (Exception e) {
            //init new blockchain
            initBlockChain();
        }
    }

    /**
     * restart block chain
     */
    public  void initBlockChain() {
        initBlockChain(new Block("00000000", "Genesis Block"));
    }

    /**
     * Restart blockchain with a genesis block
     *
     * @param genesis genesis block
     */
    public final void initBlockChain(Block genesis) {
        //init new chain
        chain = new ArrayList<>();
        //creates first block
        chain.add(genesis);
    }

    /**
     * Block of blockchain
     *
     * @return list of blocks
     */
    public ArrayList<Block> getChain() {
        return chain;
    }

    /**
     * last block of blockchain
     *
     * @return last block
     */
    public Block getLastBlock() {
        return chain.getLast();
    }

    /**
     * create a new block with data and insert in the block chain
     *
     * @param data
     */
    public void add(String data) {

        //last hash
        String lastHash = chain.getLast().getBlockHash();
        //add new block
        add(new Block(lastHash, data));

    }

    /**
     * add a block to blockchain
     *
     * @param block
     */
    public void add(Block block) {
        try {
            //Block is not valid
            if (!block.isValid()) {
                throw new InvalidParameterException("Block is invalid");
            }
            //if the hash dont match throw an exception (make a chain)
            if (!block.previousHash.equals(chain.getLast().blockHash)) {
                throw new InvalidParameterException("Blocks dont match");
            }
            //add a block
            chain.add(block);
            //save blockchain
            Utils.save(chain, fileName);
        } catch (IOException ex) {
            System.getLogger(BlockChain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    /**
     * verify if the blocks of blockchain are correct
     *
     * @return
     */
    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            //actual and previous hash not combine
            if (!chain.get(i - 1).getBlockHash().equals(chain.get(i).getPreviousHash())) {
                return false;
            }
            if (!chain.get(i - 1).isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String txt = "";
        for (Block block : chain) {
            txt += block.toString() + "\n";
        }
        return txt;
    }

    public double getBalance(String user) {
        double balance = 0;
        for (int i = 1; i < chain.size(); i++) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //try to extract transactions from data
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::            
            try {
                Transaction t = Utils.fromBase64(chain.get(i).data);
                //update balance
                if (t.getDestination().equalsIgnoreCase(user)) {
                    balance += t.getValue();
                }
                if (t.getSource().equalsIgnoreCase(user)) {
                    balance -= t.getValue();
                }
            } catch (Exception e) {
                //not  base64 - do nothing
            }
        }
        return balance;
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604240825L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
