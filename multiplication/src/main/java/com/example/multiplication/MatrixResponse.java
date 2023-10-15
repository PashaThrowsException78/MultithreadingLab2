package com.example.multiplication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MatrixResponse {

    private List<List<Integer>> matrix;
}
