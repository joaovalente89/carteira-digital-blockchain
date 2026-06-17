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

package blockchain.user;

import blockchain.helpers.Security;
import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HexFormat;

/**
 * Created on 24/04/2026, 15:04:25
 *
 * @author manso - computer
 */
public class User {

    private final String name;
    private final PublicKey pub;
    private final PrivateKey priv;
    
    //correçao da password validador, codigo do professor
    private volatile String password; 

    public User(String name, String password) {
        this.name = name;
        this.priv = Security.loadPrivateKey(name + ".priv", password.toCharArray());
        this.pub = Security.loadPublicKey(name + ".pub");
        this.password= password;
        System.out.println(name + " loaded.");
    }

    public String getPassword() {
        return password;
    }
     
     

    public static void register(String name, String password) {
        KeyPair kp = Security.generateKeyPair();
        Security.savePrivateKey(kp.getPrivate(), name + ".priv", password.toCharArray());
        Security.savePublicKey(kp.getPublic(), name + ".pub");
        System.out.println(name + " registered.");
    }

    @Override
    public String toString() {
        return "User : " + name
                + "\nPublic Key  : " + HexFormat.of().formatHex(pub.getEncoded())
                + "\nPrivate Key : " + HexFormat.of().formatHex(priv.getEncoded());
    }

    public String getName() {
        return name;
    }

    public PublicKey getPub() {
        return pub;
    }

    public PrivateKey getPriv() {
        return priv;
    }
    
    
    public static String [] loadAllUsers(){
        ArrayList<String> users = new ArrayList<>();
        File [] f = new File(".").listFiles();
        for (File file : f) {
            if( file.getAbsolutePath().endsWith(".priv"))
                users.add(file.getName().replace(".priv", ""));
        }
        String[] array = new String[users.size()];
        return  users.toArray(array);
        
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604241504L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::


///////////////////////////////////////////////////////////////////////////
}
