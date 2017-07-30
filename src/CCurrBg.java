
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ivaylo
 */
public class CCurrBg {

    private static final String[] units = {"НУЛА ", "ЕДИН ", "ДВА ", "ТРИ ", "ЧЕТИРИ ",
        "ПЕТ ", "ШЕСТ ", "СЕДЕМ ", "ОСЕМ ", "ДЕВЕТ ",
        "ДЕСЕТ ", "ЕДИНАДЕСЕТ ", "ДВАНАДЕСЕТ ", "ТРИНАДЕСЕТ ", "ЧЕТИРИНАДЕСЕТ ",
        "ПЕТНАДЕСЕТ ", "ШЕСТНАДЕСЕТ ", "СЕДЕМНАДЕСЕТ ", "ОСЕМНАДЕСЕТ ", "ДЕВЕТНАДЕСЕТ "};
    private static final String[] tens = {"", "", "ДВАДЕСЕТ ", "ТРИДЕСЕТ ", "ЧЕТИРИДЕСЕТ ",
        "ПЕТДЕСЕТ ", "ШЕСТДЕСЕТ ", "СЕДЕМДЕСЕТ ", "ОСЕМДЕСЕТ ", "ДЕВЕТДЕСЕТ "};
    private static final String[] hund = {"", "CTO ", "ДВЕСТА ", "ТРИСТА ", "ЧЕТИРИСТОТИН ",
        "ПЕТСТОТИН ", "ШЕСТСТОТИН ", "СЕДЕМСТОТИН ", "ОСЕМСТОТИН ", "ДЕВЕТСТОТИН "};
    private static final String[] powers = {"", "ХИЛЯДИ ", "МИЛИОНА ", "МИЛИАРДА ", "ТРИЛИЯРДА "};
    private static final String[] valuta = {"ПЕНС", "ПЕНСА", "ПАУНД ", "ПАУНДА ", "СТОТИНКА", "СТОТИНКИ", "ЛЕВ ", "ЛЕВА ",
        "ЦЕНТ", "ЦЕНТА", "ЕВРО ", "ЕВРО ", "ЦЕНТ", "ЦЕНТА", "ДОЛАР ", "ДОЛАРА "};
    private static final String and = "И ";

