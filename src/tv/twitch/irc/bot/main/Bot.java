package tv.twitch.irc.bot.main;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import tv.twitch.irc.bot.config.Config;
import tv.twitch.irc.bot.engines.Poll;
import tv.twitch.irc.bot.engines.Raffle;
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
    // TODO : Add command handler with register
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
        String command = message.substring(1);
        if (command.startsWith("kick")) {
          handleKick(channel, sender, message);
        } else if (command.startsWith("ban")) {
          handleBan(channel, sender, message);
        } else if (command.startsWith("unban")) {
          handleUnBan(channel, sender, message);
        } else if (command.startsWith("mod")) {
          handleMod(channel, sender, message);
        } else if (command.startsWith("unmod")) {
          handleUnMod(channel, sender, message);
        } else if (command.startsWith("subMode")) {
          handleSubMode(channel, sender, message);
        }
      }
    } else { // normal chat
      handleCursing(channel, sender, message);
      handleGreetings(message.toLowerCase(), sender);
      if (Config.ALLOW_RAFFLES)
        handleRaffle(message, sender);
      if (Config.ALLOW_POLLS)
        handlePoll(message);
    }
  }

  /**
   * This method handles an ongoing poll
   * 
   * @param message
   */
  private void handlePoll(String message) {
    // when we add the gui this will be completed as the raffle engine
    Poll poll = new Poll("poll", null);
    poll.vote(message);
  }

  /**
   * Handles an ongoing raffle
   *
   * @param message
   * @param sender
   */
  private void handleRaffle(String message, String sender) {
    // when we add the gui we will add the keyword
    Raffle raffle = new Raffle("giveaway", "keyword");
    if (message.equals(raffle.getKeyword()))
      raffle.addUser(sender);
  }

  /**
   * Shows the kind side of the bot :)
   *
   * @param msg
   * @param sender
   */
  private void handleGreetings(String msg, String sender) {
    if (msg.startsWith("yo")) {
      this.sendMessage(sender, "Yo " + sender + " how are u ?");
    } else if (msg.startsWith("hey") || msg.startsWith("hi") || msg.startsWith("hello")) {
      this.sendMessage(sender, "Hello " + sender + " welcome to our channel !!");
    } else if (msg.startsWith("bye") || msg.startsWith("bb") || msg.startsWith("good bye")) {
      this.sendMessage(sender, "Bye " + sender + " see you around !");
    }
  }

  private void handleSubMode(String channel, String sender, String message) {
    // TODO : use setMode method probably
  }

  /**
   * This method removes mod status from a user
   *
   * @param channel
   * @param sender
   * @param message
   */
  private void handleUnMod(String channel, String sender, String message) {
    tokenizer = new StringTokenizer(message);
    tokenizer.nextToken();
    String name = tokenizer.nextToken();
    if (name.equals(this.broadcaster)) {
      this.sendMessage(channel, "You cannot unmod the broadcaster " + sender);
      return;
    }
    this.sendMessage(channel, "User " + name + " is no longer a mod in the channel by " + sender);
    this.deOp(channel, name);
  }

  /**
   * This method makes a user, mod.
   *
   * @param channel
   * @param sender
   * @param message
   */
  private void handleMod(String channel, String sender, String message) {
    tokenizer = new StringTokenizer(message);
    tokenizer.nextToken();
    String name = tokenizer.nextToken();
    this.sendMessage(channel, "User " + name + " is now a mod in the channel by " + sender);
    this.op(channel, name);
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
    // TODO : keep online time separately for each user, add points for every x minutes
  }

  @Override
  protected void onPart(String channel, String sender, String login, String hostname) {
    // TODO : Close the online point system
  }

  @Override
  protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
    // TODO
  }

  @Override
  protected void onKick(String channel, String kickerNick, String kickerLogin,
      String kickerHostname, String recipientNick, String reason) {
    // no need to override right now
    super.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason);
  }

  @Override
  protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
    super.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
  }

  @Override
  protected void onTopic(String channel, String topic) {
    // TODO
  }

  @Override
  protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
    // TODO
  }

  @Override
  protected void onChannelInfo(String channel, int userCount, String topic) {
    super.onChannelInfo(channel, userCount, topic);
  }

  @Override
  protected void onMode(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String mode) {
    // TODO
  }

  @Override
  protected void onUserMode(String targetNick, String sourceNick, String sourceLogin,
      String sourceHostname, String mode) {
    // TODO
  }

  @Override
  protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname,
      String recipient) {
    // TODO
  }

  @Override
  protected void onDeop(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String recipient) {
    // TODO
  }

  @Override
  protected void onVoice(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String recipient) {
    // TODO
  }

  @Override
  protected void onDeVoice(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String recipient) {
    // TODO
  }

  @Override
  protected void onSetChannelKey(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String key) {
    // TODO
  }

  @Override
  protected void onRemoveChannelKey(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String key) {
    // TODO
  }

  @Override
  protected void onSetChannelLimit(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, int limit) {
    // TODO
  }

  @Override
  protected void onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onSetChannelBan(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String hostmask) {
    // TODO
  }

  @Override
  protected void onRemoveChannelBan(String channel, String sourceNick, String sourceLogin,
      String sourceHostname, String hostmask) {
    // TODO
  }

  @Override
  protected void onSetTopicProtection(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    super.onSetTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
  }

  @Override
  protected void onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    super.onRemoveTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
  }

  @Override
  protected void onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    super.onSetNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
  }

  @Override
  protected void onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    super.onRemoveNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
  }

  @Override
  protected void onSetInviteOnly(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onSetModerated(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onRemoveModerated(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onSetPrivate(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onRemovePrivate(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onSetSecret(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onRemoveSecret(String channel, String sourceNick, String sourceLogin,
      String sourceHostname) {
    // TODO
  }

  @Override
  protected void onInvite(String targetNick, String sourceNick, String sourceLogin,
      String sourceHostname, String channel) {
    // TODO
  }

  @Override
  protected void onDccSendRequest(String sourceNick, String sourceLogin, String sourceHostname,
      String filename, long address, int port, int size) {
    // TODO
  }

  @Override
  protected void onDccChatRequest(String sourceNick, String sourceLogin, String sourceHostname,
      long address, int port) {
    // TODO
  }

  @Override
  protected void onIncomingFileTransfer(DccFileTransfer transfer) {
    // TODO
  }

  @Override
  protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
    // TODO
  }

  @Override
  protected void onIncomingChatRequest(DccChat chat) {
    // TODO
  }

  @Override
  protected void onVersion(String sourceNick, String sourceLogin, String sourceHostname,
      String target) {
    super.onVersion(sourceNick, sourceLogin, sourceHostname, target);
  }

  @Override
  protected void onPing(String sourceNick, String sourceLogin, String sourceHostname,
      String target, String pingValue) {
    super.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
  }

  @Override
  protected void onServerPing(String response) {
    super.onServerPing(response);
  }

  @Override
  protected void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target) {
    super.onTime(sourceNick, sourceLogin, sourceHostname, target);
  }

  @Override
  protected void onFinger(String sourceNick, String sourceLogin, String sourceHostname,
      String target) {
    super.onFinger(sourceNick, sourceLogin, sourceHostname, target);
  }

  @Override
  protected void onUnknown(String line) {
    System.out.println("Uknown line received : ");
    System.out.println(line);
  }
}
