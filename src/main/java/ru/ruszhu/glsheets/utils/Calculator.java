package ru.ruszhu.glsheets.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ruszhu.glsheets.model.Cell;

import java.util.Arrays;
import java.util.Stack;

public class Calculator {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);
    private static Cell[][] table;

    private Calculator() {
    }

    public static String calculate(Cell[][] table, Cell cell) {
        Calculator.table = table;
        String data = cell.getValue();
        String expression = validFormula(data);
        if (expression == null) {
            int column = cell.getColumn() - 'A';
            int row = cell.getRow() - 1;
            return table[row][column].getValue();
        }
        double result = Evaluator.evaluateExpression(expression);
        String res = result % 1 == 0 ? String.valueOf((int)result) : String.format("%.2f", result);
        return res;
    }

    private static String validFormula(String data) {
        data = data.replaceAll("\s+", "").trim();
        String[] operands = data.substring(1).split("[+*/)(-]");
        String[] operations = Arrays.stream(data.split("[^-+*/()]")).filter(e -> e.trim().length() > 0)
                                    .toArray(String[]::new);
        String[] newOperands = new String[operands.length];
        for (int i = 0; i < operands.length; i++) {
            if (!isNumeric(operands[i])) {
                String newValue = cellNumberValue(operands[i]);
                if (newValue == null || !isNumeric(newValue)) {
                    log.error("Cannot transform expression! Some of the values are not int value");
                    return null;
                }
                newOperands[i] = newValue;
            } else {
                newOperands[i] = operands[i];
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=");
        Stack<String> operandsStack = new Stack<>();
        Stack<String> operationsStack = new Stack<>();
        for (int i = newOperands.length - 1; i >= 0; i--) {
            operandsStack.add(newOperands[i]);
        }
        for (int i = operations.length - 1; i >= 0; i--) {
            operationsStack.add(operations[i]);
        }
        while (!operandsStack.isEmpty()) {
            sb.append(operandsStack.pop());
            if (!operationsStack.isEmpty()) {
                sb.append(operationsStack.pop());
            }
        }
        return sb.toString();
    }

    private static String cellNumberValue(String cell) {
        char column = cell.charAt(0);
        String row = cell.substring(1);
        if (!isNumeric(row)) {
            return null;
        }
        int x = Integer.parseInt(row) - 1;
        int y = column - 'A';
        if (x < table.length && y < table[0].length) {
            return Calculator.table[x][y].getValue();
        }
        return null;
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
