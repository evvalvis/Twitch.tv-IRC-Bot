package tv.twitch.irc.bot.main;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import tv.twitch.irc.bot.config.Config;

public class Bot extends PircBot {

  public Bot() {
    this.setName(Config.BOT_NAME);
  }

  @Override
  protected void onConnect() {

  }

  @Override
  protected void onDisconnect() {

  }

  @Override
  protected void onServerResponse(int code, String response) {

  }

  @Override
  protected void onUserList(String channel, User[] users) {

  }

  @Override
  protected void onMessage(String channel, String sender, String login, String hostname,
      String message) {

  }

  @Override
  protected void onPrivateMessage(String sender, String login, String hostname, String message) {

  }

  @Override
  protected void onAction(String sender, String login, String hostname, String target, String action) {

  }

  @Override
  protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname,
      String target, String notice) {

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
