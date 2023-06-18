package ru.ruszhu.glsheets.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Evaluator {
    private static final Map<Character, Integer> precedence = new HashMap<>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    public static double evaluateExpression(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                double value = Double.parseDouble(sb.toString());
                values.push(value);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    applyOperator(values, operators);
                }
                operators.pop();
            } else if (precedence.containsKey(c)) {
                while (!operators.isEmpty() && operators.peek() != '(' && precedence.get(operators.peek()) >= precedence.get(c)) {
                    applyOperator(values, operators);
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(values, operators);
        }

        return values.pop();
    }

    private static void applyOperator(Stack<Double> values, Stack<Character> operators) {
        char operator = operators.pop();
        double right = values.pop();
        double left = values.pop();
        double result = switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> left / right;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
        values.push(result);
    }
}
