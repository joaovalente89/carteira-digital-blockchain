/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blockchain.helpers;

import blockchain.core.Block;
import blockchain.core.BlockChainPOS;
import blockchain.core.Transaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import moedas.Currency;

/**
 *
 * @author carlo
 */
public class Exporter {
    public static void toCSV(BlockChainPOS blockchain, String username) {
        StringBuilder csv = new StringBuilder();
        for (Block b : blockchain.getChain()) {
            try {
                // validar bloco da blockchain
                if (b == null || b.getData() == null) {
                    continue;
                }

                // extrair transações
                Transaction t = (Transaction) Utils.fromBase64(b.getData());

                //filtrar pela wallet 
                if (!isRelatedToWallet(t, username)) {
                    continue;
                }

                // exportar com "audit info"
                csv.append("[BLOCK] ")
                        .append(b.getBlockHash())   // se existir
                        .append(" | ");

                csv.append(t.getSource()).append(" ; ")
                        .append(t.getDestination()).append(" ; ")
                        .append(t.getCurrency().getName()).append(" ; ")
                        .append(t.getValue())
                        .append("\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        writeFile(username + ".csv", csv.toString());
    }
     
     
    public static String toCSV(BlockChainPOS blockchain, String username, Currency coin) throws IOException {
        StringBuilder csv = new StringBuilder();
        for (Block b : blockchain.getChain()) {
            try {
                if (b == null || b.getData() == null) {
                    continue;
                }
                Transaction t = (Transaction) Utils.fromBase64(b.getData());
                // filtro moeda
                if (!t.getCurrency().getName().equalsIgnoreCase(coin.getName())) {
                    continue;
                }
                if (!isRelatedToWallet(t, username)) {
                    continue;
                }

                csv.append("[BLOCK] ")
                        .append(b.getBlockHash())
                        .append(" | ");
                csv.append(t.getSource()).append(" ; ")
                        .append(t.getDestination()).append(" ; ")
                        .append(t.getCurrency().getName()).append(" ; ")
                        .append(t.getValue())
                        .append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String file = username + "_" + coin.getName() + ".csv";
        writeFile(file, csv.toString());
        return file;
    }
     
    private static boolean isRelatedToWallet(Transaction t, String username) {
        return t.getSource().equalsIgnoreCase(username) || 
               t.getDestination().equalsIgnoreCase(username);
    }
    private static void writeFile(String file, String data) {
        try {
             Files.write(Path.of(file), data.getBytes());
        } catch (IOException e) {
             System.out.println("Erro ao escrever ficheiro: " + e.getMessage());
        }
    }
}
