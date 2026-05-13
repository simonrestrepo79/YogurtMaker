package com.danieldev87.demo.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "yogurt_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Lote de producción de yogurt")
public class YogurtBatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del lote")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Código único del lote generado automáticamente")
    private String batchCode;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Schema(description = "Receta usada para este lote")
    private Recipe recipe;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Estado actual del lote")
    private BatchStatus status;
    
    @Column(nullable = false)
    @Schema(description = "Volumen de leche en litros")
    private Double milkVolume;
    
    @Column(nullable = false)
    @Schema(description = "Cantidad de cultivo iniciador en cucharadas")
    private Double starterAmount;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura objetivo en °C")
    private Double targetTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo de incubación en horas")
    private Integer incubationTime;
    
    @Schema(description = "Fecha y hora de inicio del lote")
    private LocalDateTime startTime;
    @Schema(description = "Fecha y hora de inicio de incubación")
    private LocalDateTime incubationStartTime;
    @Schema(description = "Fecha y hora de fin de incubación")
    private LocalDateTime incubationEndTime;
    @Schema(description = "Fecha y hora de inicio de refrigeración")
    private LocalDateTime refrigerationStartTime;
    
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    @Builder.Default
    @Schema(description = "Registros de temperatura del lote")
    private List<TemperatureLog> temperatureLogs = new ArrayList<>();
    
    @Schema(description = "Notas adicionales del lote")
    private String notes;
    
    @Column(nullable = false)
    @Schema(description = "Fecha y hora de creación del lote")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha y hora de última actualización")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        batchCode = "YB-" + System.currentTimeMillis();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum BatchStatus {
        PREPARING, 
        HEATING, 
        COOLING, 
        INOCULATING, 
        INCUBATING, 
        REFRIGERATING, 
        COMPLETED, 
        FAILED
    }
}