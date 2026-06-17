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
 *
 * @author manso - computer
 */
public class Transaction implements Serializable, Validable {

    private final String source;
    private final String destination;
    private String signature;
    private final double value;
    private final Currency currency;

    public Transaction(String source, String destination, double value, Currency currency) {
        this.source = source;
        this.destination = destination;
        this.value = value;
        this.currency = currency;
        this.signature = "unsigned";
    }

    public void sign(PrivateKey priv) {
        //join data
        String data = source + destination + value + currency.getName();
        //sign data
        this.signature = Security.sign(data.getBytes(), priv);
    }

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
    
    public Currency getCurrency(){
        return currency;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getSign() {
        return signature;
    }

    public double getValue() {
        return value;
    }
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::

///////////////////////////////////////////////////////////////////////////
}
