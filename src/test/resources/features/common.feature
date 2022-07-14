@common
Feature: Common steps

  Scenario: Common steps
    Given User redirects to "arm.staging"
      And "page" shows up
      And "page" shows up in 2 timeout
      And User clicks on "element"
      And User clicks on "element" by JS
      And User clicks on "element" if present
      And User clicks on "elementType" by "visibleText"
      And User types "text" into "element"
      And User types "text" into "element" by JS
      And User refresh browser
      And User navigate "Back/Forward"
      And User switch to "iframe"
      And User exit current iframe
      And User scroll down by 200 pixel
      And User scroll down by 200 pixel 4 times
      And User scroll to "element"
      And "element" is present
      And "element" is present in 2 timeout
      And "element" is not present
      And "element" is not present in 2 timeout
      And "element" shows "text"
      And "element" contains "text"
      And "attribute" of "element" has "value"
      And "attribute" of "element" contains "value"
      And User double click on "element"
      And User mouse hover on "element"
      And User press key "ENTER"
      And User waits for 5 seconds

  Scenario: Business steps
    Given User switch to language "English"
