package com.dhcho.ac.api;

import com.dhcho.ac.repository.AccessRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccessApiController {

    private final AccessRepository accessRepository;

//    @GetMapping("/api/v1/access")
//    public Result addAccessV1()
//    @PostMapping("/api/v1/access")
//    public Result findAccessV1()

//    @PutMapping("/api/v1/access/{id}")
//    public Result updateAccessV1(@PathVariable("id") Long id) {
//    }

//    @DeleteMapping("/api/v1/access/{id}")
//    public Result removeAccessV1(@PathVariable("id") Long id) {
//
//        return new Result("");
//    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
