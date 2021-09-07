# Created by luisf at 07/09/2021
Feature: Manager MainPage
  The manager's main page must allow operations of adding new Locations, viewing orders placed in locations and confirming receptions at locations

  Scenario: Adding Location - Getting to the form
    Given the manager is on manager/MainPage_form
    When the manager clicks on AddLocation Button
    Then the manager gets access to the form


  Scenario: Adding Location - Submitting form
    Given the manager is on manager/addLocationForm
    And the location field in the form isn't empty
    When the manager clicks on the submit button
    Then the manager is on manager/MainPage_Form
    And the new location has been added
