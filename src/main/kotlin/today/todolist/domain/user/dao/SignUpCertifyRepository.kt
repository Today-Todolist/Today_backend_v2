package today.todolist.domain.user.dao

import org.springframework.data.repository.CrudRepository
import today.todolist.domain.user.domain.SignUpCertify

interface SignUpCertifyRepository: CrudRepository<SignUpCertify, Long>