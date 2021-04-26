package com.sysu.test2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
//        in.close();
        long X = 0;
        //特1 小于20的X是N本身
        if (N < 22) {
            System.out.println(N);
            return;
        } else {
            int i = 1;
            while (true) {
//            System.out.println(1);
                X = N * i;
                String s = Long.toString(X);
                String[] arr = s.split("");
                int count = 0;
                int first = 0;
                for (String s1 : arr) {
                    if (Integer.parseInt(s1) > 1) {
                        if (first > 1 && Integer.parseInt(s1) != first) {
                            count++;
                        }
                        if (first < 2) {
                            first = Integer.parseInt(s1);
                            count++;
                        }

                    }
                    if (count > 1) {
                        break;
                    }
                }
                if (count < 2) {
                    System.out.println(X);
                    return;
                }
                i++;
            }
        }

    }
}