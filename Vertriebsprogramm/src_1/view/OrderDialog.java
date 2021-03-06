/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OrderDialog.java
 *
 * Created on Feb 28, 2012, 9:09:23 AM
 */
package view;

/**
 *
 * @author Mike
 */
public class OrderDialog extends javax.swing.JDialog {

    /** Creates new form OrderDialog */
    public OrderDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setLocationRelativeTo(null);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbOrderDialogCustomer = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cbOrderDialogProduct = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        tfOrderDialogQuantity = new javax.swing.JTextField();
        btOrderDialogOk = new javax.swing.JButton();
        btOrderProductCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(4, 2));

        jLabel2.setText("Kunde");
        jPanel1.add(jLabel2);

        cbOrderDialogCustomer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbOrderDialogCustomer);

        jLabel1.setText("Artikel");
        jPanel1.add(jLabel1);

        cbOrderDialogProduct.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbOrderDialogProduct);

        jLabel3.setText("Menge");
        jPanel1.add(jLabel3);
        jPanel1.add(tfOrderDialogQuantity);

        btOrderDialogOk.setText("Ok");
        btOrderDialogOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOrderDialogOk(evt);
            }
        });
        jPanel1.add(btOrderDialogOk);

        btOrderProductCancel.setText("Cancel");
        btOrderProductCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOrderDialogCancel(evt);
            }
        });
        jPanel1.add(btOrderProductCancel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onOrderDialogOk(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onOrderDialogOk
        
    }//GEN-LAST:event_onOrderDialogOk

    private void onOrderDialogCancel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onOrderDialogCancel
        
    }//GEN-LAST:event_onOrderDialogCancel

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                OrderDialog dialog = new OrderDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btOrderDialogOk;
    private javax.swing.JButton btOrderProductCancel;
    private javax.swing.JComboBox cbOrderDialogCustomer;
    private javax.swing.JComboBox cbOrderDialogProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tfOrderDialogQuantity;
    // End of variables declaration//GEN-END:variables
}
