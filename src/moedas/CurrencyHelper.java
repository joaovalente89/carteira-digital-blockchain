/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moedas;

/**
 *
 * @author carlo
 */
public class CurrencyHelper {
    public static Currency create(String name){
        if( name.equalsIgnoreCase("bitcoin"))
            return new BitCoin();
        if( name.equalsIgnoreCase("ether"))
            return new Ether();
        if( name.equalsIgnoreCase("solana"))
            return new Solana();
        return null;
        
    }
}
