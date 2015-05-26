package tv.twitch.irc.bot.main;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import tv.twitch.irc.bot.config.Config;
import tv.twitch.irc.bot.thread.Uptime;
import tv.twitch.irc.bot.util.Alerter;

public class Bot extends PircBot {

  private Uptime uptime = new Uptime();
  private Thread thread = new Thread(uptime);
  private String broadcaster = Config.BROADCASTER;
  private String ucs = Config.USER_COMMAND_CHAR;
  private String mcs = Config.MOD_COMMAND_CHAR;
  private StringTokenizer tokenizer;
  private ArrayList<Alerter> warnings = new ArrayList<Alerter>();

  public Bot() {
    this.setName(Config.BOT_NAME);
    this.op("#" + Config.CHANNEL_NAME, Config.BOT_NAME);
  }

  @Override
  protected void onConnect() {
    thread.start();
    this.sendMessage("#" + Config.CHANNEL_NAME, "Hello everyone!");
    this.sendMessage("#" + Config.CHANNEL_NAME, "I am " + Config.BOT_NAME);
    this.sendMessage("#" + Config.CHANNEL_NAME,
        "And i am here to ensure that you have a great time!");
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void onDisconnect() {
    thread.stop();
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
    if (message.startsWith(ucs)) { // user commands
      switch (message.substring(1)) {
        case "uptime":
          this.sendMessage(channel, "Up for " + uptime.getTime());
          break;
        case "twitter":
          this.sendMessage(channel, Config.TWITTER);
          break;
        case "youtube":
          this.sendMessage(channel, Config.YOUTUBE);
          break;
        case "instagram":
          this.sendMessage(channel, Config.INSTAGRAM);
          break;
      }
    } else if (message.startsWith(mcs)) { // mod commands
      User[] users = this.getUsers(channel);
      boolean moderator = false;
      for (User user : users) {
        if (user.getNick().equals(sender) && user.isOp()) {
          moderator = true;
          break;
        }
      }
      if (!moderator) {
        this.sendMessage(channel, sender + " you cannot use mod commands without being a mod!!");
      } else {
        if (message.substring(1).startsWith("kick")) {
          handleKick(channel, sender, message);
        } else if (message.substring(1).startsWith("ban")) {
          handleBan(channel, sender, message);
        } else if (message.substring(1).startsWith("unban")) {
          handleUnBan(channel, sender, message);
        }
      }
    } else { // normal chat
      handleCursing(channel, sender, message);
    }
  }

  /**
   * This method bans a user the mod has chosen
   * 
   * @param channel
   * @param sender
   * @param message
   */
  private void handleUnBan(String channel, String sender, String message) {
    tokenizer = new StringTokenizer(message);
    tokenizer.nextToken();
    String name = tokenizer.nextToken();
    this.sendMessage(channel, "User " + name + " was unbanned from the channel by " + sender);
    this.unBan(channel, name);
  }

  /**
   * This method bans a user the mod has chosen
   * 
   * @param channel
   * @param sender
   * @param message
   */
  private void handleBan(String channel, String sender, String message) {
    tokenizer = new StringTokenizer(message);
    tokenizer.nextToken();
    String name = tokenizer.nextToken();
    if (name.equals(this.broadcaster)) {
      this.sendMessage(channel, "You cannot ban the broadcaster " + sender);
      return;
    }
    this.sendMessage(channel, "User " + name + " was banned from the channel by " + sender);
    this.ban(channel, name);
  }

  /**
   * This method kicks a user the mod has chosen
   * 
   * @param channel
   * @param sender
   * @param message
   */
  private void handleKick(String channel, String sender, String message) {
    tokenizer = new StringTokenizer(message);
    tokenizer.nextToken();
    String name = tokenizer.nextToken();
    if (name.equals(this.broadcaster)) {
      this.sendMessage(channel, "You cannot kick the broadcaster " + sender);
      return;
    }
    this.sendMessage(channel, "User " + name + " was kicked from the channel by " + sender);
    this.kick(channel, name);
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
                a.clear();
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
