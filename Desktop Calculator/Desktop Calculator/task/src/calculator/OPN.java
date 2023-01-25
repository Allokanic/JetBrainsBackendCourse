package calculator;

import java.beans.Expression;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.Flow;

public class OPN {
    public static String evaluateExpression(String input) {
        String correctInput = fightWithMinus(input.trim());
        ArrayList<String> tokens = getTokens(correctInput);
        ArrayList<String> reverseNotation = getReverseNotation(tokens);
        return evaluateReverseNotation(reverseNotation);
    }

    public static ArrayList<String> getReverseNotation(ArrayList<String> tokens) {
        Deque<String> stack = new ArrayDeque<>();
        ArrayList<String> result = new ArrayList<>();
        for (String elem : tokens) {
            //System.out.println("Processing: " + elem);
            if ("".equals(elem)) {
                break;
            }
            try {
                Float.parseFloat(elem);
                result.add(elem);
            } catch (Exception e) {
                if ("(".equals(elem)) {
                    stack.addFirst(elem);
                } else if (")".equals(elem)) {
                    while (!"(".equals(stack.peekFirst())) {
                        result.add(stack.pollFirst());
                    }
                    stack.pollFirst();
                } else {
                    while (!stack.isEmpty() &&
                            getPriority(elem) <= getPriority(stack.peekFirst()) &&
                            !"(".equals(stack.peekFirst())) {
                        result.add(stack.pollFirst());
                    }
                    stack.addFirst(elem);
                }
            }
            //System.out.println("Stack is: " + stack);
            //System.out.println("Result is: " + result);
            //System.out.println();
        }
        while (!stack.isEmpty()) {
            result.add(stack.pollFirst());
        }
        return result;
    }

    public static String evaluateReverseNotation(ArrayList<String> reverseNotation) {
        Deque<String> stack = new ArrayDeque<>();
        for (String elem : reverseNotation) {
            try {
                Float.parseFloat(elem);
                stack.addFirst(elem);
            } catch (Exception e) {
                processOperator(elem, stack);
            }
        }
        return stack.pollFirst();
    }

    private static int getPriority(String a) {
        if ("+".equals(a) || "-".equals(a)) {
            return 1;
        } else if ("x".equals(a) || "\u00F7".equals(a) || "*".equals(a) || "/".equals(a) || "×".equals(a)) {
            return 2;
        } else {
            return 3;
        }
    }

    public static ArrayList<String> getTokens(String input) {
        ArrayList<String> result = new ArrayList<>();
        int start = 0;
        boolean lastIsSign = false;
        for (int i = 0; i < input.length(); ++i) {
            char cur = input.charAt(i);
            if (cur == '+' ||
                    cur == '-' ||
                    cur == 'x' ||
                    cur == '\u00F7' ||
                    cur == '^' ||
                    cur == '(' ||
                    cur == ')' ||
                    cur == '*' ||
                    cur == '/' ||
                    cur == '×') {
                if (!lastIsSign && start != i) {
                    result.add(input.substring(start, i));
                }
                result.add(input.substring(i, i + 1));
                lastIsSign = true;
                start = i + 1;
            } else {
                lastIsSign = false;
            }
        }
        result.add(input.substring(start));
        return result;
    }

    private static void processOperator(String operator, Deque<String> stack) {
        float a2 = Float.parseFloat(stack.pollFirst());
        float result = 0;
        if ("+".equals(operator)) {
            float a1 = Float.parseFloat(stack.pollFirst());
            result = a1 + a2;
        } else if ("-".equals(operator)) {
            float a1 = Float.parseFloat(stack.pollFirst());
            result = a1 - a2;
        } else if ("*".equals(operator) || "x".equals(operator) || "×".equals(operator)) {
            float a1 = Float.parseFloat(stack.pollFirst());
            result = a1 * a2;
        } else if ("/".equals(operator) || "\u00F7".equals(operator)) {
            float a1 = Float.parseFloat(stack.pollFirst());
            result = a1 / a2;
        } else if ("^".equals(operator)) {
            float a1 = Float.parseFloat(stack.pollFirst());
            result = (float) Math.pow(a1, a2);
        } else if ("\u221A".equals(operator)) {
            result = (float) Math.sqrt(a2);
        }
        stack.addFirst(Float.toString(result));
    }

    public static String fightWithMinus(String input) {
        StringBuilder tmp = new StringBuilder(input);
        if (tmp.charAt(0) == '-') {
            tmp.insert(0, '0');
        }
        int i = 0;
        while (i < tmp.length() - 1) {
            if (tmp.charAt(i) == '(' && tmp.charAt(i+1) == '-') {
                tmp.insert(i+1, '0');
            }
            i++;
        }
        return tmp.toString();
    }
}
