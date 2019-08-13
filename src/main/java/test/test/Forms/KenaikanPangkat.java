/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Forms;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.LazyList;
import test.test.Classes.MendekatiTMT;
import test.test.Models.KenaikanPangkatModel;
import test.test.Models.PangkatGolModel;
import test.test.Models.PegawaiModel;
import test.test.Models.UsulanModel;
import test.test.Reports.Config;

/**
 *
 * @author user
 */
public class KenaikanPangkat extends javax.swing.JFrame {
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
     * Creates new form PangkatGol
     */
    public KenaikanPangkat() {
        initComponents();
        
        String path = "src/main/resources/assets/logometro.png";
        ImageIcon MyImage = new ImageIcon(path);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        Logo.setIcon(image);
        
        loadTable();
        
        loadComboBox();
        
        ComboPegawai.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                comboPegawaiIndex = ComboPegawai.getSelectedIndex();
                if (comboPegawaiIndex >= 0) {
                    selectedComboPegawaiIndex = comboPegawaiID.get(comboPegawaiIndex);

                    Base.open();
                    PegawaiModel p = PegawaiModel.findById(selectedComboPegawaiIndex);
                    Base.close();

                    int pangkatLamaIndex = comboPangkatGolBaruID.indexOf(Integer.parseInt(p.get("id_pangkatgol").toString()));
                    ComboPangkatGolLama.setSelectedIndex(pangkatLamaIndex);
                }
            }
        });
        
        TMT.addPropertyChangeListener("date",new PropertyChangeListener  () { 
            public void propertyChange(PropertyChangeEvent e){
                JDateChooser chooser=(JDateChooser)e.getSource();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                if (chooser.getCalendar() != null) {
                    Calendar calendar = chooser.getCalendar();
                    calendar.add(Calendar.YEAR, 2);
                    YAD.setText(sdf.format(calendar.getTime()));
                }
            }
        });
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    public void loadComboBox() {
        ComboPangkatGolBaru.removeAllItems();
        ComboPangkatGolLama.removeAllItems();
        ComboPegawai.removeAllItems();
        
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
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            try {
                Date tmt = format.parse(kenaikanPangkat.getString("tmt"));
                Date yad = format.parse(kenaikanPangkat.getString("yad"));
                
                SimpleDateFormat parsedFormat = new SimpleDateFormat("dd-MM-YYYY");
                String parsedtmt = parsedFormat.format(tmt);
                String parsedyad = parsedFormat.format(yad);
                
                model.addRow(new Object[]{
                    kenaikanPangkat.getId(),
                    pegawai.getString("nip"),
                    pegawai.getString("nama"),
                    PangkatGolLama.getString("pangkatgol"),
                    PangkatGolBaru.getString("pangkatgol"),
                    parsedtmt,
                    parsedyad,
                });                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
        Base.close();
        
        TableKenaikanPangkat.setModel(model);
        
        MendekatiTMT colorRenderer = new MendekatiTMT(kenaikanPangkats, "kenaikanPangkat");
        TableKenaikanPangkat.setDefaultRenderer(Object.class, colorRenderer);
        
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
    
    private void tambahData() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat parsedFormat = new SimpleDateFormat("yyyy-MM-dd");
        Base.open();
        try {
            KenaikanPangkatModel kenaikanPangkat = new KenaikanPangkatModel();
            kenaikanPangkat.set("tmt", dateFormat.format(TMT.getDate()));
            kenaikanPangkat.set("yad", parsedFormat.format(format.parse(YAD.getText())));
            kenaikanPangkat.set("id_pegawai", selectedComboPegawaiIndex);
            kenaikanPangkat.set("id_pangkat_lama", selectedComboPangkatGolLamaIndex);
            kenaikanPangkat.set("id_pangkat_baru", selectedComboPangkatGolBaruIndex);
            kenaikanPangkat.save();
            
            PegawaiModel pegawai = kenaikanPangkat.parent(PegawaiModel.class);
            pegawai.set("id_pangkatgol", selectedComboPangkatGolBaruIndex);
            pegawai.set("tmt_pangkat", dateFormat.format(TMT.getDate()));
            pegawai.set("yad_pangkat", parsedFormat.format(format.parse(YAD.getText())));
            pegawai.save();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        Base.close();
    }
    
    private void ubahData(String id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat parsedFormat = new SimpleDateFormat("yyyy-MM-dd");
        Base.open();
        try {
            KenaikanPangkatModel kenaikanPangkat = KenaikanPangkatModel.findById(ID);
            kenaikanPangkat.set("tmt", dateFormat.format(TMT.getDate()));
            kenaikanPangkat.set("yad", parsedFormat.format(format.parse(YAD.getText())));
            kenaikanPangkat.set("id_pegawai", selectedComboPegawaiIndex);
            kenaikanPangkat.set("id_pangkat_lama", selectedComboPangkatGolLamaIndex);
            kenaikanPangkat.set("id_pangkat_baru", selectedComboPangkatGolBaruIndex);
            kenaikanPangkat.save();
            
//            PegawaiModel pegawai = kenaikanPangkat.parent(PegawaiModel.class);
//            pegawai.set("id_pangkatgol", selectedComboPangkatGolBaruIndex);
//            pegawai.save();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        Base.close();
    }

    private void resetForm() {
        ComboPegawai.setSelectedIndex(0);
        ComboPangkatGolLama.setSelectedIndex(0);
        ComboPangkatGolBaru.setSelectedIndex(0);
        TMT.setDate(null);
        YAD.setText("");
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
        LabelPangkatGol = new javax.swing.JLabel();
        ComboPangkatGolBaru = new javax.swing.JComboBox<>();
        LabelPangkatGol1 = new javax.swing.JLabel();
        LabelPangkatGol2 = new javax.swing.JLabel();
        ComboPangkatGolLama = new javax.swing.JComboBox<>();
        LabelPangkatGol3 = new javax.swing.JLabel();
        TMT = new com.toedter.calendar.JDateChooser();
        LabelPangkatGol4 = new javax.swing.JLabel();
        YAD = new javax.swing.JTextField();
        ComboPegawai = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ButtonHome = new javax.swing.JButton();
        ButtonRefresh = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        TableKenaikanPangkat = new javax.swing.JTable();
        ButtonTambahUbah = new javax.swing.JButton();
        ButtonResetHapus = new javax.swing.JButton();
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
        jLabel5.setText("UPDATE KENAIKAN PANGKAT");

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

        TMT.setDateFormatString("dd-MM-yyyy");

        LabelPangkatGol4.setText("YAD");

        YAD.setEditable(false);

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
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol)
                                .addGap(18, 18, 18)
                                .addComponent(ComboPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol2)
                                .addGap(18, 18, 18)
                                .addComponent(ComboPangkatGolLama, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol3)
                                .addGap(18, 18, 18)
                                .addComponent(TMT, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol1)
                                .addGap(18, 18, 18)
                                .addComponent(ComboPangkatGolBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelPangkatGol4)
                                .addGap(18, 18, 18)
                                .addComponent(YAD, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPangkatGolLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboPangkatGolBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPangkatGol1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol3)
                    .addComponent(TMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelPangkatGol4)
                    .addComponent(YAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addGap(283, 283, 283)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(164, 164, 164)
                                .addComponent(ButtonHome))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ButtonCetak)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(530, 530, 530)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonResetHapus)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(169, 169, 169)
                                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                        .addComponent(jLabel1))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ButtonTambahUbah)
                                .addGap(40, 40, 40)
                                .addComponent(ButtonRefresh)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonHome)
                    .addComponent(jLabel4))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonResetHapus)
                    .addComponent(ButtonTambahUbah)
                    .addComponent(ButtonRefresh)
                    .addComponent(ButtonCetak))
                .addGap(53, 53, 53)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRefreshActionPerformed
        resetForm();
        loadTable();
    }//GEN-LAST:event_ButtonRefreshActionPerformed

    private void ButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHomeActionPerformed
        new MenuUtama().setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_ButtonHomeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new MenuUtama().setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void TableKenaikanPangkatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableKenaikanPangkatMouseClicked
        int i =TableKenaikanPangkat.getSelectedRow();
        if(i>=0){
            ID = model.getValueAt(i, 0).toString();

            Base.open();
            KenaikanPangkatModel kenaikanPangkat = KenaikanPangkatModel.findById(ID);
            Base.close();

            ComboPegawai.setSelectedIndex(comboPegawaiID.indexOf(Integer.parseInt(kenaikanPangkat.getString("id_pegawai"))));
            ComboPangkatGolLama.setSelectedIndex(comboPangkatGolLamaID.indexOf(Integer.parseInt(kenaikanPangkat.getString("id_pangkat_lama"))));
            ComboPangkatGolBaru.setSelectedIndex(comboPangkatGolBaruID.indexOf(Integer.parseInt(kenaikanPangkat.getString("id_pangkat_baru"))));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                TMT.setDate(format.parse(kenaikanPangkat.getString("tmt")));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

            setState("edit");
        }
    }//GEN-LAST:event_TableKenaikanPangkatMouseClicked

    private void ComboPangkatGolBaruItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPangkatGolBaruItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboPangkatGolBaruItemStateChanged

    private void ComboPangkatGolBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPangkatGolBaruActionPerformed
        comboPangkatGolBaruIndex = ComboPangkatGolBaru.getSelectedIndex();
        if (comboPangkatGolBaruIndex >= 0) {
            selectedComboPangkatGolBaruIndex = comboPangkatGolBaruID.get(comboPangkatGolBaruIndex);
        }
    }//GEN-LAST:event_ComboPangkatGolBaruActionPerformed

    private void ComboPangkatGolLamaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPangkatGolLamaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboPangkatGolLamaItemStateChanged

    private void ComboPangkatGolLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPangkatGolLamaActionPerformed
        comboPangkatGolLamaIndex = ComboPangkatGolLama.getSelectedIndex();
        if (comboPangkatGolLamaIndex >= 0) {
            selectedComboPangkatGolLamaIndex = comboPangkatGolLamaID.get(comboPangkatGolLamaIndex);
        }
    }//GEN-LAST:event_ComboPangkatGolLamaActionPerformed

    private void ComboPegawaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPegawaiItemStateChanged

    }//GEN-LAST:event_ComboPegawaiItemStateChanged

    private void ComboPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPegawaiActionPerformed
        comboPegawaiIndex = ComboPegawai.getSelectedIndex();
        if (comboPegawaiIndex >= 0) {
            selectedComboPegawaiIndex = comboPegawaiID.get(comboPegawaiIndex);
        }
    }//GEN-LAST:event_ComboPegawaiActionPerformed

    private void ButtonTambahUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTambahUbahActionPerformed
        if (state.equals("index")) {
            if (TMT.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Form TMT Masih Kosong !!!");
            } else {
                tambahData();
                resetForm();
                loadTable();
            }
        } else {
            if (TMT.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Form TMT Masih Kosong !!!");
            } else {
                ubahData(ID);
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

    private void ButtonCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCetakActionPerformed
        try{
            Config objkoneksi = new Config();
            Connection con = objkoneksi.bukakoneksi();
            String fileName="src/main/java/test/test/Reports/kenaikan_pangkat.jrxml";
            String filetoFill="src/main/java/test/test/Reports/kenaikan_pangkat.jasper";
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
            java.util.logging.Logger.getLogger(KenaikanPangkat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KenaikanPangkat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KenaikanPangkat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KenaikanPangkat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KenaikanPangkat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCetak;
    private javax.swing.JButton ButtonHome;
    private javax.swing.JButton ButtonRefresh;
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
    private javax.swing.JLabel Logo;
    private javax.swing.JScrollPane ScrollPane;
    private com.toedter.calendar.JDateChooser TMT;
    private javax.swing.JTable TableKenaikanPangkat;
    private javax.swing.JTextField YAD;
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
