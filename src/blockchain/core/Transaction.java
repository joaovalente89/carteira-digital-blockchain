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
import java.security.PrivateKey;
import java.security.PublicKey;
import moedas.Currency;

/**
 * Created on 24/04/2026, 15:16:29
 *  class que representa as transações na blockchain
 * cada transação tem origem(quem fez a transação), destino(para é realizada), moeda(crypto moeda)e valor(quantidade de moedas)
 * as transações são autenticadas por um validador na blockchain
 * @author manso - computer
 */
public class Transaction implements Serializable, Validable {

    private final String source;
    private final String destination;
    private String signature;
    private final double value;
    private final Currency currency;
    
    /**
     * representa as transações na blockchain
     * @param source utilizador que realiza a transação
     * @param destination   utilizador a quem se destina a transação
     * @param value quantidade de moedas da transação
     * @param currency  moeda utilizda na transação
     */
    public Transaction(String source, String destination, double value, Currency currency) {
        this.source = source;
        this.destination = destination;
        this.value = value;
        this.currency = currency;
        this.signature = "unsigned";
    }
    
    /**
     * assina a transação usando uma chave privada
     * @param priv chave privada utilizada para assinar a transação
     */
    public void sign(PrivateKey priv) {
        //join data
        String data = source + destination + value + currency.getName();
        //sign data
        this.signature = Security.sign(data.getBytes(), priv);
    }
    
    /**
     * Assina a transação utilizando o nome do utilizador e password.
     * @param user utilizador que assinou a transação
     * @param password  password do utilizador que assinou a transação
     */
    public void sign(String user, String password) {
        //load private key and sign
        sign(Security.loadPrivateKey(user, password.toCharArray()));
    }

    @Override
    public boolean isValid() {
        try {
            //join data
            String data = source + destination + value + currency.getName();
            //load public key
            PublicKey pub = Security.loadPublicKey(source + ".pub");
            //verify with public key
            return Security.verify(data.getBytes(), signature, pub);
        } catch (Exception e) {
            return false;
        }

    }
    /**
     * converte o objeto em string
     * @return devolve uma string
     */
    @Override
    public String toString() {
        return String.format("%-6s %6.2f %-5s %-6s <%-10s:%b>",
                Utils.cutString(source, 10),
                value,
                currency.getSymbol(),
                Utils.cutString(destination, 10),
                Utils.cutString(signature, 10),
                isValid());
    }
    
    /**
     * devolve a moeda utilizada
     * @return currency
     */
    public Currency getCurrency(){
        return currency;
    }

    /**
     * devolve o utilizador / cliente
     * @return string nome do utilizador
     */
    public String getSource() {
        return source;
    }
    
    /**
     * devolve o utilizador a quem se detina a transação
     * @return string nome do utilizador 
     */
    public String getDestination() {
        return destination;
    }
    
    /**
     * devolve a assinatura do assinador
     * @return string assinatura 
     */
    public String getSign() {
        return signature;
    }
    
    /**
     * devolve a quantidade de moedas
     * @return double quantidade das moedas
     */
    public double getValue() {
        return value;
    }
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
