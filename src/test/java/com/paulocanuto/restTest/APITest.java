package com.paulocanuto.restTest;

import com.paulocanuto.api.SparkMockAPI;
import com.paulocanuto.api.model.Pessoa;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import static org.hamcrest.CoreMatchers.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

public class  APITest {

    @BeforeClass
    public static void setUp() {
        baseURI = "http://127.0.0.1";
        basePath = "/api";
        port = 4567;

        SparkMockAPI.main(null);
    }

    @AfterClass
    public static void tearDown() {
        Spark.stop();
    }

    @Test
    public void getPessoaById() {
        when().
            get("/pessoa/{id}", 3).
        then().
            contentType("application/json").and().
            body("nome", equalTo("Jequi")).and().
            body("endereco", equalTo("RS")).and().
            body("profissao", equalTo("Geólogo")).and().
            statusCode(200);
    }

    @Test
    public void insertPessoa() {
        given()
            .contentType(ContentType.JSON)
            .body(new Pessoa("Fernando", "SC", "dev")).
        when()
            .post("/pessoa").
        then().
            body("nome", equalTo("Fernando")).and().
            body("endereco", equalTo("SC")).and().
            body("profissao", equalTo("dev")).and().
            statusCode(200);
    }

    @Test
    public void deletePessoa() {
        int pessoaID = 
            given()
                .contentType(ContentType.JSON)
                .body(new Pessoa("Jardel", "AM", "Gerente")).
        when().
            post("/pessoa").
        then().
            extract().
                path("id");
                
        when().
            delete("/pessoa/{id}", pessoaID).
        then().
            body("status", equalTo("success")).and().
            statusCode(200);
    }

    @Test
    public void alterPessoa() {
        given().
            contentType(ContentType.JSON).
            body(new Pessoa("João", "SC", "fazer bolacha")).
        when().
            put("/pessoa/{id}", 1).
        then().
            body("nome", equalTo("João")).and().
            body("endereco", equalTo("SC")).and().
            body("profissao", equalTo("fazer bolacha")).and().
            statusCode(200);
    }
}
