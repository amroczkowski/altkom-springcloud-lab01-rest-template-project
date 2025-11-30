package pl.altkom.springcloud.lab01.resttemplate.projectservice.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import pl.altkom.springcloud.lab01.resttemplate.projectservice.client.model.Employee;
import pl.altkom.springcloud.lab01.resttemplate.projectservice.configuration.ProjectClientsConfiguration;

@Service
public class EmployeeRestClient {

    private static final String GET_PROJECT_EMPLOYEES = "/employee/project/{projectId}";
    private static final String GET_ALL_EMPLOYEES = "/employee";
    private final RestClient restClient;

    public EmployeeRestClient(final RestClient.Builder builder, final ProjectClientsConfiguration projectClientsConfiguration) {
        this.restClient = builder
                .baseUrl(projectClientsConfiguration.getEmployee().getUrl())
                .build();
    }

    public List<Employee> getProjectEmployees(final Long projectId) {

        final Employee[] response = restClient.get()
                .uri(GET_PROJECT_EMPLOYEES, Map.of("projectId", projectId))
                .retrieve()
                .body(Employee[].class);
        return Optional.ofNullable(response).map(Arrays::asList).orElseThrow();
    }

    public List<Employee> getEmployees() {

        final Employee[] response = restClient.get()
                .uri(GET_ALL_EMPLOYEES)
                .retrieve()
                .body(Employee[].class);
        return Optional.ofNullable(response).map(Arrays::asList).orElseThrow();
    }
}
