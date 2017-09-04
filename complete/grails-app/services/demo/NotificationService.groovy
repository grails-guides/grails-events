package demo

import grails.gorm.services.Service
import groovy.transform.CompileStatic

@Service(Notification)
@CompileStatic
interface NotificationService {
    int count()
    void deleteByEmail(String email)
}