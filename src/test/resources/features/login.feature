@MHRSLogin
Feature:MHRS Login Check Test Cases
  @SuccessfulLogin
  Scenario Outline: Correct "<username>" Username &  Correct "<password>" Password for login
    Given User at login page
    When write "<username>" for username field
    And write "<password>" for password field
    And Click login button
    Then Check Successful login
    Examples:
      |username               |password         |
      |correctTcID            |correctPassword  |