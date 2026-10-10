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

package moedas;

import java.io.Serializable;

/**
 * Created on 26/05/2026, 17:51:48 
 * class que define uma moeda ether
 * @author manso - computer
 */
public class Ether extends Currency implements Serializable{



    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202605261751L;

    public Ether() {
        super("Ether", "ETH");
    }
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
