
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivaylo
 */
public class Test {
    public static void main(String[] args){
        /*Date a = new Date();
        for(long i = 0; i<1000000;++i){
            System.out.println(CCurrBg.NumberToWord("" + i , 0));
        }
        Date b = new Date();
        /*for(long i = 0; i<1000000;++i){
            System.out.println(CCurrEn.NumberToWord("" + i , 0));
        }
        Date c = new Date();*/
        
        //System.out.println(CDate.dateDiff(a, b, TimeUnit.MILLISECONDS));
        //System.out.println(CDate.dateDiff(b, c, TimeUnit.SECONDS));
        
        byte[] b = ProPass.getSalt();
        
        String pass = "1234";
        String notpass = "not" + pass;
        System.out.println(ProPass.genPass(b, pass.toCharArray()));
        System.out.println(ProPass.isExpected(b, pass.toCharArray(), ProPass.genPass(b, pass.toCharArray())));
        System.out.println(ProPass.isExpected(b, notpass.toCharArray(), ProPass.genPass(b, pass.toCharArray())));
    }
}
