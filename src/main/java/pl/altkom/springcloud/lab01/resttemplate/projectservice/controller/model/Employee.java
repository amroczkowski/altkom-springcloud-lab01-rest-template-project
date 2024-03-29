package pl.altkom.springcloud.lab01.resttemplate.projectservice.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Employee {
    private Long id;

    private String firstName;
    private String lastName;
    private String city;
    @JsonIgnore
    private Long projectId;
}