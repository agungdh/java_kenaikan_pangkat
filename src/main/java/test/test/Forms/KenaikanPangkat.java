/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;
import test.test.Models.KenaikanPangkatModel;
import test.test.Models.PegawaiModel;
import test.test.Models.PangkatGolModel;

/**
 *
 * @author user
 */
public class KenaikanPangkat extends javax.swing.JInternalFrame {
    private List<Integer> comboPangkatGolBaruID = new ArrayList<Integer>();
    private int comboPangkatGolBaruIndex;
    private int selectedComboPangkatGolBaruIndex;

    private List<Integer> comboPangkatGolLamaID = new ArrayList<Integer>();
    private int comboPangkatGolLamaIndex;
    private int selectedComboPangkatGolLamaIndex;

    private List<Integer> comboPegawaiID = new ArrayList<Integer>();
    private int comboPegawaiIndex;
    private int selectedComboPegawaiIndex;

    private DefaultTableModel model = new DefaultTableModel();
    private String ID;
    private String state;
    
    /**
     * Creates new form Orang
     */
    public KenaikanPangkat() {
        initComponents();
        
        loadTable();
        
        loadComboBox();
    }
    
    public void loadComboBox() {
        ComboPangkatGolBaru.removeAllItems();
        ComboPangkatGolLama.removeAllItems();
        ComboPangkatGolLama.removeAllItems();
        
        Base.open();
        LazyList<PangkatGolModel> pangkatGols = PangkatGolModel.findAll();
        LazyList<PegawaiModel> pegawais = PegawaiModel.findAll();
        
        for(PangkatGolModel pangkatGol : pangkatGols) {
            comboPangkatGolBaruID.add(Integer.parseInt(pangkatGol.getString("id")));
            ComboPangkatGolBaru.addItem(pangkatGol.getString("pangkatgol"));
            
            comboPangkatGolLamaID.add(Integer.parseInt(pangkatGol.getString("id")));
            ComboPangkatGolLama.addItem(pangkatGol.getString("pangkatgol"));
        }
        
        for(PegawaiModel pegawai : pegawais) {
            comboPegawaiID.add(Integer.parseInt(pegawai.getString("id")));
            ComboPegawai.addItem(pegawai.getString("nip") + " " + pegawai.getString("nama"));
        }

        Base.close();
    }
    
    private void loadTable() {
        model = new DefaultTableModel();
        
        Base.open();
        LazyList<KenaikanPangkatModel> kenaikanPangkats = KenaikanPangkatModel.findAll();
        Base.close();
        
        model.addColumn("#ID");
        model.addColumn("NIP");
        model.addColumn("Nama");
        model.addColumn("Pangkat Golongan Lama");
        model.addColumn("Pangkat Golongan Baru");
        model.addColumn("TMT");
        model.addColumn("YAD");

        Base.open();
        for(KenaikanPangkatModel kenaikanPangkat : kenaikanPangkats) {
            PegawaiModel pegawai = kenaikanPangkat.parent(PegawaiModel.class);
            PangkatGolModel PangkatGolLama = PangkatGolModel.findById(kenaikanPangkat.getString("id_pangkat_lama"));
            PangkatGolModel PangkatGolBaru = PangkatGolModel.findById(kenaikanPangkat.getString("id_pangkat_baru"));
            
            model.addRow(new Object[]{
                pegawai.getId(),
                pegawai.getString("nip"),
                pegawai.getString("nama"),
                PangkatGolLama.getString("pangkatgol"),
                PangkatGolBaru.getString("pangkatgol"),
                kenaikanPangkat.getString("tmt"),
                kenaikanPangkat.getString("yad"),
            });
        }
        
        Base.close();
        
        TableKenaikanPangkat.setModel(model);
        
        setState("index");
    }
    
    private void hapusData() {
        Base.open();
        KenaikanPangkatModel kenaikanPangkat = KenaikanPangkatModel.findById(ID);
        try {
            kenaikanPangkat.delete();
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
    
    private void tambahData(String nip, String nama) {
//        Base.open();
//        PegawaiModel pegawai = new PegawaiModel();
//        pegawai.set("nip", nip);
//        pegawai.set("nama", nama);
//        pegawai.set("id_pangkatgol", selectedComboPangkatGolIndex);
//        pegawai.save();
//        Base.close();
    }
    
    private void ubahData(String id, String nip, String nama) {
//        Base.open();
//        PegawaiModel pegawai = PegawaiModel.findById(id);
//        pegawai.set("nip", nip);
//        pegawai.set("nama", nama);
//        pegawai.set("id_pangkatgol", selectedComboPangkatGolIndex);
//        pegawai.save();
//        Base.close();
    }

    private void resetForm() {
//        TextNip.setText("");
//        TextNama.setText("");
//        ComboPangkatGol.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDatePickerUtil1 = new org.jdatepicker.util.JDatePickerUtil();
        ButtonTambahUbah = new javax.swing.JButton();
        ButtonResetHapus = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        TableKenaikanPangkat = new javax.swing.JTable();
        ComboPegawai = new javax.swing.JComboBox<>();
        LabelPangkatGol = new javax.swing.JLabel();
        ComboPangkatGolBaru = new javax.swing.JComboBox<>();
        LabelPangkatGol1 = new javax.swing.JLabel();
        LabelPangkatGol2 = new javax.swing.JLabel();
        ComboPangkatGolLama = new javax.swing.JComboBox<>();
        LabelPangkatGol3 = new javax.swing.JLabel();
        TMT = new com.toedter.calendar.JDateChooser();
        YAD = new com.toedter.calendar.JDateChooser();
        LabelPangkatGol4 = new javax.swing.JLabel();

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

        TableKenaikanPangkat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TableKenaikanPangkat.getTableHeader().setReorderingAllowed(false);
        TableKenaikanPangkat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableKenaikanPangkatMouseClicked(evt);
            }
        });
        ScrollPane.setViewportView(TableKenaikanPangkat);

