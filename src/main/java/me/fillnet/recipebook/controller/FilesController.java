package me.fillnet.recipebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import me.fillnet.recipebook.service.FileServiceIngredients;
import me.fillnet.recipebook.service.FileServiceRecipe;
import me.fillnet.recipebook.service.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")

public class FilesController {

    private final FileServiceRecipe fileServiceRecipe;
    private final FileServiceIngredients fileServiceIngredients;
    private RecipeService recipeService;

    public FilesController(FileServiceRecipe fileServiceRecipe
            , FileServiceIngredients fileServiceIngredients
            , RecipeService recipeService) {
        this.fileServiceRecipe = fileServiceRecipe;
        this.recipeService = recipeService;
        this.fileServiceIngredients = fileServiceIngredients;
    }

    @Operation(
            summary = "Сохранение ингредиента в файл",
            description = "Сохранение ингредиента в файл"
    )
    @GetMapping(value = "/export/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = fileServiceIngredients.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"IngredientsData.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @Operation(
            summary = "Загрузка ингредиента из файла",
            description = "Загрузка ингредиента из файла"
    )
    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        fileServiceIngredients.cleanDataFile();
        File dataFileIngredient = fileServiceIngredients.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFileIngredient)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(
            summary = "Загрузка Рецепта из файла",
            description = "Загрузка Рецепта из файла"
    )
    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFileRecipe(@RequestParam MultipartFile file) {
        fileServiceRecipe.cleanDataFile();
        File dataFileRecipe = fileServiceRecipe.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFileRecipe)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @Operation(
            summary = "Сохранить все рецепты",
            description = "в формате txt"
    )
    @GetMapping("/file")
    public ResponseEntity<InputStreamResource> getRecipeTxt() {
        try {
            Path path = recipeService.createRecipeFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesFile.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

