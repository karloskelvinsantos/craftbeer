package com.beerhouse;

import com.beerhouse.controller.BeerController;
import com.beerhouse.dto.BeerDTO;
import com.beerhouse.service.BeerService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest
class BeerControllerTest {

    @Autowired
    private BeerController beerController;

    @MockBean
    private BeerService beerService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(this.beerController);
    }

    BeerDTO createDTO() {
        var dto = new BeerDTO();
        dto.setName("Beer");
        dto.setIngredients("Malt");
        dto.setAlcoholContent("5%");
        dto.setPrice(BigDecimal.valueOf(5.9));
        dto.setCategory("IPA");

        return dto;
    }

    @Test
    void deveRetornarSucessoQuandoBuscarBeer() {

        given()
            .accept(ContentType.JSON)
        .when()
            .get("/beers/{id}", 1L)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarNaoEncontradoQuandoBuscarBeer() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/beers/{id}", 1L)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarCriadoQuandoCriarBeer() {

        given()
            .accept(ContentType.JSON)
        .when()
            .post("/beers", createDTO())
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarSucessoQuandoAtualizarBeer() {

        given()
            .accept(ContentType.JSON)
        .when()
            .put("/beers/{id}", 1L, createDTO())
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarNaoEncontradoQuandoAtualizarBeer() {

        given()
            .accept(ContentType.JSON)
        .when()
            .put("/beers/{id}", 5L, createDTO())
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
