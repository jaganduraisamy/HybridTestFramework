package com.jagan.api.steps;

import com.jagan.api.model.Project;
import com.jagan.api.services.ProjectMethods;
import com.jagan.api.utils.RequestManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectSteps {

    ThreadLocal<Response> responseList = new ThreadLocal<>();
    ThreadLocal<Project> projectList = new ThreadLocal<>();
    ProjectMethods projectMethods = new ProjectMethods();
    @Given("^I have an valid request$")
    public void i_have_an_authenticated_token() throws Throwable {
        RequestManager.getRequest();
    }

    @When("^I send Get request to Get all projects$")
    public void I_send_Get_request_to_Get_all_projects() throws Throwable {
        responseList.set(projectMethods.getProjects());
        System.out.println("Response is " + responseList.get().prettyPrint());
    }

    @Then("^I should have the status code \"([^\"]*)\"$")
    public void i_should_have_the_status_code(String statusCode) throws Throwable {
        Assert.assertEquals((long)responseList.get().statusCode(), (long)Integer.parseInt(statusCode));
    }

    @When("^I send post request to create new project with name as (.*)$")
    public void iSendPostRequestToCreateNewProjectWithNameAs(String projectName) throws Throwable {
        Map<String, String> map = new HashMap();
        map.put("name", projectName);
        responseList.set(projectMethods.addProject(map));
        System.out.println("Response is " + responseList.get().prettyPrint());
        projectList.set(responseList.get().getBody().as(Project.class));
    }

    @And("^I should see project name is (.*)$")
    public void iShouldSeeProjectNameIsJDTestProject(String projectName) {
        Assert.assertTrue(projectList.get().name.equals(projectName));
    }

    @And("^the body response should match with project-schema$")
    public void theBodyResponseShouldMatchWithProjectSchema() {
        responseList.get().then().assertThat().contentType(ContentType.JSON);

        responseList.get().then().assertThat().contentType(ContentType.JSON).and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("project-schema.json"));
    }

    @And("^I have an existing project with name (.*)$")
    public void iHaveAnExistingProjectWithName(String projectName) throws Exception {
//        List<Project> projectList = projectMethods.GetProjectsList(projectName);
        projectList.set(projectMethods.GetProjectsList(projectName).get(0));
    }

    @When("^I send update request to update project name as (.*)$")
    public void iSendUpdateRequestToUpdateProjectNameAs(String projectNameToChange) throws Exception {
        Map<String, String> bodyDataToChange = new HashMap();
        bodyDataToChange.put("name", projectNameToChange);
        responseList.set(projectMethods.UpdateProject(String.valueOf(projectList.get().id),bodyDataToChange));
        Assert.assertEquals(responseList.get().statusCode(), 204,"Stauts code is not expected for 'Update Project' request.");
    }

    @Then("^I should see project name successfully updated as (.*)$")
    public void iShouldSeeProjectNameSuccessfullyUpdatedAsJDTestProject(String expProjectName) throws Exception {
        Project newProject = projectMethods.GetProject(String.valueOf(projectList.get().id));
        Assert.assertTrue(newProject.name.equals(expProjectName),"'Project' name is not expected.");
    }

    @When("^I send delete request to delete existing project with name (.*)$")
    public void iSendDeleteRequestToDeleteExistingProjectWithNameContains(String projectNameKeyword) throws Exception {
        projectList.set(projectMethods.GetProjectsList(projectNameKeyword).get(0));
        responseList.set(projectMethods.deleteProject(String.valueOf(projectList.get().id)));
        System.out.println("Response is " + responseList.get().prettyPrint());
        Assert.assertEquals(responseList.get().statusCode(), 204,"Status code is not expected.");
    }

    @Then("^I should not see project in projects list$")
    public void iShouldNotSeeProjectInProjectsList() throws Exception {
        responseList.set(projectMethods.GetProjectResponse(String.valueOf(projectList.get().id)));
        System.out.println("Response is " + responseList.get().prettyPrint());
        Assert.assertEquals(responseList.get().statusCode(), 404);
        responseList.get().then().assertThat().toString().equals("Not Found");
    }
}
