package groovy

import com.twitter.TwitterEduApp.TwitterEduAppApplication
import com.twitter.TwitterEduApp.controllers.SearchController
import com.twitter.TwitterEduApp.model.LightTweet
import com.twitter.TwitterEduApp.search.TwitterSearch
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration

@ContextConfiguration(loader = SpringApplicationContextLoader,
        classes = [TwitterEduAppApplication])
@WebAppConfiguration
class SearchControllerMockSpec extends Specification {

    def twitterSearch    = Mock(TwitterSearch)
    def searchController = new SearchController(twitterSearch)

    def mockMvc = MockMvcBuilders.standaloneSetup(searchController)
            .setRemoveSemicolonContent(false)
            .build()

    def "Po wyszukaniu słowa spring powinna pojawić się strona z wynikami"() {

        when: "Szukam słowa spring"

        def response = mockMvc.perform(get("/search/mixed;keywords=spring"))

        then: "Usługa wyszukująca jest wywoływana tylko raz"

        1 * twitterSearch.search(_, _) >> [new LightTweet('Treść tweeta')]

        and: "Pojawiła się strona z wynikami"

        response
                .andExpect(status().isOk())
                .andExpect(view().name("resultPage"))

        and: "Model danych zawiera znalezione tweety"

        response
                .andExpect(model().attribute("tweets", everyItem(
                hasProperty("text", is("Treść tweeta"))
        )))
    }
}
