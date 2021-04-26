package com.sysu.test3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String kStr = in.nextLine();
        int k = Integer.parseInt(kStr);
        String line = in.nextLine();
        String[] split = line.split("");
        Arrays.sort(split);
        String T = "";
        if (k == 1) {
            System.out.println(line);
        }
        if (k == line.length()) {
            String s = split[split.length - 1];
            for (String s1 : split) {
                T += s;
            }
            System.out.println(T);
        }
        if (k>1&&k<line.length()){

        }
    }
}
