package com.example.Translation.Management.Service.Controller;

import com.example.Translation.Management.Service.DTO.PagedResponse;
import com.example.Translation.Management.Service.DTO.TranslationExportDto;
import com.example.Translation.Management.Service.DTO.TranslationTextDto;
import com.example.Translation.Management.Service.DTO.TranslationTextRequestDto;
import com.example.Translation.Management.Service.DTO.TranslationUpdateRequestDto;
import com.example.Translation.Management.Service.Servc.TranslationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/translations")
@Tag(name = "Translations", description = "APIs to manage multilingual translations")
public class TranslationController {

    private final TranslationService service;

    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new translation", description = "Creates a new translation entry. If the same key and locale already exist, the existing translation will be updated.")
    @PostMapping
    public ResponseEntity<TranslationTextDto> create(@RequestBody TranslationTextRequestDto request) {
        TranslationTextDto dto = service.createOrUpdate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Update translation by ID", description = "Updates an existing translation entry using its database ID.")
    @PutMapping("/{id}")
    public ResponseEntity<TranslationTextDto> update(@PathVariable Long id, @RequestBody TranslationUpdateRequestDto request) {
        TranslationTextDto dto = service.update(id, request);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Get translation by ID", description = "Fetches a translation entry using its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TranslationTextDto> getById(@PathVariable Long id) {
        return service.getById(id)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Search translations", description = "Searches translation entries by locale, tag, or free-text. Supports pagination.")
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<TranslationTextDto>> search(
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PagedResponse<TranslationTextDto> response = service.search(locale, tag, q, PageRequest.of(page, size));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Export translations", description = "Exports translations in JSON format. Optionally filter by locale.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Streamed JSON array of translations")
    })
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> export(@RequestParam(required = false) String locale) {
        StreamingResponseBody stream = outputStream -> service.streamExport(locale, outputStream);
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(stream);
    }

    
	/* export file in directoory
	 * @GetMapping("/export") public ResponseEntity<StreamingResponseBody>
	 * exportFile(@RequestParam(required = false) String locale) {
	 * StreamingResponseBody stream = out -> service.streamExport(locale, out);
	 * return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=translations.json")
	 * .contentType(MediaType.APPLICATION_JSON) .body(stream); }
	 */


}
