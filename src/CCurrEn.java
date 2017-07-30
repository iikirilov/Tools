
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ivaylo Kirilov iikirilov@gmail.com
 */
public class CCurrEn {

    private static final String[] units = {"ZERO ", "ONE ", "TWO ", "THREE ", "FOUR ",
        "FIVE ", "SIX ", "SEVEN ", "EIGHT ", "NINE ",
        "TEN ", "ELEVEN ", "TWELVE ", "THIRTEEN ", "FOURTEEN ",
        "FIFTEEN ", "SIXTEEN ", "SEVENTEEN ", "EIGHTEEN ", "NINTEEN "};
    private static final String[] tens = {"", "", "TWENTY ", "THIRTY ", "FOURTY ",
        "FIFTY ", "SIXTY ", "SEVENTY ", "EIGHTY ", "NINTY "};
    private static final String[] powers = {"HUNDRED ", "THOUSAND ", "MILLION ", "BILLION ", "TRILLION "};
    private static final String[] valuta = {"PENNY", "PENCE", "POUND ", "POUNDS ", "STOTOINKA", "STOTINKI", "LEV ", "LEVA ",
        "CENT", "CENTS", "EURO ", "EUROS ", "CENT", "CENTS", "DOLLAR ", "DOLLARS "};
    private static final String and = "AND ";

