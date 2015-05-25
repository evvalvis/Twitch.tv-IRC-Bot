package tv.twitch.irc.bot.main;

import java.util.ArrayList;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import tv.twitch.irc.bot.config.Config;
import tv.twitch.irc.bot.util.Alerter;

public class Bot extends PircBot {

  private long uptime;
  public ArrayList<Alerter> warnings = new ArrayList<Alerter>();

  public Bot() {
    this.setName(Config.BOT_NAME);
    this.op("#" + Config.CHANNEL_NAME, Config.BOT_NAME);
    uptime = 0;
  }

  /**
   * Get the current uptime
   *
   * @return
   */
  public long getUptime() {
    return this.uptime;
  }

  @Override
  protected void onConnect() {
    // TODO : Start uptime thread, end it on dc
    this.sendMessage("#" + Config.CHANNEL_NAME, "Hello everyone!");
    this.sendMessage("#" + Config.CHANNEL_NAME, "I am " + Config.BOT_NAME);
    this.sendMessage("#" + Config.CHANNEL_NAME,
        "And i am here to ensure that you have a great time!");
  }

  @Override
  protected void onDisconnect() {
    this.sendMessage("#" + Config.CHANNEL_NAME, "Bye everyone!");
    this.sendMessage("#" + Config.CHANNEL_NAME, Config.BOT_NAME + " singing out!");
  }

  @Override
  protected void onServerResponse(int code, String response) {
    // TODO
  }

  @Override
  protected void onUserList(String channel, User[] users) {
    super.onUserList(channel, users);
  }

  @Override
  protected void onMessage(String channel, String sender, String login, String hostname,
      String message) {
    if (message.startsWith("!")) {
      switch (message) {
        case "!uptime":
          this.sendMessage(channel, "Up for " + this.getUptime());
          break;
        case "!twitter":
          this.sendMessage(channel, Config.TWITTER);
          break;
        case "!youtube":
          this.sendMessage(channel, Config.YOUTUBE);
          break;
        case "!instagram":
          this.sendMessage(channel, Config.INSTAGRAM);
          break;
      }
    } else {
      handleCursing(channel, sender, message);
    }
  }

  /**
   * This method handles cursing by a user
   *
   * @param channel
   * @param sender
   * @param message
   */
  private void handleCursing(String channel, String sender, String message) {
    if (Config.ALLOW_WORD_FILTER) {
      for (String s : Config.CURSE_WORDS_LIST) {
        if (message.contains(s)) {
          this.sendMessage(sender, "Dont use curse words! Warning!");
          boolean userExists = false;
          for (Alerter a : warnings) {
            if (a.getUser().equals(sender)) {
              a.warning();
              if (a.getWarnings() >= 3) {
                this.sendMessage(sender, "Sorry " + sender
                    + " you are banned from the channel. Behave yourself!");
                this.ban(channel, sender);
              }
              userExists = true;
              break;
            }
          }
          if (!userExists) {
            Alerter alerter = new Alerter(sender);
            warnings.add(alerter);
          }
        }
      }
    }
  }

  @Override
  protected void onPrivateMessage(String sender, String login, String hostname, String message) {
    this.sendMessage(sender, "You may not send private messages to me!");
  }

  @Override
  protected void onAction(String sender, String login, String hostname, String target, String action) {
    super.onAction(sender, login, hostname, target, action);
  }

  @Override
  protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname,
      String target, String notice) {
    super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
  }

  @Override
  protected void onJoin(String channel, String sender, String login, String hostname) {

  }

  @Override
  protected void onPart(String channel, String sender, String login, String hostname) {

  }

  @Override
  protected void onNickChange(String oldNick, String login, String hostname, String newNick) {

  }

  @Override
  protected void onKick(String channel, String kickerNick, String kickerLogin,
      String kickerHostname, String recipientNick, String reason) {

  }

  @Override
  protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {

  }

  @Override
  protected void onTopic(String channel, String topic) {

  }

  @Override
  protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {

  }

  @Override
  protected void onChannelInfo(String channel, int userCount, String topic) {

  }

  @Override
  protected void onMode(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String mode) {

  }

  @Override
  protected void onUserMode(String targetNick, String sourceNick, String sourceLogin,
      String sourceHostname, String mode) {

  }

  @Override
  protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname,
      String recipient) {

  }

  @Override
  protected void onDeop(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String recipient) {

  }

  @Override
  protected void onVoice(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String recipient) {

  }

  @Override
  protected void onDeVoice(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String recipient) {

  }

  @Override
  protected void onSetChannelKey(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String key) {

  }

  @Override
  protected void onRemoveChannelKey(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String key) {

  }

  @Override
  protected void onSetChannelLimit(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, int limit) {

  }

  @Override
  protected void onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onSetChannelBan(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String hostmask) {

  }

  @Override
  protected void onRemoveChannelBan(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String hostmask) {

  }

  @Override
  protected void onSetTopicProtection(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onSetInviteOnly(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onSetModerated(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onRemoveModerated(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onSetPrivate(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onRemovePrivate(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onSetSecret(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onRemoveSecret(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {

  }

  @Override
  protected void onInvite(String targetNick, String sourceNick, String sourceLogin,
      String sourceHostname, String channel) {

  }

  @Override
  protected void onDccSendRequest(String sourceNick, String sourceLogin, String sourceHostname,
      String filename, long address, int port, int size) {

  }

  @Override
  protected void onDccChatRequest(String sourceNick, String sourceLogin, String sourceHostname,
      long address, int port) {

  }

  @Override
  protected void onIncomingFileTransfer(DccFileTransfer transfer) {

  }

  @Override
  protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {

  }

  @Override
  protected void onIncomingChatRequest(DccChat chat) {

  }

  @Override
  protected void onVersion(String sourceNick, String sourceLogin, String sourceHostname,
      String target) {

  }

  @Override
  protected void onPing(String sourceNick, String sourceLogin, String sourceHostname,
      String target, String pingValue) {

  }

  @Override
  protected void onServerPing(String response) {

  }

  @Override
  protected void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target) {

  }

  @Override
  protected void onFinger(String sourceNick, String sourceLogin, String sourceHostname,
      String target) {

  }

  @Override
  protected void onUnknown(String line) {

  }
}
