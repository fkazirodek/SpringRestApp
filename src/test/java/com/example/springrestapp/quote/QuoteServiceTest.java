package com.example.springrestapp.quote;

import com.example.springrestapp.author.Author;
import com.example.springrestapp.author.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private AuthorRepository authorRepository;

    private QuoteService quoteService;

    @Before
    public void before() {
        quoteService = new QuoteService(quoteRepository, authorRepository);
        Quote quote = createQuote();
        List<Quote> quotes = List.of(quote);

        when(quoteRepository.findAll()).thenReturn(quotes);
        when(quoteRepository.getById(anyLong())).thenReturn(quote);
        when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
    }

    @Test
    public void shouldReturnAllQuotes() {
        Set<QuoteDTO> returnedQuotes = quoteService.getAll();

        assertEquals(1, returnedQuotes.size());
    }

    @Test
    public void shouldReturnOneQuoteById() {
        QuoteDTO quote = quoteService.getOne(1L);

        assertEquals("Test", quote.getContent());
        assertEquals("Json", quote.getAuthor().getFirstname());
        assertEquals("Connor", quote.getAuthor().getLastname());
    }

    @Test
    public void shouldSaveQuote() {
        QuoteDTO quote = quoteService.save(QuoteDTO.convertToDTO(createQuote()));

        assertEquals("Test", quote.getContent());
        assertEquals("Json", quote.getAuthor().getFirstname());
        assertEquals("Connor", quote.getAuthor().getLastname());
    }

    @Test
    public void shouldUpdateQuote() {
        Quote updatedQuote = createQuote();
        updatedQuote.setContent("UPDATED content");
        updatedQuote.setId(1L);

        QuoteDTO quote = quoteService.update(QuoteDTO.convertToDTO(updatedQuote));

        assertEquals("UPDATED content", quote.getContent());
        assertEquals("Json", quote.getAuthor().getFirstname());
        assertEquals("Connor", quote.getAuthor().getLastname());
    }

    @Test
    public void shouldThrowExceptionWhenQuoteExists() {
        Quote quote = createQuote();
        quote.setId(1L);

        when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quote));

        Assertions.assertThrows(EntityExistsException.class, () -> quoteService.save(QuoteDTO.convertToDTO(quote)));
    }

    @Test
    public void shouldThrowOptimisticLockExceptionWhenQuoteHasDifferentVersion() {
        Quote quote = createQuote();
        quote.setId(1L);
        quote.setVersion(1L);

        when(quoteRepository.getById(anyLong())).thenReturn(quote);

        Quote updatedQuote = createQuote();
        updatedQuote.setId(1L);
        updatedQuote.setVersion(2L);

        Assertions.assertThrows(OptimisticLockException.class, () -> quoteService.update(QuoteDTO.convertToDTO(updatedQuote)));
    }

    private Quote createQuote() {
        Quote quote = new Quote();
        quote.setContent("Test");
        quote.setAuthor(createAuthor(quote));
        return quote;
    }

    private Author createAuthor(Quote quote) {
        Author author = new Author();
        author.setFirstname("Json");
        author.setLastname("Connor");
        author.getQuotes().add(quote);
        return author;
    }

}
