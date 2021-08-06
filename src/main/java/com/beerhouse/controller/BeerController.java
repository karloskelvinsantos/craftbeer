package com.beerhouse.controller;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.model.Beer;
import com.beerhouse.service.BeerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/beers")
@AllArgsConstructor
@Slf4j
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    public Page<Beer> getBeers(Pageable pageable) {
        log.info("Request received get beers");
        return beerService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Beer> getBeer(@PathVariable Long id) {
        log.info("Request received get beer with id: {}", id);
        var findedBeer = beerService.findById(id);

        return findedBeer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Beer> create(@RequestBody @Valid BeerDTO beerDTO, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Request received for create beer: {}", beerDTO);
        var beer = new Beer();
        BeanUtils.copyProperties(beerDTO, beer);

        var saved = beerService.save(beer);

        URI uri = uriComponentsBuilder.path("/beers/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("Request received for delete beer with id: {}", id);
        var delete = beerService.findById(id);

        if (delete.isPresent()) {
            beerService.delete(delete.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Beer> update(@PathVariable Long id, @RequestBody @Valid BeerDTO beerDTO) {
        log.info("Request received for update beer with id: {}", id);

        var existingBeer = beerService.findById(id);

        if (existingBeer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var beer = new Beer();
        BeanUtils.copyProperties(beerDTO, beer);
        beer.setId(id);

        return ResponseEntity.ok(beerService.save(beer));

    }

    @PatchMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Beer> patch(@PathVariable Long id, @RequestBody JsonPatch jsonPatch) {
        log.info("Request received for patch beer with id: {}", id);

        var entityToPatch = beerService.findById(id);

        try {
            if (entityToPatch.isPresent()) {
                var beerPatched = beerService.applyPatchToCustomer(jsonPatch, entityToPatch.get());
                return ResponseEntity.ok(beerService.save(beerPatched));
            }
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.notFound().build();
    }
}
