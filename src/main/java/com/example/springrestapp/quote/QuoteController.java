package com.example.springrestapp.quote;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/quotes")
class QuoteController {

    private final QuoteService quoteService;

    QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping()
    ResponseEntity<Set<QuoteDTO>> getAll() {
        return ResponseEntity.ok(quoteService.getAll());
    }

    @PostMapping()
    ResponseEntity<QuoteDTO> save(@RequestBody QuoteDTO quoteDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(quoteService.save(quoteDTO));
    }

    @PutMapping()
    ResponseEntity<QuoteDTO> update(@RequestBody QuoteDTO quoteDTO) {
        return ResponseEntity.ok(quoteService.update(quoteDTO));
    }

    @DeleteMapping()
    ResponseEntity<Void> delete(@RequestBody QuoteDTO quoteDTO) {
        quoteService.delete(quoteDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

