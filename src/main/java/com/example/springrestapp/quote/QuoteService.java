package com.example.springrestapp.quote;

import com.example.springrestapp.author.Author;
import com.example.springrestapp.generic.GenericRestService;
import com.example.springrestapp.author.AuthorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuoteService extends GenericRestService<Quote, QuoteDTO> {

    private final QuoteRepository repository;
    private final AuthorRepository authorRepository;

    public QuoteService(QuoteRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<QuoteDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public QuoteDTO getOne(Long id) {
        return convertToDTO(repository.getById(id));
    }

    @Override
    public Quote updateEntityData(Quote entity, QuoteDTO dto) {
        updateAuthor(entity, dto);
        entity.setContent(dto.getContent());
        return entity;
    }

    @Override
    public JpaRepository<Quote, Long> getRepository() {
        return repository;
    }

    @Override
    public QuoteDTO convertToDTO(Quote entity) {
        return QuoteDTO.convertToDTO(entity);
    }

    @Override
    public Quote getNewEntity() {
        return new Quote();
    }

    private void updateAuthor(Quote entity, QuoteDTO dto) {
        if (dto.getAuthor() != null) {
            Author author = authorRepository
                    .findById(dto.getAuthor().getId())
                    .orElseGet(Author::new);
            author.setFirstname(dto.getAuthor().getFirstname());
            author.setLastname(dto.getAuthor().getLastname());
            author.getQuotes().add(entity);
            entity.setAuthor(author);
        }
    }
}
