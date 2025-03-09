	Feature: Backend Order Fulfilment Processes
	  As a system administrator
	  I want to ensure backend processes for order fulfillment are triggered correctly
	  @Backend
	  Scenario: Backend Processes for Order Fulfillment
	    Given the order has been placed
	    When B2C sends checkout details to OMS
	    Then OMS should return eligible delivery methods and promise dates
	    When B2C sends an authorization request to PSP Adyen
	    Then Adyen should send a response for the authorization to B2C
	    When B2C makes a stock reservation in OCI
	    Then a stock reservation should be made in OCI
	    When B2C sends a request to Klaviyo to send an email to the client
	    Then a confirmation order email should be sent to the client
	    When B2C sends the order to OMS
	    Then the order should be exported from B2C to OMS
	    When OMS creates the order summary and applies a grace period
	    Then the order status should be "Created"
	    When OMS waits for the grace period and creates the 'FulfillmentOrder'
	    Then the order status should be "Waiting for Fulfill"
	    When OMS sends 'FulfillmentOrder' to Orli
	    Then the order should be received successfully by Orli
	    When Orli checks the fulfillment order details and creates tracking info
	    Then Shipup should receive order information
	    When Orli sends a ship confirmation to OMS
	    Then OMS should change the order status to "Fulfilled"
	    When OMS creates an expected return and sends it to ORLi
	    Then Orli should receive the expected return
	    When the stock reservation is fulfilled for the quantity sent to omnichannel inventory
	    Then a response should be received from omnichannel inventory
	    When the payment capture is triggered to Adyen or PayPal from OMS
	    Then the payment should be captured from Adyen or PayPal
	    When OMS sends the invoice payload to Talend
	    Then Talend should generate the invoice document and send it to Azure
	    When Azure sends the URL of the invoice to Talend
	    Then Talend should receive the URL and send it to OMS
	    When OMS triggers an order shipping confirmation email
	    Then Klaviyo should send the order shipping confirmation email to the client
	    When Shipup sends the tracking details to OMS
	    Then OMS should update the order summary according to the fulfillment order status and product quantities
