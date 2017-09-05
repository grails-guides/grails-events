package demo

import grails.events.annotation.Subscriber
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class WelcomeEmailService {

    @Transactional
    @Subscriber('userSaved') // <1>
    void onUserSaved(User user) {
        Notification notification = new Notification(email: user.email, subject: 'Welcome to Grails App')
        // TODO Send Email
        if ( !notification.save() ) {
            log.error('unable to save notification {}', notification.errors.toString())
        }
    }
}
