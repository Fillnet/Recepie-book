package me.fillnet.recipebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import me.fillnet.recipebook.service.FileServiceIngredients;
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
public class FilesControllerIngredients {
    private final FileServiceIngredients fileServiceIngredients;

    public FilesControllerIngredients(FileServiceIngredients fileServiceIngredients) {
        this.fileServiceIngredients = fileServiceIngredients;
    }


    @GetMapping(value = "/export/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Сохранение ингредиента в файл",
            description = "Сохранение ингредиента в файл"
    )
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
    @PostMapping(value = "/import/ingredient",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка ингредиента из файла",
            description = "Загрузка ингредиента из файла"
    )
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
}
