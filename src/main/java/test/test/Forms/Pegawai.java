/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;
import test.test.Models.PegawaiModel;
import test.test.Models.PangkatGolModel;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author user
 */
public class Pegawai extends javax.swing.JInternalFrame {
    private List<Integer> comboPangkatGolID = new ArrayList<Integer>();
    private int comboPangkatGolIndex;
    private int selectedComboPangkatGolIndex;
    private DefaultTableModel model = new DefaultTableModel();
    private String ID;
    private String state;
    
    /**
     * Creates new form Orang
     */
    public Pegawai() {
        initComponents();
        
        loadTable();
        
        loadPangkatGol();
        
        formatTextInteger();
    }
    
    public void loadPangkatGol() {
        ComboPangkatGol.removeAllItems();
        
        Base.open();
        LazyList<PangkatGolModel> pangkatGols = PangkatGolModel.findAll();
        
        for(PangkatGolModel pangkatGol : pangkatGols) {
            comboPangkatGolID.add(Integer.parseInt(pangkatGol.getString("id")));
            ComboPangkatGol.addItem(pangkatGol.getString("pangkatgol"));
        }
        Base.close();
    }
    
    private void loadTableHelper(LazyList<PegawaiModel> pegawais) {
        model = new DefaultTableModel();
        
        model.addColumn("#ID");
        model.addColumn("NIP");
        model.addColumn("Nama");
        model.addColumn("Pangkat Golongan");
        model.addColumn("Gaji");
        
        Base.open();
        for(PegawaiModel pegawai : pegawais) {
            PangkatGolModel pangkatGol = pegawai.parent(PangkatGolModel.class);
            model.addRow(new Object[]{pegawai.getId(), pegawai.getString("nip"), pegawai.getString("nama"), pangkatGol.getString("pangkatgol"), NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(pegawai.getString("gaji_pokok")))});
        }
        Base.close();
        
        TablePegawai.setModel(model);
        
