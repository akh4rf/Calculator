import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridLayout;

public class Calculator extends JFrame {

    private JButton[] buttons;
    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {

        JFrame frame = new JFrame(); // Creates empty window

        buttons = new JButton[16];
        // Top Row//
        buttons[0] = new JButton("9");
        buttons[1] = new JButton("8");
        buttons[2] = new JButton("7");
        buttons[3] = new JButton("÷");
        // Second Row //
        buttons[4] = new JButton("6");
        buttons[5] = new JButton("5");
        buttons[6] = new JButton("4");
        buttons[7] = new JButton("x");
        // Third Row //
        buttons[8] = new JButton("3");
        buttons[9] = new JButton("2");
        buttons[10] = new JButton("1");
        buttons[11] = new JButton("-");
        // Bottom Row //
        buttons[12] = new JButton(".");
        buttons[13] = new JButton("0");
        buttons[14] = new JButton("=");
        buttons[15] = new JButton("+");

        JPanel buttonGrid = new JPanel(); // Creates JPanel for the 16 buttons
        buttonGrid.setLayout(new GridLayout(4, 4)); // Sets up 4x4 layout

        // Add buttons to grid //
        for (int i = 0; i < 16; i++) {
            buttonGrid.add(buttons[i]);
        }

        // Add button grid to frame //
        frame.add(buttonGrid, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calculator");
        frame.pack();
        frame.setVisible(true);

    }
}