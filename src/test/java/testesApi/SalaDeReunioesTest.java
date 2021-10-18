package testesApi;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class SalaDeReunioesTest {

    private String url ="http://localhost:8082/api/v1/rooms";

    public String lerJson(String caminoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminoJson)));
    }

    //Criar sala de reuniões
    @Test
    public void quandoEnviarCorpoDaSala_entãoSejaCriadaASala() throws IOException {

        String jsonBody = lerJson("arquivoJson/arquivosJson.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(url)
        .then()
                .statusCode(200)
                .body("name", is("Angular"))
                .body("date", is("13/10/2021"))
                .body("startHour", is("21hs"))
                .body("endHour",is("23hs"));


    }

    //Listando todas as salas de reuniões
    @Test
    public void quandoSolicitarAListaDasSalas_EntãoMostraTodasAsSalas(){

        given()
                .log().all()
        .when()
                .get(url)
        .then()
                .statusCode(200)
                .log().all();
    }

    //Obtendo uma sala de reuniões
    @Test
    public void quandoSolicitarUmaSalaPorId_EntaoMostraASala(){

        String id ="1";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(url+"/"+id)
        .then()
                .statusCode(200)
                .body("name",is("fullstack"))
                .body("date",is("13/10/2021"))
                .body("startHour",is("21hs"))
                .body("endHour",is("23hs"))
                .log().all();
    }

    //Atualizando uma sala
    @Test
    public void quandoModificarDadosDeUmaSala_EntaoElaSEjaAtualizada() throws IOException {

        String jsonBody = lerJson("arquivoJson/arquivosJson.json");
        String id = "2";

        given()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .put(url+"/"+id)
        .then()
                .statusCode(200)
                .body("name",is("pyton"))
                .body("date",is("13/10/2021"))
                .body("startHour",is("21hs"))
                .body("endHour",is("23hs"))
                .log().all();
    }

    //Deletando uma sala
    @Test
    public void quandoInformarUmId_EntaoDeletaASala(){

        String id = "5";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(url + "/" + id)
        .then()
                .statusCode(200)
                .body("Deleted",is(true));


    }

}
