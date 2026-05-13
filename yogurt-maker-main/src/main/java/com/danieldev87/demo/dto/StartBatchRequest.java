package com.danieldev87.demo.dto;

import lombok.Data;

@Data
public class StartBatchRequest {
    private Long recipeId;
    private Double customMilkVolume;
    private Double customStarterAmount;
}
