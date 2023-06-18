package ru.ruszhu.glsheets.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ruszhu.glsheets.model.Cell;
import ru.ruszhu.glsheets.service.CellService;

@Controller
@RequiredArgsConstructor
public class CellController {

    private final CellService service;

    @GetMapping("/")
    public String getTable(Model model) {
        Cell[][] allCells = service.getAllCells();
        model.addAttribute("cells", allCells);
        return "index";
    }

    @PostMapping("/calculate")
    @ResponseBody
    public Cell updateCell(@RequestBody Cell cell) {
        Cell updatedCell = service.updateCell(cell);
        return updatedCell;
    }
}
