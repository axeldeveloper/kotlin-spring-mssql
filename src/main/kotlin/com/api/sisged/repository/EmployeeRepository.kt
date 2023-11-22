package com.api.sisged.repository

import com.api.sisged.model.EmployeeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author  Manuel Ernesto (manuelernest0)
 * @date  05/07/22 8:45 PM
 * @version 1.0
 */

@Repository
interface EmployeeRepository : JpaRepository<EmployeeEntity, Long>