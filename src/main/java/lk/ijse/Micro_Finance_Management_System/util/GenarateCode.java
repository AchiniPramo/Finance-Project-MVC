package lk.ijse.Micro_Finance_Management_System.util;

import java.util.Random;

public class GenarateCode {
    public static String genarateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
