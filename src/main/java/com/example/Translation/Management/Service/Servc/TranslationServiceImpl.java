package com.example.Translation.Management.Service.Servc;

import java.time.Instant;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Translation.Management.Service.DTO.PagedResponse;
import com.example.Translation.Management.Service.DTO.TranslationExportDto;
import com.example.Translation.Management.Service.DTO.TranslationTextDto;
import com.example.Translation.Management.Service.DTO.TranslationTextRequestDto;
import com.example.Translation.Management.Service.DTO.TranslationUpdateRequestDto;
import com.example.Translation.Management.Service.Entity.TranslationKeyEntity;
import com.example.Translation.Management.Service.Entity.TranslationTextEntity;
import com.example.Translation.Management.Service.Repository.TranslationKeyRepository;
import com.example.Translation.Management.Service.Repository.TranslationTextRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.IOException;
import java.io.OutputStream;

import java.util.stream.Stream;
import java.util.Iterator;
import java.util.Optional;

@Service
public class TranslationServiceImpl implements TranslationService {

	private final TranslationTextRepository repository;
	private final TranslationKeyRepository keyRepository;
	private final ObjectMapper objectMapper;

	public TranslationServiceImpl(TranslationTextRepository repository, TranslationKeyRepository keyRepository,
			ObjectMapper objectMapper) {
		this.repository = repository;
		this.keyRepository = keyRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public TranslationTextDto createOrUpdate(TranslationTextRequestDto request) {
		TranslationKeyEntity key = keyRepository.findByKeyNameAndTag(request.getKey(), request.getTag())
				.orElseGet(() -> {
					TranslationKeyEntity newKey = new TranslationKeyEntity();
					newKey.setKeyName(request.getKey());
					newKey.setTag(request.getTag());
					return keyRepository.save(newKey);
				});

		TranslationTextEntity entity = repository.findByTranslationKeyAndLocale(key, request.getLocale())
				.orElse(new TranslationTextEntity());

		entity.setTranslationKey(key);
		entity.setLocale(request.getLocale());
		entity.setContent(request.getContent());
		entity.setUpdatedAt(Instant.now());

		return new TranslationTextDto(repository.save(entity));
	}

	@Override
	public TranslationTextDto update(Long id, TranslationUpdateRequestDto request) {
		TranslationTextEntity entity = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Translation not found with id " + id));

		entity.setLocale(request.getLocale());
		entity.setContent(request.getContent());
		entity.setUpdatedAt(Instant.now());

		return new TranslationTextDto(repository.save(entity));
	}

	@Override
	public Optional<TranslationTextDto> getById(Long id) {
		return repository.findById(id).map(TranslationTextDto::new);
	}

	@Override
	public PagedResponse<TranslationTextDto> search(String locale, String tag, String q, Pageable pageable) {
		Page<TranslationTextEntity> page = repository.search(locale, tag, q, pageable);
		Page<TranslationTextDto> dtoPage = page.map(TranslationTextDto::new);
		return new PagedResponse<>(dtoPage);
	}
	
	
    @Override
    @Transactional(readOnly = true)
    public void streamExport(String locale, OutputStream outputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try (Stream<TranslationExportDto> translations = repository.streamByLocale(locale);
             SequenceWriter seqWriter = mapper.writer().writeValues(outputStream)) {
            translations.forEach(t -> {
				try {
					seqWriter.write(t);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}); // stream each DTO directly
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
