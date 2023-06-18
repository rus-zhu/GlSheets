package ru.ruszhu.googlesheets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    private Integer id;
    private Character column;
    private Integer row;
    private String value;
}
