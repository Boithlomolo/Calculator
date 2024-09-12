import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator {
    private JFrame frame;
    private JTextField display;
    private RoundedButton[] buttons; 
    private JPanel buttonPanel;
    private double num1, num2, result;
    private char operation;

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout(10, 10)); 

        display = new JTextField(20);
        display.setFont(new Font("Arial", Font.PLAIN, 40));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(Color.DARK_GRAY);

        String[] buttonLabels = {
            "√", "^", "^2", "C",
            "7", "8", "9", "÷",
            "4", "5", "6", "x",
            "1", "2", "3", "-",
            ".", "0", "+", "=",
        };

        buttons = new RoundedButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new RoundedButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());

            int finalI = i;
            buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    buttons[finalI].setBackground(Color.LIGHT_GRAY);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    buttons[finalI].setBackground(Color.GRAY);
                }
            });

            buttons[i].addActionListener(new ButtonListener());
            buttonPanel.add(buttons[i]);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); 

            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30).contains(x, y);
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("=")) {
                num2 = Double.parseDouble(display.getText());
                switch (operation) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case 'x':
                        result = num1 * num2;
                        break;
                    case '÷':
                        result = num1 / num2;
                        break;
                    case '^':
                        result = Math.pow(num1, num2); 
                        break;
                }
                display.setText(String.valueOf(result));
            } else if (command.equals("C")) {
                display.setText(""); 
                num1 = num2 = result = 0;
            } else if (command.equals("√")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.sqrt(num1);
                display.setText(String.valueOf(result));
            } else if (command.equals("^2")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.pow(num1, 2); 
                display.setText(String.valueOf(result));
            } else if (command.equals("+") || command.equals("-") || command.equals("x") || command.equals("÷") || command.equals("^")) {
                num1 = Double.parseDouble(display.getText());
                operation = command.charAt(0);
                display.setText("");
            } else if (command.equals(".")) {
                display.setText(display.getText() + ".");
            } else {
                display.setText(display.getText() + command);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new); 
    }
}
