@projects
Feature: This feature is contains API test

  @Get_Projects
  Scenario: To verify user able to get all project list
    Given I have an valid request
    When I send Get request to Get all projects
    Then I should have the status code "200"

  @Add_Project
  Scenario: User should be able to add a new project
    Given I have an valid request
    When I send post request to create new project with name as JD-Test Project 1
    Then I should have the status code "200"
    And I should see project name is JD-Test Project 1
    And the body response should match with project-schema

  @Update_Project
  Scenario: User should be able to edit and update an existing project name
    Given I have an valid request
    And I have an existing project with name JD-Test Project 1
    When I send update request to update project name as JD-Test Project 2
    Then I should see project name successfully updated as JD-Test Project 2

  @Delete_Project
  Scenario: User should be able to delete existing projects
    Given I have an valid request
    When I send delete request to delete existing project with name JD-Test Project 2
    Then I should not see project in projects list