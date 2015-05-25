package tv.twitch.irc.bot.thread;

public class Uptime implements Runnable {

  private int time = 0;

  @Override
  public void run() {
    while (true) {
      time++;
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public int getTime() {
    return time;
  }
}
