Feature: Brand

  Scenario Outline: Add and save new brand
    Given I open web browser
    When I navigate to brand.html page
    And I click on add brand button
    And I provide brand name as "<name>"
    Then click submit button

    Examples:
      | name |