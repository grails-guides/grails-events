package demo

import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@CompileStatic
class RegisterController {

    static allowedMethods = [index: 'GET', save: 'POST']

    RegisterService registerService

    MessageSource messageSource

    def index() {
        [signUpInstance:  new RegisterCommand()]
    }

    def save(RegisterCommand cmd) {

        if ( cmd.hasErrors() ) {
            render view: 'index', model: [signUpInstance: cmd]
            return
        }

        User user = registerService.register(cmd)
        flash.message = messageSource.getMessage('user.saved',
                [user.email] as Object[],
                "User saved with ${user?.email} email address",
                request.locale)
        redirect action: 'index'
    }
}