@Quotation_settings
Feature: Quotation

  Background: User is Logged In
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up in 4 timeout
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 10 timeout


  @QDS-01 @UI @Quotation
  Scenario Outline: QDS-01 Verify User can access and see default values in Quotation Default setting
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
      And User waits for 2 seconds
    When User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL" is present
      And "value" of "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA" has ""
    When User types "test Reset button" into "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA"
      And User waits for 2 seconds
      And User clicks on "SETTINGS_PAGE_QUOTATION_RESET_BUTTON"
    Then "value" of "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA" has ""
      And "class" of "SETTINGS_PAGE_QUOTATION_DELETE_IMAGE_BUTTON" contains "disabled"
      And "value" of "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA" has "<issuer_default_val>"
    When User types "<issuer_editable_val>" into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User waits for 2 seconds
    Then "value" of "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA" has "<issuer_editable_val>"
      And "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_DEFAULT_LABEL" is present
      And "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_HAS_IMAGE" is not present

    Examples:
      | issuer_default_val | issuer_editable_val  |
      | ARM Bear Office 11 | test Issuer editable |


  @QDS-06 @Func @Quotation
  Scenario Outline: QDS-06 Verify Validate of Quotation Default setting
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User enter a string with 201 chars into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<issuer_max_len_err>"
    When User clicks on "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User press key "CTRL_A"
      And User press key "DEL"
      And User waits for 1 seconds
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
      And User waits for 2 seconds
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<issuer_required_field_err>"
    When User clicks on "SETTINGS_PAGE_QUOTATION_RESET_BUTTON"
      And User enter a string with 2001 chars into "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<remarks_max_len_err>"
    When User clicks on "SETTINGS_PAGE_QUOTATION_RESET_BUTTON"
      And User upload a "stamp" image with "935kb" size into "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_UPLOAD_INPUT"
      And User waits for 1 seconds
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
      And User waits for 2 seconds
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<stamp_max_size_err>"

    Examples:
      | issuer_max_len_err       | issuer_required_field_err | remarks_max_len_err       | stamp_max_size_err               |
      | 200文字以内で入力してください | 入力してください            | 2000文字以内で入力してください | ファイルのサイズが512KBを超えています |


  @QDS-07 @Func @Quotation
  Scenario: QDS-07 Verify User can Edit Quotation Default setting
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User enter a string with 5 chars into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
    Then "SETTINGS_PAGE_QUOTATION_ISSUER_PREVIEW_LABEL" should show correct text per inputted
    When User enter a string with 10 chars into "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA"
    Then "SETTINGS_PAGE_QUOTATION_REMARKS_PREVIEW_LABEL" should show correct text per inputted
    When User upload a "stamp" image with "68kb" size into "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_UPLOAD_INPUT"
    Then "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_PREVIEW_IMAGE" should show correct text per inputted
      And "SETTINGS_PAGE_QUOTATION_DELETE_IMAGE_BUTTON" should be "ENABLED"
    When User clicks on "SETTINGS_PAGE_QUOTATION_DELETE_IMAGE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_DEFAULT_LABEL" is present


  @QDS-13 @Func @Quotation
  Scenario Outline: QDS-13 Verify two different offices have unique default format settings of Quotation
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User types "<issuer_office_1_input>" into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL" is present
    When User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up in 5 timeout
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present
    When User select first "Client" for New Quotation
      And User select first "Item" for New Quotation
      And User clicks on "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON"
    Then "QUOTATION_PAGE_NEW_QUOTATION_SAVED_SUCCESS_LABEL" is present
      And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" shows "<issuer_office_1_input>"
    When User logout ARM
      And User clicks on "PRE_LOGIN_PAGE_LOGIN_BUTTON"
    Then "PRE_LOGIN_PAGE" shows up in 4 timeout
    When User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
    Then "LOGIN_PAGE" shows up
    When User clicks on "LOGIN_PAGE_USE_ANOTHER_BUTTON"
      And User enter valid credentials of "diff.office"
    Then "MASTER_PAGE" shows up in 10 timeout
    When User navigate to "Settings" on left menu
    Then "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User types "<issuer_office_2_input>" into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL" is present
    When User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up in 5 timeout
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present
    When User select first "Client" for New Quotation
      And User clicks on "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON"
    Then "QUOTATION_PAGE_NEW_QUOTATION_SAVED_SUCCESS_LABEL" is present
      And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" shows "<issuer_office_2_input>"

    Examples:
      | issuer_office_1_input | issuer_office_2_input |
      | ARM Test Office A     | ARM Test Office B     |


  @QDS-14 @Func @Quotation #TODO: write scripts to switch to same office
  Scenario Outline: QDS-14 Verify two users in the same office have same Quotation setting
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User types "<issuer_office_1_input>" into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL" is present
    When User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up in 5 timeout
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present
    When User select first "Client" for New Quotation
      And User select first "Item" for New Quotation
      And User clicks on "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON"
    Then "QUOTATION_PAGE_NEW_QUOTATION_SAVED_SUCCESS_LABEL" is present
      And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" shows "<issuer_office_1_input>"
    When User logout ARM
      And User clicks on "PRE_LOGIN_PAGE_LOGIN_BUTTON"
    Then "PRE_LOGIN_PAGE" shows up in 4 timeout
    When User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
    Then "LOGIN_PAGE" shows up
    When User clicks on "LOGIN_PAGE_USE_ANOTHER_BUTTON"
      And User enter valid credentials of "diff.office"
    Then "MASTER_PAGE" shows up in 10 timeout
    When User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up in 5 timeout
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present
    When User select first "Client" for New Quotation
      And User clicks on "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON"
    Then "QUOTATION_PAGE_NEW_QUOTATION_SAVED_SUCCESS_LABEL" is present
      And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" shows "<issuer_office_1_input>"

    Examples:
      | issuer_office_1_input |
      | ARM Test Office A     |


  @QDS-15 @Func @Quotation #TODO: write scripts to switch to same office
  Scenario Outline: QDS-15 Verify while one user creating quotation, if another one user update the quotation setting
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User types "<issuer_office_1_input>" into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL" is present
    When User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up in 5 timeout
    When User clicks on "BUTTON" by "Create"
    Then "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON" is present in 5 timeout
      And "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT" is present
    When User select first "Client" for New Quotation
      And User select first "Item" for New Quotation
      And User update Quotation setting using "same.office" account
      And User clicks on "QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON"
    Then "QUOTATION_PAGE_NEW_QUOTATION_SAVED_SUCCESS_LABEL" is present
    And "QUOTATION_PAGE_NEW_QUOTATION_ISSUER_PREVIEW_LABEL" is present in 5 timeout

    Examples:
      | issuer_office_1_input |
      | ARM Test Office A     |


  @QDS-0 @UI @Quotation @Quotation_list
  Scenario: Verify can access Quotation list and check UI of Quotation List
    Given User navigate to "Quotations" on left menu
      And "QUOTATION_PAGE" shows up in 5 timeout
      And "QUOTATION_PAGE_FILTER_BY_QUOTATION_NUM_INPUT" is present
      And "QUOTATION_PAGE_FILTER_BY_QUOTATION_CLIENT_INPUT" is present
      And "QUOTATION_PAGE_FILTER_BY_QUOTATION_TITLE_INPUT" is present
      And "QUOTATION_PAGE_FILTER_BY_QUOTATION_QUOTE_DATE_DATEPICKER" is present
      And "QUOTATION_PAGE_FILTER_BY_CREATOR_DROPDOWN" is present
      And "QUOTATION_PAGE_FILTER_BY_ESTIMATED_AMOUNT_FROM_INPUT" is present
      And "QUOTATION_PAGE_FILTER_BY_ESTIMATED_AMOUNT_TO_INPUT" is present
      And "QUOTATION_PAGE_FILTER_BUTTON" is present
      And "QUOTATION_PAGE_FILTER_BUTTON" should be "ENABLED"
      And "QUOTATION_PAGE_RESET_BUTTON" is present
      And "QUOTATION_PAGE_RESET_BUTTON" should be "ENABLED"
      And "QUOTATION_PAGE_CREATE_BUTTON" is present
      And "QUOTATION_PAGE_CREATE_BUTTON" should be "ENABLED"
      And "QUOTATION_PAGE_HIDE_SHOW_FILTER_BUTTON" is present











