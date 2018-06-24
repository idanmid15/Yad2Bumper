
import org.openqa.selenium.chrome.ChromeDriver
import scala.collection.JavaConverters._

object Bumper {
  System.setProperty("webdriver.chrome.driver", "./Chrome/chromedriver.exe")

  def main(args: Array[String]): Unit = {
    val browser = new ChromeDriver()
    val appConfig = AppConfig.loadConfig()
    val userConfig = appConfig.userConfig
    val htmlConfig = appConfig.htmlConfig
    val transportProtocol = "https:"
    browser.get(appConfig.itemsUrl)
    browser.findElementById(htmlConfig.usernameHtmlId).sendKeys(userConfig.username)
    browser.findElementById(htmlConfig.passwordHtmlId).sendKeys(userConfig.password)
    browser.findElementById(htmlConfig.submitHtmlId).click()

    browser.get(appConfig.itemsUrl)

    // Find all iframes in the list of items
    browser.findElementsByCssSelector("tr[data-frame]").asScala.foreach(dataFrame => {
        val iFrameUrl = dataFrame.getAttribute("data-frame")
        browser.get(transportProtocol + iFrameUrl)
        //browser.waitAtMost(5).secondsFor(browser.find(htmlConfig.bumpHtmlId).nonEmpty).toBecomeTrue()
        browser.findElementById(htmlConfig.bumpHtmlId).click()
      })
    browser.close()
  }
}