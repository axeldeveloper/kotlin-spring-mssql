package com.api.sisged.service

import com.api.sisged.model.EmployeeEntity
import com.api.sisged.repository.EmployeeRepository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  05/07/22 8:46 PM
 * @version 1.0
 */

@Service
class EmployeeService(val repository: EmployeeRepository) {

    fun getAll(): List<EmployeeEntity> = repository.findAll()

    fun getById(id: Long): EmployeeEntity = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(player: EmployeeEntity): EmployeeEntity = repository.save(player)

    fun remove(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    /* 
    fun update(id: Long, player: EmployeeEntity): EmployeeEntity {
        return if (repository.existsById(id)) {
            player.id = id
            repository.save(player)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }*/
}