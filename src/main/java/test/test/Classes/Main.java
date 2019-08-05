/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Classes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Util;
import test.test.Forms.Form;
/**
 *
 * @author user
 */
public class Main {
    public static void main(String args[]) {
        Base.open();
        List<Map> results = new DB().all("SELECT DISTINCT(id_pegawai) FROM kenaikan_pangkat");
        Base.close();
        
        String query = "SELECT * FROM kenaikan_pangkat WHERE id_pegawai IN (";
        List<String> aidi = new ArrayList<String>();
        for(Map result : results) {
            aidi.add(result.get("id_pegawai").toString());
        }
        query += String.join(",", aidi);
        query += ")";
        System.out.println(query);
//        new Form().setVisible(true);
    }
}
