package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.User;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class UserEndPoints {

	// This class defined the CRUD operations methods you for your APIs

	public static Response createUser(User payload) {

		Response response = 
				given()
				       .contentType(ContentType.JSON)
				       .accept(ContentType.JSON)
				       .body(payload)
			   .when()
				       .post(Routes.post_url).andReturn();

		return response;

	}

	public static Response getUser(String username) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", username).when().get(Routes.get_url).andReturn();

		return response;

	}

	public static Response updateUser(String username, User UserPayload) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", username).body(UserPayload).when().put(Routes.update_url).andReturn();

		return response;

	}

	public static Response deleteUser(String username) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", username).when().delete(Routes.update_url).andReturn();

		return response;

	}
}
