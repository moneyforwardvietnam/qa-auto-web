@ARM-2129 @regression
Feature: [ARM-2129] Can create quotation

  @C4924
  Scenario: C4924 - Verify UI of Quotation Menu in Left Menu bar & Create screen of quotation
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 4 timeout
      And User switch to language "English"
    When User navigate to "Quotations" on left menu
    Then "QUOTATION_PAGE" shows up
      And User clicks on "BUTTON" by "Create"
      And "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present

  @C4925
  Scenario: C4925 - Verify Create button in the quotation creation screen is enabled initially
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 4 timeout
      And User switch to language "English"
    When User navigate to "Quotations" on left menu
    Then "QUOTATION_PAGE" shows up
      And User clicks on "BUTTON" by "Create"
      And "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present
      And Create button in the quotation creation screen should be enabled initially


