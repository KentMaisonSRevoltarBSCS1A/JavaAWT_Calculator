
package calculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    
        // GUI components
    private final Frame frame; // Main window
    private final TextField textField; // Display field for input and results
    private String currentInput = ""; // Stores current input
    private double firstNum = 0; // Stores first operand
    private String operator = ""; // Stores selected operator

    public Calculator() {
        // Setting up the frame (window)
        frame = new Frame("Calculator");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);

        // Setting up the text field (display area)
        textField = new TextField();
        textField.setEditable(false); // Prevent manual input
        textField.setFont(new Font("Arial", Font.PLAIN, 35));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setPreferredSize(new Dimension(80, 80));

        frame.add(textField, BorderLayout.NORTH);

        // Panel to hold buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns
        buttonPanel.setBackground(Color.GRAY);

        // Button labels for numbers, operators, and functions
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "C", "+",
            "√", "%", "^", "=" // √ represents the square root symbol
        };

        // Creating buttons and adding them to the panel
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setFocusTraversalKeysEnabled(false);
            button.addActionListener(new ButtonClickListener(button)); // Add event listener
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Handling window close event
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                System.exit(0);
            }
        });
    }

    // Handles button clicks
    private class ButtonClickListener implements ActionListener {
        private final Button button;

        public ButtonClickListener(Button button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand(); // Get button label

            switch (command) {
                case "=" -> calculate(); // Perform calculation
                case "/", "*", "-", "+" -> {
                    if (!currentInput.isEmpty()) {
                        firstNum = Double.parseDouble(currentInput); // Store first number
                        operator = command; // Store operator
                        currentInput = ""; // Clear input for second number
                        textField.setText("");
                    }
                }
                case "C" -> { // Clear input and reset
                    currentInput = "";
                    firstNum = 0;
                    operator = "";
                    textField.setText(currentInput);
                }
                case "√" -> { // Square root
                    if (!currentInput.isEmpty()) {
                        double num = Double.parseDouble(currentInput);
                        currentInput = String.valueOf(Math.sqrt(num));
                        textField.setText(currentInput);
                    }
                }
                case "%" -> { // Percentage
                    if (!currentInput.isEmpty()) {
                        double num = Double.parseDouble(currentInput);
                        currentInput = String.valueOf(num / 100);
                        textField.setText(currentInput);
                    }
                }
                case "^" -> { // Square the number
                    if (!currentInput.isEmpty()) {
                        double num = Double.parseDouble(currentInput);
                        currentInput = String.valueOf(Math.pow(num, 2));
                        textField.setText(currentInput);
                    }
                }
                default -> { // Number or decimal input
                    if (currentInput.equals("0")) {
                        currentInput = command;
                    } else {
                        currentInput += command;
                    }
                    textField.setText(currentInput);
                }
            }
        }

        // Performs the calculation
        private void calculate() {
            if (currentInput.isEmpty()) return;

            double secondNum = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+" -> result = firstNum + secondNum;
                case "-" -> result = firstNum - secondNum;
                case "*" -> result = firstNum * secondNum;
                case "/" -> {
                    if (secondNum != 0) {
                        result = firstNum / secondNum;
                    } else {
                        textField.setText("Error"); // Handle division by zero
                        return;
                    }
                }
            }

            textField.setText(String.valueOf(result)); // Display result
            currentInput = String.valueOf(result); // Store result for further calculations
        }
    }

    public static void main(String[] args) {
        new Calculator(); // Start the calculator application
    }
}

