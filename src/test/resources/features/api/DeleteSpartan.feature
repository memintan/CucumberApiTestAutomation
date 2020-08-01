@api
Feature:Delete Spartan

  @delete_spartan
  Scenario:
    Given authorization credentials are provided for "admin"
    And user accepts content type "application/json"
    Then user sends delete request to "/api/spartans" with last id
    Then user verifies that response status code is 204