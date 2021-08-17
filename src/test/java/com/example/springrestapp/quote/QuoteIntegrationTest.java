package com.example.springrestapp.quote;

import com.example.springrestapp.SpringRestAppApplication;
import com.example.springrestapp.author.AuthorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringRestAppApplication.class
)
@AutoConfigureMockMvc
@Transactional
public class QuoteIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    public void shouldGetAllQuotesWithStatusOK() throws Exception {
        mvc.perform(get("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(quoteRepository.findAll().size())));
    }

    @Test
    public void shouldSaveQuotesWithStatusCreated() throws Exception {
        mvc.perform(post("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createQuoteWithAuthor()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value("Test"))
                .andExpect(jsonPath("$.author.firstname").value("Json"))
                .andExpect(jsonPath("$.author.lastname").value("Connor"));
    }

    @Test
    public void shouldUpdateQuotesWithStatusOK() throws Exception {
        QuoteDTO quote = quoteRepository.findAll().stream()
                .findFirst()
                .map(QuoteDTO::convertToDTO)
                .orElseThrow(EntityNotFoundException::new);
        quote.setContent("UPDATED content");
        quote.getAuthor().setFirstname("UPDATED firstname");
        quote.getAuthor().setLastname("UPDATED lastname");

        mvc.perform(put("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quote))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value("UPDATED content"))
                .andExpect(jsonPath("$.author.firstname").value("UPDATED firstname"))
                .andExpect(jsonPath("$.author.lastname").value("UPDATED lastname"));
    }

    @Test
    public void shouldRemoveQuoteWithStatusNoContent() throws Exception {
        QuoteDTO quote = quoteRepository.findAll().stream()
                .findFirst()
                .map(QuoteDTO::convertToDTO)
                .orElseThrow(EntityNotFoundException::new);

        mvc.perform(delete("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quote))
                )
                .andExpect(status().isNoContent());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> quoteRepository.findById(quote.getId()).orElseThrow(EntityNotFoundException::new)
        );
    }

    private QuoteDTO createQuoteWithAuthor() {
        return new QuoteDTO("Test", new AuthorDTO("Json", "Connor"));
    }
}
