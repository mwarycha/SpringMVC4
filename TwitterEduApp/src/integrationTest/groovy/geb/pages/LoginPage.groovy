package geb.pages

import geb.Page

class LoginPage extends Page {

    static url = '/login'

    static at  = {
        $('h2', 0).text() == 'Logowanie'
    }

    static content = {
        twitterSignin { $('button', name: 'twitterSignin') }
    }

    void loginWithTwitter() {
        twitterSignin.click()
    }
}
