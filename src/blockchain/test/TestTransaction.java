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
import blockchain.core.BlockChain;
import blockchain.helpers.Security;
import blockchain.helpers.Utils;
import blockchain.core.Transaction;
import java.security.PrivateKey;
import java.util.ArrayList;
import moedas.BitCoin;
import moedas.Currency;

/**
 * Created on 24/04/2026, 15:25:00 
 * @author manso - computer
 */
public class TestTransaction {
    
    public static void main(String[] args) throws Exception {
        
        Currency moeda = new BitCoin();
        
        //dados da transacao        
        String source = "SYSTEM";  // ORIGEM
        String password = "123qwe";// password         
        String destin = "pedro";    // DESTINO         
        double value = 10.0;       // VALOR        
        //objeto da transacao
        Transaction t1 = new Transaction(source, destin, value, moeda);
        //assinatura da transacao
        PrivateKey priv = Security.loadPrivateKey(source+".priv", password.toCharArray());
        t1.sign(priv);
        System.out.println("Nova transacao : " + t1);
        
        //ler a blockchain
        blockchain.core.BlockChain bc = new BlockChain();
        //adicionar a transação em Base64
        bc.add(Utils.toBase64(t1));
        System.out.println("Transacao adicionada");
        //::::::::::::::::::::::::::::::::::::
        //imprimir a block chain        
        //::::::::::::::::::::::::::::::::::::
        System.out.println("Blockchain");
        //lista de blocos
        ArrayList<Block> chain = bc.getChain();        
        //iterar os blocao a partir do primeiro
        for (int i = 1; i < chain.size(); i++) {
            String data =chain.get(i).getData();
            //Base64 -> Transacao
            Transaction t = Utils.fromBase64(data);
            //imprimir a atransacao
            System.out.println(t);
        }
    }
    
    



    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604241525L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
