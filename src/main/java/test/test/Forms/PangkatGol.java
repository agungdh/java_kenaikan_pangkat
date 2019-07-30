/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;
import test.test.Models.PangkatGolModel;

/**
 *
 * @author user
 */
public class PangkatGol extends javax.swing.JInternalFrame {
    private DefaultTableModel model = new DefaultTableModel();
    private String ID;
    private String state;
    
    /**
     * Creates new form Orang
     */
    public PangkatGol() {
        initComponents();
        
        loadTable();
    }
    
    private void loadTable() {
        model = new DefaultTableModel();
        
        Base.open();
        LazyList<PangkatGolModel> pangkatGols = PangkatGolModel.findAll();
        Base.close();
        
        model.addColumn("#ID");
        model.addColumn("Pangkat Golongan");
        
        Base.open();
        for(PangkatGolModel pangkatGol : pangkatGols) {
            model.addRow(new Object[]{pangkatGol.getId(), pangkatGol.getString("pangkatgol")});
        }
        Base.close();
        
        TablePangkatGol.setModel(model);
        
        setState("index");
    }
    
    private void hapusData() {
        Base.open();
        PangkatGolModel pangkatGol = PangkatGolModel.findById(ID);
        try {
            pangkatGol.delete();
        } catch (DBException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        Base.close();
    }
    
    private void setState(String IndexOrEdit) {
        if (IndexOrEdit.equals("index")) {
            ButtonTambahUbah.setText("Tambah");
            ButtonResetHapus.setText("Reset");
            
            state = IndexOrEdit;
        } else if (IndexOrEdit.equals("edit")) {
            ButtonTambahUbah.setText("Ubah");
            ButtonResetHapus.setText("Hapus");
            
            state = IndexOrEdit;
        } else {
            JOptionPane.showMessageDialog(null, "Index/Edit ?");
        }
    }
    
    private void tambahData(String pangkatgol) {
        Base.open();
        PangkatGolModel pangkatGol = new PangkatGolModel();
        pangkatGol.set("pangkatgol", pangkatgol);
        pangkatGol.save();
        Base.close();
    }
    
    private void ubahData(String id, String pangkatgol) {
        Base.open();
        PangkatGolModel pangkatGol = PangkatGolModel.findById(id);
        pangkatGol.set("pangkatgol", pangkatgol);
        pangkatGol.save();
        Base.close();
    }

    private void resetForm() {
        TextPangkatGol.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonTambahUbah = new javax.swing.JButton();
        ButtonResetHapus = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        TablePangkatGol = new javax.swing.JTable();
        LabelPangkatGol = new javax.swing.JLabel();
        TextPangkatGol = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Pangkat Golongan");

        ButtonTambahUbah.setText("Tambah");
        ButtonTambahUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTambahUbahActionPerformed(evt);
            }
        });

        ButtonResetHapus.setText("Reset");
        ButtonResetHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonResetHapusActionPerformed(evt);
            }
        });

        TablePangkatGol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TablePangkatGol.getTableHeader().setReorderingAllowed(false);
        TablePangkatGol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablePangkatGolMouseClicked(evt);
            }
        });
        ScrollPane.setViewportView(TablePangkatGol);

        LabelPangkatGol.setText("Pangkat Golongan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol)
                                .addGap(18, 18, 18)
                                .addComponent(TextPangkatGol))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ButtonTambahUbah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                                .addComponent(ButtonResetHapus)))
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextPangkatGol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonTambahUbah)
                    .addComponent(ButtonResetHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTambahUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTambahUbahActionPerformed
        if (state.equals("index")) {
            if (TextPangkatGol.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form Pangkat Golongan Masih Kosong !!!");
            } else {
                tambahData(TextPangkatGol.getText());
                resetForm();
                loadTable();
            }
        } else {
            if (TextPangkatGol.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form Pangkat Golongan Masih Kosong !!!");
            } else {
                ubahData(ID, TextPangkatGol.getText());
                resetForm();
                loadTable();
            }
        }
    }//GEN-LAST:event_ButtonTambahUbahActionPerformed

    private void ButtonResetHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonResetHapusActionPerformed
        if (state.equals("edit")) {
            hapusData();
            loadTable();
        }

        resetForm();
    }//GEN-LAST:event_ButtonResetHapusActionPerformed

    private void TablePangkatGolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePangkatGolMouseClicked
        int i =TablePangkatGol.getSelectedRow();
        if(i>=0){
            ID = model.getValueAt(i, 0).toString();

            Base.open();
            PangkatGolModel pangkatGol = PangkatGolModel.findById(ID);
            Base.close();

            TextPangkatGol.setText(pangkatGol.getString("pangkatgol"));

            setState("edit");
        }
    }//GEN-LAST:event_TablePangkatGolMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonResetHapus;
    private javax.swing.JButton ButtonTambahUbah;
    private javax.swing.JLabel LabelPangkatGol;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JTable TablePangkatGol;
    private javax.swing.JTextField TextPangkatGol;
    // End of variables declaration//GEN-END:variables
}
