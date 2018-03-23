
import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver

reportsDir = new File('./build/geb-reports')
driver = {

    def setupDriver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\EMAWARY\\Downloads\\chromedriver_win32\\chromedriver.exe"
        )

    def driver = new ChromeDriver()
        driver.manage().window().setSize(new Dimension(1024, 768))

    return driver
}
