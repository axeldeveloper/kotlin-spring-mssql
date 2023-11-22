package com.api.sisged.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/hi")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity<String>("Hi There",HttpStatus.ACCEPTED)
    }
}
