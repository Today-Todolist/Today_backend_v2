package today.todolist.domain.user.dao

import org.springframework.data.repository.CrudRepository
import today.todolist.domain.user.domain.ChangePasswordCertify

interface ChangePasswordCertifyRepository: CrudRepository<ChangePasswordCertify, Long>