package pl.altkom.springcloud.lab01.resttemplate.projectservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.CreateProjectRequest;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.UpdateProjectRequest;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model.Project;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.service.ProjectService;

@RequiredArgsConstructor
@RequestMapping("/project")
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable("id") final Long projectId) {
        return projectService.getProject(projectId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project createProject(@Valid @RequestBody final CreateProjectRequest request) {
        return projectService.createProject(request);
    }

    @PutMapping("/{id}")
    public Project modifyProject(@PathVariable("id") final Long projectId, @Valid @RequestBody final UpdateProjectRequest request) {
        return projectService.modifyProject(projectId, request);
    }
}
