package com.danieldev87.demo.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danieldev87.demo.domain.model.Recipe;
import com.danieldev87.demo.domain.service.RecipeService;
import com.danieldev87.demo.dto.RecipeDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Recetas", description = "Endpoints para gestionar las recetas de yogurt")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Crear receta", description = "Crea una nueva receta de yogurt con sus ingredientes y parámetros")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Receta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la receta inválidos")
    })
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.createRecipe(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar receta", description = "Actualiza los datos de una receta existente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener receta", description = "Retorna una receta específica por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    @Operation(summary = "Listar recetas", description = "Retorna todas las recetas activas disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recetas obtenidas correctamente"),
        @ApiResponse(responseCode = "404", description = "No hay recetas disponibles")
    })
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllActiveRecipes());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar recetas", description = "Busca recetas que coincidan con una palabra clave")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido")
    })
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String keyword) {
        return ResponseEntity.ok(recipeService.searchRecipes(keyword));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desactivar receta", description = "Desactiva una receta para que no esté disponible para nuevos lotes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta desactivada correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<Void> deactivateRecipe(@PathVariable Long id) {
        recipeService.deactivateRecipe(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activar receta", description = "Reactiva una receta previamente desactivada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta activada correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    public ResponseEntity<Void> activateRecipe(@PathVariable Long id) {
        recipeService.activateRecipe(id);
        return ResponseEntity.ok().build();
    }
}