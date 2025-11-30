package pl.altkom.springcloud.lab01.resttemplate.projectservice.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.client.model.Employee;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.configuration.ProjectClientsConfiguration;

@RequiredArgsConstructor
@Service
public class EmployeeRestTemplateClient {

    private static final String GET_PROJECT_EMPLOYEES = "/employee/project/{projectId}";
    private static final String GET_ALL_EMPLOYEES = "/employee";
    private final RestTemplate restTemplate;
    private final ProjectClientsConfiguration projectClientsConfiguration;

    public List<Employee> getProjectEmployees(final Long projectId) {
        final ResponseEntity<Employee[]> response = restTemplate
                .exchange(projectClientsConfiguration.getEmployee().getUrl() + GET_PROJECT_EMPLOYEES,
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        Employee[].class,
                        Map.of("projectId", projectId));
        return Optional.ofNullable(response.getBody()).map(Arrays::asList).orElseThrow();
    }

    public List<Employee> getEmployees() {
        final ResponseEntity<Employee[]> response = restTemplate
                .exchange(projectClientsConfiguration.getEmployee().getUrl() + GET_ALL_EMPLOYEES,
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        Employee[].class,
                        Map.of());
        return Optional.ofNullable(response.getBody()).map(Arrays::asList).orElseThrow();
    }
}