    /* private static final long[] numTens = {0, 0, 20, 30, 40, 50, 60, 70, 80, 90};
     private static final long[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
     11, 12, 13, 14, 15, 16, 17, 18, 19};
     private static final long[] multi = {100L, 1000L, 1000000L, 1000000000L, 100000000000L};*/
    public static void main(String[] args) {
        String x;
        for (int i = 1; i < 999999; ++i) {
            x = NumberToWord("" + i, 2);
            System.out.println(x);
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(CCurrEn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        x = NumberToWord("" + 3000, 0);
        System.out.println(x);
    }

    /**
     *
     * @param toConvert - string chislo
     * @param valuta_ - 0 = pound, 1 = lev, 3 = evro , 4 = dolar
     * @return - string na angliiski
     */
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
        String pence;
        if (frac[0] == '0' && frac[1] == '0') {
            pence = units[0] + manFrac;
        } else if (frac[0] == '0' && frac[1] == '1') {
            pence = units[1] + sinFrac;
        } else {
            pence = getTens(frac) + manFrac;
        }

        //calculate pounds
        char[] integer = new char[15];
        int intLen = integer.length;
        boolean cont = false;
        for (int i = 0; i < intLen; i++) {
            if (i < tempLen - 3) {
                integer[intLen - 1 - i] = temp[tempLen - 4 - i];
                if (integer[intLen - 1 - i] != '0') {
                    cont = true;
                }
            } else {
                integer[intLen - 1 - i] = '0';
            }
        }

        String pounds = "";

        char[] tempTri = {integer[0], integer[1], integer[2]};
        boolean tri = isValid(tempTri);
        if (tri) {
            pounds = pounds + getHunds(tempTri) + powers[4];
        }
        char[] tempBil = {integer[3], integer[4], integer[5]};
        boolean bil = isValid(tempBil);
        if (bil) {
            if ((tri) && integer[3] == '0') {
                pounds = pounds + and + getHunds(tempBil);
            } else {
                pounds = pounds + getHunds(tempBil);
            }
            pounds = pounds + powers[3];
        }
        char[] tempMil = {integer[6], integer[7], integer[8]};
        boolean mil = isValid(tempMil);
        if (mil) {
            if ((bil || tri) && integer[6] == '0') {
                pounds = pounds + and + getHunds(tempMil);
            } else {
                pounds = pounds + getHunds(tempMil);
            }
            pounds = pounds + powers[2];
        }
        char[] tempTho = {integer[9], integer[10], integer[11]};
        boolean tho = isValid(tempTho);
        if (tho) {
            if ((mil || bil || tri) && integer[9] == '0') {
                pounds = pounds + and + getHunds(tempTho);
            } else {
                pounds = pounds + getHunds(tempTho);
            }
            pounds = pounds + powers[1];
        }
        char[] tempHund = {integer[12], integer[13], integer[14]};
        boolean hund = isValid(tempHund);
        if (hund) {
            if (!(tho || mil || bil || tri)
                    && (integer[12] == '0' && integer[13] == '0')
                    && integer[14] == '1') {
                pounds = units[1] + sinUni;
            } else {
                if ((tho || mil || bil || tri) && integer[12] == '0') {
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

    private static String getHunds(char[] input) {
        String output = "";
        if (input[0] != '0') {
            for (int i = 0; i <= 9; ++i) {
                if ((char) i + 48 == input[0]) {
                    output = units[i] + powers[0];
                }
            }
        }
        char[] tempTens = {input[1], input[2]};
        if ((input[0] != '0' && input[1] != '0')
                || (input[0] != '0' && input[1] == '0' && input[2] != '0')) {
            output = output + and + getTens(tempTens);
        } else {
            output = output + getTens(tempTens);
        }
        return output;
    }

    private static String getTens(char[] input) {
        String output = "";

        if (input[0] != '1') {
            for (int i = 1; i <= 9; ++i) {
                if ((char) i + 48 == input[1]) {
                    output = units[i];
                }
            }
            for (int i = 0; i <= 9; ++i) {
                if ((char) i + 48 == input[0]) {
                    output = tens[i] + output;
                }
            }
        } else if (input[0] == '1') {
            for (int i = 0; i <= 9; ++i) {
                if ((char) i + 48 == input[1]) {
                    output = units[i + 10] + output;
                }
            }
        }
        return output;
    }

    /*public static String WordToNumber(String toConvert) {
     toConvert = toConvert.replace(" AND", "");
     String[] tokens = toConvert.split("(?<=\\s)");
     int tokenLen = tokens.length;
     int powLen = powers.length;
     int uniLen = units.length;
     int tenLen = tens.length;
     long[] numTokens = new long[tokenLen];
     int count = 0;
     int currentPos = -1;
     boolean isValid = false;
     if (tokenLen > 3) {
     isValid = true;
     }
     while (!tokens[count].equals("POUNDS ") && !tokens[count].equals("POUND ") && isValid) {
     String toCheck = tokens[count];
     for (int a = 0; a < uniLen; ++a) {
     if (toCheck.equals(units[a])) {
     numTokens[count] = num[a];
     currentPos = count;
     }
     }
     for (int b = 0; b < tenLen; ++b) {
     if (toCheck.equals(tens[b])) {
     numTokens[count] = numTens[b];
     currentPos = count;
     }
     }
     for (int j = 0; j < powLen; ++j) {
     if (toCheck.equals(powers[j])) {
     numTokens[currentPos] = numTokens[currentPos] * multi[j];
     }
     }
     count++;
     }
     long uniAns = 0;
     for (int i = 0; i < tokenLen; ++i) {
     uniAns = uniAns + numTokens[i];
     }
     count++;
     while (count < tokenLen - 1) {
     String toCheck = tokens[count];
     for (int a = 0; a < uniLen; ++a) {
     if (toCheck.equals(units[a])) {
     numTokens[count] = num[a];
     }
     }
     for (int b = 0; b < tenLen; ++b) {
     if (toCheck.equals(tens[b])) {
     numTokens[count] = numTens[b];
     }
     }
     count++;
     }
     long fracAns = 0;
     if (tokenLen < 3) {
     fracAns = numTokens[tokenLen - 2];
     } else {
     fracAns = numTokens[tokenLen - 2] + numTokens[tokenLen - 3];
     }
     String ans = uniAns + "." + fracAns;
     String toFix = "" + fracAns;
     int index = ans.indexOf('.');
     if (index == -1) {
     ans = ans + ".00";
     } else {
     if (index == ans.length() - 2) {
     toFix = "0" + toFix;
     } else {
     if (index < ans.length() - 3) {
     ans = ans.substring(0, index + 3);
     }
     }
     }
     ans = uniAns + "." + toFix;
     return ans;
     }*/
}
