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

import blockchain.user.User;

/**
 * Created on 24/04/2026, 15:08:45 
 * @author manso - computer
 */
public class Create_Users {
    public static void main(String[] args) {
        User.register("SYSTEM", "123qwe");
        User.register("ana", "123qwe");
        User.register("maria", "123qwe");
        User.register("pedro", "123qwe");
        User.register("paulo", "123qwe");
        //load an user        
        User system = new User("SYSTEM", "123qwe");
        System.out.println(system);
        
    }



    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202604241508L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
