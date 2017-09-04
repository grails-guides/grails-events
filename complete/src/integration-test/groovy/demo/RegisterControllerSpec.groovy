package demo

import geb.spock.GebSpec
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.IgnoreIf

@IgnoreIf({System.getProperty('geb.env')})
@Integration
class RegisterControllerSpec extends GebSpec {

    NotificationService notificationService

    UserService userService

    @Rollback
    def "If you signup a User, an Event triggers which causes a Notification to be saved"() {
        when:
        go '/signup'
        $(name: "firstName") << 'Sergio'
        $(name: "lastName") << 'del Amo'
        $(name: "email") << 'delamos@email.com'
        $('#submit').click()

        then:
        userService.count() == old(userService.count()) + 1
        notificationService.count() == old(notificationService.count()) + 1

        cleanup:
        userService.deleteByEmail('delamos@email.com')
        notificationService.deleteByEmail('delamos@email.com')
    }
}
