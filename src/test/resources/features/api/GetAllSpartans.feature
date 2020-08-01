@0
Feature: Display All Spartans
  Verify status code 200 when send get request to see all spartans

  Scenario: Display All Spartans
    Given authorization credentials are provided for "admin"
    And user accepts content type "application/json"
    When user sends GET request to "/api/spartans"
    Then user verifies that response status code is 200
    And user verifies that response has 102 Spartans

