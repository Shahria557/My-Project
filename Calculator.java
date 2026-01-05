import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    JTextField display;
    double num1 = 0, num2 = 0;
    String operator = "";

    String[] buttons = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "="
    };

    JButton[] btn = new JButton[buttons.length];

    public Calculator() {
        setTitle("Calculator");
        setSize(320, 480);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        display = new JTextField("0");
        display.setBounds(10, 30, 285, 70);
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display);

        int x = 10, y = 120;
        int w = 65, h = 55;
        int gap = 10;

        for (int i = 0; i < buttons.length; i++) {
            btn[i] = new JButton(buttons[i]);
            btn[i].setFont(new Font("Arial", Font.BOLD, 18));
            btn[i].setFocusPainted(false);
            btn[i].addActionListener(this);

            if (buttons[i].equals("0")) {
                btn[i].setBounds(x, y, w * 2 + gap, h);
                x += w + gap;
            } else {
                btn[i].setBounds(x, y, w, h);
            }

            if ("÷×-+=".contains(buttons[i])) {
                btn[i].setBackground(new Color(255, 149, 0));
                btn[i].setForeground(Color.WHITE);
            } else if ("AC+/- %".contains(buttons[i])) {
                btn[i].setBackground(Color.LIGHT_GRAY);
            } else {
                btn[i].setBackground(new Color(50, 50, 50));
                btn[i].setForeground(Color.WHITE);
            }

            add(btn[i]);

            x += w + gap;
            if ((i + 1) % 4 == 0) {
                x = 10;
                y += h + gap;
            }
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9.]")) {
            if (display.getText().equals("0"))
                display.setText(cmd);
            else
                display.setText(display.getText() + cmd);
        } else if ("+-×÷".contains(cmd)) {
            num1 = Double.parseDouble(display.getText());
            operator = cmd;
            display.setText("0");
        } else if (cmd.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "×": result = num1 * num2; break;
                case "÷": result = num2 == 0 ? 0 : num1 / num2; break;
            }
            display.setText(String.valueOf(result));
        } else if (cmd.equals("AC")) {
            display.setText("0");
            num1 = num2 = 0;
        } else if (cmd.equals("+/-")) {
            double val = Double.parseDouble(display.getText());
            display.setText(String.valueOf(-val));
        } else if (cmd.equals("%")) {
            double val = Double.parseDouble(display.getText());
            display.setText(String.valueOf(val / 100));
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
