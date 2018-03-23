package pages;

/**
 * Created by emawary on 2018-03-13.
 */
import com.google.common.base.Joiner;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.support.FindBy;

public class SearchResultPage extends FluentPage {

    @FindBy(css = "ul.collection")
    FluentWebElement resultList;

    public void isAt(String... keywords) {
        assertThat(findFirst("h2").getText())
                .isEqualTo("Tweet results for " + Joiner.on(",").
                        join(keywords));
    }

    public int getNumberOfResults() {
        return resultList.find("li").size();
    }
}