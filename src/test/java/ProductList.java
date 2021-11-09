import org.testng.Assert;
import org.testng.annotations.*;


import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ProductList extends BaseClass {

	String accesstoken1;

	/* To get Access Token */

	@Test(priority = 1)
	public void GetAccessToken1() {

		Response geturl = given().when().header("Authorization ",
				"Basic dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=").log()
				.all().post(LoadProperties().getProperty("GetAccessToken"));

		geturl.prettyPrint();

		System.out.println("Status code is :" + geturl.getStatusCode());
		Assert.assertEquals(geturl.getStatusCode(), 200, "status code not matching");

		accesstoken1 = geturl.asString().substring(276, 316);
		System.out.println("Access_token obtained is :" + accesstoken1);
	}

	/* To login with Access Token and valid Credentials */

	@Test(priority = 2, dependsOnMethods = "GetAccessToken1")
	public void LoginAccessToken1() {

		ComplexPojo.payload details = new ComplexPojo.payload("upskills_admin", "Talent4$$");

		Response postLogin = given().when().header("Content-Type", "application/json").auth().oauth2(accesstoken1)
				.body(details).log().all().post(LoadProperties().getProperty("UserLogin"));

		postLogin.prettyPrint();

		Assert.assertEquals(postLogin.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(postLogin.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");

		System.out.println("Status code is :" + postLogin.getStatusCode());
		System.out.println("Admin Successfully logged in");
	}

	/* To get the List of All Products */

	@Test(priority = 3, dependsOnMethods = "LoginAccessToken1")
	public void GetProductDetails() {

		Response getProductDetails = given().when().header("Content-Type", "application/json").auth()
				.oauth2(accesstoken1).log().all().get(LoadProperties().getProperty("GetListProductDetails"));

		getProductDetails.prettyPrint();

		/* Script Assertion */
		Assert.assertEquals(getProductDetails.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(getProductDetails.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");
		Assert.assertEquals(getProductDetails.getContentType(), "application/json; charset=utf-8", "status code not matching");
		Assert.assertEquals(getProductDetails.getSessionId(), null, "status code not matching");
		
		System.out.println("Status code is :" + getProductDetails.getStatusCode());
		System.out.println("Response time is :" + getProductDetails.getTime());
		System.out.println("Status line is :" + getProductDetails.getStatusLine());
		System.out.println("ContentType is :" + getProductDetails.getContentType());
		System.out.println("Session ID is :" + getProductDetails.getSessionId());
		System.out.println("Successfully retrived List of ALL the Products");
	}

	/* To Update the Products to the Store */

	@Test(priority = 4, dependsOnMethods = "GetProductDetails")
	public void Updateproducts() {

		String details1 = " [{\"product_id\": \"58\",\r\n" + "  \"price\": \"6000.00\"\r\n" + "  }]";
		String details2 = " [{\"product_id\": \"57\",\r\n" + "  \"price\": \"3000.00\"\r\n" + "  }]";

		Response Updateproducts = given().when().header("Content-Type", "application/json").auth().oauth2(accesstoken1)
				.body(details1).log().all().put(LoadProperties().getProperty("UpdateProductDetails"));

		Updateproducts.prettyPrint();

		/* Script Assertion */
		Assert.assertEquals(Updateproducts.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(Updateproducts.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");
		Assert.assertEquals(Updateproducts.getContentType(), "application/json; charset=utf-8", "status code not matching");
		Assert.assertEquals(Updateproducts.getSessionId(), null, "status code not matching");
		
		System.out.println("Status code is :" + Updateproducts.getStatusCode());
		System.out.println("Response time is :" + Updateproducts.getTime());
		System.out.println("Status line is :" + Updateproducts.getStatusLine());
		System.out.println("ContentType is :" + Updateproducts.getContentType());
		System.out.println("Session ID is :" + Updateproducts.getSessionId());
		System.out.println("Successfully updated the Product Details to the Store");
	}

	/* Get Product By ID to Verify if the Product is Updated to the Store */

	@Test(priority = 5, dependsOnMethods = "Updateproducts")
	public void GetProductDetaildByID() {

		Response ProductID = given().when().header("Content-Type", "application/json").auth().oauth2(accesstoken1).log()
				.all().get(LoadProperties().getProperty("GetProductDetailsByID"));

		ProductID.prettyPrint();

		/* Script Assertion */
		Assert.assertEquals(ProductID.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(ProductID.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");
		Assert.assertEquals(ProductID.getContentType(), "application/json; charset=utf-8", "status code not matching");
		Assert.assertEquals(ProductID.getSessionId(), null, "status code not matching");
		
		System.out.println("Status code is :" + ProductID.getStatusCode());
		System.out.println("Response time is :" + ProductID.getTime());
		System.out.println("Status line is :" + ProductID.getStatusLine());
		System.out.println("ContentType is :" + ProductID.getContentType());
		System.out.println("Session ID is :" + ProductID.getSessionId());

		String Updated_Price = ProductID.asString().substring(407, 411);

		/* To Verify if the Product Price is Updated */
		System.out.println("Updated Price :" + Updated_Price);

	}

	/* To Update the Product Quantity */

	@Test(priority = 6)
	public void UpdateProductQuantity() {

		String details2 = " [{\"product_id\": \"59\",\r\n" + "  \"quantity\": \"5000.00\"\r\n" + "  }]";

		Response UpdateproductsQuantity = given().when().header("Content-Type", "application/json").auth()
				.oauth2(accesstoken1).body(details2).log().all()
				.put(LoadProperties().getProperty("UpdateProductQuantity"));

		UpdateproductsQuantity.prettyPrint();

		/* Script Assertion */
		Assert.assertEquals(UpdateproductsQuantity.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(UpdateproductsQuantity.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");
		Assert.assertEquals(UpdateproductsQuantity.getContentType(), "application/json; charset=utf-8", "status code not matching");
		Assert.assertEquals(UpdateproductsQuantity.getSessionId(), null, "status code not matching");
		
		System.out.println("Status code is :" + UpdateproductsQuantity.getStatusCode());
		System.out.println("Response time is :" + UpdateproductsQuantity.getTime());
		System.out.println("Status line is :" + UpdateproductsQuantity.getStatusLine());
		System.out.println("ContentType is :" + UpdateproductsQuantity.getContentType());
		System.out.println("Session ID is :" + UpdateproductsQuantity.getSessionId());
		System.out.println("Successfully updated the Product Quantity to the Store");
	}

	/* Get Product By ID to Verify if the Product Quantity is Updated */

	@Test(priority = 7, dependsOnMethods = "UpdateProductQuantity")
	public void GetProductQuantityDetailsByID() {

		Response ProductQuantityID = given().when().header("Content-Type", "application/json").auth()
				.oauth2(accesstoken1).log().all().get(LoadProperties().getProperty("GetProductDetailsByID1"));

		ProductQuantityID.prettyPrint();

		/* Script Assertion */
		Assert.assertEquals(ProductQuantityID.getStatusCode(), 200, "status code not matching");
		Assert.assertEquals(ProductQuantityID.getStatusLine(), "HTTP/1.1 200 OK", "status code not matching");
		Assert.assertEquals(ProductQuantityID.getContentType(), "application/json; charset=utf-8", "status code not matching");
		Assert.assertEquals(ProductQuantityID.getSessionId(), null, "status code not matching");
		
		System.out.println("Status code is :" + ProductQuantityID.getStatusCode());
		System.out.println("Response time is :" + ProductQuantityID.getTime());
		System.out.println("Status line is :" + ProductQuantityID.getStatusLine());
		System.out.println("ContentType is :" + ProductQuantityID.getContentType());
		System.out.println("Session ID is :" + ProductQuantityID.getSessionId());
		
		String Updated_Quantity = ProductQuantityID.asString().substring(1494,1498);

		/* To Verify if the Product Price is Updated */
		System.out.println("Updated Quantity :" + Updated_Quantity);
	}
}
