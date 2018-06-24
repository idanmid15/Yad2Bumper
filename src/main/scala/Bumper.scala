
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

    // Find all item categories such as: real-estate, etc..
    browser.findElementsByClassName("nav-link").asScala.foreach(itemCategory => {
      browser.get(itemCategory.getAttribute("href"))

      // Find all iframes in the list of items
      browser.findElementsByCssSelector("tr[data-frame]").asScala.foreach(dataFrame => {
        val iFrameUrl = dataFrame.getAttribute("data-frame")
        browser.get(transportProtocol + iFrameUrl)
        browser.findElementById(htmlConfig.bumpHtmlId).click()
      })
    })
    browser.close()
  }
}