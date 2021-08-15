package com.example.springrestapp.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
    private Long id;
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDTO baseDTO = (BaseDTO) o;
        return Objects.equals(id, baseDTO.id) && Objects.equals(version, baseDTO.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}

