package me.fillnet.recipebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import me.fillnet.recipebook.service.FileServiceRecipe;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesControllerRecipe {
    private final FileServiceRecipe fileServiceRecipe;

    public FilesControllerRecipe(FileServiceRecipe fileServiceRecipe) {
        this.fileServiceRecipe = fileServiceRecipe;
    }

    @GetMapping(value = "/export/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Сохранение рецепта в файл",
            description = "Сохранение рецепта в файл"
    )
    public ResponseEntity<InputStreamResource> downloadDataFileRecipe() throws FileNotFoundException {
        File file = fileServiceRecipe.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"RecipeData.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка рецепта из файла",
            description = "Загрузка рецепта из файла"
    )
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
}
