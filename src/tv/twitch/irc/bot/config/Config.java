package tv.twitch.irc.bot.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {

  public static String BOT_NAME;
  public static String BROADCASTER;
  public static String CHANNEL_NAME;
  public static String USER_COMMAND_CHAR;
  public static String MOD_COMMAND_CHAR;
  public static boolean ALLOW_WORD_FILTER;
  public static String CURSE_WORDS;
  public static List<String> CURSE_WORDS_LIST;
  public static String YOUTUBE;
  public static String TWITTER;
  public static String INSTAGRAM;

  /**
   * This method loads the config properties file
   *
   * @param file
   */
  public void load(String file) {
    Properties properties = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);

    try {
      if (inputStream != null) {
        properties.load(inputStream);
        BOT_NAME = properties.getProperty("bot_name");
        BROADCASTER = properties.getProperty("broadcaster");
        CHANNEL_NAME = properties.getProperty("channel_name");
        USER_COMMAND_CHAR = properties.getProperty("user_command_char");
        MOD_COMMAND_CHAR = properties.getProperty("mod_command_char");
        ALLOW_WORD_FILTER = Boolean.parseBoolean(properties.getProperty("allow_word_filter"));
        CURSE_WORDS = properties.getProperty("curse_word_list");
        CURSE_WORDS_LIST = new ArrayList<String>();
        for (String word : CURSE_WORDS.split(",")) {
          CURSE_WORDS_LIST.add(word);
        }
        YOUTUBE = properties.getProperty("youtube");
        TWITTER = properties.getProperty("twitter");
        INSTAGRAM = properties.getProperty("instagram");
      } else {
        throw new FileNotFoundException("property file '" + file + "' not found in the classpath");
      }
    } catch (IOException io) {
      io.printStackTrace();
    }
  }
}
