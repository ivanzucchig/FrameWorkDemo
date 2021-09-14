Feature: Search Rooms

  Scenario: Make reserve
    Given Open chrome
    And Navigate to "https://www.melia.com/es/home.htm"
    And Accept cookies
    When User enters "CASTILLA"
    And Select Melia Castilla
    Then Calendar is displayed
    And Select day of check in and check out
    And Select Search


  Scenario: Rooms count
    Given Open chrome
    And Navigate to Melia Castilla
    And Accept cookies
    Then The rooms found are displayed
