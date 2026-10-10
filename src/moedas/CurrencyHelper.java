/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moedas;

/**
 *  class que converte strings de moedas e cria novas crypto moedas utilizadas na blockchain
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
         if( name.equalsIgnoreCase("templarcoin"))
            return new TemplarCoin();
        return null;
        
    }
}
