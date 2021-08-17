package com.example.springrestapp.quote;

import com.example.springrestapp.author.Author;
import com.example.springrestapp.author.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityExistsException;
import java.lang.reflect.Field;
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
        setIdByReflection(updatedQuote);

        QuoteDTO quote = quoteService.update(QuoteDTO.convertToDTO(updatedQuote));

        assertEquals("UPDATED content", quote.getContent());
        assertEquals("Json", quote.getAuthor().getFirstname());
        assertEquals("Connor", quote.getAuthor().getLastname());
    }

    @Test
    public void shouldThrowExceptionWhenQuoteExists() {
        Quote quote = createQuote();
        setIdByReflection(quote);

        when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quote));

        Assertions.assertThrows(EntityExistsException.class, () -> quoteService.save(QuoteDTO.convertToDTO(quote)));
    }

    private void setIdByReflection(Quote updatedQuote) {
        Field id = ReflectionUtils.findField(Quote.class, "id");
        if (id != null) {
            id.setAccessible(true);
            ReflectionUtils.setField(id, updatedQuote, 1L);
        }
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
