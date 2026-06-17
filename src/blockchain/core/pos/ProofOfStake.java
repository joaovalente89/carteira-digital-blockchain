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
import blockchain.helpers.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static blockchain.core.pos.Validator.getVerifiableRandom;

/**
 * Created on 18/05/2026, 16:04:02
 *
 * @author IPT - computer
 * @version 1.0
 */
public class ProofOfStake {

    public static String selectValidator(BlockChain bc) {
        return selectValidator(bc, bc.getChain().size());
    }

    public static String selectValidator(BlockChain bc, int lastBlock) {
        //extrair o saldos dos users da blockchain
        Map<String, Double> stake = getStake(bc, lastBlock);
        //lista dos users
        List<String> users = new ArrayList(stake.keySet());
        //lista dos saldos
        List<Double> values = new ArrayList(stake.values());
        //calcular total dos saldos
        double total = 0;
        for (double d : values) {
            if (d > 0) {//considerar apenas saldos positivos
                total += d;
            }
        }
        // gerar um numero aletorio [0..total]
        //hash do ultimo bloco
        byte[] lastHash = bc.getChain().getLast().getBlockHash().getBytes();
        //valor aletorio  [0..total]
        double rnd = getVerifiableRandom(lastHash) * total;
        //identificar o index aleatorio
        double acum = 0;
        for (int index = 0; index < values.size(); index++) {
            if (values.get(index) > 0) {//considerar apenas saldos positivos
                acum += values.get(index); //acumular o valor
                if (acum > rnd) { //verificar o index
                    return users.get(index);
                }
            }
        }
        //nunca devia acontecer
        return users.getLast();
    }

    /**
     * calcula o saldo das carteiras na blockchain
     * @param bc blockchain com transações (Transaction)
     * @param numberOfBlocks  numero de blocos a considerar
     * @return 
     */
    public static Map<String, Double> getStake(BlockChain bc, int numberOfBlocks ) {
        HashMap<String, Double> balance = new HashMap<>();
        ArrayList<Block> chain = bc.getChain();
        //iterar os blocos pretendidos
        for (int i = 0; i < numberOfBlocks ; i++) {
            try {
                //extrair o bloco da cadeia
                Block b = chain.get(i);
                //extrair as transacoes do bloco
                Transaction t = Utils.fromBase64(b.getData());
                //extrair os dados da transacao
                String from = t.getSource();
                String to = t.getDestination();
                double value = t.getValue();
                //atualizar o saldo da origem (retirar o valor)
                if (balance.containsKey(from)) {
                    //decrementar o saldo
                    balance.put(from, balance.get(from) - value);
                } else {
                    //novo utilizador - inicia o saldo
                    balance.put(from, -value);
                }
                //atualizar o saldo do destina (acrescentar valor)
                if (balance.containsKey(to)) {
                    //incrementar o saldo
                    balance.put(to, balance.get(to) + value);
                } else {
                    //novo utilizador - iniciar o saldo
                    balance.put(to, value);
                }
            } catch (Exception e) {
                //não e uma transacao
            }
        }
        return balance;
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605181604L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
