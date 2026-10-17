package io.github.redvortexdev.streamermode.chat;

import io.github.redvortexdev.streamermode.StreamerMode;
import io.github.redvortexdev.streamermode.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.ChatVisiblity;
import org.joml.Matrix3x2f;

public final class SideChat {

    private static final int HORIZONTAL_PADDING = 12;

    private static ChatComponent chat;
    private static int appliedWidth;

    private SideChat() {
    }

    public static boolean isEnabled() {
        return Config.HANDLER.instance().twitchSideChat;
    }

    public static boolean isVisible() {
        Minecraft minecraft = StreamerMode.MC;
        return isEnabled() && minecraft.player != null && minecraft.options.chatVisibility().get() != ChatVisiblity.HIDDEN;
    }

    public static void addMessage(Component message) {
        get().addClientSystemMessage(message);
    }

    public static void clear() {
        if (chat != null) {
            chat.clearMessages(false);
        }
    }

    public static void rescale() {
        if (chat != null) {
            chat.rescaleChat();
        }
    }

    public static void resetScroll() {
        if (chat != null) {
            chat.resetChatScroll();
        }
    }

    public static void scroll(int amount) {
        get().scrollChat(amount);
    }

    public static void extractRenderState(GuiGraphicsExtractor graphics, Font font, int ticks, int mouseX, int mouseY, ChatComponent.DisplayMode displayMode, boolean changeCursorOnInsertions) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(getOffsetX(graphics.guiWidth()), 0.0F);
        get().extractRenderState(graphics, font, ticks, mouseX, mouseY, displayMode, changeCursorOnInsertions);
        graphics.pose().popMatrix();
    }

    public static void captureClickableText(ActiveTextCollector collector, int guiWidth, int guiHeight, int ticks, ChatComponent.DisplayMode displayMode) {
        ActiveTextCollector.Parameters parameters = collector.defaultParameters();
        Matrix3x2f pose = new Matrix3x2f(parameters.pose()).translate(getOffsetX(guiWidth), 0.0F);

        collector.defaultParameters(parameters.withPose(pose));
        get().captureClickableText(collector, guiHeight, ticks, displayMode);
        collector.defaultParameters(parameters);
    }

    public static boolean isOverSideChat(double mouseX, int guiWidth) {
        return mouseX >= getOffsetX(guiWidth);
    }

    private static float getOffsetX(int guiWidth) {
        double scale = StreamerMode.MC.options.chatScale().get();
        int textWidth = Mth.ceil(getWidth() / (float) scale);
        float drawnWidth = (textWidth + HORIZONTAL_PADDING) * (float) scale;
        return Math.max(0.0F, guiWidth - drawnWidth);
    }

    public static int getWidth() {
        return Config.HANDLER.instance().twitchSideChatWidth;
    }

    public static int getHeight() {
        return Config.HANDLER.instance().twitchSideChatHeight;
    }

    public static boolean isSideChat(ChatComponent component) {
        return component == chat;
    }

    private static ChatComponent get() {
        if (chat == null) {
            chat = new ChatComponent(StreamerMode.MC);
        }
        if (appliedWidth != getWidth()) {
            appliedWidth = getWidth();
            chat.rescaleChat();
        }
        return chat;
    }

}
