import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calculator extends JFrame {

    private JButton[] buttons;
    public JLabel field;
    public ArrayList<String> numAndOper;
    public boolean radians;
    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {

        radians = true;
        numAndOper = new ArrayList<>();
        JFrame frame = new JFrame(); // Creates empty window
        // Create button grid //
        String[] names = {"rad","π","CLR","7","8","9","÷",
                            "sin(x)","e","x^2","4","5","6","x",
                            "cos(x)","e^x","SQRT","1","2","3","-",
                            "tan(x)","ln(x)","x!",".","0","=","+"};
        buttons = new JButton[28];
        for (int i = 0; i < 28; i++) {
            buttons[i] = new JButton(names[i]);
            buttons[i].addActionListener(new buttonListener()); // Adds ActionListener so buttons trigger an event
        }
        JPanel buttonGrid = new JPanel(); // Creates JPanel for the 20 buttons
        buttonGrid.setLayout(new GridLayout(4, 7)); // Sets up 4x7 layout

        // Add buttons to grid //
        for (int i = 0; i < 28; i++) {
            buttonGrid.add(buttons[i]);
        }
        // Create container for everything //
        JPanel overall = new JPanel();
        overall.setLayout(new GridLayout(2, 1));
        // Editable text field //
        field = new JLabel("0");
        // Add components //
        overall.add(field, BorderLayout.NORTH);
        overall.add(buttonGrid, BorderLayout.SOUTH);
        // Add overall to frame //
        frame.add(overall, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calculator");
        frame.pack();
        frame.setVisible(true);
    }

    private class buttonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String beforetext = field.getText();
            String name = ((JButton) e.getSource()).getActionCommand();

            // CLEAR CASE //
            if (name.equals("CLR")) {
                numAndOper.removeAll(numAndOper);
                field.setText("0");
            }

            // rad/deg switch //
            else if (name.equals("rad")) {
                buttons[0].setText("deg");
                radians = false;
            }
            else if (name.equals("deg")) {
                buttons[0].setText("rad");
                radians = true;
            }
            
            // CASE 1: displayed text is not an operator //
            else if (!(beforetext.equals("÷") || beforetext.equals("x") || beforetext.equals("-") || beforetext.equals("+"))) {
                double num = Double.parseDouble(beforetext);
                // If operator, add displayed number to ArrayList and display operator //
                if (name.equals("÷") || name.equals("x") || name.equals("-") || name.equals("+")) {
                    if (numAndOper.size()==0) {
                        numAndOper.add(beforetext);
                    }
                    else if (!numAndOper.get(0).equals(beforetext)) {
                        numAndOper.add(beforetext);
                    }
                    field.setText(name);
                }
                // If equals, add the displayed number to ArrayList and calculate result //
                else if (name.equals("=")) {
                    numAndOper.add(beforetext);
                    double result = calc(numAndOper);
                    field.setText(Double.toString(result));
                    numAndOper.removeAll(numAndOper);
                    numAndOper.add(Double.toString(result));
                }
                // If x-squared, square displayed number //
                else if (name.equals("x^2")) {
                    String squared = Double.toString(Math.pow(num, 2));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(squared);
                    }
                    field.setText(squared);
                }
                // If square-root, perform sqrt on displayed number //
                else if (name.equals("SQRT")) {
                    String sqrt = Double.toString(Math.pow(num, 0.5));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(sqrt);
                    }
                    field.setText(sqrt);
                }
                // If !, perform factorial on displayed number //
                else if (name.equals("x!")) {
                    // Solves problem where 171! and above return "infinity" //
                    if (num > 170) {
                        numAndOper.removeAll(numAndOper);
                        field.setText("LIMIT EXCEEDED");
                    }
                    else {
                        String fac = Double.toString(factorial(num));
                        // Edge case when size = 1, must replace saved value with new value //
                        if (numAndOper.size()==1) {
                            numAndOper.remove(0);
                            numAndOper.add(fac);
                        }
                        field.setText(fac);
                    }
                }
                // If sin(x), perform sin(x) on displayed number //
                else if (name.equals("sin(x)")) {
                    String sin;
                    // Radian Case //
                    if (radians) {
                        sin = Double.toString(Math.sin(num));
                    }
                    // Degree Case //
                    else {
                        sin = Double.toString(Math.sin(Math.toRadians(num)));
                    }
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(sin);
                    }
                    field.setText(sin);
                }
                // If cos(x), perform cos(x) on displayed number //
                else if (name.equals("cos(x)")) {
                    String cos;
                    // Radian Case //
                    if (radians) {
                        cos = Double.toString(Math.cos(num));
                    }
                    // Degree Case //
                    else {
                        cos = Double.toString(Math.cos(Math.toRadians(num)));
                    }
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(cos);
                    }
                    field.setText(cos);
                }
                // If tan(x), perform tan(x) on displayed number //
                else if (name.equals("tan(x)")) {
                    String tan;
                    // Radian Case //
                    if (radians) {
                        tan = Double.toString(Math.tan(num));
                    }
                    // Degree Case //
                    else {
                        tan = Double.toString(Math.tan(Math.toRadians(num)));
                    }
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(tan);
                    }
                    field.setText(tan);
                }
                // If e^x, perform e^x on displayed number //
                else if (name.equals("e^x")) {
                    String eToX = Double.toString(Math.pow(Math.E, num));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(eToX);
                    }
                    field.setText(eToX);
                }
                // If ln(x), perform natural log on displayed number //
                else if (name.equals("ln(x)")) {
                    String lnx = Double.toString(Math.log(num));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(lnx);
                    }
                    field.setText(lnx);
                }
                // If π, multiply diplayed number by π //
                else if (name.equals("π")) {
                    if (num==0.0) {num=1.0;}
                    String timespi = Double.toString(num*Math.PI);
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(timespi);
                    }
                    field.setText(timespi);
                }
                // If e, multiply diplayed number by e //
                else if (name.equals("e")) {
                    if (num==0.0) {num=1.0;}
                    String timesE = Double.toString(num*Math.E);
                    // Edge case when size = 1, must replace saved value with new value //
                    if (numAndOper.size()==1) {
                        numAndOper.remove(0);
                        numAndOper.add(timesE);
                    }
                    field.setText(timesE);
                }
                // Else, add another digit to number //
                else {
                    String aftertext = beforetext;
                    if (name.equals(".")) {
                        // Disallow multiple decimal points //
                        if (!beforetext.contains(".")) {
                            aftertext += name;
                        }
                    }
                    else {aftertext += name;}
                    field.setText(aftertext);
                }
            }

            // CASE 2: displayed text is an operator //
            else {
                // If new operator, replace old one //
                if (name.equals("÷") || name.equals("x") || name.equals("-") || name.equals("+")) {
                    field.setText(name);
                }
                // If equals, disregard displayed operator and calculate result //
                else if (name.equals("=")) {
                    double result = calc(numAndOper);
                    field.setText(Double.toString(result));
                    numAndOper.removeAll(numAndOper);
                    numAndOper.add(Double.toString(result));
                }
                // If function, display "ERROR" (you can't enter an operator and then square/SQRT/factorial it) //
                else if (name.equals("x^2") || name.equals("SQRT") || name.equals("x!") ||
                        name.equals("sin(x)") || name.equals("cos(x)") || name.equals("tan(x)") || 
                        name.equals("e^x") || name.equals("ln(x)")) {
                    numAndOper.removeAll(numAndOper);
                    field.setText("ERROR");
                }
                // PI Case //
                else if (name.equals("π")) {
                    numAndOper.add(beforetext);
                    String aftertext = Double.toString(Math.PI);
                    field.setText(aftertext);
                }
                // e Case //
                else if (name.equals("e")) {
                    numAndOper.add(beforetext);
                    String aftertext = Double.toString(Math.E);
                    field.setText(aftertext);
                }
                // Else, add the operator to the ArrayList and display new number //
                else {
                    numAndOper.add(beforetext);
                    String aftertext = name;
                    field.setText(aftertext);
                }
            }
        }
    }
    /**
     * A function that takes an input ArrayList<String> of doubles and operators and returns the reduced result
     * @param al
     * @return
     */
    public double calc(ArrayList<String> al) {
        // Edge cases //
        if (al.size() == 0) {return 0.0;}
        if (al.size() == 1) {return Double.parseDouble(al.get(0));}
        else {
            double result = Double.parseDouble(al.get(0));
            // Iterates over oper-double pairs //
            for (int j = 1; j <= al.size() - 2; j+=2) {
                String oper = al.get(j);
                double nxt = Double.parseDouble(al.get(j+1));
                // Checks which operation to use //
                switch (oper) {
                    case "+":
                        result += nxt;
                        break;
                    case "-":
                        result -= nxt;
                        break;
                    case "x":
                        result *= nxt;
                        break;
                    case "÷":
                        result /= nxt;
                        break;
                }
            }
            return result;
        }
    }
    /**
     * Returns d!
     * @param d
     * @return
     */
    public double factorial(double d) {
        // Zero case //
        if (d==0) {return 1.0;}
        else {
            double result = 1.0;
            while (d > 1.0) {
                result*=d;
                d-=1;
            }
            return result;
        }
    }
}