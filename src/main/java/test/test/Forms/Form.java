/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

/**
 *
 * @author user
 */
public class Form extends javax.swing.JFrame {

    /**
     * Creates new form Orang
     */
    public Form() {
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        Init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesktopPane = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        UserLabel = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        MenuCRUD = new javax.swing.JMenu();
        SubMenuPangkatGolongan = new javax.swing.JMenuItem();
        SubMenuPegawai = new javax.swing.JMenuItem();
        SubMenuUsulan = new javax.swing.JMenuItem();
        SubMenuKenaikanPangkat = new javax.swing.JMenuItem();
        SubMenuGajiBerkala = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        SubMenuLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Master CRUD");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout DesktopPaneLayout = new javax.swing.GroupLayout(DesktopPane);
        DesktopPane.setLayout(DesktopPaneLayout);
        DesktopPaneLayout.setHorizontalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 871, Short.MAX_VALUE)
        );
        DesktopPaneLayout.setVerticalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );

        jToolBar1.setRollover(true);
        jToolBar1.add(UserLabel);

        MenuCRUD.setText("CRUD");
        MenuCRUD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCRUDActionPerformed(evt);
            }
        });

        SubMenuPangkatGolongan.setText("Pangkat Golongan");
        SubMenuPangkatGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuPangkatGolonganActionPerformed(evt);
            }
        });
        MenuCRUD.add(SubMenuPangkatGolongan);

        SubMenuPegawai.setText("Pegawai");
        SubMenuPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuPegawaiActionPerformed(evt);
            }
        });
        MenuCRUD.add(SubMenuPegawai);

        SubMenuUsulan.setText("Usulan");
        SubMenuUsulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuUsulanActionPerformed(evt);
            }
        });
        MenuCRUD.add(SubMenuUsulan);

        SubMenuKenaikanPangkat.setText("Kenaikan Pangkat");
        SubMenuKenaikanPangkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuKenaikanPangkatActionPerformed(evt);
            }
        });
        MenuCRUD.add(SubMenuKenaikanPangkat);

        SubMenuGajiBerkala.setText("Gaji Berkala");
        SubMenuGajiBerkala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuGajiBerkalaActionPerformed(evt);
            }
        });
        MenuCRUD.add(SubMenuGajiBerkala);

        MenuBar.add(MenuCRUD);

        jMenu1.setText("System");

        SubMenuLogout.setText("Logout");
        SubMenuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(SubMenuLogout);

        MenuBar.add(jMenu1);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DesktopPane)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesktopPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SubMenuPangkatGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuPangkatGolonganActionPerformed
        PangkatGol display = new PangkatGol();
        DesktopPane.add(display);
        display.show();
    }//GEN-LAST:event_SubMenuPangkatGolonganActionPerformed

    private void MenuCRUDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCRUDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuCRUDActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void SubMenuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuLogoutActionPerformed
        DesktopPane.removeAll();
        UserLabel.setText("");
        
        Init();
    }//GEN-LAST:event_SubMenuLogoutActionPerformed

    private void SubMenuPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuPegawaiActionPerformed
        Pegawai display = new Pegawai();
        DesktopPane.add(display);
        display.show();
    }//GEN-LAST:event_SubMenuPegawaiActionPerformed

    private void SubMenuUsulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuUsulanActionPerformed
        Usulan display = new Usulan();
        DesktopPane.add(display);
        display.show();
    }//GEN-LAST:event_SubMenuUsulanActionPerformed

    private void SubMenuKenaikanPangkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuKenaikanPangkatActionPerformed
        KenaikanPangkat display = new KenaikanPangkat();
        DesktopPane.add(display);
        display.show();
    }//GEN-LAST:event_SubMenuKenaikanPangkatActionPerformed

    private void SubMenuGajiBerkalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuGajiBerkalaActionPerformed
        GajiBerkala display = new GajiBerkala();
        DesktopPane.add(display);
        display.show();
    }//GEN-LAST:event_SubMenuGajiBerkalaActionPerformed

    public void Init() {
        Login login = new Login();
        DesktopPane.add(login);
        login.show();

//        Pegawai dbg = new Pegawai();
//        DesktopPane.add(dbg);
//        dbg.show();
//        
        MenuBar.setVisible(false);
    }
    
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
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane DesktopPane;
    public javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu MenuCRUD;
    private javax.swing.JMenuItem SubMenuGajiBerkala;
    private javax.swing.JMenuItem SubMenuKenaikanPangkat;
    private javax.swing.JMenuItem SubMenuLogout;
    private javax.swing.JMenuItem SubMenuPangkatGolongan;
    private javax.swing.JMenuItem SubMenuPegawai;
    private javax.swing.JMenuItem SubMenuUsulan;
    public javax.swing.JLabel UserLabel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
