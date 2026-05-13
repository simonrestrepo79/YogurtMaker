package com.danieldev87.demo.domain.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Receta de yogurt con sus parámetros de producción")
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la receta")
    private Long id;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre único de la receta")
    private String name;
    
    @Schema(description = "Descripción general de la receta")
    private String description;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Builder.Default
    @Schema(description = "Lista de ingredientes de la receta")
    private List<Ingredient> ingredients = new ArrayList<>();
    
    @Column(nullable = false)
    @Schema(description = "Volumen de leche por defecto en litros")
    private Double defaultMilkVolume;
    
    @Column(nullable = false)
    @Schema(description = "Cantidad de cultivo iniciador por defecto en cucharadas")
    private Double defaultStarterAmount;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura de calentamiento en °C")
    private Double heatingTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Duración del calentamiento en minutos")
    private Integer heatingDuration;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura de inoculación en °C")
    private Double inoculationTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura de incubación en °C")
    private Double incubationTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo mínimo de incubación en horas")
    private Integer minIncubationTime;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo máximo de incubación en horas")
    private Integer maxIncubationTime;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo de refrigeración en horas")
    private Integer refrigerationTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Nivel de dificultad de la receta")
    private DifficultyLevel difficulty;
    
    @Schema(description = "Consejos y recomendaciones para la receta")
    private String tips;
    
    @Column(nullable = false)
    @Schema(description = "Indica si la receta está activa y disponible")
    private Boolean active;
    
    public enum DifficultyLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}