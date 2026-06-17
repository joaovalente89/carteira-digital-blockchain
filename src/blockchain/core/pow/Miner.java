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

package blockchain.core.pow;

import blockchain.helpers.Security;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Created on 15/05/2026, 15:05:46
 *
 * @author manso - computer
 */
public class Miner {

    public static long mine(String data, int dificuldade) {

        try {
            //prefix of the hash (zeros) 00000000
            String zeros = "0".repeat(dificuldade);
            //Hasher with SHA-256 algorithm (defined in Security library)
            MessageDigest hasher = MessageDigest.getInstance(Security.HASH_ALGORITH);
            //numer to find
            long nonce = 0;
            while (true) {
                //concatenate data
                String dataNonce = data + nonce;
                //calculate hash
                byte[] hash = hasher.digest(dataNonce.getBytes());
                //convert to hex format
                String hexHash = HexFormat.of().formatHex(hash);
                //verify nonce
                if (hexHash.startsWith(zeros)) {
                    //System.out.println("nonce = " + nonce);
                    //System.out.println("Hash = " + hexHash );
                    //nonce found
                    return nonce;
                }
                //test next nonce
                nonce++;
            }
        } catch (NoSuchAlgorithmException ex) {
            //something is wrong
            System.getLogger(Miner.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return 0;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605151505L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
