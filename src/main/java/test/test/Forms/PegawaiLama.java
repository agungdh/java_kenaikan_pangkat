/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;
import test.test.Models.PegawaiModel;
import test.test.Models.PangkatGolModel;
import javax.swing.text.NumberFormatter;
import test.test.Classes.MendekatiTMT;

/**
 *
 * @author user
 */
public class PegawaiLama extends javax.swing.JInternalFrame {
    private List<Integer> comboPangkatGolID = new ArrayList<Integer>();
    private int comboPangkatGolIndex;
    private int selectedComboPangkatGolIndex;
    private DefaultTableModel model = new DefaultTableModel();
    private String ID;
    private String state;
    
    /**
     * Creates new form Orang
     */
    public PegawaiLama() {
        initComponents();
        
        loadTable();
        
        loadPangkatGol();
        
        formatTextInteger();
        
        TMTPangkat.addPropertyChangeListener("date",new PropertyChangeListener  () { 
            public void propertyChange(PropertyChangeEvent e){
                JDateChooser chooser=(JDateChooser)e.getSource();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                if (chooser.getCalendar() != null) {
                    Calendar calendar = chooser.getCalendar();
                    calendar.add(Calendar.YEAR, 2);
                    YADPangkat.setText(sdf.format(calendar.getTime()));
                }
            }
        });

        TMTGaji.addPropertyChangeListener("date",new PropertyChangeListener  () { 
            public void propertyChange(PropertyChangeEvent e){
                JDateChooser chooser=(JDateChooser)e.getSource();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                if (chooser.getCalendar() != null) {
                    Calendar calendar = chooser.getCalendar();
                    calendar.add(Calendar.YEAR, 4);
                    YADGaji.setText(sdf.format(calendar.getTime()));
                }
            }
        });
        
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
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        model.addColumn("#ID");
        model.addColumn("NIP");
        model.addColumn("Nama");
        model.addColumn("Pangkat Golongan");
        model.addColumn("TMT Pangkat");
        model.addColumn("YAD Pangkat");
        model.addColumn("TMT Gaji");
        model.addColumn("YAD Gaji");
        
        Base.open();
        try {

            for(PegawaiModel pegawai : pegawais) {
                Date tmtpangkat = format.parse(pegawai.getString("tmt_pangkat"));
                Date yadpangkat = format.parse(pegawai.getString("yad_pangkat"));
                Date tmtgaji = format.parse(pegawai.getString("tmt_gaji"));
                Date yadgaji = format.parse(pegawai.getString("yad_gaji"));
                
                SimpleDateFormat parsedFormat = new SimpleDateFormat("dd-MM-YYYY");
                
                String parsedtmtpangkat = parsedFormat.format(tmtpangkat);    
                String parsedyadpangkat = parsedFormat.format(yadpangkat);    
                String parsedtmtgaji = parsedFormat.format(tmtgaji);    
                String parsedyadgaji = parsedFormat.format(yadgaji);    
                
                PangkatGolModel pangkatGol = pegawai.parent(PangkatGolModel.class);
                model.addRow(new Object[]{pegawai.getId(), pegawai.getString("nip"), pegawai.getString("nama"), pangkatGol.getString("pangkatgol"), parsedtmtpangkat, parsedyadpangkat,parsedtmtgaji, parsedyadgaji});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        Base.close();
        
        TablePegawai.setModel(model);
        
        MendekatiTMT colorRenderer = new MendekatiTMT(pegawais, "pegawai");
        TablePegawai.setDefaultRenderer(Object.class, colorRenderer);
        
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
    
    private void tambahData(String nip, String nama) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat parsedFormat = new SimpleDateFormat("yyyy-MM-dd");
        Base.open();
        try {
            PegawaiModel pegawai = new PegawaiModel();
            pegawai.set("nip", nip);
            pegawai.set("nama", nama);
            pegawai.set("id_pangkatgol", selectedComboPangkatGolIndex);
            pegawai.set("tmt_pangkat", dateFormat.format(TMTPangkat.getDate()));
            pegawai.set("yad_pangkat", parsedFormat.format(format.parse(YADPangkat.getText())));
            pegawai.set("tmt_gaji", dateFormat.format(TMTGaji.getDate()));
            pegawai.set("yad_gaji", parsedFormat.format(format.parse(YADGaji.getText())));
            pegawai.save();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        Base.close();
    }
    
    private void ubahData(String id, String nip, String nama) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat parsedFormat = new SimpleDateFormat("yyyy-MM-dd");
        Base.open();
        try {
            PegawaiModel pegawai = PegawaiModel.findById(id);
            pegawai.set("nip", nip);
            pegawai.set("nama", nama);
            pegawai.set("id_pangkatgol", selectedComboPangkatGolIndex);
            pegawai.set("tmt_pangkat", dateFormat.format(TMTPangkat.getDate()));
            pegawai.set("yad_pangkat", parsedFormat.format(format.parse(YADPangkat.getText())));
            pegawai.set("tmt_gaji", dateFormat.format(TMTGaji.getDate()));
            pegawai.set("yad_gaji", parsedFormat.format(format.parse(YADGaji.getText())));
            pegawai.save();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        Base.close();
    }

    private void resetForm() {
        TextNip.setText("");
        TextNama.setText("");
        TMTPangkat.setDate(null);
        YADPangkat.setText("");
        TMTGaji.setDate(null);
        YADGaji.setText("");
        ComboPangkatGol.setSelectedIndex(0);
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
        TMTPangkat = new com.toedter.calendar.JDateChooser();
        LabelPangkatGol3 = new javax.swing.JLabel();
        LabelPangkatGol4 = new javax.swing.JLabel();
        TMTGaji = new com.toedter.calendar.JDateChooser();
        LabelPangkatGol5 = new javax.swing.JLabel();
        LabelPangkatGol6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        YADGaji = new javax.swing.JTextField();
        YADPangkat = new javax.swing.JTextField();

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

        TMTPangkat.setDateFormatString("dd-MM-yyyy");
        TMTPangkat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TMTPangkatMouseReleased(evt);
            }
        });

        LabelPangkatGol3.setText("TMT");

        LabelPangkatGol4.setText("YAD");

        TMTGaji.setDateFormatString("dd-MM-yyyy");

        LabelPangkatGol5.setText("TMT");

        LabelPangkatGol6.setText("YAD");

        jLabel1.setText("Gaji");

        YADGaji.setEditable(false);

        YADPangkat.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(LabelNama)
                                            .addComponent(LabelPangkatGol)
                                            .addComponent(LabelNip))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextNip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(ComboPangkatGol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(TextNama, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(LabelPangkatGol4)
                                        .addGap(18, 18, 18)
                                        .addComponent(YADPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(LabelPangkatGol3)
                                        .addGap(18, 18, 18)
                                        .addComponent(TMTPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(95, 95, 95)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(LabelPangkatGol5)
                                                .addGap(18, 18, 18)
                                                .addComponent(TMTGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(LabelPangkatGol6)
                                                .addGap(18, 18, 18)
                                                .addComponent(YADGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(213, 213, 213))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(107, 107, 107)
                                        .addComponent(ButtonTambahUbah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ButtonResetHapus)))
                                .addGap(0, 37, Short.MAX_VALUE))
                            .addComponent(ScrollPane))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(LabelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TextCari, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelPangkatGol3)
                            .addComponent(TMTPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelPangkatGol4)
                            .addComponent(YADPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelPangkatGol5)
                            .addComponent(TMTGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol6)
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ButtonTambahUbah)
                                    .addComponent(ButtonResetHapus)))
                            .addComponent(YADGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            } else if (TMTPangkat.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Form TMT Pangkat Masih Kosong !!!");
            } else if (TMTGaji.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Form TMT Gaji Masih Kosong !!!");
            } else {
                tambahData(TextNip.getText(), TextNama.getText());
                resetForm();
                loadTable();
            }
        } else {
            if (TextNip.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form NIP Masih Kosong !!!");
            } else if (TextNama.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Form Nama Masih Kosong !!!");
            } else if (TMTPangkat.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Form TMT Pangkat Masih Kosong !!!");
            } else if (TMTGaji.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Form TMT Gaji Masih Kosong !!!");
            } else {
                ubahData(ID, TextNip.getText(), TextNama.getText());
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            try {
                TMTPangkat.setDate(format.parse(pegawai.getString("tmt_pangkat")));
                TMTGaji.setDate(format.parse(pegawai.getString("tmt_gaji")));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            
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

    private void TMTPangkatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TMTPangkatMouseReleased
        
    }//GEN-LAST:event_TMTPangkatMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonResetHapus;
    private javax.swing.JButton ButtonTambahUbah;
    private javax.swing.JComboBox<String> ComboPangkatGol;
    private javax.swing.JLabel LabelCari;
    private javax.swing.JLabel LabelNama;
    private javax.swing.JLabel LabelNip;
    private javax.swing.JLabel LabelPangkatGol;
    private javax.swing.JLabel LabelPangkatGol3;
    private javax.swing.JLabel LabelPangkatGol4;
    private javax.swing.JLabel LabelPangkatGol5;
    private javax.swing.JLabel LabelPangkatGol6;
    private javax.swing.JScrollPane ScrollPane;
    private com.toedter.calendar.JDateChooser TMTGaji;
    private com.toedter.calendar.JDateChooser TMTPangkat;
    private javax.swing.JTable TablePegawai;
    private javax.swing.JTextField TextCari;
    private javax.swing.JTextField TextNama;
    private javax.swing.JTextField TextNip;
    private javax.swing.JTextField YADGaji;
    private javax.swing.JTextField YADPangkat;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}