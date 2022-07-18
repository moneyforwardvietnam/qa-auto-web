@QuotationDefaultSetting @regression
Feature: [ARM-2129] Can create quotation

  Background: User is Logged In
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up in 4 timeout
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 4 timeout

  @QDS-01
  Scenario: QDS-01 Verify UI of Quotation Menu in Left Menu bar & Create screen of quotation
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
      And User waits for 2 seconds
    When User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL" is present
    When User types "test Reset button" into "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA"
      And User waits for 2 seconds
      And User clicks on "SETTINGS_PAGE_QUOTATION_RESET_BUTTON"
    Then "value" of "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA" has ""



