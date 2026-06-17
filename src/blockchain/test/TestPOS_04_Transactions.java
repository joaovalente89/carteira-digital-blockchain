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
import blockchain.core.pos.BlockPOS;
import blockchain.core.Transaction;
import blockchain.helpers.Security;
import blockchain.helpers.Utils;
import java.security.PrivateKey;
import moedas.BitCoin;
import moedas.Currency;

/**
 * Created on 18/05/2026, 17:11:51
 *
 * @author IPT - computer
 * @version 1.0
 */
public class TestPOS_04_Transactions {

    public static void main(String[] args) {
        String password = "123qwe";
        BlockChain blockchain = new BlockChain();
        blockchain.initBlockChain(BlockPOS.createGenesis());

        //TRANSACTION 1 
        Transaction transaction = createTransaction("System", "ana", 5, password);
        BlockPOS block = new BlockPOS(blockchain, Utils.toBase64(transaction), password);
        blockchain.add(block);

        //TRANSACTION 2 
        transaction = createTransaction("System", "maria", 10, password);
        block = new BlockPOS(blockchain, Utils.toBase64(transaction), password);
        blockchain.add(block);

        //TRANSACTION 3 
        transaction = createTransaction("System", "pedro", 10, password);
        block = new BlockPOS(blockchain, Utils.toBase64(transaction), password);
        blockchain.add(block);

        //TRANSACTION 4 
        transaction = createTransaction("maria", "pedro", 5, password);
        block = new BlockPOS(blockchain, Utils.toBase64(transaction), password);
        blockchain.add(block);

        //TRANSACTION 4 
        transaction = createTransaction("ana", "maria", 4, password);
        block = new BlockPOS(blockchain, Utils.toBase64(transaction), password);
        blockchain.add(block);

        System.out.println(blockchain);

    }

    public static Transaction createTransaction(String from, String to, double value, String password) {
        Currency moeda = new BitCoin();
        
        //criar a transacao
        Transaction transaction = new Transaction(from, to, value, moeda);
       
        //assinar a transacao
        PrivateKey privateKey = Security.loadPrivateKey(transaction.getSource(), password.toCharArray());
        transaction.sign(privateKey);
        return transaction;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605181711L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
