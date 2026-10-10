/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package carteiradigital;

import blockchain.core.Block;
import blockchain.core.BlockChainPOS;
import blockchain.core.Transaction;
import blockchain.core.pos.BlockPOS;
import blockchain.helpers.Utils;
import blockchain.user.User;
import java.util.ArrayList;
import java.util.List;
import moedas.Currency;
import moedas.CurrencyHelper;
import blockchain.helpers.Exporter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *  frame da wallet do utilizador que realizou o login
 * permite fazer transações para outros utilizador
 * permite comprar moedas ao System a partir do mesmo menu de transações
 * permite ver as transferencias totais efetuadas pelo utilizador e para o mesmo, podendo selecionar moedas especificas
 * é possivel exportar as transferencias, totais ou de uma moeda expecifica para um ficheiro excel 
 * consegue se ver o saldo especifico de cada moeda
 * @author manso
 */
public class DigitalWalletGui extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DigitalWalletGui.class.getName());
    User myUser;
    blockchain.core.BlockChainPOS myblockchain = new BlockChainPOS("poo.blk");

    /**
     * construtor da interface principal do programa
     * 
     * @param u Utilizador que esta a interagir com a frame
     */
    public DigitalWalletGui(User u) {
        initComponents();
        this.myUser = u;
        cbDestino.removeAllItems();
        //primeiro item da CB é vazio
        cbDestino.addItem("Selecione um destinatário");

        //na combobox Destino ignora o SYSTEM, o user logado e o admin 
        for (String user : User.loadAllUsers()) {
            if (!user.equalsIgnoreCase(u.getName())
                    && !user.equalsIgnoreCase("SYSTEM") && !user.equalsIgnoreCase("admin")) {
                cbDestino.addItem(user);
            }
        }
        initMyComponents();
        if (myUser.getName().equalsIgnoreCase("SYSTEM")) {
            lblSaldo.setVisible((false));
            txtSaldo.setVisible(false);
        } else {
            txtSaldo.setText(String.valueOf(getCoinBalance(cmbox_moeda.getSelectedItem().toString())));
        }
    }

    /**
     * metodo para iniciar componentes e fazer um update das transações e saldo
     */
    public void initMyComponents() {
        txt_origem.setText(myUser.getName());
        //bloqueia a ediçao de componentes na table
        txt_origem.setEnabled(false);
        txt_ledger.setText(getTransactions());

        refreshTable();
        refreshSaldo();
        //mete na textbox o valor do saldo 
        txtSaldo.setText(String.valueOf(getCoinBalance(cmbox_moeda.getSelectedItem().toString())));
        txtSaldo.setEditable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * metodo para devolver o saldo da wallet do utilizador logado baseado na moeda selecionada 
     * @param coin  cripto moeda selecionada na cmbox
     * @return  devolve o saldo baseado na moeda selecionada
     */
    public double getCoinBalance(String coin) {
        double balance = 0.0;
        List<Block> chain = myblockchain.getChain();
        for (Block block : chain) {
            try {
                Transaction t = (Transaction) Utils.fromBase64(block.getData());
                // filtra a moeda
                if (!t.getCurrency().getName().equalsIgnoreCase(coin)) {
                    continue;
                }
                // saída de moedas(dinheiro/quantidade)
                if (t.getSource().equalsIgnoreCase(myUser.getName())) {
                    balance -= t.getValue();
                }
                // entrada de moedas(dinheiro/quantidade)
                if (t.getDestination().equalsIgnoreCase(myUser.getName())) {
                    balance += t.getValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return balance;
    }

    /**
     * metodo para fazer refresh do saldo
     * ao mostrar o saldo ignorando numeros deixando num formato com apenas 2 casas decimais
     */
    private void refreshSaldo() {
        StringBuilder saldo = new StringBuilder("");
        //transaforma o valor da moeda em uma string
        String valor = String.valueOf(getCoinBalance(cmbox_moeda.getSelectedItem().toString()));
        boolean encontrouPonto = false;
        int nIndex = 0;
        // itera todos os caracteres do array
        for(char c : valor.toCharArray()) {
            //mete no array os caracteres iterados
            saldo.append(c);
            System.out.println(saldo);
            if (encontrouPonto == true){
                nIndex++;
            }if(nIndex == 2){
                break;    
            }
            if(c == '.'){
                encontrouPonto = true;
            } 
        }
        txtSaldo.setText(saldo.toString());
        txtSaldo.setEditable(false);
    }

    /**
     *  metodo para ir buscar as transações a blockchain
     * @return transação em String
     */
    String getTransactions() {
        ArrayList<Block> chain = myblockchain.getChain();
        StringBuilder txt = new StringBuilder();
        for (int i = 1; i < chain.size(); i++) {
            try {
                String data = chain.get(i).getData();
                Transaction t = Utils.fromBase64(data);
                if (t.getSource().equalsIgnoreCase(myUser.getName())
                        || t.getDestination().equalsIgnoreCase(myUser.getName())) {
                    txt.append(t.toString() + "\n");
                }
            } catch (Exception ex) {
                System.err.println("ERRO : " + ex.getMessage());
            }
        }
        return txt.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btExportar = new javax.swing.JButton();
        btLogout = new javax.swing.JButton();
        bt_transacao = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_origem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbDestino = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbox_moeda = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        sp_valor = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_ledger = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTransactions = new javax.swing.JTable();
        lblSaldo = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btCompraMoeda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btExportar.setText("Exportar transações");
        btExportar.addActionListener(this::btExportarActionPerformed);
        getContentPane().add(btExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 160, -1));

        btLogout.setText("LogOut");
        btLogout.addActionListener(this::btLogoutActionPerformed);
        getContentPane().add(btLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 100, -1));

        bt_transacao.setText("Efetuar transação");
        bt_transacao.addActionListener(this::bt_transacaoActionPerformed);
        getContentPane().add(bt_transacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 130, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Origem: ");
        jLabel1.setOpaque(false);

        txt_origem.addActionListener(this::txt_origemActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Destino:");
        jLabel2.setOpaque(false);

        cbDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", " ", "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDestino.addActionListener(this::cbDestinoActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Moeda:");

        cmbox_moeda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "BitCoin", "Ether", "Solana", "TemplarCoin" }));
        cmbox_moeda.addActionListener(this::cmbox_moedaActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Valor:");

        sp_valor.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.01d));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_origem, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbox_moeda, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sp_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(175, 175, 175))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_origem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbox_moeda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sp_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 250, -1));

        txt_ledger.setColumns(20);
        txt_ledger.setRows(5);
        jScrollPane1.setViewportView(txt_ledger);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 460, 328, -1));

        tblTransactions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"ana", "maria", "bitcoin", "10"},
                {"system", "joana", "ether", "5"},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Origem", "Destino", "Moeda", "Valor"
            }
        ));
        tblTransactions.setEnabled(false);
        jScrollPane2.setViewportView(tblTransactions);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 411, 345));

        lblSaldo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSaldo.setForeground(new java.awt.Color(0, 0, 0));
        lblSaldo.setText("Moeda");
        getContentPane().add(lblSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, 50, 20));

        txtSaldo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSaldo.setForeground(new java.awt.Color(0, 0, 0));
        txtSaldo.addActionListener(this::txtSaldoActionPerformed);
        getContentPane().add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 80, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Saldo:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blockchain/imgs/walletImg.jpg"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-3, 116, -1, 450));

        btCompraMoeda.setText("Compra Moeda");
        btCompraMoeda.addActionListener(this::btCompraMoedaActionPerformed);
        getContentPane().add(btCompraMoeda, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *  botão para exportar as transações para um ficheiro excel
     * podendo selecionar por moedas para focar em determinadas transferencias
     * @param evt
     */
    private void btExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExportarActionPerformed
        Currency cur = CurrencyHelper.create(cmbox_moeda.getSelectedItem().toString().toLowerCase());
        String user = myUser.getName();
        if (cur == null || cmbox_moeda.getSelectedItem().toString().equalsIgnoreCase("Todas")) {
            Exporter.toCSV(myblockchain, user);
            JOptionPane.showMessageDialog(this, user + ".csv Gravado com Sucesso");
        } else {
            try {
                String file = Exporter.toCSV(myblockchain, user, cur);
                JOptionPane.showMessageDialog(this, file + " Gravado com Sucesso");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "erro " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btExportarActionPerformed

    /**
     *  botão para fazer logout da wallet e voltar ao ecra de login
     * @param evt
     */
    private void btLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLogoutActionPerformed

        dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_btLogoutActionPerformed

    /**
     * metodo que atualiza e constroi a table das transaçoes
     */
    private void refreshTable() {
        javax.swing.table.DefaultTableModel model
                = new javax.swing.table.DefaultTableModel(
                        new Object[]{"Origem", "Destino", "Moeda", "Valor"}, 0);
        ArrayList<Block> chain = myblockchain.getChain();
        String moedaSelecionada = cmbox_moeda.getSelectedItem().toString();
        for (int i = 1; i < chain.size(); i++) {
            try {
                Transaction t = Utils.fromBase64(chain.get(i).getData());
                if (t.getSource().equalsIgnoreCase(myUser.getName())
                        || t.getDestination().equalsIgnoreCase(myUser.getName())) {
                    if (moedaSelecionada.equalsIgnoreCase("Todas")
                            || t.getCurrency().getName().equalsIgnoreCase(moedaSelecionada)) {
                        model.addRow(new Object[]{
                            t.getSource(),
                            t.getDestination(),
                            t.getCurrency().getName(),
                            t.getValue()
                        });
                    }
                }
            } catch (Exception e) {
            }
        }
        tblTransactions.setModel(model);
    }

    /**
     *  botão para efetuar transação
     * vendo quem a esta a realizar "myUser", para quem vai "cbDestino", qual a moeda "cur" e a quantidade "value"
     * atualizando a blockchain e fazendo um update das tabelas saldo e transaçoes
     * @param evt
     */
    private void bt_transacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_transacaoActionPerformed
        String origem = myUser.getName();
        String destino = cbDestino.getSelectedItem().toString();
        
        Currency cur = CurrencyHelper.create(cmbox_moeda.getSelectedItem().toString().toLowerCase());
        Double value = (Double) sp_valor.getValue();

        //nao permite enviar dinheiro para o "Selecione um destinatario"
        if (destino.equalsIgnoreCase("Selecione um destinatário")) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecione um destinatário válido!");
            return;
        }
        // caso o valor da transação seja 0 não a realiza e indica que tem um valor invalido
        if (value <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Valor inválido!");
            return;
        }
        //impedir que se envie saldo insuficiente
        if (value > getCoinBalance(cmbox_moeda.getSelectedItem().toString()) && !myUser.getName().equalsIgnoreCase("SYSTEM")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Saldo insuficiente!\nSaldo atual " + getCoinBalance(cmbox_moeda.getSelectedItem().toString()), "erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cur == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Moeda inválida!");
            return;
        }
        ValidarTransaction vt = new ValidarTransaction(this, true, destino, cmbox_moeda.getSelectedItem().toString(), value);
        vt.setVisible(true);
        if (vt.retConfirmacao()) {
            Transaction t = new Transaction(origem, destino, value, cur);
            t.sign(myUser.getPriv());
            //cria o bloco de concenso POS
            Block block
                    = new BlockPOS(
                            myblockchain,
                            Utils.toBase64(t),
                            myUser.getPassword());
            //adiciona o bloco à blockchain
            myblockchain.add(block);
        
        } else {
            return;
        }

        //atualiza a blockchain
        txt_ledger.setText(getTransactions());
        
        
        refreshTable();
        refreshSaldo();

        

    }//GEN-LAST:event_bt_transacaoActionPerformed

    /**
     *  campo que identifica a wallet do cliente
     * @param evt
     */
    private void txt_origemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_origemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_origemActionPerformed

    /**
     * campo das moedas, qual está selecionada de momento
     * ao selecionar outra moeda faz um update do saldo e da tabela de transferencias
     * @param evt
     */
    private void cmbox_moedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbox_moedaActionPerformed
        lblSaldo.setText(cmbox_moeda.getSelectedItem().toString());
        refreshTable();
        refreshSaldo();
    }//GEN-LAST:event_cmbox_moedaActionPerformed

    /**
     *  campo onde mostra o saldo
     * @param evt
     */
    private void txtSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoActionPerformed

    }//GEN-LAST:event_txtSaldoActionPerformed
    /**
     *  campo onde se seleciona o destino da transação
     * @param evt
     */
    private void cbDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDestinoActionPerformed
    /**
     * botão para comprar moedas ao System
     * atualiza a tabela de transaferencia e saldo
     * metodo por melhorar
     * @param evt 
     */
    private void btCompraMoedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCompraMoedaActionPerformed
        String destino = myUser.getName();
        Currency cur = CurrencyHelper.create(cmbox_moeda.getSelectedItem().toString().toLowerCase());
        Double value = (Double) sp_valor.getValue();
        ValidarTransaction vt = new ValidarTransaction(this, true, destino, cmbox_moeda.getSelectedItem().toString(), value);
        vt.setVisible(true);
        if (vt.retConfirmacao()) {
            // caso o valor da transação seja 0 não a realiza e indica que tem um valor invalido
            if (value <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Valor inválido!");
                return;
            }
            if (cur == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Moeda inválida!");
                return;
            }
            Transaction t = new Transaction("System", destino, value, cur);
            t.sign(myUser.getPriv());
            //cria o bloco de concenso POS
            Block block
                    = new BlockPOS(
                            myblockchain,
                            Utils.toBase64(t),
                            myUser.getPassword());
            //adiciona o bloco à blockchain
            myblockchain.add(block);
            
        }else{
            return;
        }
        txt_ledger.setText(getTransactions());

        refreshSaldo();
        refreshTable();
    }//GEN-LAST:event_btCompraMoedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCompraMoeda;
    private javax.swing.JButton btExportar;
    private javax.swing.JButton btLogout;
    private javax.swing.JButton bt_transacao;
    private javax.swing.JComboBox<String> cbDestino;
    private javax.swing.JComboBox<String> cmbox_moeda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JSpinner sp_valor;
    private javax.swing.JTable tblTransactions;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextArea txt_ledger;
    private javax.swing.JTextField txt_origem;
    // End of variables declaration//GEN-END:variables
}
