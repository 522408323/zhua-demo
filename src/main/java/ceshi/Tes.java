package ceshi;

/**
 * @ClassName: Tes
 * @Author: shenyafei
 * @Date: 2019/9/20
 * @Desc
 **/
public class Tes {

    public static void main(String[] args){
        String t0 = "abc";
        String t1 = "ab" + "c";
        System.out.println(t0 == t1);
        String t2 = "ab";
        String t3 = t2 + "c";
        System.out.println(t0 == t3);
    }
}
