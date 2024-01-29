import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KlimberApiRest {

    @BeforeClass
    void setup() {
        RestAssured.baseURI = "https://api.nasa.gov";
    }

    @Test
    void GetPlanetaryNasa () {
        Response res = RestAssured.given()
                .param("api_key", System.getenv("keyValue"))
                .when()
                .get("/planetary/apod")
                .then()
                .extract().response();
        System.out.println("----Status Code: " + res.statusCode());
        int statusCode = res.statusCode();
        Assert.assertEquals(statusCode,200);
        ResponseBody body = res.getBody();
        System.out.println("----Status Body: " + body.asString());
    }

    @Test
    void GetRoversPhotos () {
        Response res = RestAssured.given()
                .param("api_key", System.getenv("keyValue"))
                .when()
                .get("/mars-photos/api/v1/rovers/curiosity/photos?sol=1000")
                .then()
                .extract().response();
        System.out.println("----Status Code: " + res.statusCode());
        int statusCode = res.statusCode();
        Assert.assertEquals(statusCode,200);

        JsonPath jsnPath = res.jsonPath();
        Number id = jsnPath.get("photos[0].id");
        System.out.println("First id: " + id);

        System.out.println("------------------All Body----------------");
        ResponseBody body = res.getBody();
        System.out.println("----Body: " + body.asString());
    }
}
