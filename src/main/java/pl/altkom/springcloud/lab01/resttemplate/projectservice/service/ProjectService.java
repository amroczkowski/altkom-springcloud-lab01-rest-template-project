package pl.altkom.springcloud.lab01.resttemplate.projectservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.client.EmployeeRestTemplateClient;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.mapper.RequestMapper;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.mapper.ResponseMapper;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.CreateProjectRequest;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.Employee;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.Project;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.UpdateProjectRequest;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.repository.ProjectRepository;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final EmployeeRestTemplateClient employeeClient;
//    private final EmployeeWebClient employeeClient;
//    private final EmployeeRestClient employeeClient;
    private final ProjectRepository projectRepository;

    public List<Project> getProjects() {
        final List<Employee> employees = pl.altkom.springcloud.lab01.resttemplate.projectservice.client.mapper.ResponseMapper.map(
                employeeClient.getEmployees());
        return ResponseMapper.map(projectRepository.findAll(), employees);
    }

    public Project getProject(final Long projectId) {
        final pl.altkom.springcloud.lab01.resttemplate.projectservice.repository.model.Project project = projectRepository.findById(
                        projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final List<Employee> employees = pl.altkom.springcloud.lab01.resttemplate.projectservice.client.mapper.ResponseMapper.map(
                employeeClient.getProjectEmployees(project.getId()));
        return ResponseMapper.map(project, employees);
    }

    public Project createProject(final CreateProjectRequest request) {
        final pl.altkom.springcloud.lab01.resttemplate.projectservice.repository.model.Project savedProject = projectRepository.save(
                RequestMapper.bind(request));
        final List<Employee> employees = pl.altkom.springcloud.lab01.resttemplate.projectservice.client.mapper.ResponseMapper.map(
                employeeClient.getEmployees());
        return ResponseMapper.map(savedProject, employees);
    }

    public Project modifyProject(final Long projectId, final UpdateProjectRequest request) {

        final pl.altkom.springcloud.lab01.resttemplate.projectservice.repository.model.Project sourceProject = projectRepository.findById(
                        projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        final pl.altkom.springcloud.lab01.resttemplate.projectservice.repository.model.Project modifiedProject = projectRepository
                .save(RequestMapper.bind(request, sourceProject));
        final List<Employee> employees = pl.altkom.springcloud.lab01.resttemplate.projectservice.client.mapper.ResponseMapper
                .map(employeeClient.getProjectEmployees(modifiedProject.getId()));
        return ResponseMapper.map(modifiedProject, employees);
    }
}
