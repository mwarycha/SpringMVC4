package groovy

import com.twitter.TwitterEduApp.SessionBuilder
import com.twitter.TwitterEduApp.TwitterEduAppApplication
import com.twitter.TwitterEduApp.search.StubTwitterSearchConfig
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.web.WebAppConfiguration

@ContextConfiguration(loader = SpringApplicationContextLoader,
        classes = [TwitterEduAppApplication, StubTwitterSearchConfig])
@WebAppConfiguration
class HomeControllerSpec extends Specification {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    def "Użytkownik podczas pierwszej wizyty jest kierowany na stronę profilu"() {

        when: "Jestem kierowany na stronę główną"
        def response = this.mockMvc.perform(get("/"))

        then: "Jestem kierowany na stronę profilu"
        response
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/profile"))
    }

    def "Jeżeli profil jest utworzony, powinno nastąpić przekierowanie na stronę z wynikami"() {

        given: "Użytkownik ze zdefiniowanymi preferencjami otworzył sesję"

        MockHttpSession session = new SessionBuilder().userTastes("spring", "groovy").build();

        when: "Gdy otwieram stronę główną..."
        ResultActions response = this.mockMvc.perform(get("/").session(session))

        then: "...Przechodzę do strony z wynikami wyszukiwnia"
        response
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/search/mixed;keywords=spring,groovy"));
    }
}