    public static String NumberToWord(String toConvert, int valuta_) {

        //opredelqne na valuta
        int valOffset = valuta_ * 4;
        String sinFrac = valuta[valOffset];
        String manFrac = valuta[valOffset + 1];
        String sinUni = valuta[valOffset + 2];
        String manUni = valuta[valOffset + 3];

        //format number
        toConvert = Format.formatPrice(toConvert);

        //split number into pounds and pence
        char[] temp = toConvert.toCharArray();
        int tempLen = temp.length;

        //calculate pence
        char[] frac = {temp[tempLen - 2], temp[tempLen - 1]};
        String pence = "";
        if (frac[0] == '0' && frac[1] == '0') {
            pence = pence.concat(units[0]).concat(manFrac);
        } else if (frac[0] == '0' && frac[1] == '1') {
            if (valuta_ == 1) {
                pence = pence.concat("ЕДНА").concat(sinFrac);
            } else {
                pence = pence.concat(units[1]).concat(sinFrac);
            }
        } else {
            pence = pence.concat(getTens(frac)).concat(manFrac);
        }

        //calculate pounds
        char[] integer = new char[15];
        int intLen = integer.length;
        boolean cont = false;
        for (int i = 0; i < intLen; ++i) {
            if (i < tempLen - 3) {
                integer[i] = temp[tempLen - 4 - i];
                if (integer[i] != '0') {
                    cont = true;
                }
            } else {
                integer[i] = '0';
            }
        }
        String pounds = "";

        char[] tempTri = {integer[14], integer[13], integer[12]};
        boolean tri = isValid(tempTri);
        if (tri) {
            pounds = pounds.concat(getHunds(tempTri)).concat(powers[4]);
        }
        char[] tempBil = {integer[11], integer[10], integer[9]};
        boolean bil = isValid(tempBil);
        if (bil) {
            if (tri) {
                pounds = pounds.concat(and).concat(getHunds(tempBil));
            } else {
                pounds = pounds.concat(getHunds(tempBil));
            }
            pounds = pounds.concat(powers[3]);
        }
        char[] tempMil = {integer[8], integer[7], integer[6]};
        boolean mil = isValid(tempMil);
        if (mil) {
            if (bil || tri) {
                pounds = pounds.concat(and).concat(getHunds(tempMil));
            } else {
                pounds = pounds.concat(getHunds(tempMil));
            }
            pounds = pounds.concat(powers[2]);
        }
        char[] tempTho = {integer[5], integer[4], integer[3]};
        boolean tho = isValid(tempTho);
        if (tho) {
            if (integer[3] == '1' && integer[4] == '0' && integer[5] == '0') {
                pounds = pounds + "ХИЛЯДА ";
            } else if (integer[3] == '2' && integer[4] == '0' && integer[5] == '0') {
                pounds = pounds + "ДВЕ " + powers[1];
            } else if (mil || bil || tri) {
                pounds = pounds.concat(and).concat(getHunds(tempTho)) + powers[1];
            } else {
                pounds = pounds.concat(getHunds(tempTho)) + powers[1];
            }
        }

        char[] tempHund = {integer[2], integer[1], integer[0]};
        boolean hund = isValid(tempHund);
        if (hund) {
            if (!(tho || mil || bil || tri)
                    && (integer[0] == '1' && integer[1] == '0')
                    && integer[2] == '0') {
                pounds = pounds + units[1] + sinUni;
            } else if (!(tho || mil || bil || tri)
                    && (integer[0] == '0' && integer[1] == '0')
                    && integer[2] == '0') {
                pounds = pounds + units[0] + sinUni;
            } else {
                if (tho || mil || bil || tri) {
                    pounds = pounds + and + getHunds(tempHund);
                } else {
                    pounds = pounds + getHunds(tempHund);
                }
                pounds = pounds + manUni;
            }
        }
        if (!hund && cont) {
            pounds = pounds + manUni;
        }
        if (!cont) {
            pounds = units[0] + manUni + pounds;
        }
        if (!pounds.equals("")) {
            pounds = pounds + and;
        }
        return pounds + pence;
    }

    private static boolean isValid(char[] input) {
        return !(input[0] == '0' && input[1] == '0' && input[2] == '0');
    }

    private static String getTens(char[] input) {
        String output = "";
        String t = "";
        String u = "";
        if (input[0] != '1') {
            for (int i = 1; i <= 9; ++i) {
                if ((char) i + 48 == input[1]) {
                    u = units[i];
                    break;
                }
            }
            for (int j = 2; j <= 9; ++j) {
                if ((char) j + 48 == input[0]) {
                    t = tens[j];
                    break;
                }
            }

        } else {
            for (int i = 0; i <= 9; ++i) {
                if ((char) i + 48 == input[1]) {
                    u = units[i + 10];
                    break;
                }
            }
        }
        if (!u.equals("") && !t.equals("")) {
            output = t + and + u;
        } else {
            output = t + u;
        }
        return output;
    }

    private static String getHunds(char[] input) {
        String output = "";
        if (input[0] != '0') {
            for (int i = 1; i <= 9; ++i) {
                if ((char) i + 48 == input[0]) {
                    output = hund[i];
                    break;
                }
            }
        }
        char[] tempTens = {input[1], input[2]};
        if (input[0] != '0' && input[1] == '0' && input[2] != '0') {
            output = output + and + getTens(tempTens);
        } else if (input[0] != '0' && input[1] == '1') {
            output = output + and + getTens(tempTens);
        } else {
            output = output + getTens(tempTens);
        }
        return output;
    }

}
