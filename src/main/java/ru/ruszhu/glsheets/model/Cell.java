package ru.ruszhu.glsheets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    private Integer id;
    private Character column;
    private Integer row;
    private String value;
}
