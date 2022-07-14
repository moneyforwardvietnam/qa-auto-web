@ARM-2129
Feature: [ARM-2129] Can create quotation

  @C4924
  Scenario: C4924 - Verify UI of Quotation Menu in Left Menu bar & Create screen of quotation
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 2 timeout
      And User waits for 30 seconds
