# StreamerMode

A 1.21.1 Fabric mod for official DiamondFire streaming and content creation.

## Features

### Config
Configure the mod using ModMenu or `/streamermode`.

### Twitch Plot Queue
Run /queue for a list of currently queued plots, click on a plot to join it and remove it from the queue. A message is later sent to un-hide the plot. Entries whose description contain "node beta" will have an extra message below signifying the plot may be on node beta.

### Vast Chat Message Hiding Features
Hide messages from the chat matching a custom regex or messages that are:
- Admin messages
- Moderation messages
- Support messages
- Direct messages
- Plot advertisements
- Plot boosts
- Spy messages (session, dm, and muted spy)
- Plugin update messages (BuycraftX, FastAsyncWorldEdit, and ViaVersion)

Messages in the list that also play a sound also have their sound muted.

# Twitch Chat Relay
When joining DiamondFire the twitch channel in the config will be relayed into the game with unique indicators for subscribers, moderators, and bits, the system automatically updates to config changes.