        setState("index");
    }
    
    private void formatTextInteger() {
       
    }
    
    private void loadTable() {
        Base.open();
        LazyList<PegawaiModel> pegawais = PegawaiModel.findAll();
        Base.close();
        
        loadTableHelper(pegawais);
    }

    private void loadTable(String cariNIP) {
        Base.open();
        LazyList<PegawaiModel> pegawais = PegawaiModel.where("nip like ?", '%' + cariNIP + '%');
        Base.close();
        
        loadTableHelper(pegawais);
    }

    
    private void hapusData() {
        Base.open();
        PegawaiModel pegawai = PegawaiModel.findById(ID);
        try {
            pegawai.delete();
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
    
    private void tambahData(String nip, String nama, int gajiPokok) {
        Base.open();
        PegawaiModel pegawai = new PegawaiModel();
        pegawai.set("nip", nip);
        pegawai.set("nama", nama);
        pegawai.set("id_pangkatgol", selectedComboPangkatGolIndex);
        pegawai.set("gaji_pokok", gajiPokok);
        pegawai.save();
        Base.close();
    }
    
    private void ubahData(String id, String nip, String nama, int gajiPokok) {
        Base.open();
        PegawaiModel pegawai = PegawaiModel.findById(id);
        pegawai.set("nip", nip);
        pegawai.set("nama", nama);
        pegawai.set("id_pangkatgol", selectedComboPangkatGolIndex);
        pegawai.set("gaji_pokok", gajiPokok);
        pegawai.save();
        Base.close();
    }

    private void resetForm() {
        TextNip.setText("");
        TextNama.setText("");
        SpinnerGajiPokok.setValue(0);
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

        ButtonTambahUbah = new javax.swing.JButton();
        ButtonResetHapus = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        TablePegawai = new javax.swing.JTable();
        LabelNip = new javax.swing.JLabel();
        TextNip = new javax.swing.JTextField();
        TextNama = new javax.swing.JTextField();
        LabelNama = new javax.swing.JLabel();
        ComboPangkatGol = new javax.swing.JComboBox<>();
        LabelPangkatGol = new javax.swing.JLabel();
        TextCari = new javax.swing.JTextField();
        LabelCari = new javax.swing.JLabel();
        LabelGajiPokok = new javax.swing.JLabel();
        SpinnerGajiPokok = new javax.swing.JSpinner();

        setClosable(true);
        setTitle("Pegawai");

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

        TablePegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TablePegawai.getTableHeader().setReorderingAllowed(false);
        TablePegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablePegawaiMouseClicked(evt);
            }
        });
        ScrollPane.setViewportView(TablePegawai);

        LabelNip.setText("NIP");

        LabelNama.setText("Nama");

        ComboPangkatGol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboPangkatGol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPangkatGolItemStateChanged(evt);
            }
        });
        ComboPangkatGol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboPangkatGolActionPerformed(evt);
            }
        });

        LabelPangkatGol.setText("Pangkat Golongan");

        TextCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextCariActionPerformed(evt);
            }
        });

        LabelCari.setText("Cari (NIP)");

        LabelGajiPokok.setText("Gaji Pokok");

        SpinnerGajiPokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SpinnerGajiPokokKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LabelNama)
                    .addComponent(LabelPangkatGol)
                    .addComponent(LabelNip)
                    .addComponent(LabelGajiPokok))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextNip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ComboPangkatGol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextNama, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SpinnerGajiPokok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(LabelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TextCari, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ButtonTambahUbah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ButtonResetHapus)
                                .addGap(31, 31, 31)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPangkatGol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelGajiPokok)
                    .addComponent(SpinnerGajiPokok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonTambahUbah)
                    .addComponent(ButtonResetHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCari))
                .addGap(25, 25, 25)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTambahUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTambahUbahActionPerformed
        if (state.equals("index")) {
            if (TextNip.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form NIP Masih Kosong !!!");
            } else if (TextNama.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form Nama Masih Kosong !!!");
            } else {
                tambahData(TextNip.getText(), TextNama.getText(), Integer.parseInt(SpinnerGajiPokok.getValue().toString()));
                resetForm();
                loadTable();
            }
        } else {
            if (TextNip.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form NIP Masih Kosong !!!");
            } else if (TextNama.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form Nama Masih Kosong !!!");
            } else {
                ubahData(ID, TextNip.getText(), TextNama.getText(), Integer.parseInt(SpinnerGajiPokok.getValue().toString()));
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

    private void TablePegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePegawaiMouseClicked
        int i =TablePegawai.getSelectedRow();
        if(i>=0){
            ID = model.getValueAt(i, 0).toString();

            Base.open();
            PegawaiModel pegawai = PegawaiModel.findById(ID);
            Base.close();

            TextNip.setText(pegawai.getString("nip"));
            TextNama.setText(pegawai.getString("nama"));
            ComboPangkatGol.setSelectedIndex(comboPangkatGolID.indexOf(Integer.parseInt(pegawai.getString("id_pangkatgol"))));
            SpinnerGajiPokok.setValue(Integer.parseInt(pegawai.getString("gaji_pokok")));
            
            setState("edit");
        }
    }//GEN-LAST:event_TablePegawaiMouseClicked

    private void ComboPangkatGolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPangkatGolActionPerformed
        comboPangkatGolIndex = ComboPangkatGol.getSelectedIndex();
        if (comboPangkatGolIndex >= 0) {
            selectedComboPangkatGolIndex = comboPangkatGolID.get(comboPangkatGolIndex);
        }
    }//GEN-LAST:event_ComboPangkatGolActionPerformed

    private void ComboPangkatGolItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPangkatGolItemStateChanged
        
    }//GEN-LAST:event_ComboPangkatGolItemStateChanged

    private void TextCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCariActionPerformed
        if (TextCari.getText().equals("")) {
            loadTable();
        } else {
            loadTable(TextCari.getText());            
        }
    }//GEN-LAST:event_TextCariActionPerformed

    private void SpinnerGajiPokokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpinnerGajiPokokKeyReleased
        
    }//GEN-LAST:event_SpinnerGajiPokokKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonResetHapus;
    private javax.swing.JButton ButtonTambahUbah;
    private javax.swing.JComboBox<String> ComboPangkatGol;
    private javax.swing.JLabel LabelCari;
    private javax.swing.JLabel LabelGajiPokok;
    private javax.swing.JLabel LabelNama;
    private javax.swing.JLabel LabelNip;
    private javax.swing.JLabel LabelPangkatGol;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JSpinner SpinnerGajiPokok;
    private javax.swing.JTable TablePegawai;
    private javax.swing.JTextField TextCari;
    private javax.swing.JTextField TextNama;
    private javax.swing.JTextField TextNip;
    // End of variables declaration//GEN-END:variables
}
