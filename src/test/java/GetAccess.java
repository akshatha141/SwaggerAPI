import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetAccess extends BaseClass {

	String accesstoken;

	/* To login with Access Token and valid Credentials */

	@Test(priority = 2, dependsOnMethods = "GetAccessToken")
	public void LoginAccessToken() {

		ComplexPojo.payload details = new ComplexPojo.payload("upskills_admin", "Talent4$$");

		Response postLogin = given().when().header("Content-Type", "application/json").auth().oauth2(accesstoken)
				.body(details).log().all().post(LoadProperties().getProperty("UserLogin"));

		postLogin.prettyPrint();

		System.out.println("Status code is :" + postLogin.getStatusCode());
		System.out.println("Response time is :" + postLogin.getTime());
		System.out.println("Status line is :" + postLogin.getStatusLine());
		System.out.println("ContentType is :" + postLogin.getContentType());
		System.out.println("Session ID is :" + postLogin.getSessionId());

		Assert.assertEquals(postLogin.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(postLogin.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");
		Assert.assertEquals(postLogin.getContentType(), "application/json; charset=utf-8", "status code not matching");
		Assert.assertEquals(postLogin.getSessionId(), null, "status code not matching");
		System.out.println("Admin Successfully logged in");

	}

	/* To get Access Token */

	@Test(priority = 1)
	public void GetAccessToken() {

		Response geturl = given().when().header("Authorization ",
				"Basic dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=").log()
				.all().post(LoadProperties().getProperty("GetAccessToken"));

		geturl.prettyPrint();

		System.out.println("Status code is :" + geturl.getStatusCode());
		Assert.assertEquals(geturl.getStatusCode(), 200, "status code not matching");

		accesstoken = geturl.asString().substring(276, 316);

		/* Access Token Stored in variable accesstoken */
		System.out.println("Access_token obtained is :" + accesstoken);

	}

	/* To get User Account Details after Login */

	@Test(priority = 3, dependsOnMethods = "LoginAccessToken")
	public void GetUserAccountDetails() {
		Response getAccountDetails = given().when().header("Content-Type", "application/json").auth()
				.oauth2(accesstoken).log().all().get(LoadProperties().getProperty("UserAccountDetails"));

		getAccountDetails.prettyPrint();

		Assert.assertEquals(getAccountDetails.getStatusCode(), 200, "status code not matching");

		System.out.println("Status code is :" + getAccountDetails.getStatusCode());

		System.out.println(getAccountDetails.jsonPath());
	}

	/* To logout from the system */

	@Test(priority = 4, dependsOnMethods = "LoginAccessToken")
	public void LogoutFromSystem() {

		Response postLogout = given().when().header("Content-Type", "application/json").auth().oauth2(accesstoken).log()
				.all().post(LoadProperties().getProperty("UserLogout"));

		postLogout.prettyPrint();

		Assert.assertEquals(postLogout.getStatusCode(), 200, "status code not matching");

		System.out.println("Status code is :" + postLogout.getStatusCode());
		System.out.println("Admin Successfully logged out");

	}
}
