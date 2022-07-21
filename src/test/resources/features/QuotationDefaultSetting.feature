@QuotationDefaultSetting @regression
Feature: [ARM-2129] Can create quotation

  Background: User is Logged In
    Given User redirects to "arm.staging"
      And "PRE_LOGIN_PAGE" shows up in 4 timeout
      And User clicks on "PRE_LOGIN_PAGE_CONTINUE_BUTTON"
      And "LOGIN_PAGE" shows up
    When User enter valid credentials
    Then "MASTER_PAGE" shows up in 4 timeout

  @QDS-01 @UI
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

  @QDS-06 @FUNC
  Scenario Outline: QDS-06 Verify Validate of Quotation Default setting
    Given User navigate to "Settings" on left menu
      And "SETTINGS_PAGE" shows up
    When User clicks on "SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL" is present
    When User enter a string with 201 chars into "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<issuer_max_len_validation>"
    When User clicks on "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA"
      And User press key "CTRL_A"
      And User press key "DEL"
      And User waits for 1 seconds
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
      And User waits for 2 seconds
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<issuer_required_field_validation>"
    When User clicks on "SETTINGS_PAGE_QUOTATION_RESET_BUTTON"
      And User enter a string with 2001 chars into "SETTINGS_PAGE_QUOTATION_REMARKS_TEXTAREA"
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<remarks_max_len_validation>"
    When User clicks on "SETTINGS_PAGE_QUOTATION_RESET_BUTTON"
      And User upload a "stamp" image with "935kb" size into "SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_UPLOAD_INPUT"
      And User waits for 1 seconds
      And User clicks on "SETTINGS_PAGE_QUOTATION_SAVE_BUTTON"
      And User waits for 2 seconds
    Then "SETTINGS_PAGE_QUOTATION_ERROR_MESSAGE_LABEL" shows "<stamp_max_size_validation>"

    Examples:
      | issuer_max_len_validation | issuer_required_field_validation | remarks_max_len_validation | stamp_max_size_validation       |
      | 200文字以内で入力してください | 入力してください                    | 2000文字以内で入力してください | ファイルのサイズが512KBを超えています |






