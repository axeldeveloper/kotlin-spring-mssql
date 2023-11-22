package com.api.sisged.controller
import org.springframework.beans.factory.annotation.Autowired
//import java.util.*

//import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import com.api.sisged.service.EmployeeService
import com.api.sisged.model.EmployeeEntity



@RequestMapping("/api/v1/")
@RestController
class EmployeeController(val service: EmployeeService) {

    @GetMapping("/employees")
    fun getAllEmployees() = service.getAll()

    @GetMapping("/employee/{id}")
    fun getById(@PathVariable("id") employeeId: Long) : ResponseEntity<EmployeeEntity> {
        var rows: EmployeeEntity? = service.getById(employeeId);
        return ResponseEntity<EmployeeEntity>(rows, HttpStatus.OK)
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    fun savePlayer(@RequestBody payload: EmployeeEntity): EmployeeEntity = service.create(payload)

    // @PutMapping("/employee/{id}")
    // fun updateById(@PathVariable("id") employeeId: Long) : ResponseEntity<Long> {
    //     return ResponseEntity<Long>(employeeId, HttpStatus.OK)
    // }

    @PutMapping("/employee/{id}")
    fun updateById(@PathVariable("id") employeeId: Long, @RequestBody user: EmployeeEntity): ResponseEntity<EmployeeEntity> {

        val existingUser = service.getById(employeeId)

        if (existingUser == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedUser = existingUser.copy(userName = user.userName, firstName = user.firstName)
        service.create(updatedUser)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }


}