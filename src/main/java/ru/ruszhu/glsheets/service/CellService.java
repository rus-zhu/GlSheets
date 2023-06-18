package ru.ruszhu.glsheets.service;

import ru.ruszhu.glsheets.model.Cell;

public interface CellService {
    Cell updateCell(Cell cell);

    Cell[][] getAllCells();
}
