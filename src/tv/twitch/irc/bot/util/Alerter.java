package tv.twitch.irc.bot.util;

public class Alerter {

  private String user;
  private int warnings;

  public Alerter(String user) {
    this.user = user;
    this.warnings = 1;
  }

  public void warning() {
    ++warnings;
  }

  public void clear() {
    this.warnings = 0;
  }

  public String getUser() {
    return this.user;
  }

  public int getWarnings() {
    return this.warnings;
  }
}
