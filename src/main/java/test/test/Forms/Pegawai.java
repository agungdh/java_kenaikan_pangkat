/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;
import test.test.Classes.MendekatiTMT;
import test.test.Models.PangkatGolModel;
import test.test.Models.PegawaiModel;
import test.test.Reports.Config;

/**
 *
 * @author user
 */
public class Pegawai extends javax.swing.JFrame {
    private List<Integer> comboPangkatGolID = new ArrayList<Integer>();
    private int comboPangkatGolIndex;
    private int selectedComboPangkatGolIndex;
    private DefaultTableModel model = new DefaultTableModel();
    private String ID;
    private String state;
    /**
     * Creates new form PangkatGol
     */
    public Pegawai() {
        initComponents();
        
        String path = "src/main/resources/assets/logometro.png";
        ImageIcon MyImage = new ImageIcon(path);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        Logo.setIcon(image);
        
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
        
        TextCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                cariNIK();
            }

            public void removeUpdate(DocumentEvent e) {
                cariNIK();
            }

            public void changedUpdate(DocumentEvent e) {
                cariNIK();
            }
        });
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    public void cariNIK() {
        if (TextCari.getText().equals("")) {
            loadTable();
        } else {
            loadTable(TextCari.getText());
        }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        TMTPangkat = new com.toedter.calendar.JDateChooser();
        ComboPangkatGol = new javax.swing.JComboBox<>();
        TextNama = new javax.swing.JTextField();
        TextNip = new javax.swing.JTextField();
        YADPangkat = new javax.swing.JTextField();
        LabelPangkatGol4 = new javax.swing.JLabel();
        LabelPangkatGol3 = new javax.swing.JLabel();
        LabelPangkatGol = new javax.swing.JLabel();
        LabelNama = new javax.swing.JLabel();
        LabelNip = new javax.swing.JLabel();
        LabelPangkatGol5 = new javax.swing.JLabel();
        YADGaji = new javax.swing.JTextField();
        TMTGaji = new com.toedter.calendar.JDateChooser();
        LabelPangkatGol6 = new javax.swing.JLabel();
        LabelPangkatGol7 = new javax.swing.JLabel();
        LabelPangkatGol8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ButtonHome = new javax.swing.JButton();
        ButtonRefresh = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        TablePegawai = new javax.swing.JTable();
        ButtonTambahUbah = new javax.swing.JButton();
        ButtonResetHapus = new javax.swing.JButton();
        TextCari = new javax.swing.JTextField();
        LabelCari = new javax.swing.JLabel();
        ButtonCetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("PEMERINTAH KOTA METRO");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("DINAS TENAGA KERJA DAN TRANSMIGRASI KOTA METRO");

        jLabel3.setText("Alamat : Jl. KH. Dewantara No 155 Kota Metro");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setText("APLIKASI KENAIKAN PANGKAT DAN GAJI BERKALA");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setText("INPUT DATA PEGAWAI");

        TMTPangkat.setDateFormatString("dd-MM-yyyy");
        TMTPangkat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TMTPangkatMouseReleased(evt);
            }
        });

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

        YADPangkat.setEditable(false);

        LabelPangkatGol4.setText("YAD");

        LabelPangkatGol3.setText("TMT");

        LabelPangkatGol.setText("Pangkat Golongan");

        LabelNama.setText("Nama");

        LabelNip.setText("NIP");

        LabelPangkatGol5.setText("TMT");

        YADGaji.setEditable(false);

        TMTGaji.setDateFormatString("dd-MM-yyyy");

        LabelPangkatGol6.setText("YAD");

        LabelPangkatGol7.setText("Pangkat");

        LabelPangkatGol8.setText("Gaji");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(LabelPangkatGol5)
                                        .addGap(18, 18, 18)
                                        .addComponent(TMTGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(LabelPangkatGol6)
                                        .addGap(18, 18, 18)
                                        .addComponent(YADGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(LabelPangkatGol4)
                                        .addGap(18, 18, 18)
                                        .addComponent(YADPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(LabelPangkatGol3)
                                        .addGap(18, 18, 18)
                                        .addComponent(TMTPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGap(119, 119, 119)
                                    .addComponent(LabelPangkatGol8)
                                    .addGap(86, 86, 86)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelNama)
                                    .addComponent(LabelPangkatGol)
                                    .addComponent(LabelNip))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextNip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ComboPangkatGol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(TextNama, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LabelPangkatGol7)
                .addGap(120, 120, 120))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPangkatGol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelPangkatGol7)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol3)
                    .addComponent(TMTPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol4)
                    .addComponent(YADPangkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelPangkatGol8)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol5)
                    .addComponent(TMTGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol6)
                    .addComponent(YADGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("D-III SISTEM INFORMASI UNIVERSITAS MUHAMMADIYAH METRO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6))
        );

        ButtonHome.setText("Home");
        ButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonHomeActionPerformed(evt);
            }
        });

        ButtonRefresh.setText("Refresh");
        ButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRefreshActionPerformed(evt);
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

        TextCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextCariActionPerformed(evt);
            }
        });

        LabelCari.setText("Cari (NIP)");

        ButtonCetak.setText("Cetak");
        ButtonCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(652, 652, 652)
                                    .addComponent(LabelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(TextCari, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(ButtonTambahUbah)
                                            .addGap(29, 29, 29)
                                            .addComponent(ButtonRefresh)
                                            .addGap(28, 28, 28)
                                            .addComponent(ButtonResetHapus)
                                            .addGap(31, 31, 31)
                                            .addComponent(ButtonCetak))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(26, 26, 26)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ButtonHome)
                                            .addGap(19, 19, 19))
                                        .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(107, 107, 107)
                                        .addComponent(jLabel1)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonHome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelCari))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonRefresh)
                    .addComponent(ButtonTambahUbah)
                    .addComponent(ButtonResetHapus)
                    .addComponent(ButtonCetak))
                .addGap(85, 85, 85)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRefreshActionPerformed
        resetForm();
        loadTable();
        TextCari.setText("");
    }//GEN-LAST:event_ButtonRefreshActionPerformed

    private void ButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHomeActionPerformed
        new MenuUtama().setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_ButtonHomeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new MenuUtama().setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

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

    private void TMTPangkatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TMTPangkatMouseReleased

    }//GEN-LAST:event_TMTPangkatMouseReleased

    private void ComboPangkatGolItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPangkatGolItemStateChanged

    }//GEN-LAST:event_ComboPangkatGolItemStateChanged

    private void ComboPangkatGolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPangkatGolActionPerformed
        comboPangkatGolIndex = ComboPangkatGol.getSelectedIndex();
        if (comboPangkatGolIndex >= 0) {
            selectedComboPangkatGolIndex = comboPangkatGolID.get(comboPangkatGolIndex);
        }
    }//GEN-LAST:event_ComboPangkatGolActionPerformed

    private void TextCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCariActionPerformed
        cariNIK();
    }//GEN-LAST:event_TextCariActionPerformed

    private void ButtonCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCetakActionPerformed
        try{
            Config objkoneksi = new Config();
            Connection con = objkoneksi.bukakoneksi();
            String fileName="src/main/java/test/test/Reports/pegawai.jrxml";
            String filetoFill="src/main/java/test/test/Reports/pegawai.jasper";
            JasperCompileManager.compileReport(fileName);
            Map param= new HashMap();
            JasperFillManager.fillReport(filetoFill, param, con);
            JasperPrint jp=JasperFillManager.fillReport(filetoFill, param,con);
            JasperViewer.viewReport(jp,false);

        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_ButtonCetakActionPerformed

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
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCetak;
    private javax.swing.JButton ButtonHome;
    private javax.swing.JButton ButtonRefresh;
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
    private javax.swing.JLabel LabelPangkatGol7;
    private javax.swing.JLabel LabelPangkatGol8;
    private javax.swing.JLabel Logo;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
