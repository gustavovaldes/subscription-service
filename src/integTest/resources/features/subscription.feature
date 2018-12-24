Feature: Email Subscription

  Scenario: User subscribe to service with all the information
    Given I have a valid token
    When I subscribe to the service with this information
      | firstName    | Jose               |
      | email        | abcd1234@gmail.com |
      | dateOfBirth  | 2000-10-10         |
      | newsletterId | 123123123          |
      | consent      | true               |
    Then The subscription should be created successfully
    And The subscription information should be
      | email        | abcd1234@gmail.com |
      | dateOfBirth  | 2000-10-10         |
      | status       | COMPLETED          |
      | newsletterId | 123123123          |


  @NotImplemented
  Scenario: User try to subscribe without all the mandatory information

  @NotImplemented
  Scenario: User try to subscribe with an invalid date format

  @NotImplemented
  Scenario: User try to subscribe with an email already retered

  @NotImplemented
  Scenario: User try to subscribe when event service is failing

  @NotImplemented
  Scenario: User try to subscribe when email service is failing
