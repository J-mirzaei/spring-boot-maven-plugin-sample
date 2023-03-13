Feature: Get answer when endpoint is called

  Scenario: When we call the /greetings endpoint we expect the correct answer
    When I call "/greetings"
    Then I expect "Hello, Initializr!"
