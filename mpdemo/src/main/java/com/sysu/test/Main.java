package com.sysu.test;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String lengthAStr = in.nextLine();
        int lengthA = Integer.parseInt(lengthAStr);

        String line = in.nextLine();
        String[] arrA = line.trim().split(" ");
        if (lengthA < 3) {
            System.out.println(0);
            return;
        }
        int lengthB = 0;
        int left = 0;
        int right = 0;

        for (int i = 1; i < arrA.length; i++) {
            //如果增加 right就等于当前i
            if (ParseInt(arrA[i]) > ParseInt(arrA[left])) {
                

            }
            //在减少 left
            if (ParseInt(arrA[i]) < ParseInt(arrA[left])) {

            }
        }

    }

    public static int ParseInt(String str) {
        return Integer.parseInt(str);
    }
}