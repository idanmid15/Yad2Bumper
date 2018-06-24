import java.io.File

import com.typesafe.config.ConfigFactory

object AppConfig {
  case class AppConfig(itemsUrl: String, userConfig: UserConfig, htmlConfig: HtmlConfig)
  case class UserConfig(username: String, password: String)
  case class HtmlConfig(usernameHtmlId: String, passwordHtmlId: String, submitHtmlId: String, bumpHtmlId: String)

  def loadConfig(): AppConfig = {
    val config = ConfigFactory.parseFile(new File("app.conf")).resolve()
    AppConfig(
      config.getString("itemsUrl"),
      UserConfig(config.getString("user.name"), config.getString("user.password")),
      HtmlConfig(config.getString("html.usernameId"), config.getString("html.passwordId"),
                 config.getString("html.submitId"), config.getString("html.bumpId"))
    )
  }
}

