package pages;

/**
 * Created by emawary on 2018-03-13.
 */

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.support.FindBy;

public class LoginPage extends FluentPage {

    @FindBy(name = "twitterSignin")
    FluentWebElement signinButton;

    public String getUrl() {
        return "/login";
    }

    public void isAt() {
        assertThat(findFirst("h2").getText()).isEqualTo("Logowanie");
    }

    public void login() {
        signinButton.click();
    }
}