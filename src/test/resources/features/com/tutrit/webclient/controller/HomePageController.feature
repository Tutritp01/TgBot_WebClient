Feature: User data on home page
  User should be able to see his name and age on the main page

  Scenario: Open page with user predefined values
    Given EngineerGateway has no connection to rest service
    When User opens home page
    Then He should see "nul null" in the user data element