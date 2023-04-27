package com.in28minutes.learnspringsecurity;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final List<Todo> TODOS_LIST = List.of(new Todo("in28minutes", "Learn AWS"), new Todo("in28minutes", "Get AWS Certified"));

    @GetMapping("/todos")
    public List<Todo> retrieveAllTodos() {
        return TODOS_LIST;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize(value = "hasRole('USER') and #username == authentication.name")
    @PostAuthorize("returnObject.username() == 'in28minutes'")
    @RolesAllowed({"USER"}) //@EnableMethodSecurity(jsr250Enabled = true)
    @Secured({"ROLE_USER"}) //@EnableMethodSecurity(securedEnabled = true)
    public Todo retrieveTodosForSpecificUser(@PathVariable String username) {
        return TODOS_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodoForSpecificUser(@PathVariable String username, @RequestBody Todo todo) {
        logger.info("Create {} for {}", todo, username);
    }

}

record Todo(String username, String description) {
}