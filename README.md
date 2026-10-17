![Banner](./.github/banner.png)

# StreamerMode

A 26.2 Fabric mod for official DiamondFire streaming and content creation.

## Dependencies
* [YetAnotherConfigLib](https://modrinth.com/mod/yacl)

## Streamer Features

Features only available to streamers.

### Twitch Plot Queue

Run /queue for a list of currently queued plots, click on a plot to join it and remove it from the queue. A message is later sent to un-hide the plot. Entries whose description contain "node beta" will have an extra message below signifying the plot may be on node beta.

### Chat Message Hiding

Hide messages from the chat matching a custom regex or messages that are:

-   Admin messages
-   Moderation messages
-   Support messages
-   Direct messages
-   Plot advertisements
-   Plot boosts
-   Spy messages (session, dm, and muted spy)
-   Plugin update messages (FastAsyncWorldEdit and ViaVersion)

Messages in the list that also play a sound also have their sound muted.

## Non Streamer Features

Features available to anyone using the mod.

### Twitch Chat Relay

When joining DiamondFire the twitch channel in the config will be relayed into the game chat (or optionally, a side chat) with unique indicators for subscribers, moderators, and bits, the system automatically updates to config changes.

### Network Protocol Error Suppression

An enabled by default config option that suppresses server kicks causes by network protocol errors.

### Config

Configure the mod using ModMenu or `/streamermode`.

## Note

When running the mod in a development environment, such as through Intellij, streamer features will be enabled regardless on whether the account is permitted to use them.

## Account Authentication

Being logged in is important when testing. Run the following once and sign in with your Microsoft account, after that the Minecraft Client run configuration in IntelliJ launches you already logged in.

```
./gradlew microsoftLogin
```
