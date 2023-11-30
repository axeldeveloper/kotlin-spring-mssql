package com.api.sisged.model

import java.time.LocalDate
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Email

/**
 * Represents the database entity for storing the employee details.
 */
@Entity
@Table(name = "employees")
data class EmployeeEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(name = "user_name", unique = true, nullable = false)
        val userName: String,
        @Column(name = "first_name", nullable = false)
        val firstName: String = "",
        @Column(name = "middle_name", nullable = true)
        val middleName: String? = null,
        @field:NotBlank(message = "Name must not be blank")
        @Column(name = "last_name", nullable = false)
        val lastName: String = "",
        @Column(name = "email_address", nullable = false)
        val emailId: String = "",
        @Column(name = "day_of_birth", nullable = false)
        val dayOfBirth: LocalDate



) {
    companion object ModelMapper {
        fun from(employee: Employee): EmployeeEntity {
            return EmployeeEntity(
                    id = employee.id,
                    userName = employee.userName,
                    firstName = employee.firstName,
                    middleName = employee.middleName,
                    lastName = employee.lastName,
                    emailId = employee.emailId,
                    dayOfBirth = employee.dayOfBirth
            )
        }
    }
}