/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Helpers;

import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class ADHhelper {
    public static void d(String string, boolean pembatas) {
        if (!pembatas) {
            out.println(string);
        } else {
            d(string);
        }
    }
    
    public static void d(String string) {
        out.println("===========================================");
        out.println(string);
        out.println("===========================================");
    }
    
    public static String parseTanggalIndoString(Date tanggalParam) {
        SimpleDateFormat formatHari =new SimpleDateFormat("dd");
        SimpleDateFormat formatTahun =new SimpleDateFormat("yyyy");
        SimpleDateFormat formatBulan =new SimpleDateFormat("MM");

        String hari = formatHari.format(tanggalParam); 
        String bulan = ADHhelper.bulan(Integer.parseInt(formatBulan.format(tanggalParam)));
        String tahun = formatTahun.format(tanggalParam);
        String tanggal = hari + " " + bulan + " " + tahun;
        
        return tanggal;
    }
    
    public static String bulan(int i) {
        switch(i) {
            case 1:
              return "Januari";
            case 2:
              return "Februari";
            case 3:
              return "Maret";
            case 4:
              return "April";
            case 5:
              return "Mei";
            case 6:
              return "Juni";
            case 7:
              return "Juli";
            case 8:
              return "Agustus";
            case 9:
              return "September";
            case 10:
              return "Oktober";
            case 11:
              return "November";
            case 12:
              return "Desember";
            default:
              return "ERROR !!!";
          }
    }
}
