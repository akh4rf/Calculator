package Calculator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calculator extends JFrame {

    private JButton[] buttons;                      // Array that stores buttons for easy access
    public JLabel history;
    public JLabel field;                            // Displayed text area on top of calculator
    public ArrayList<String> currentNumAndOp;       // Pointer for current ArrayList, stores numbers & operators
    public Stack<ArrayList<String>> listStack;      // Stack for multi-level calculations and grouping with parentheses
    public boolean radians;                         // Radians mode if true, degrees mode if false
    public static void main(String[] args) {
        new Calculator();
    }

    //-------------------------------GUI PREVIEW-------------------------------//
    //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\//
    //-------------------------------------------------------------------------//
    //-                                                                       -//
    //-   3+(2x3)÷7                                                           -//
    //-                                                                       -//
    //-   6                                                                   -//
    //-                                                                       -//
    //-------------------------------------------------------------------------//
    //-      (          )        rad    +/-   CLR    7     8     9     ÷      -//
    //-     10^x     log10(x)    e^x   ln(x)  x^2    4     5     6     x      -//
    //-    sin(x)     cos(x)    tan(x)   π    SQRT   1     2     3     -      -//
    //-   arcsin(x)  arccos(x) arctan(x) e     x!    .     0     =     x      -//
    //-------------------------------------------------------------------------//

    public Calculator() {

        radians = true;
        currentNumAndOp = new ArrayList<>();
        listStack = new Stack<ArrayList<String>>();
        listStack.push(currentNumAndOp);
        JFrame frame = new JFrame(); // Creates empty window


        // Create button grid //
        String[] names = {"(",")","rad","+/-","CLR","7","8","9","\u00F7",
                            "10^x","log10(x)","e^x","ln(x)","x^2","4","5","6","x",
                            "sin(x)","cos(x)","tan(x)","\u03C0","SQRT","1","2","3","-",
                            "arcsin(x)","arccos(x)","arctan(x)","e","x!",".","0","=","+"};
        buttons = new JButton[36];
        for (int i = 0; i < 36; i++) {
            buttons[i] = new JButton(names[i]);
            buttons[i].addActionListener(new buttonListener()); // Adds ActionListener so buttons trigger an event
        }
        JPanel buttonGrid = new JPanel();                       // Creates JPanel for the 36 buttons
        buttonGrid.setLayout(new GridLayout(4, 9));             // Sets up 4x9 layout

        // Add buttons to grid //
        for (int i = 0; i < 36; i++) {
            buttonGrid.add(buttons[i]);
        }
        // Create container for everything //
        JPanel overall = new JPanel();
        overall.setLayout(new GridLayout(3, 1));
        // History field //
        history = new JLabel("");
        // Editable text field //
        field = new JLabel("0");
        // Add components //
        overall.add(history, BorderLayout.NORTH);
        overall.add(field, BorderLayout.CENTER);
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
            String historyText = history.getText();

            // CLEAR CASE //
            if (name.equals("CLR")) {
                // Clear function must clear both the current ArrayList & the overall Stack //
                currentNumAndOp = new ArrayList<String>();
                listStack.clear();
                listStack.push(currentNumAndOp);
                field.setText("0");
                history.setText("");
            }

            // rad/deg switch //
            else if (name.equals("rad")) {
                buttons[2].setText("deg");
                radians = false;
            }
            else if (name.equals("deg")) {
                buttons[2].setText("rad");
                radians = true;
            }
            
            // CASE 1: displayed text is not an operator //
            else if (!(beforetext.equals("\u00F7") || beforetext.equals("x") || beforetext.equals("-") || beforetext.equals("+"))) {
                double num = Double.parseDouble(beforetext);
                int divIndx = historyText.lastIndexOf("\u00F7");
                int multIndx = historyText.lastIndexOf("x");
                int subIndx = historyText.lastIndexOf("-");
                int addIndx = historyText.lastIndexOf("+");
                int indx = Math.max(Math.max(divIndx, multIndx), Math.max(subIndx, addIndx));
                String before = historyText.substring(0, indx+1);
                // If operator, add displayed number to ArrayList and display operator //
                if (name.equals("\u00F7") || name.equals("x") || name.equals("-") || name.equals("+")) {
                    if (currentNumAndOp.size()==0) {
                        currentNumAndOp.add(beforetext);
                    }
                    else if (!currentNumAndOp.get(0).equals(beforetext)) {
                        currentNumAndOp.add(beforetext);
                    }
                    field.setText(name);
                    history.setText(historyText+name);
                }
                // If equals, add the displayed number to ArrayList and calculate result //
                else if (name.equals("=")) {
                    currentNumAndOp.add(beforetext);
                    double result = calc(currentNumAndOp);
                    field.setText(Double.toString(result));
                    history.setText(Double.toString(result));
                    currentNumAndOp.removeAll(currentNumAndOp);
                    currentNumAndOp.add(Double.toString(result));
                }
                // If x-squared, square displayed number //
                else if (name.equals("x^2")) {
                    String squared = Double.toString(Math.pow(num, 2));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(squared);
                    }
                    field.setText(squared);
                    history.setText(before+squared);
                }
                // If square-root, perform sqrt on displayed number //
                else if (name.equals("SQRT")) {
                    String sqrt = Double.toString(Math.pow(num, 0.5));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(sqrt);
                    }
                    field.setText(sqrt);
                    history.setText(before+sqrt);
                }
                // If !, perform factorial on displayed number //
                else if (name.equals("x!")) {
                    // Solves problem where 171! and above return "infinity" //
                    if (num > 170) {
                        currentNumAndOp.removeAll(currentNumAndOp);
                        field.setText("LIMIT EXCEEDED");
                    }
                    // Disallows non-integer inputs //
                    else if ((num == Math.floor(num)) && !Double.isInfinite(num)) {
                        String fac = Double.toString(factorial(num));
                        // Edge case when size = 1, must replace saved value with new value //
                        if (currentNumAndOp.size()==1) {
                            currentNumAndOp.remove(0);
                            currentNumAndOp.add(fac);
                        }
                        field.setText(fac);
                        history.setText(historyText+"!");
                    }
                    // Scenario when input is not an integer //
                    else {
                        currentNumAndOp = new ArrayList<String>();
                        listStack.clear();
                        listStack.push(currentNumAndOp);
                        field.setText("ERROR: MUST BE INTEGER");
                        history.setText("");
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
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(sin);
                    }
                    field.setText(sin);
                    history.setText(before+sin);
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
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(cos);
                    }
                    field.setText(cos);
                    history.setText(before+cos);
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
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(tan);
                    }
                    field.setText(tan);
                    history.setText(before+tan);
                }
                // If arcsin(x), perform arcsin(x) on displayed number //
                else if (name.equals("arcsin(x)")) {
                    String asin;
                    // Radian Case //
                    if (radians) {
                        asin = Double.toString(Math.asin(num));
                    }
                    // Degree Case //
                    else {
                        asin = Double.toString(Math.toDegrees(Math.asin(num)));
                    }
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(asin);
                    }
                    field.setText(asin);
                    history.setText(before+asin);
                }
                // If arccos(x), perform arccos(x) on displayed number //
                else if (name.equals("arccos(x)")) {
                    String acos;
                    // Radian Case //
                    if (radians) {
                        acos = Double.toString(Math.acos(num));
                    }
                    // Degree Case //
                    else {
                        acos = Double.toString(Math.toDegrees(Math.acos(num)));
                    }
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(acos);
                    }
                    field.setText(acos);
                    history.setText(before+acos);
                }
                // If arctan(x), perform arctan(x) on displayed number //
                else if (name.equals("arctan(x)")) {
                    String atan;
                    // Radian Case //
                    if (radians) {
                        atan = Double.toString(Math.atan(num));
                    }
                    // Degree Case //
                    else {
                        atan = Double.toString(Math.toDegrees(Math.atan(num)));
                    }
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(atan);
                    }
                    field.setText(atan);
                    history.setText(before+atan);
                }
                // If e^x, perform e^x on displayed number //
                else if (name.equals("e^x")) {
                    String eToX = Double.toString(Math.pow(Math.E, num));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(eToX);
                    }
                    field.setText(eToX);
                    history.setText(before+eToX);
                }
                // If ln(x), perform natural log on displayed number //
                else if (name.equals("ln(x)")) {
                    // Edge case when num = 0 //
                    if (num == 0) {
                        currentNumAndOp = new ArrayList<String>();
                        listStack.clear();
                        listStack.push(currentNumAndOp);
                        history.setText("");
                        field.setText("ERROR");
                    }
                    else {
                        String lnx = Double.toString(Math.log(num));
                        // Edge case when size = 1, must replace saved value with new value //
                        if (currentNumAndOp.size()==1) {
                            currentNumAndOp.remove(0);
                            currentNumAndOp.add(lnx);
                        }
                        field.setText(lnx);
                        history.setText(before+lnx);
                    }
                }
                // If 10^x, perform 10^x on displayed number //
                else if (name.equals("10^x")) {
                    String tenToX = Double.toString(Math.pow(10, num));
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(tenToX);
                    }
                    field.setText(tenToX);
                    history.setText(before+tenToX);
                }
                // If log10(x), perform log base 10 on displayed number //
                else if (name.equals("log10(x)")) {
                    
                    // Edge case when num = 0 //
                    if (num == 0) {
                        currentNumAndOp = new ArrayList<String>();
                        listStack.clear();
                        listStack.push(currentNumAndOp);
                        history.setText("");
                        field.setText("ERROR");
                    }
                    else {
                        String logTenX = Double.toString(Math.log10(num));
                        // Edge case when size = 1, must replace saved value with new value //
                        if (currentNumAndOp.size()==1) {
                            currentNumAndOp.remove(0);
                            currentNumAndOp.add(logTenX);
                        }
                        field.setText(logTenX);
                        history.setText(before+logTenX);
                    }
                }
                // If π, replace diplayed number with π //
                else if (name.equals("\u03C0")) {
                    String piString = Double.toString(Math.PI);
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(piString);
                    }
                    field.setText(piString);
                    history.setText(before+piString);
                }
                // If e, replace diplayed number with e //
                else if (name.equals("e")) {
                    String eString = Double.toString(Math.E);
                    // Edge case when size = 1, must replace saved value with new value //
                    if (currentNumAndOp.size()==1) {
                        currentNumAndOp.remove(0);
                        currentNumAndOp.add(eString);
                    }
                    field.setText(eString);
                    history.setText(before+eString);
                }
                // +/- button //
                else if (name.equals("+/-")) {
                    String complement = Double.toString(num*=-1);
                        // Edge case when size = 1, must replace saved value with new value //
                        if (currentNumAndOp.size()==1) {
                            currentNumAndOp.remove(0);
                            currentNumAndOp.add(complement);
                        }
                        field.setText(complement);
                        history.setText(before+complement);
                }
                // Open Parenthesis //
                else if (name.equals("(")) {
                    // Since displayed text is a number, we will    //
                    // multiply that by what's in the parentheses   //
                    if (currentNumAndOp.size()>0) {
                        // Edge case when 0 is only number in memory //
                        if (Double.parseDouble(beforetext)==0.0 && currentNumAndOp.size()==1) {
                            history.setText("");
                            currentNumAndOp.remove(0);
                            currentNumAndOp.add("1");
                        }
                        currentNumAndOp.add("x");
                    }
                    // Edge case when first started, i.e. nothing in memory //
                    else {
                        currentNumAndOp.add("0");
                        currentNumAndOp.add("+");
                    }
                    // Add new ArrayList to stack & move pointer    //
                    ArrayList<String> newNumAndOp = new ArrayList<String>();
                    listStack.push(newNumAndOp);
                    currentNumAndOp = newNumAndOp;
                    field.setText("0");
                    historyText = history.getText();
                    history.setText(historyText+name);
                }
                // Close Parenthesis //
                else if (name.equals(")")) {
                    currentNumAndOp.add(beforetext);
                    double result = calc(currentNumAndOp);
                    field.setText(Double.toString(result));
                    // Remove ArrayList from stack & move pointer   //
                    currentNumAndOp.removeAll(currentNumAndOp);
                    listStack.pop();
                    currentNumAndOp = listStack.peek();
                    history.setText(historyText+name);
                }
                // Else, add another digit to number //
                else {
                    // Disallow appending to π/e approximations & calc function results //
                    if (!(beforetext.equals(Double.toString(Math.PI)) || beforetext.equals(Double.toString(Math.E))) 
                            && !(currentNumAndOp.size()==1)) {
                        String aftertext = beforetext;
                        if (name.equals(".")) {
                            // Disallow multiple decimal points //
                            if (!beforetext.contains(".")) {
                                aftertext += name;
                            }
                        }
                        else {aftertext += name;}
                        field.setText(aftertext);
                        history.setText(historyText+name);
                    }
                }
            }

            // CASE 2: displayed text is an operator //
            else {
                // If new operator, replace old one //
                if (name.equals("\u00F7") || name.equals("x") || name.equals("-") || name.equals("+")) {
                    field.setText(name);
                    history.setText(historyText.substring(0,historyText.length()-1)+name);
                }
                // If equals, disregard displayed operator and calculate result //
                else if (name.equals("=")) {
                    double result = calc(currentNumAndOp);
                    field.setText(Double.toString(result));
                    history.setText(Double.toString(result));
                    currentNumAndOp.removeAll(currentNumAndOp);
                    currentNumAndOp.add(Double.toString(result));
                }
                // If function, display "ERROR" (you can't enter an operator and then square/SQRT/factorial it) //
                else if (name.equals("x^2") || name.equals("SQRT") || name.equals("x!") ||
                        name.equals("sin(x)") || name.equals("cos(x)") || name.equals("tan(x)") || 
                        name.equals("arcsin(x)") || name.equals("arccos(x)") || name.equals("arctan(x)") || 
                        name.equals("e^x") || name.equals("ln(x)") || name.equals("+/-") ||
                        name.equals("log10(x)") || name.equals("10^x")) {

                    currentNumAndOp = new ArrayList<String>();
                    listStack.clear();
                    listStack.push(currentNumAndOp);
                    history.setText("");
                    field.setText("ERROR");
                }
                // π Case //
                else if (name.equals("\u03C0")) {
                    currentNumAndOp.add(beforetext);
                    String aftertext = Double.toString(Math.PI);
                    field.setText(aftertext);
                    history.setText(historyText+aftertext);
                }
                // e Case //
                else if (name.equals("e")) {
                    currentNumAndOp.add(beforetext);
                    String aftertext = Double.toString(Math.E);
                    field.setText(aftertext);
                    history.setText(historyText+aftertext);
                }
                // Open Parenthesis //
                else if (name.equals("(")) {
                    currentNumAndOp.add(beforetext);
                    // Add new ArrayList to stack & move pointer    //
                    ArrayList<String> newNumAndOp = new ArrayList<String>();
                    listStack.push(newNumAndOp);
                    currentNumAndOp = newNumAndOp;
                    field.setText("0");
                    history.setText(historyText+name);
                }
                // Close Parenthesis //
                else if (name.equals(")")) {
                    double result = calc(currentNumAndOp);
                    field.setText(Double.toString(result));
                    // Remove ArrayList from stack & move pointer   //
                    currentNumAndOp.removeAll(currentNumAndOp);
                    listStack.pop();
                    currentNumAndOp = listStack.peek();
                    history.setText(historyText+name);
                }
                // Else, add the operator to the ArrayList and display new number //
                else {
                    currentNumAndOp.add(beforetext);
                    field.setText(name);
                    history.setText(historyText+name);
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
        int size = al.size();
        // Edge cases //
        if (al.size() == 0) {return 0.0;}
        if (al.size() == 1) {return Double.parseDouble(al.get(0));}
        else {
            // Handle multiplication and division first, moving left-to-right //
            for (int i = 1; i <= size - 2; i+=2) {
                switch (al.get(i)) {
                    case "x":
                        double mult = Double.parseDouble(al.get(i-1))*Double.parseDouble(al.get(i+1));
                        al.set(i-1, Double.toString(mult));
                        al.remove(i);
                        al.remove(i);
                        // Set to -1, as loop will add 2 //
                        i=-1;
                        size = al.size();
                        break;
                    case "\u00F7":
                        double div = Double.parseDouble(al.get(i-1))/Double.parseDouble(al.get(i+1));
                        al.set(i-1, Double.toString(div));
                        al.remove(i);
                        al.remove(i);
                        // Set to -1, as loop will add 2 //
                        i=-1;
                        size = al.size();
                        break;
                }
            }
            // Now handle addition and subtraction, again left-to-right //
            double result = Double.parseDouble(al.get(0));
            // Iterates over oper-double pairs //
            for (int j = 1; j <= size - 2; j+=2) {
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
