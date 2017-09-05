package demo

import grails.events.EventPublisher
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@CompileStatic
@Slf4j
class RegisterService implements EventPublisher { // <1>

    @Transactional
    User register(RegisterCommand cmd) {
        String email = cmd.email
        User user = cmd as User
        if ( !user.save() ) {
            log.error 'Unable to save user {}', user.errors.toString()
            return user
        }

        notify('userSaved', email) // <2>
        user
    }
}
