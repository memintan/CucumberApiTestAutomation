@2
  Feature: Add new Spartan

    @add_spartan
    Scenario Outline: Add new Spartan
      Given authorization credentials are provided for "admin"
      And user accepts content type "application/json"
      When user create Spartan POJO Object with following information
        | name   | gender   | phone   |
        | <name> | <gender> | <phone> |
      Then user sends POST request to "/api/spartans"
      Then user verifies that response status code is 201
      And user verifies that Spartan Born
      Examples:
      | name          | gender | phone      |
      | ErosRamazotti | Male   | 0658565888 |
      | PaoloMaldini  | Male   | 5352874610 |
      | NadiaKomonaci | Female | 5557457981 |
      | BridgedBardo  | Female | 8323393781 |


    @add_spartan_with_list
    Scenario: Add new Spartan
      Given authorization credentials are provided for "admin"
      And user accepts content type "application/json"
      When user create Spartan POJO Object with List information
        | Mehmet     |
        | Male       |
        | 8328556449 |
      Then user sends POST request to "/api/spartans"
      Then user verifies that response status code is 201
      And user verifies that Spartan Born

