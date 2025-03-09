Feature: Checkout Functionality - France - Visa Payment - Chronopost
  As a user in France
  I want to complete the checkout process using Visa Payment and Chronopost shipping
  So that I can purchase items from the Jacquemus website
  @Checkout
  Scenario: User completes the checkout process successfully
    Given I am on the checkout page France As Region And Accepting all Cookies
    When I fill the cart with a product
    And I check if have item in my cart
    And I fill my shipping details
    And I select a shipping method
    And I fill in my payment details
    And I click the Verify My Information button
    And I Accept the Term and Conditions
    And I click the Pay button
    Then I should see an order confirmation thanks message
