package com.example.springrestapp.author;

import com.example.springrestapp.generic.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorDTO extends BaseDTO {
    private String firstname;
    private String lastname;

    public static AuthorDTO convertToDTO(Author author) {
        if (author == null) {
            return null;
        }
        AuthorDTO authorDTO = new AuthorDTO(author.getFirstname(), author.getLastname());
        authorDTO.setId(author.getId());
        authorDTO.setVersion(author.getVersion());
        return authorDTO;
    }
}
