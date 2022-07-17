@ARM-2129 @regression
Feature: [ARM-2129] Can create quotation

  Background: User is Logged In
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 4 timeout
      And User switch to language "English"

  @C4924
  Scenario: C4924 - Verify UI of Quotation Menu in Left Menu bar & Create screen of quotation
    Given User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 4 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present

  @C4925
  Scenario: C4925 - Verify Create button in the quotation creation screen is enabled initially
    Given User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 4 timeout
      And Create button in the quotation creation screen should be enabled initially

  @C4930
  Scenario Outline: C4930 - Verify Quotation is created with the mandatory fields alone
    Given User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 4 timeout
    When User enter Client info as below
      | client         | vu bach       |
      | client_details | Money Forward |
      | title          | QA Engineer   |
      And User enter "<quotation_number>" into the mandatory field "QUOTATION_PAGE_NEW_QUOTATION_QUOTATION_NUMBER_INPUT"
      And User enter "<quote_date>" into the mandatory field "QUOTATION_PAGE_NEW_QUOTATION_QUOTE_DATE_INPUT"
      And User enter "<expiration_date>" into the mandatory field "QUOTATION_PAGE_NEW_QUOTATION_EXPIRATION_DATE_INPUT"
      And User waits for 5 seconds
    Examples:
      | quotation_number | quote_date | expiration_date |
      | 123              | 2022/07/17 | 2022/08/30      |




