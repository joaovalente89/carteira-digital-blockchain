/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moedas;

import java.io.Serializable;

/**
 *  class para definir o que é uma moeda
 * @author joao-
 */
public abstract class Currency implements Serializable {
    
    protected final String name;
    protected final String symbol;
    
    //construtor para criar uma currency "moeda"
    public Currency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    
    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
    
    public String toString(){
        return name + "("+ symbol +")";
    }
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2026  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
