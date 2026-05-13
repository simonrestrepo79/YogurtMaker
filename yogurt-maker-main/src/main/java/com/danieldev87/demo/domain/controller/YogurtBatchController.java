package com.danieldev87.demo.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danieldev87.demo.domain.model.YogurtBatch;
import com.danieldev87.demo.domain.service.YogurtMakingService;
import com.danieldev87.demo.dto.FailBatchRequest;
import com.danieldev87.demo.dto.StartBatchRequest;
import com.danieldev87.demo.dto.TemperatureRecordDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
@Tag(name = "Lotes de Yogurt", description = "Endpoints para gestionar el ciclo de vida de los lotes de yogurt")
public class YogurtBatchController {

    private final YogurtMakingService yogurtMakingService;

    @PostMapping
    @Operation(summary = "Iniciar lote", description = "Crea e inicia un nuevo lote de yogurt basado en una receta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lote creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del lote inválidos")
    })
    public ResponseEntity<YogurtBatch> startNewBatch(@RequestBody StartBatchRequest request) {
        YogurtBatch batch = yogurtMakingService.startNewBatch(
            request.getRecipeId(),
            request.getCustomMilkVolume(),
            request.getCustomStarterAmount()
        );
        return new ResponseEntity<>(batch, HttpStatus.CREATED);
    }

    @PostMapping("/{batchId}/heating")
    @Operation(summary = "Iniciar calentamiento", description = "Cambia el estado del lote a fase de calentamiento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fase de calentamiento iniciada"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> startHeating(@PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startHeating(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/inoculating")
    @Operation(summary = "Iniciar inoculación", description = "Cambia el estado del lote a fase de inoculación del cultivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fase de inoculación iniciada"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> startInoculating(@PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startInoculating(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/incubation")
    @Operation(summary = "Iniciar incubación", description = "Cambia el estado del lote a fase de incubación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fase de incubación iniciada"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> startIncubation(@PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startIncubation(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/refrigeration")
    @Operation(summary = "Iniciar refrigeración", description = "Cambia el estado del lote a fase de refrigeración")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fase de refrigeración iniciada"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> startRefrigeration(@PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startRefrigeration(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/complete")
    @Operation(summary = "Completar lote", description = "Marca el lote como completado exitosamente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote completado correctamente"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> completeBatch(@PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.completeBatch(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/fail")
    @Operation(summary = "Marcar lote fallido", description = "Marca el lote como fallido e indica el motivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote marcado como fallido"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> markAsFailed(
            @PathVariable Long batchId,
            @RequestBody FailBatchRequest request) {
        YogurtBatch batch = yogurtMakingService.markAsFailed(batchId, request.getReason());
        return ResponseEntity.ok(batch);
    }

    @GetMapping
    @Operation(summary = "Listar lotes", description = "Retorna todos los lotes, con filtro opcional por estado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lotes obtenidos correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    public ResponseEntity<List<YogurtBatch>> getAllBatches(
            @RequestParam(required = false) YogurtBatch.BatchStatus status) {
        if (status != null) {
            return ResponseEntity.ok(yogurtMakingService.getBatchesByStatus(status));
        }
        return ResponseEntity.ok(yogurtMakingService.getAllBatches());
    }

    @GetMapping("/{batchId}")
    @Operation(summary = "Obtener lote", description = "Retorna la información detallada de un lote específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<YogurtBatch> getBatch(@PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.getBatch(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/temperature")
    @Operation(summary = "Registrar temperatura", description = "Registra una nueva lectura de temperatura para un lote activo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Temperatura registrada correctamente"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    public ResponseEntity<Void> recordTemperature(
            @PathVariable Long batchId,
            @RequestBody TemperatureRecordDTO request) {
        yogurtMakingService.recordTemperature(batchId, request.getTemperature(), request.getType());
        return ResponseEntity.ok().build();
    }
}