        ComboPegawai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboPegawai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPegawaiItemStateChanged(evt);
            }
        });
        ComboPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboPegawaiActionPerformed(evt);
            }
        });

        LabelPangkatGol.setText("Pegawai");

        ComboPangkatGolBaru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboPangkatGolBaru.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPangkatGolBaruItemStateChanged(evt);
            }
        });
        ComboPangkatGolBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboPangkatGolBaruActionPerformed(evt);
            }
        });

        LabelPangkatGol1.setText("Pangkat Baru");

        LabelPangkatGol2.setText("Pangkat Lama");

        ComboPangkatGolLama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboPangkatGolLama.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPangkatGolLamaItemStateChanged(evt);
            }
        });
        ComboPangkatGolLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboPangkatGolLamaActionPerformed(evt);
            }
        });

        LabelPangkatGol3.setText("TMT");

        LabelPangkatGol4.setText("YAD");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ButtonTambahUbah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ButtonResetHapus)
                                .addGap(9, 9, 9)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol)
                                .addGap(18, 18, 18)
                                .addComponent(ComboPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol2)
                                .addGap(18, 18, 18)
                                .addComponent(ComboPangkatGolLama, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol3)
                                .addGap(18, 18, 18)
                                .addComponent(TMT, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol4)
                                .addGap(18, 18, 18)
                                .addComponent(YAD, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol1)
                                .addGap(18, 18, 18)
                                .addComponent(ComboPangkatGolBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPangkatGolLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPangkatGolBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol3)
                    .addComponent(TMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol4)
                    .addComponent(YAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonTambahUbah)
                    .addComponent(ButtonResetHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTambahUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTambahUbahActionPerformed
//        if (state.equals("index")) {
//            if (TextNip.getText().trim().equals("")) {
//                JOptionPane.showMessageDialog(null, "Form NIP Masih Kosong !!!");
//            } else if (TextNama.getText().trim().equals("")) {
//                JOptionPane.showMessageDialog(null, "Form Nama Masih Kosong !!!");
//            } else {
//                tambahData(TextNip.getText(), TextNama.getText());
//                resetForm();
//                loadTable();
//            }
//        } else {
//            if (TextNip.getText().trim().equals("")) {
//                JOptionPane.showMessageDialog(null, "Form NIP Masih Kosong !!!");
//            } else if (TextNama.getText().trim().equals("")) {
//                JOptionPane.showMessageDialog(null, "Form Nama Masih Kosong !!!");
//            } else {
//                ubahData(ID, TextNip.getText(), TextNama.getText());
//                resetForm();
//                loadTable();
//            }
//        }
    }//GEN-LAST:event_ButtonTambahUbahActionPerformed

    private void ButtonResetHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonResetHapusActionPerformed
        if (state.equals("edit")) {
            hapusData();
            loadTable();
        }

        resetForm();
    }//GEN-LAST:event_ButtonResetHapusActionPerformed

    private void TableKenaikanPangkatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableKenaikanPangkatMouseClicked
//        int i =TableKenaikanPangkat.getSelectedRow();
//        if(i>=0){
//            ID = model.getValueAt(i, 0).toString();
//
//            Base.open();
//            PegawaiModel pegawai = PegawaiModel.findById(ID);
//            Base.close();
//
//            TextNip.setText(pegawai.getString("nip"));
//            TextNama.setText(pegawai.getString("nama"));
//            ComboPegawai.setSelectedIndex(comboPangkatGolID.indexOf(Integer.parseInt(pegawai.getString("id_pangkatgol"))));
//            
//            setState("edit");
//        }
    }//GEN-LAST:event_TableKenaikanPangkatMouseClicked

    private void ComboPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPegawaiActionPerformed
//        comboPangkatGolIndex = ComboPegawai.getSelectedIndex();
//        if (comboPangkatGolIndex >= 0) {
//            selectedComboPangkatGolIndex = comboPangkatGolID.get(comboPangkatGolIndex);
//        }
    }//GEN-LAST:event_ComboPegawaiActionPerformed

    private void ComboPegawaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPegawaiItemStateChanged
        
    }//GEN-LAST:event_ComboPegawaiItemStateChanged

    private void ComboPangkatGolBaruItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPangkatGolBaruItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboPangkatGolBaruItemStateChanged

    private void ComboPangkatGolBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPangkatGolBaruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboPangkatGolBaruActionPerformed

    private void ComboPangkatGolLamaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPangkatGolLamaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboPangkatGolLamaItemStateChanged

    private void ComboPangkatGolLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPangkatGolLamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboPangkatGolLamaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonResetHapus;
    private javax.swing.JButton ButtonTambahUbah;
    private javax.swing.JComboBox<String> ComboPangkatGolBaru;
    private javax.swing.JComboBox<String> ComboPangkatGolLama;
    private javax.swing.JComboBox<String> ComboPegawai;
    private javax.swing.JLabel LabelPangkatGol;
    private javax.swing.JLabel LabelPangkatGol1;
    private javax.swing.JLabel LabelPangkatGol2;
    private javax.swing.JLabel LabelPangkatGol3;
    private javax.swing.JLabel LabelPangkatGol4;
    private javax.swing.JScrollPane ScrollPane;
    private com.toedter.calendar.JDateChooser TMT;
    private javax.swing.JTable TableKenaikanPangkat;
    private com.toedter.calendar.JDateChooser YAD;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    // End of variables declaration//GEN-END:variables
}
