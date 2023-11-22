package com.api.sisged

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import java.util.*


@SpringBootApplication
class SisgedApiApplication

fun main(args: Array<String>) {
	runApplication<SisgedApiApplication>(*args)
}


//@RequestMapping("/")
@GetMapping("/")
@ResponseBody
fun index()  : ResponseEntity<String> {
	return ResponseEntity("This is home!" , HttpStatus.OK)
}


@GetMapping("/hello")
@ResponseBody
fun hello(@RequestParam  names: String): String {
    return "Hello $names"
}