package com.example.springrestapp.quote;

import com.example.springrestapp.author.AuthorDTO;
import com.example.springrestapp.generic.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuoteDTO extends BaseDTO {
    private String content;
    private AuthorDTO author;

    public static QuoteDTO convertToDTO(Quote quote) {
        if (quote == null) {
            return null;
        }
        QuoteDTO quoteDTO = new QuoteDTO(quote.getContent(), AuthorDTO.convertToDTO(quote.getAuthor()));
        quoteDTO.setId(quote.getId());
        quoteDTO.setVersion(quote.getVersion());
        return quoteDTO;
    }
}
