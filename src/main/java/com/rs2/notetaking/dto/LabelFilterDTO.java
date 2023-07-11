package com.rs2.notetaking.dto;

import java.util.List;

import lombok.Data;

@Data
public class LabelFilterDTO {

    private List<Integer> labels;

    public LabelFilterDTO() {
    }
}
