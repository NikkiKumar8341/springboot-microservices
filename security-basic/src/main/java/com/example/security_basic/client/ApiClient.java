package com.example.security_basic.client;

import com.example.security_basic.dto.request.DepartmentDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "DEPARTMENT-SERVICE")
public interface ApiClient {
    @GetMapping("api/departments/{department-code}")
//    @LoadBalanced
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
}
