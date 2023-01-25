package calculator;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class Calculator extends JFrame {
    private static int leftp = 0;
    private static int rightp = 0;

    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    public static final int BASICX = 15;
    public static final int BASICY = 115;
    public static final int DISTANCEY = 5;
    public static final int DISTANCEX = 5;
    public static final int allWidth = BASICX + 4*WIDTH + 3*DISTANCEX + BASICX + 15;
    public static final int allHeight = 435;

    public Calculator() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(allWidth, allHeight);
        setLayout(null);
        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }
    
    public void initComponents() {
        JLabel labelSub = new JLabel();
        labelSub.setBounds(BASICX, 70, 4*WIDTH + 3*DISTANCEX, 40);
        labelSub.setFont(labelSub.getFont().deriveFont(24.f));
        labelSub.setHorizontalAlignment(SwingConstants.RIGHT);
        labelSub.setName("EquationLabel");
        add(labelSub);

        JLabel labelMain = new JLabel();
        labelMain.setBounds(BASICX, 20, 4*WIDTH + 3*DISTANCEX, 40);
        labelMain.setFont(labelMain.getFont().deriveFont(42.f));
        labelMain.setHorizontalAlignment(SwingConstants.RIGHT);
        labelMain.setForeground(Color.BLUE);
        labelMain.setText("0");
        labelMain.setName("ResultLabel");
        add(labelMain);

        addNumberButtons(labelMain, labelSub);
    }

    public void initButton(JButton x) {
        x.setFont(x.getFont().deriveFont(20f));
        x.setVerticalAlignment(SwingConstants.CENTER);
        x.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void addNumberButtons(JLabel main, JLabel field) {

        JButton Parentheses = new JButton("()");
        Parentheses.setBounds(BASICX, BASICY, WIDTH, HEIGHT);
        Parentheses.setName("Parentheses");
        initButton(Parentheses);
        add(Parentheses);

        Parentheses.addActionListener(e -> {
            System.out.println("()\n");
            if (leftp == rightp) {
                field.setText(field.getText() + "(");
                leftp++;
            }else {
                char tmp = field.getText().charAt(field.getText().length()-1);
                if ((Util.notDecimal(tmp) && tmp != ')')) {
                    field.setText(field.getText() + "(");
                    leftp++;
                } else {
                    field.setText(field.getText() + ")");
                    rightp++;
                }
            }
        });

        JButton CE = new JButton("CE");
        CE.setMargin(new Insets(0, 0, 0, 0));
        CE.setBounds(BASICX + WIDTH + DISTANCEX, BASICY, WIDTH, HEIGHT);
        initButton(CE);
        add(CE);

        JButton clear = new JButton("C");
        clear.setMargin(new Insets(0, 0, 0, 0));
        clear.setBounds(BASICX + 2*WIDTH + 2*DISTANCEX, BASICY, WIDTH, HEIGHT);
        initButton(clear);
        clear.setName("Clear");
        add(clear);

        clear.addActionListener(e -> {
            System.out.println("C\n");
            field.setForeground(Color.BLACK);
            String tmp = field.getText();
            for (int i = 0; i < tmp.length(); ++i) {
                if (tmp.charAt(i) == '(') {
                    leftp--;
                } else if (tmp.charAt(i) == ')') {
                    rightp--;
                }
            }
            field.setText("");
            main.setText("0");
        });

        JButton deleter = new JButton("Del");
        deleter.setMargin(new Insets(0, 0, 0,0));
        deleter.setBounds(BASICX + 3*WIDTH + 3*DISTANCEX, BASICY, WIDTH, HEIGHT);
        deleter.setName("Delete");
        initButton(deleter);
        add(deleter);

        deleter.addActionListener(e -> {
            System.out.println("Del\n");
            field.setForeground(Color.BLACK);
            if (field.getText().length() != 0) {
                char tmp = field.getText().charAt(field.getText().length()-1);
                if (tmp == '(') {
                    leftp--;
                } else if (tmp == ')') {
                    rightp--;
                }
                field.setText(field.getText().substring(0, field.getText().length()-1));
            }
        });

        JButton quadro = new JButton("x²");
        quadro.setName("PowerTwo");
        quadro.setBounds(BASICX, BASICY + HEIGHT + DISTANCEY, WIDTH, HEIGHT);
        initButton(quadro);
        add(quadro);

        quadro.addActionListener(e -> {
            System.out.println("x^2\n");
            field.setText(field.getText() + "^(2)");
            leftp++;
            rightp++;
        });

        JButton power = new JButton("xⁿ");
        power.setName("PowerY");
        power.setMargin(new Insets(0, 0, 0, 0));
        power.setBounds(BASICX + WIDTH + DISTANCEX, BASICY + HEIGHT + DISTANCEY, WIDTH, HEIGHT);
        initButton(power);
        add(power);

        power.addActionListener(e -> {
            System.out.println("x^n\n");
            field.setText(field.getText() + "^(");
            leftp++;
        });

        JButton sqrt = new JButton("\u221A");
        sqrt.setName("SquareRoot");
        sqrt.setMargin(new Insets(0, 0, 0, 0));
        sqrt.setBounds(BASICX + 2*WIDTH + 2*DISTANCEX, BASICY + HEIGHT + DISTANCEY, WIDTH, HEIGHT);
        initButton(sqrt);
        add(sqrt);

        sqrt.addActionListener(e -> {
            System.out.println("squareRoot\n");
            field.setText(field.getText() + "\u221A(");
            leftp++;
        });

        JButton operatorDivide = new JButton("\u00F7");
        operatorDivide.setMargin(new Insets(0, 0, 0, 0));
        operatorDivide.setBounds(BASICX + 3*WIDTH + 3*DISTANCEX, BASICY + HEIGHT + DISTANCEY, WIDTH, HEIGHT);
        operatorDivide.setName("Divide");
        initButton(operatorDivide);
        add(operatorDivide);

        operatorDivide.addActionListener(e -> {
            System.out.println("/\n");
            if (!"".equals(field.getText())) {
                if (Util.prevIsOperator(field.getText())) {
                    field.setText(field.getText().substring(0, field.getText().length() - 1) + "\u00F7");
                } else {
                    field.setText(Util.prepareString(field.getText()));
                    field.setText(field.getText() + "\u00F7");
                }
            }
        });

        JButton number7 = new JButton("7");
        number7.setBounds(BASICX, BASICY + 2*HEIGHT + 2*DISTANCEY, WIDTH, HEIGHT);
        number7.setBackground(Color.pink);
        number7.setName("Seven");
        initButton(number7);
        add(number7);

        number7.addActionListener(e -> {
            System.out.println("7\n");
            field.setText(field.getText() + "7");
        });

        JButton number8 = new JButton("8");
        number8.setBounds(BASICX + WIDTH + DISTANCEX, BASICY + 2*HEIGHT + 2*DISTANCEY, WIDTH, HEIGHT);
        number8.setBackground(Color.pink);
        number8.setName("Eight");
        initButton(number8);
        add(number8);

        number8.addActionListener(e -> {
            System.out.println("8\n");
            field.setText(field.getText() + "8");
        });

        JButton number9 = new JButton("9");
        number9.setBounds(BASICX + 2*WIDTH + 2*DISTANCEX, BASICY + 2*HEIGHT + 2*DISTANCEY, WIDTH, HEIGHT);
        number9.setBackground(Color.pink);
        number9.setName("Nine");
        initButton(number9);
        add(number9);

        number9.addActionListener(e -> {
            System.out.println("9\n");
            field.setText(field.getText() + "9");
        });

        JButton operatorMultiply = new JButton("x");
        operatorMultiply.setMargin(new Insets(0, 0, 0, 0));
        operatorMultiply.setBounds(BASICX + 3*WIDTH + 3*DISTANCEX, BASICY + 2*HEIGHT + 2*DISTANCEY, WIDTH, HEIGHT);
        operatorMultiply.setName("Multiply");
        initButton(operatorMultiply);
        add(operatorMultiply);

        operatorMultiply.addActionListener(e -> {
            System.out.println("x\n");
            if (!"".equals(field.getText())) {
                if (Util.prevIsOperator(field.getText())) {
                    field.setText(field.getText().substring(0, field.getText().length() - 1) + "×");
                } else {
                    field.setText(Util.prepareString(field.getText()));
                    field.setText(field.getText() + "×");
                }
            }
        });

        JButton number4 = new JButton("4");
        number4.setBounds(BASICX, BASICY + 3*HEIGHT + 3*DISTANCEY, WIDTH, HEIGHT);
        number4.setBackground(Color.pink);
        number4.setName("Four");
        initButton(number4);
        add(number4);

        number4.addActionListener(e -> {
            System.out.println("4\n");
            field.setText(field.getText() + "4");
        });

        JButton number5 = new JButton("5");
        number5.setBounds(BASICX + WIDTH + DISTANCEX, BASICY + 3*HEIGHT + 3*DISTANCEY, WIDTH, HEIGHT);
        number5.setBackground(Color.pink);
        number5.setName("Five");
        initButton(number5);
        add(number5);

        number5.addActionListener(e -> {
            System.out.println("5\n");
            field.setText(field.getText() + "5");
        });

        JButton number6 = new JButton("6");
        number6.setBounds(BASICX + 2*WIDTH + 2*DISTANCEX, BASICY + 3*HEIGHT + 3*DISTANCEY, WIDTH, HEIGHT);
        number6.setBackground(Color.pink);
        number6.setName("Six");
        initButton(number6);
        add(number6);

        number6.addActionListener(e -> {
            System.out.println("6\n");
            field.setText(field.getText() + "6");
        });

        JButton operatorSubtract = new JButton("-");
        operatorSubtract.setMargin(new Insets(0, 0, 0, 0));
        operatorSubtract.setBounds(BASICX + 3*WIDTH + 3*DISTANCEX, BASICY + 3*HEIGHT + 3*DISTANCEY, WIDTH, HEIGHT);
        operatorSubtract.setName("Subtract");
        initButton(operatorSubtract);
        add(operatorSubtract);

        operatorSubtract.addActionListener(e -> {
            System.out.println("-\n");
            if (!"".equals(field.getText())) {
                if (Util.prevIsOperator(field.getText())) {
                    field.setText(field.getText().substring(0, field.getText().length() - 1) + "-");
                } else {
                    field.setText(Util.prepareString(field.getText()));
                    field.setText(field.getText() + "-");
                }
            }
        });

        JButton number1 = new JButton("1");
        number1.setBounds(BASICX, BASICY + 4*HEIGHT + 4*DISTANCEY, WIDTH, HEIGHT);
        number1.setBackground(Color.pink);
        number1.setName("One");
        initButton(number1);
        add(number1);

        number1.addActionListener(e -> {
            System.out.println("1\n");
            field.setText(field.getText() + "1");
        });

        JButton number2 = new JButton("2");
        number2.setBounds(BASICX + WIDTH + DISTANCEX, BASICY + 4*HEIGHT + 4*DISTANCEY, WIDTH, HEIGHT);
        number2.setBackground(Color.pink);
        number2.setName("Two");
        initButton(number2);
        add(number2);

        number2.addActionListener(e -> {
            System.out.println("2\n");
            field.setText(field.getText() + "2");
        });

        JButton number3 = new JButton("3");
        number3.setBounds(BASICX + 2*WIDTH + 2*DISTANCEX, BASICY + 4*HEIGHT + 4*DISTANCEY, WIDTH, HEIGHT);
        number3.setBackground(Color.pink);
        number3.setName("Three");
        initButton(number3);
        add(number3);

        number3.addActionListener(e -> {
            System.out.println("3\n");
            field.setText(field.getText() + "3");
        });

        JButton operatorAdd = new JButton("+");
        operatorAdd.setMargin(new Insets(0, 0, 0, 0));
        operatorAdd.setBounds(BASICX + 3*WIDTH + 3*DISTANCEX, BASICY + 4*HEIGHT + 4*DISTANCEY, WIDTH, HEIGHT);
        operatorAdd.setName("Add");
        initButton(operatorAdd);
        add(operatorAdd);

        operatorAdd.addActionListener(e -> {
            System.out.println("+\n");
            if (!"".equals(field.getText())) {
                if (Util.prevIsOperator(field.getText())) {
                    field.setText(field.getText().substring(0, field.getText().length() - 1) + "+");
                } else {
                    field.setText(Util.prepareString(field.getText()));
                    field.setText(field.getText() + "+");
                }
            }
        });

        JButton plusMinus = new JButton("\u00B1");
        plusMinus.setName("PlusMinus");
        plusMinus.setBounds(BASICX, BASICY + 5*HEIGHT + 5*DISTANCEY, WIDTH, HEIGHT);
        initButton(plusMinus);
        add(plusMinus);

        plusMinus.addActionListener(e -> {
            System.out.println("+-\n");
            String tmp = field.getText();
            if ("".equals(tmp)) {
                field.setText("(-");
                leftp++;
            } else if ("(-".equals(tmp)) {
                field.setText("");
                leftp--;
            } else {
                try {
                    Float.parseFloat(tmp);
                    field.setText("(-" + tmp);
                    leftp++;
                } catch (Exception o) {
                    if (tmp.length() > 2 &&
                            tmp.charAt(0) == '(' &&
                            tmp.charAt(1) == '-') {
                        leftp--;
                        field.setText(tmp.substring(2));
                    } else {
                        field.setText("(-(" + tmp + "))");
                    }
                }
            }
        });

        JButton number0 = new JButton("0");
        number0.setName("Zero");
        number0.setBounds(BASICX + WIDTH + DISTANCEX, BASICY + 5*HEIGHT + 5*DISTANCEY, WIDTH, HEIGHT);
        number0.setBackground(Color.pink);
        initButton(number0);
        add(number0);

        number0.addActionListener(e -> {
            System.out.println("0\n");
            field.setText(field.getText() + "0");
        });

        JButton point = new JButton(".");
        point.setMargin(new Insets(0, 0, 0, 0));
        point.setBounds(BASICX + 2*WIDTH + 2*DISTANCEX, BASICY + 5*HEIGHT + 5*DISTANCEY, WIDTH, HEIGHT);
        point.setName("Dot");
        initButton(point);
        add(point);

        point.addActionListener(e -> {
            System.out.println(".\n");
            field.setText(field.getText() + ".");
        });

        JButton acceptButton = new JButton("=");
        acceptButton.setMargin(new Insets(0, 0, 0, 0));
        acceptButton.setName("Equals");
        acceptButton.setBounds(BASICX + 3*WIDTH + 3*DISTANCEX, BASICY + 5*HEIGHT + 5*DISTANCEY, WIDTH, HEIGHT);
        acceptButton.setBackground(Color.cyan.brighter());
        initButton(acceptButton);
        add(acceptButton);

        acceptButton.addActionListener(e -> {
            System.out.println("=\n");
            if (leftp != rightp || Util.prevIsOperator(field.getText()) || Util.contatinsDivisionByZero(field.getText()) || !Util.checkSquareRule(field.getText())) {
                field.setForeground(Color.RED.darker());
            } else {
                float result = Float.parseFloat(OPN.evaluateExpression(field.getText()));
                if (result == (int) result) {
                    main.setText(Integer.toString((int) result));
                } else {
                    main.setText(Float.toString(result));
                }
            }
        });
    }
}
