package com.beerhouse.service;

import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;
    private final ObjectMapper objectMapper;

    public Page<Beer> findAll(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    public Beer save(Beer beer) {
        return beerRepository.save(beer);
    }

    public void delete(Beer beer) {
        beerRepository.delete(beer);
    }

    public Optional<Beer> findById(Long id) {
        return beerRepository.findById(id);
    }

    public Beer applyPatchToCustomer(JsonPatch patch, Beer targetCustomer)
            throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Beer.class);
    }
}
