package tests.user;

import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endpoints.UserEndPoints;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.User;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class PostTests {
	Faker faker; // This class helps us to generate fake dummy data to used for API payloads.
	User userpayload;

	@BeforeTest
	public void Setup() {

		faker = new Faker();
		userpayload = new User();

		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());

	}

	/* -------------------- POST API - TESTS ----------------------- */

	@Test(description = "Validate that API should return API status code within 2XX series", groups = {
			"USER - POST API" }, priority = 1)
	public void PostUserTestMethod1() {

		Response response = UserEndPoints.createUser(userpayload);
		Matcher<String> matcher1 = matchesPattern("^20[0-9]$"); // Boolean res =
		matcher1.matches(String.valueOf(response.getStatusCode()));

		System.out.println("Response : " + response.asPrettyString() + " username :" + this.userpayload.getUsername());
		assertThat("Assertion 1", matcher1.matches(String.valueOf(response.getStatusCode())));

	}

	@Test(description = "Validate that API should return API status code as 200", groups = {
			"USER - POST API" }, priority = 2)
	public void PostUserTestMethod2() {

		Response response = UserEndPoints.createUser(userpayload);
		assertThat(response.getStatusCode(), is(200));

	}

	@Test(description = "Validate that API should return a response payload i.e. Non-empty response body and response header", groups = {
			"USER - POST API" }, priority = 3)
	public void PostUserTestMethod3() {

		Response response = UserEndPoints.createUser(userpayload);

		String responseBody = response.getBody().toString();
		assertThat(responseBody, not(isEmptyString()));

		String responseHeaders = response.getHeaders().toString();
		assertThat(responseHeaders, not(isEmptyString()));

	}

	@Test(description = "Validate that API should return response of type = JSON", groups = {
			"USER - POST API" }, priority = 4)
	public void PostUserTestMethod4() {

		Response response = UserEndPoints.createUser(userpayload);

		assertThat(response.getContentType(), is("application/json"));

	}

	@Test(description = "Validate that API should should adhere to Response structure as per data model mentioned in API Spec or API Doc.- JSON SCHEMA VALIDATION", groups = {
			"USER - POST API" }, priority = 5)
	public void PostUserTestMethod5() {

		Response response = UserEndPoints.createUser(userpayload);
		JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschemaval.json");

		assertThat(response.jsonPath().prettyPrint(),
				JsonSchemaValidator.matchesJsonSchemaInClasspath("User\\Post_JsonSchema.json"));
	}

	@Test(description = "Valdiate that API should return response body as per the req spec", groups = {
			"USER - POST API" }, priority = 6)
	public void PostUserTestMethod6() {

		Response response = UserEndPoints.createUser(userpayload);

		assertThat(response.body().jsonPath().get("code"), is(200));
		assertThat(response.body().jsonPath().get("type"), is("unknown"));

		assertThat(response.body().jsonPath().get("message").toString().length(), is(greaterThan(6)));
	}

	@Test(description = "Valdiate that API should return response headers as per the req spec", groups = {
			"USER - POST API" }, priority = 7)
	public void PostUserTestMethod7() {

		Response response = UserEndPoints.createUser(userpayload);

		Headers headers = response.getHeaders();

		assertThat("Contains required headers", headers.hasHeaderWithName("Content-type"));

		assertThat(response.getHeader("Content-type"), is("application/json"));
	}

	
	@AfterTest
	public void Teardown() {

		System.out.println("Execution completed");
	}
}
