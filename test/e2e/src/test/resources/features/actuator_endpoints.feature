Feature: Actuator endpoints are secured except for health and info

  Scenario Outline: When we call the <endpoint> endpoint with no authentication we expect the status code to be <code>
    When I call "<endpoint>"
    Then I expect a HTTP <code>

    Examples:
      | endpoint         | code |
      | /actuator/info   | 200  |
      | /actuator/health | 200  |
      | /actuator/env    | 401  |
