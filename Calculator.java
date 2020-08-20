import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;

public class Calculator extends JFrame {

    private JButton[] buttons;
    public JLabel field;
    public ArrayList<String> numAndOper;
    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {

        numAndOper = new ArrayList<>();
        JFrame frame = new JFrame(); // Creates empty window
        // Create button grid //
        String[] names = {"9","8","7","÷","6","5","4","x","3","2","1","-",".","0","=","+"};
        buttons = new JButton[16];
        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton(names[i]);
            buttons[i].addActionListener(new buttonListener()); // Adds ActionListener so buttons trigger an event
        }
        JPanel buttonGrid = new JPanel(); // Creates JPanel for the 16 buttons
        buttonGrid.setLayout(new GridLayout(4, 4)); // Sets up 4x4 layout

        // Add buttons to grid //
        for (int i = 0; i < 16; i++) {
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

            // CASE 1: displayed text is not an operator //
            if (!(beforetext.equals("÷") || beforetext.equals("x") || beforetext.equals("-") || beforetext.equals("+"))) {
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
        if (al.size() == 0) {return 0;}
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
}