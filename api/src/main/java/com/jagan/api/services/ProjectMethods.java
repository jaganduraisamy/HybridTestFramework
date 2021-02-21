package com.jagan.api.services;

import com.jagan.api.model.Project;
import com.jagan.api.utils.RequestManager;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectMethods {

    public Response addProject(Map<String, String> bodyData) throws Exception {
        Response response = null;
        try {
            response = RequestManager.getRequest().body(bodyData).when().post("");
            Reporter.log("'Add project' request response details as below.,  <br /> "+response.asString());
            return response;
        }catch (Throwable ex){
            if(response != null)
                Reporter.log("Failed to add project : <br /> "+response.asString());
            throw new Exception(ex);
        }
    }

    public Response deleteProject(String projectId) throws Exception {
        Response response = null;
        try {
            response = RequestManager.getRequest().when().delete("/" + projectId);
            Reporter.log("'Delete project' request for project '"+projectId+"' details as below.,  <br /> "+response.asString());
            return response;
        }catch (Throwable ex){
            if(response != null)
                Reporter.log("Failed to delete project : <br /> "+response.asString());
            throw new Exception(ex);
        }
    }

    public Response getProjects() throws Exception {
        Response response = null;
        try {
            response = RequestManager.getRequest().when().get("" );
            Reporter.log("'Get projects' request response details as below.,  <br /> "+response.asString());
            return response;
        }catch (Throwable ex){
            if(response != null)
                Reporter.log("Failed to Get projects : <br /> "+response.asString());
            throw new Exception(ex);
        }
    }

    public Project GetProject(String projectId) throws Exception {
        return RequestManager.setRequest().when().get("/" + projectId ).as(new TypeRef<Project>() {});
    }

    public Response GetProjectResponse(String projectId) throws Exception {
        return RequestManager.getRequest().when().get("/" + projectId);
    }
    public List<Project> GetProjectsList(String ProjectName) throws Exception {
        List<Project> actualProjectList = RequestManager.getRequest().when().get("").as(new TypeRef<List<Project>>() {});
        ArrayList<Project> matchingProjects = new ArrayList();
        for (Project project: actualProjectList) {
            if(project.name.equals(ProjectName))
                matchingProjects.add(project);
        }
        return matchingProjects;
    }

    public Response UpdateProject(String projectId, Map<String, String> bodyDataToChange) throws Exception {
        return RequestManager.getRequest().body(bodyDataToChange).when().post("/" + projectId);
    }
}
