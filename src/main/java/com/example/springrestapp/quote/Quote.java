package com.example.springrestapp.quote;

import com.example.springrestapp.author.Author;
import com.example.springrestapp.generic.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quote extends BaseEntity {

    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
