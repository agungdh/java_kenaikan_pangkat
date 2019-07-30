/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test.Classes;

import org.javalite.activejdbc.LazyList;
import test.test.Helpers.ADHhelper;
import test.test.Models.BarangModel;
import test.test.Models.OrangModel;

/**
 *
 * @author user
 */
public class Temp {
    public static void test(){
        OrangModel o = OrangModel.findById(2);
        ADHhelper.d(o.toJson(true));
        
        LazyList<BarangModel> b = o.getAll(BarangModel.class);
        ADHhelper.d(b.toJson(true));
        
        BarangModel b2 = BarangModel.findById(1);
        test.test.Models.OrangModel o2 = b2.parent(test.test.Models.OrangModel.class);
        ADHhelper.d(o2.toJson(true));
    }
}
