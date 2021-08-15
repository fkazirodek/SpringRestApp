package com.example.springrestapp.author;

import com.example.springrestapp.generic.BaseEntity;
import com.example.springrestapp.quote.Quote;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author extends BaseEntity {

    private String firstname;

    private String lastname;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Quote> quotes = new HashSet<>();
}
