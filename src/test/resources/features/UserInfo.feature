Feature: User data on home page
  User should be able to see his name and age on the main page

  Scenario: User Sign in to application
    Given User login form opened
    When User enter valid credentials
    Then He should see his name and age in the top of left menu