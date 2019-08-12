/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Classes;

/**
 *
 * @author user
 */
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.joda.time.DateTime;
import org.joda.time.Months;
import test.test.Models.GajiBerkalaModel;
import test.test.Models.KenaikanPangkatModel;
import test.test.Models.PegawaiModel;

/**
 * @author ashraf_sarhan
 *
 */
public class MendekatiTMT implements TableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    public Object mdl;
    public String tipe;

    public MendekatiTMT(Object mdl, String tipe) {
        this.mdl = mdl;
        this.tipe = tipe;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);

        int i = 0;
        List<Integer> yangHarusDimerahin = new ArrayList<Integer>();
        List<Map> results;
        List<Map> dummyData;
        List<String> IDCheck = new ArrayList<String>();

        if (this.tipe == "pegawai") {
//                Gaji Berkala
            Base.open();
            results = new DB().all("SELECT DISTINCT(id_pegawai) FROM gaji_berkala");
            Base.close();

            for (Map result : results) {
                Base.open();
                dummyData = new DB().all("SELECT * FROM gaji_berkala WHERE id_pegawai = ? ORDER BY yad desc LIMIT 1", result.get("id_pegawai"));
                Base.close();
                IDCheck.add(dummyData.get(0).get("id_pegawai").toString());
            }

            for (PegawaiModel data : (LazyList<PegawaiModel>) this.mdl) {
                if (IDCheck.contains(data.getString("id")) && cekDeadline(data.getString("yad_gaji"))) {
                    yangHarusDimerahin.add(i);
                }

                i++;
            }

            i = 0;
            
//                Kenaikan Pangkat
            Base.open();
            results = new DB().all("SELECT DISTINCT(id_pegawai) FROM kenaikan_pangkat");
            Base.close();

            for (Map result : results) {
                Base.open();
                dummyData = new DB().all("SELECT * FROM kenaikan_pangkat WHERE id_pegawai = ? ORDER BY yad desc LIMIT 1", result.get("id_pegawai"));
                Base.close();
                IDCheck.add(dummyData.get(0).get("id_pegawai").toString());
            }

            for (PegawaiModel data : (LazyList<PegawaiModel>) this.mdl) {
                if (IDCheck.contains(data.getString("id")) && cekDeadline(data.getString("yad_pangkat"))) {
                    yangHarusDimerahin.add(i);
                }

                i++;
            }

            if (yangHarusDimerahin.contains(row)) {
                c.setBackground(Color.RED);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
            
//            System.out.println(yangHarusDimerahin.toString());
        } else {
            switch (tipe) {
                case "kenaikanPangkat":
                    Base.open();
                    results = new DB().all("SELECT DISTINCT(id_pegawai) FROM kenaikan_pangkat");
                    Base.close();

                    for (Map result : results) {
                        Base.open();
                        dummyData = new DB().all("SELECT * FROM kenaikan_pangkat WHERE id_pegawai = ? ORDER BY yad desc LIMIT 1", result.get("id_pegawai"));
                        Base.close();
                        IDCheck.add(dummyData.get(0).get("id").toString());
                    }

                    for (KenaikanPangkatModel data : (LazyList<KenaikanPangkatModel>) this.mdl) {
                        if (IDCheck.contains(data.getString("id")) && cekDeadline(data.getString("yad"))) {
                            yangHarusDimerahin.add(i);
                        }

                        i++;
                    }

                    if (yangHarusDimerahin.contains(row)) {
                        c.setBackground(Color.RED);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                    break;
                case "gajiBerkala":
                    Base.open();
                    results = new DB().all("SELECT DISTINCT(id_pegawai) FROM gaji_berkala");
                    Base.close();

                    for (Map result : results) {
                        Base.open();
                        dummyData = new DB().all("SELECT * FROM gaji_berkala WHERE id_pegawai = ? ORDER BY yad desc LIMIT 1", result.get("id_pegawai"));
                        Base.close();
                        IDCheck.add(dummyData.get(0).get("id").toString());
                    }

                    for (GajiBerkalaModel data : (LazyList<GajiBerkalaModel>) this.mdl) {
                        if (IDCheck.contains(data.getString("id")) && cekDeadline(data.getString("yad"))) {
                            yangHarusDimerahin.add(i);
                        }

                        i++;
                    }

                    if (yangHarusDimerahin.contains(row)) {
                        c.setBackground(Color.RED);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                    break;

                default:
                    break;
            }
        }

        return c;
    }

    public boolean cekDeadline(String dateStop) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

        Date d1 = null;
        Date d2 = null;

        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            d1 = cal.getTime();
            d2 = format.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            if (Months.monthsBetween(dt1, dt2).getMonths() <= 3) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
