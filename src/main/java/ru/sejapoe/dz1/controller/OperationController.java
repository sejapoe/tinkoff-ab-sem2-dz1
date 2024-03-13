package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sejapoe.dz1.model.Operation;
import ru.sejapoe.dz1.service.OperationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operations")
public class OperationController {
    private final OperationService operationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Operation> getOperations() {
        return operationService.getAll();
    }
}
