import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class APITestScenarios {
    public static final String OPPONENT1 = "Manchester";
    public static final String OPPONENT2 = "Liverpool";
    public static final String DATE1 = "2020-01-11";
    public static final String DATE2 = "2020-01-05";

    @Test
    public void getNonExistingGame() {
        given()
                .pathParam("gameId", "-1")
                .when()
                .get("http://localhost:8080/api/v1/games/{gameId}")
                .then()
                .statusCode(404);
    }

    @Test
    public void createANewGame() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("opponent", "Manchester");
        requestParams.put("date", "2020-11-01");

        given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
        .when()
                .post("http://localhost:8080/api/v1/games")
        .then()
                .statusCode(201)
                .and()
                .body("date", equalTo(requestParams.get("date")))
                .body("opponent", equalTo(requestParams.get("opponent")));
  }

    @Test
    public void updateExistingGame() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("gameId", "12345");
        requestParams.put("opponent", "Bacelona");
        requestParams.put("date", "2020-11-05");

        given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
        .when()
                .put("http://localhost:8080/api/v1//games/{gameId}")
                .then()
        .statusCode(200)
                .and()
                .body("date", equalTo(requestParams.get("date")))
                .body("opponent", equalTo(requestParams.get("opponent")))
                .body("gameId", equalTo(requestParams.get("gameId")));

    }


    @Test
    public void getCreatedGame() {

        ResponseBody responseBody = createNewGame(OPPONENT1, DATE1);

        given()
                .pathParam("gameId", responseBody.jsonPath().get("id"))
        .when()
                .get("http://localhost:8080/api/v1/games/{gameId}")
        .then()
                .statusCode(200)
                .and()
                .body("date", equalTo(DATE1))
                .body("opponent", equalTo(OPPONENT1))
                .body("id", equalTo(responseBody.jsonPath().get("id")));

        deleteGame(responseBody.jsonPath().get("id"));
    }



    @Test
    public void fromToDate() {

        ResponseBody game1Response = createNewGame(OPPONENT1, DATE1);
        ResponseBody game2Response = createNewGame(OPPONENT2, DATE2);


        given()
                .param("from","2020-01-01")
                .param("to","2020-02-01")
        .when()
                .get("http://localhost:8080/api/v1/games")
        .then()
                .statusCode(200)
                .and().log().all().and()
                .body("[0].date", equalTo(DATE1))
                .body("[0].opponent", equalTo(OPPONENT1))
                .body("[0].id", equalTo(game1Response.jsonPath().get("id")))
                .body("[1].date", equalTo(DATE2))
                .body("[1].opponent", equalTo(OPPONENT2))
                .body("[1].id", equalTo(game2Response.jsonPath().get("id")));
        deleteGame(game1Response.jsonPath().get("id"));
        deleteGame(game2Response.jsonPath().get("id"));
    }

    @Test
    public void deleteGame(){
        ResponseBody game1Response = createNewGame(OPPONENT1, DATE1);

        given()
                .pathParam("gameId", game1Response.jsonPath().get("id"))
        .when()
                .get("http://localhost:8080/api/v1/games/{gameId}")
        .then()
                .statusCode(200);

        given()
                .pathParam("gameId", game1Response.jsonPath().get("id"))
        .when()
                .delete("http://localhost:8080/api/v1/games/{gameId}")
        .then()
                .statusCode(200);

        given()
                .pathParam("gameId", game1Response.jsonPath().get("id"))
        .when()
                .get("http://localhost:8080/api/v1/games/{gameId}")
        .then()
                .statusCode(404);

    }

    private ResponseBody createNewGame(String opponent, String date) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("opponent", opponent);
        requestParams.put("date", date);

        return given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
                .post("http://localhost:8080/api/v1/games")
                .getBody();
    }

    private void deleteGame(int id) {
        given()
                .pathParam("gameId", id)
        .when()
                .delete("http://localhost:8080/api/v1/games/{gameId}")
        .then()
                .statusCode(200);
    }

    @Test
    void deleteInvalidGame() {
        given()
                .pathParam("gameId", 0)
        .when()
                .delete("http://localhost:8080/api/v1/games/{gameId}")
        .then()
                .statusCode(404);


    }

    @Test
    public void createAInvalidGameUsingInvalidDate() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("opponent", "Manchester");
        requestParams.put("date", "2020-50-01");

        given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
        .when()
                .post("http://localhost:8080/api/v1/games")
        .then()
                .statusCode(404);
    }
}
