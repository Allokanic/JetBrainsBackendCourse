package banking;

import java.util.Random;

public class Util {
    public static String generateNumber(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (len > 0) {
            sb.append(random.nextInt(10));
            len--;
        }
        return sb.toString();
    }

    public static String getCheckSum(String cardNumber) {
        byte[] numbers = cardNumber.getBytes();
        int sum = 0;
        for(int i = 0; i < numbers.length; ++i) {
            numbers[i] -= 48;
            if (i % 2 == 0) {
                numbers[i] *= 2;
                if (numbers[i] > 9) {
                    numbers[i] -= 9;
                }
            }
            sum += numbers[i];
        }
        int result = (10 - (sum % 10)) % 10;
        return Integer.toString(result);
    }
}
