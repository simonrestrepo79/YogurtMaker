package com.danieldev87.demo.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Ingrediente usado en una receta")
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ingrediente")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre del ingrediente")
    private String name;
    
    @Schema(description = "Cantidad del ingrediente")
    private Double quantity;
    
    @Schema(description = "Unidad de medida (kg, g, ml, cucharadas, etc.)")
    private String unit;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Schema(description = "Receta a la que pertenece")
    private Recipe recipe;
    
    @Schema(description = "Notas adicionales")
    private String notes;
    
    @Column(nullable = false)
    @Schema(description = "Indica si el ingrediente es opcional")
    private Boolean optional;
}