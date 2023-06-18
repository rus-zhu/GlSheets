package ru.ruszhu.googlesheets.service;

import ru.ruszhu.googlesheets.model.Cell;

public interface CellService {
    Cell updateCell(Cell cell);

    Cell[][] getAllCells();
}
