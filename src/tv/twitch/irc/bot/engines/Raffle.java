package tv.twitch.irc.bot.engines;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Raffle {

  // instance variables
  private boolean enabled;
  private Set<String> users;
  private String item;
  private String keyword;

  /**
   * Constructor
   *
   * @param item
   */
  public Raffle(String item, String keyword) {
    this.item = item;
    this.keyword = keyword;
    this.users = new HashSet<String>();
    this.enabled = true;
  }

  /**
   * Set the giveaway's keyword
   * 
   * @param keyword
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  /**
   * Get the current raffles' keyword
   * 
   * @return
   */
  public String getKeyword() {
    return this.keyword;
  }

  /**
   * See if the engine is enabled
   *
   * @return
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /**
   * Enable the engine
   *
   * @param enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Set the name of the giveaway item
   *
   * @param item
   */
  public void setItem(String item) {
    this.item = item;
  }

  /**
   * Returns the name of the giveaway item
   *
   * @return
   */
  public String getItem() {
    return this.item;
  }

  /**
   * Add a user to the raffle
   *
   * @param user
   */
  public void addUser(String user) {
    users.add(user);
  }

  /**
   * Return the users entered the raffle
   *
   * @return
   */
  public Set<String> getRaffleList() {
    return this.users;
  }

  /**
   * Clears and reinitializes the raffle
   */
  public void clear() {
    this.enabled = true;
    this.item = null;
    this.users.clear();
  }

  /**
   * Select the winner of the raffle
   *
   * @return
   */
  public String selectWinner() {
    Random rnd = new Random();
    return (String) this.users.toArray()[rnd.nextInt(users.size())];
  }
}
