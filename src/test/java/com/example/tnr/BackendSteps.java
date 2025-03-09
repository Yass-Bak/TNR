package com.example.tnr;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendSteps {

	private static final Logger logger = LoggerFactory.getLogger(BackendSteps.class);
	APIConstants APIConstants = new APIConstants();
	WireMockUtils WireMockUtils = new WireMockUtils();
	@Given("the order has been placed")
	public void orderIsPlaced() {
		try {
			WireMockUtils.stubGetRequest(APIConstants.BASE_URL+APIConstants.PRODUCTS_ENDPOINT,200,"products.json");
			logger.info("Order placed.");
		} catch (Exception e) {
			logger.error("Error at: @Given(\"the order has been placed\")", e);
			throw new RuntimeException("Step failed: " + e.getMessage());
		}
	}

	@When("B2C sends checkout details to OMS")
	public void b2cSendsDetailsToOms() {
		try {
			WireMockUtils.stubPostRequest(APIConstants.BASE_URL+APIConstants.PRODUCTS_ENDPOINT,201, "CheckoutPost.json","CheckoutPostResponse.json");
			logger.info("Checkout details sent to OMS.");
		} catch (Exception e) {
			logger.error("Error at: @When(\"B2C sends checkout details to OMS\")", e);
			throw new RuntimeException("Step failed: " + e.getMessage());
		}
	}

	@Then("OMS should return eligible delivery methods and promise dates")
	public void omsReturnsDeliveryOptions() {
		WireMockUtils.stubGetRequestWithoutBody(APIConstants.BASE_URL+APIConstants.PRODUCT_ID_ENDPOINT);

		Response response = RestAssured.get(APIConstants.BASE_URL+APIConstants.PRODUCT_ID_ENDPOINT);
		logger.info("Response Body: " + response.getBody().asString());

		response.then().statusCode(200);
		response.then().body("title", equalTo("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops"));
		response.then().body("description", containsString("everyday use and walks in the forest"));
		response.then().body("price", equalTo(109.95F));
		logger.info("OMS returned delivery options.");
	}

	@When("B2C sends an authorization request to PSP Adyen")
	public void b2cSendsAuthRequestToAdyen() {
		// System.out.println("Adyen authorization request sent via Apidog mock.");
	}

	@Then("Adyen should send a response for the authorization to B2C")
	public void adyenSendsResponse() {
		// System.out.println("Adyen response received.");
	}

	@When("B2C makes a stock reservation in OCI")
	public void b2cMakesStockReservationInOci() {
		// System.out.println("Stock reservation made in OCI.");
	}

	@Then("a stock reservation should be made in OCI")
	public void ociReservationConfirmed() {
		// System.out.println("OCI confirmed stock reservation.");
	}

	@When("B2C sends a request to Klaviyo to send an email to the client")
	public void b2cSendsEmailRequestToKlaviyo() {
		// System.out.println("Email request sent to Klaviyo via Requestly mock.");
	}

	@Then("a confirmation order email should be sent to the client")
	public void klaviyoSendsEmail() {
		// System.out.println("Klaviyo sent confirmation email.");
	}

	@When("B2C sends the order to OMS")
	public void b2cSendsOrderToOms() {
		// System.out.println("Order sent to OMS via Requestly mock.");
	}

	@Then("the order should be exported from B2C to OMS")
	public void omsReceivesOrder() {
		// System.out.println("OMS received order.");
	}

	@When("OMS creates the order summary and applies a grace period")
	public void omsCreatesOrderSummary() {
		// System.out.println("OMS created order summary.");
	}

	@Then("the order status should be \"Created\"")
	public void orderStatusCreated() {
		// System.out.println("Order status: Created.");
	}

	@When("OMS waits for the grace period and creates the 'FulfillmentOrder'")
	public void omsCreatesFulfillmentOrder() {
		// System.out.println("OMS created FulfillmentOrder.");
	}

	@Then("the order status should be \"Waiting for Fulfill\"")
	public void orderStatusWaitingForFulfill() {
		// System.out.println("Order status: Waiting for Fulfill.");
	}

	@When("OMS sends 'FulfillmentOrder' to Orli")
	public void omsSendsFulfillmentOrderToOrli() {
		// System.out.println("FulfillmentOrder sent to Orli via WireMock.");
	}

	@Then("the order should be received successfully by Orli")
	public void orliReceivesOrder() {
		// System.out.println("Orli received FulfillmentOrder.");
	}

	@When("Orli checks the fulfillment order details and creates tracking info")
	public void orliCreatesTrackingInfo() {
		// System.out.println("Orli created tracking info.");
	}

	@Then("Shipup should receive order information")
	public void shipupReceivesOrderInfo() {
		// System.out.println("Shipup received order info.");
	}

	@When("Orli sends a ship confirmation to OMS")
	public void orliSendsShipConfirmationToOms() {
		// System.out.println("Ship confirmation sent to OMS.");
	}

	@Then("OMS should change the order status to \"Fulfilled\"")
	public void omsUpdatesOrderStatusToFulfilled() {
		// System.out.println("Order status: Fulfilled.");
	}

	@When("OMS creates an expected return and sends it to ORLi")
	public void omsSendsExpectedReturnToOrli() {
		// System.out.println("Expected return sent to Orli.");
	}

	@Then("Orli should receive the expected return")
	public void orliReceivesExpectedReturn() {
		// System.out.println("Orli received expected return.");
	}

	@When("the stock reservation is fulfilled for the quantity sent to omnichannel inventory")
	public void stockReservationFulfilled() {
		// System.out.println("Stock reservation fulfilled.");
	}

	@Then("a response should be received from omnichannel inventory")
	public void omnichannelInventoryResponds() {
		// System.out.println("Omnichannel inventory confirmed.");
	}

	@When("the payment capture is triggered to Adyen or PayPal from OMS")
	public void omsTriggersPaymentCapture() {
		// System.out.println("Payment capture triggered.");
	}

	@Then("the payment should be captured from Adyen or PayPal")
	public void paymentCaptured() {
		// System.out.println("Payment captured.");
	}

	@When("OMS sends the invoice payload to Talend")
	public void omsSendsInvoiceToTalend() {
		// System.out.println("Invoice payload sent to Talend.");
	}

	@Then("Talend should generate the invoice document and send it to Azure")
	public void talendGeneratesInvoice() {
		// System.out.println("Talend generated invoice.");
	}

	@When("Azure sends the URL of the invoice to Talend")
	public void azureSendsInvoiceUrlToTalend() {
		// System.out.println("Azure sent invoice URL to Talend.");
	}

	@Then("Talend should receive the URL and send it to OMS")
	public void talendSendsInvoiceUrlToOms() {
		// System.out.println("Talend sent invoice URL to OMS.");
	}

	@When("OMS triggers an order shipping confirmation email")
	public void omsTriggersShippingEmail() {
		// System.out.println("Shipping email triggered.");
	}

	@Then("Klaviyo should send the order shipping confirmation email to the client")
	public void klaviyoSendsShippingEmail() {
		// System.out.println("Klaviyo sent shipping email.");
	}

	@When("Shipup sends the tracking details to OMS")
	public void shipupSendsTrackingDetailsToOms() {
		// System.out.println("Tracking details sent to OMS via Apidog mock.");
	}

	@Then("OMS should update the order summary according to the fulfillment order status and product quantities")
	public void omsUpdatesOrderSummary() {
		// System.out.println("OMS updated order summary.");
	}
}