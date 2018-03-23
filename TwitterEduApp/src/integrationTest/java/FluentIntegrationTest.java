
import com.twitter.TwitterEduApp.TwitterEduAppApplication;
import com.twitter.TwitterEduApp.search.StubTwitterSearchConfig;
import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

import pages.LoginPage;
import pages.ProfilePage;
import pages.SearchResultPage;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.fluentlenium.core.annotation.Page;

/**
 * Created by EMAWARY on 2018-03-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        TwitterEduAppApplication.class,
        StubTwitterSearchConfig.class
    })
@WebIntegrationTest(randomPort = true)
public class FluentIntegrationTest extends FluentTest {

    @Page
    private LoginPage loginPage;
    @Page
    private ProfilePage profilePage;
    @Page
    private SearchResultPage searchResultPage;

    //application must be run via gradlew bootrun before start this integration test!

    private final String CHROME_DRIVER     = "webdriver.chrome.driver";
    private final String CHROME_DRIVER_EXE = "C:\\Users\\EMAWARY\\Downloads\\chromedriver_win32\\chromedriver.exe";

    @Override
    public WebDriver getDefaultDriver() {
        System.setProperty(CHROME_DRIVER, CHROME_DRIVER_EXE);
        return new ChromeDriver();
    }

    public String getDefaultBaseUrl() {
        return "https://localhost:8443";
    }

    @Test
    public void hasPageTitle() {
        goTo("/");
        assertThat(findFirst("h2").getText()).isEqualTo("Logowanie");
    }

    @Test
    public void should_be_redirected_after_filling_form() {
        goTo("/");
        assertThat(findFirst("h2").getText()).isEqualTo("Logowanie");
        find("button", withName("twitterSignin")).click();
        assertThat(findFirst("h2").getText()).isEqualTo("Your profile");
        fill("#twitterHandle").with("geowarin");
        fill("#email").with("geowarin@mymail.com");
        fill("#birthDate").with("03/19/1987");
        find("button", withName("addTaste")).click();
        fill("#tastes0").with("spring");
        find("button", withName("save")).click();
        takeScreenShot();
        assertThat(findFirst("h2").getText()).isEqualTo("Wyniki wyszukiwania spring");
        assertThat(findFirst("ul.collection").find("li")).hasSize(2);
    }

    @Test
    public void should_be_redirected_after_filling_form_pages() {
            goTo("/");
            loginPage.isAt();
            loginPage.login();
            profilePage.isAt();
            profilePage.fillInfos("geowarin", "geowarin@mymail.com", "03/19/1987");
            profilePage.addTaste("spring");
            profilePage.saveProfile();
            takeScreenShot();
            searchResultPage.isAt();
            assertThat(searchResultPage.getNumberOfResults()).isEqualTo(2);
    }
}