package demo

import grails.gorm.services.Service
import groovy.transform.CompileStatic

@Service(User)
@CompileStatic
interface UserService {
    int count()
    void deleteByEmail(String email)
}