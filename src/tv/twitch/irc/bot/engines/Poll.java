package tv.twitch.irc.bot.engines;

import java.util.List;

public class Poll {

  // instance variables
  private String name;
  private List<String> options;
  private int[] rates;
  private boolean enabled;

  /**
   * Constructor
   * 
   * @param name
   * @param options
   */
  public Poll(String name, List<String> options) {
    this.options = options;
    this.name = name;
    this.rates = new int[options.size()];
    this.enabled = true;
  }

  /**
   * Get the poll's name
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set the poll's name
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the list with the poll's options
   * 
   * @return
   */
  public List<String> getOptions() {
    return this.options;
  }

  /**
   * Set the poll's options
   * 
   * @param options
   */
  public void setOptions(List<String> options) {
    this.options = options;
  }

  /**
   * Get the rates for each of the poll's options
   * 
   * @return
   */
  public int[] getRates() {
    return this.rates;
  }

  /**
   * Cheat! Change the rates of the poll as you wish
   * 
   * @param rates
   */
  public void cheat(int[] rates) {
    this.rates = rates;
  }

  /**
   * Check if this poll is enabled
   * 
   * @return
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /**
   * Decide whether the poll is enabled
   * 
   * @param enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Vote for a poll's option
   * 
   * @param selected
   */
  public void vote(String selected) {
    if (!options.contains(selected))
      return;
    rates[options.indexOf(selected)]++;
  }

  /**
   * Get the winning option from the poll
   * 
   * @return
   */
  public String getWinner() {
    int max = -1;
    for (int i = 0; i < rates.length; ++i) {
      if (rates[i] > max)
        max = rates[i];
    }
    return options.get(max);
  }

  /**
   * Clear the current poll
   */
  public void clear() {
    this.name = null;
    this.options.clear();
    this.rates = null;
    this.enabled = true;
  }
}
