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

package carteiradigital;

import blockchain.core.Block;
import blockchain.core.BlockChain;
import blockchain.helpers.Security;
import blockchain.helpers.Utils;
import blockchain.core.Transaction;
import blockchain.core.pow.BlockPow;
import blockchain.user.User;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Scanner;
import moedas.BitCoin;
import moedas.Currency;

/**
 * Created on 24/04/2026, 15:39:02
 *
 * @author manso - computer
 */
public class DigitalWalletPOW {

    static blockchain.core.BlockChain bc = new BlockChain();

    public static void main(String[] args) {
        bc.initBlockChain();
        while (true) {
            switch (menu()) {
                case 1 ->
                    register();
                case 2 ->
                    transfer();
                case 3 ->
                    listTransactions();
                case 0 -> {
                    System.out.println("Obrigado por utilizar o nosso sistema");
                    return;
                }
            }

        }
    }

    public static int menu() {
        Scanner key = new Scanner(System.in);
        System.out.println("\nB L O C K C H A I N\n" + bc.toString() + "\n");
        System.out.println("1 - Registar utilizador");
        System.out.println("2 - Transferir ativos ");
        System.out.println("3 - Listar Movimentos ");
        System.out.println("0 - Sair");
        while (true) {
            System.out.print("opcao : ");
            int opc = key.nextInt();
            if (opc >= 0 && opc <= 4) {
                return opc;
            }
        }
    }

    public static void register() {
        try {
            Scanner key = new Scanner(System.in);
            System.out.print("Nome :");
            String name = key.nextLine();
            System.out.print("Pass :");
            String pass = key.nextLine();
            User.register(name, pass);
            System.out.println(name + " registred");
        } catch (Exception ex) {
            System.err.println("ERRO : " + ex.getMessage());
        }
    }

    public static void transfer() {
        try {
            Scanner key = new Scanner(System.in);
            System.out.print("source :");
            String source = key.nextLine();
            System.out.print("Pass :");
            String pass = key.nextLine().trim();
            PrivateKey priv = Security.loadPrivateKey(source + ".priv", pass.toCharArray());
            //password esta correta
            System.out.print("Destination :");
            String dest = key.nextLine();
            System.out.print("Value :");
            double value = key.nextDouble();
            Currency moeda = new BitCoin();
              
            Transaction t = new Transaction(source, dest, value, moeda);
            t.sign(priv);
            Block block = new BlockPow(bc, Utils.toBase64(t));
            bc.add(block);
        } catch (Exception e) {
            System.err.println("ERRO : " + e.getMessage());
        }

    }

    public static void listTransactions() {
        Scanner key = new Scanner(System.in);
        System.out.print("User Name :");
        String user = key.nextLine();
        ArrayList<Block> chain = bc.getChain();
        for (int i = 1; i < chain.size(); i++) {
            try { //verificar se é uma transacao
                String data = chain.get(i).getData();
                Transaction t = Utils.fromBase64(data);
                if (t.getSource().equalsIgnoreCase(user)
                        || t.getDestination().equalsIgnoreCase(user)) {
                    System.out.println(t);
                }
            } catch (Exception ex) {
                //não é transação
            }
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604241539L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
