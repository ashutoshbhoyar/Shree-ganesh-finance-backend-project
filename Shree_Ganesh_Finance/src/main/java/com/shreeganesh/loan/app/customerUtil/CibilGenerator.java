package com.shreeganesh.loan.app.customerUtil;

import java.util.Random;

public class CibilGenerator{
    public static Integer  ciBillCheck(String pan) {
        int min = 600;
        int max = 900;
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}


