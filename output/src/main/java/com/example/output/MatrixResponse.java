package com.example.output;

import lombok.Data;

import java.util.List;

@Data
public class MatrixResponse {

    private List<List<Integer>> matrix;
}
