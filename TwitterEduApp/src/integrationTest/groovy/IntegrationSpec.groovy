
import com.twitter.TwitterEduApp.TwitterEduAppApplication
import com.twitter.TwitterEduApp.search.StubSocialSigninConfig
import com.twitter.TwitterEduApp.search.StubTwitterSearchConfig
import geb.Configuration
import geb.pages.LoginPage
import geb.pages.ProfilePage
import geb.pages.SearchResultPage
import geb.spock.GebSpec
import org.springframework.boot.test.SpringApplicationContextLoader

import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(loader = SpringApplicationContextLoader,
        classes = [TwitterEduAppApplication, StubTwitterSearchConfig, StubSocialSigninConfig])
@WebIntegrationTest(randomPort = true)
class IntegrationSpec extends GebSpec {

    Configuration createConf() {
        def configuration = super.createConf()
        configuration.baseUrl = "https://localhost:8443"
        configuration
    }

    def "Jeżeli użytkownik nie jest zalogowany, kierowany jest na stronę logowania"() {

        when: "Otwieram stronę główną"
        go '/'

        then: "Jestem kierowany na stronę logowania"
        $('h2').text() == 'Logowanie'
    }

    def "Użytkownik podczas pierwszej wizyty jest kierowany na stronę profilu"() {

        when: 'Połączyłem się'
        to LoginPage
        loginWithTwitter()

        and: "Otwieram stronę główną"
        go '/'

        then: "Jestem kierowany na stronę profilu"
        $('h2').text() == 'Your profile'
    }

    def "Po zdefiniowaniu profilu wyświetlane są wyniki odpowiadające jego preferencjom"() {

        given: 'Połączyłem się'
        to LoginPage
        loginWithTwitter()

        and: 'Jestem na stronie profilu’'
        to ProfilePage

        when: 'Wypełniam profil'
        fillInfos("mwarycha", "programista@adrespoczty.pl", "03/19/1987");
        addTaste("spring")

        and: 'Zapisuję'
        saveProfile()

        then: 'Jestem przeniesiony na stronę z wynikami wyszukiwania'
        at SearchResultPage
        page.results.size() == 2
    }
}