package com.example.Translation.Management.Service.Servc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;


import com.example.Translation.Management.Service.DTO.PagedResponse;
import com.example.Translation.Management.Service.DTO.TranslationTextDto;
import com.example.Translation.Management.Service.DTO.TranslationTextRequestDto;
import com.example.Translation.Management.Service.DTO.TranslationUpdateRequestDto;


import org.springframework.data.domain.Pageable;

public interface TranslationService {
	
	  TranslationTextDto createOrUpdate(TranslationTextRequestDto request);

	    TranslationTextDto update(Long id, TranslationUpdateRequestDto request);

	    Optional<TranslationTextDto> getById(Long id);

	    PagedResponse<TranslationTextDto> search(String locale, String tag, String q, Pageable pageable);

	    void streamExport(String locale, OutputStream outputStream) throws IOException;
}