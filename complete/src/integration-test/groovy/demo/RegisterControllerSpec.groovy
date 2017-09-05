package demo

import geb.spock.GebSpec
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.IgnoreIf

@IgnoreIf({ !System.getProperty('geb.env') })
@Integration
class RegisterControllerSpec extends GebSpec {

    NotificationService notificationService

    UserService userService

    @Rollback
    def "If you signup a User, an Event triggers which causes a Notification to be saved"() {
        when: 'you signup with a non existing user'
        go '/signup'
        $(name: "firstName") << 'Sergio'
        $(name: "lastName") << 'del Amo'
        $(name: "email") << 'delamos@email.com'
        $('#submit').click()

        then: 'the user gets created and a notification is saved due to the event being triggered'
        userService.count() == old(userService.count()) + 1
        notificationService.count() == old(notificationService.count()) + 1

        when: 'you try to signup with a user which is already in the database'
        go '/signup'
        $(name: "firstName") << 'Sergio'
        $(name: "lastName") << 'del Amo'
        $(name: "email") << 'delamos@email.com'
        $('#submit').click()

        then: 'The user is not saved and no event gets triggered'
        noExceptionThrown()
        userService.count() == old(userService.count())
        notificationService.count() == old(notificationService.count())


        cleanup:
        userService.deleteByEmail('delamos@email.com')
        notificationService.deleteByEmail('delamos@email.com')
    }
}
