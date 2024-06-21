package com.example.helpers;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatHelper {

    public String convertToRupiah(int value) {
        return "Rp" + convertToCurrency(value);
    }
    
    public String convertToCurrency(int value) {
        return String.valueOf(this.currencyFormatter("id", "ID").format(value));
    }
    
    @SuppressWarnings("deprecation")
    public NumberFormat currencyFormatter(String language, String country) {
        Locale country_local = new Locale(language, country);
        
        return NumberFormat.getInstance(country_local);
    }
}
