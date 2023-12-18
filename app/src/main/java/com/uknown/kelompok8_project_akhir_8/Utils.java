package com.uknown.kelompok8_project_akhir_8;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static String toRupiah (double value){
        NumberFormat curency = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        return curency.format(value).replace(",00","");
    }

}
