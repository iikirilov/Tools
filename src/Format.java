/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivaylo
 */
public class Format {

    public static String formatPrice(String toConvert) {
        int index = toConvert.indexOf('.');
        if (index == -1) {
            return toConvert + ".00";
        } else {
            if (index == toConvert.length() - 2) {
                return toConvert + "0";
            } else if (index < toConvert.length() - 3) {
                return toConvert.substring(0, index + 3);
            } else {
                return toConvert;
            }
        }
    }

}
