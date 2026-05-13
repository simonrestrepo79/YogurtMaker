package com.danieldev87.demo.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "temperature_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Registro de temperatura de un lote de yogurt")
public class TemperatureLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del registro")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    @Schema(description = "Lote al que pertenece este registro")
    private YogurtBatch batch;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura registrada en °C")
    private Double temperature;
    
    @Column(nullable = false)
    @Schema(description = "Fecha y hora del registro")
    private LocalDateTime recordedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Tipo de registro de temperatura")
    private LogType type;
    
    @Schema(description = "Notas adicionales del registro")
    private String notes;
    
    public enum LogType {
        HEATING, COOLING, INCUBATION, REFRIGERATION, MANUAL
    }
}