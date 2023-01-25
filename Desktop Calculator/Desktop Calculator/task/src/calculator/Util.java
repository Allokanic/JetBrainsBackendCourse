package calculator;

public class Util {
    public static boolean prevIsOperator(String input) {
        char tmp = input.charAt(input.length()-1);
        return tmp == '+' || tmp == '-' || tmp == 'x' || tmp == '/' || tmp == '^' || tmp == '×' || tmp == '\u00F7';
    }

    public static boolean contatinsDivisionByZero(String input) {
        return input.contains("\u00F70");
    }

    public static String prepareString(String input) {
        StringBuilder tmp = new StringBuilder(input);
        if (tmp.charAt(0) == '.') {
            tmp.insert(0, '0');
        }
        if (tmp.charAt(tmp.length()-1) == '.') {
            tmp.append('0');
        }
        int i = 0;
        while( i < tmp.length() - 1) {
            if (tmp.charAt(i+1) == '.' && notDecimal(tmp.charAt(i))) {
                tmp.insert(i+1, '0');
            } else if (tmp.charAt(i) == '.' && notDecimal(tmp.charAt(i + 1))) {
                tmp.deleteCharAt(i);
            }
            i++;
        }
        return tmp.toString();
    }

    public static boolean notDecimal(char tmp) {
        return tmp > '9' || tmp < '0';
    }

    public static boolean checkSquareRule(String input) {
        int i = 0;
        while (i < input.length()) {
            if (input.charAt(i) == '\u221A') {
                int start = i+1;
                i = i+2;
                int lp = 0;
                while (true) {
                    if (input.charAt(i) == '(') {
                        lp++;
                    } else if (input.charAt(i) == ')') {
                        lp--;
                    }
                    if (lp < 0) {
                        break;
                    }
                    i++;
                }
                String res = OPN.evaluateExpression(input.substring(start, i+1));
                Float fres = res == null ? 0 : Float.parseFloat(res);
                if (fres < 0) {
                    return false;
                }
            }
            i++;
        }
        return true;
    }
}
