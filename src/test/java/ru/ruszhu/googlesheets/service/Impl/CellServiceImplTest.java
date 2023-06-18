package ru.ruszhu.googlesheets.service.Impl;

import org.junit.jupiter.api.Test;
import ru.ruszhu.googlesheets.model.Cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellServiceImplTest {

    @Test
    public void testUpdateCorrectFormulaAndCellValueShouldReturnOK() throws Exception {
        CellServiceImpl service = new CellServiceImpl();
        Cell cellRequest = new Cell(4, 'D', 1, "=B1+C1");
        service.updateCell(cellRequest);

        String value = service.getAllCells()[0][3].getValue();

        assertEquals("5", value);
    }

    @Test
    public void testUpdateMixDataShouldReturnOK() throws Exception {
        CellServiceImpl service = new CellServiceImpl();
        Cell cellRequest = new Cell(1, 'A', 1, "=D2/4");
        service.updateCell(cellRequest);

        String value = service.getAllCells()[0][0].getValue();

        assertEquals("2", value);
    }

    @Test
    public void testUpdateTextDataShouldReturnTextItself() throws Exception {
        CellServiceImpl service = new CellServiceImpl();
        Cell cellRequest = new Cell(4, 'D', 1, "Hello");
        service.updateCell(cellRequest);

        String value = service.getAllCells()[0][3].getValue();

        assertEquals("Hello", value);
    }

    @Test
    public void testUpdateMixTextDataShouldReturnException() throws Exception {
        CellServiceImpl service = new CellServiceImpl();
        Cell cellRequest1 = new Cell(4, 'D', 1, "sometext");
        Cell cellRequest2 = new Cell(1, 'A', 1, "=D2+D1");
        service.updateCell(cellRequest1);
        service.updateCell(cellRequest2);
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.updateCell(cellRequest2));
//        assertEquals("Cannot transform expression to int value", exception.getMessage());
        Cell[][] cells = service.getAllCells();
        assertEquals(cellRequest1.getValue(), cells[0][3].getValue());
    }

    @Test
    public void testUpdateMixDataShouldReturnException() throws Exception {
        CellServiceImpl service = new CellServiceImpl();
        Cell[][] cells = service.getAllCells();
        String oldValue = cells[0][0].getValue();
        Cell cellRequest = new Cell(1, 'A', 1, "=D2+sometext");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.updateCell(cellRequest));
//        assertEquals("Cannot transform expression to int value", exception.getMessage());
        service.updateCell(cellRequest);
        Cell[][] cells1 = service.getAllCells();
        assertEquals(oldValue, cells1[0][0].getValue());
    }
}