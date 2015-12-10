package ija.projekt;

import ija.projekt.gui.HotseatGui;
import ija.projekt.gui.ReplayGui;
import ija.projekt.gui.VsAIGui;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Hlavni trida aplikace, rozsiruje JFrame. Resi nacteni ulozene hry a spousti
 * HotseatGui, VersusAIGui nebo ReplayGui.
 *
 * @author Pavel Vesely, Ales Drevo
 */
public class Menu extends javax.swing.JFrame {

    /**
     * Konstruktor - vytvori novy frame Menu.
     */
    public Menu() {
        initComponents();
        setLocation(20, 10);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newGameButton = new javax.swing.JButton();
        loadGameButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        newGameButton.setText("Nová hra");
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });

        loadGameButton.setText("Načíst");
        loadGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newGameButton)
                .addGap(18, 18, 18)
                .addComponent(loadGameButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Spusti se klikem na tlacitko Nova hra. Spousti HotseatGui nebo
     * VersusAIGui dle volby v dialogu.
     */
    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameButtonActionPerformed
        Object[] options = {"Hotseat",
            "Hra proti PC"};
        int n = JOptionPane.showOptionDialog(this,
                "Zvolte druh hry:",
                "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        if (n == 0) {
            new HotseatGui(null);
        } else if (n == 1) {
            new VsAIGui(null);
        }

    }//GEN-LAST:event_newGameButtonActionPerformed

    /**
     * Spusti se klikem na tlacitko Nacist. Resi vyber ulozene hry a spousti
     * HotseatGui, VersusAIGui nebo ReplayGui dle volby v dialogu.
     */
    private void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadGameButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        File file = null;
        if (fc.showDialog(Menu.this, "Načíst") == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        } else {
            return;
        }

        Object[] options = {"Hotseat",
            "Hra proti PC",
            "Přehrát záznam"};
        int n = JOptionPane.showOptionDialog(this,
                "Zvolte druh hry:",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        if (n == 0) {
            new HotseatGui(file);
        } else if (n == 1) {
            new VsAIGui(file);
        } else if (n == 2) {
            new ReplayGui(file);
        }
    }//GEN-LAST:event_loadGameButtonActionPerformed

    /**
     * Metoda main. Spousti beh aplikace.
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loadGameButton;
    private javax.swing.JButton newGameButton;
    // End of variables declaration//GEN-END:variables
}
