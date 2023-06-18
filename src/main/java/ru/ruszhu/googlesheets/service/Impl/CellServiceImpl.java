package ru.ruszhu.googlesheets.service.Impl;

import org.springframework.stereotype.Service;
import ru.ruszhu.googlesheets.model.Cell;
import ru.ruszhu.googlesheets.service.CellService;
import ru.ruszhu.googlesheets.utils.Calculator;

@Service
public class CellServiceImpl implements CellService {

    private Cell[][] table;

    public CellServiceImpl() {
        init();
    }

    private void init() {
        Cell[][] table = new Cell[4][4];
        for (int i = 0; i < 4; i++) {
            table[i] = new Cell[]{new Cell(), new Cell(), new Cell(), new Cell()};
        }
        int k = 1;
        for (int i = 0; i < 4; i++) {
            char st = 'A';
            for (int j = 0; j < 4; j++) {
                table[i][j].setId(k);
                table[i][j].setColumn(st);
                table[i][j].setRow(i + 1);
                table[i][j].setValue(String.valueOf(k));
                k++;
                st++;
            }
        }
        this.table = table;
    }

    @Override
    public Cell updateCell(Cell cell) {
        Cell updatedCell = new Cell();
        String newValue = cell.getValue();
        if (isFormula(cell)) {
             newValue = Calculator.calculate(table, cell);
        }
        updatedCell.setId(cell.getId());
        updatedCell.setColumn(cell.getColumn());
        updatedCell.setRow(cell.getRow());
        updatedCell.setValue(newValue);
        int x = cell.getRow() - 1;
        int y = cell.getColumn() - 'A';
        this.table[x][y] = updatedCell;
        return updatedCell;
    }

    @Override
    public Cell[][] getAllCells() {
        return this.table;
    }

    private boolean isFormula(Cell cell) {
        String data = cell.getValue();
        return data.startsWith("=");
    }
}
