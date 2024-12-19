package Bot;

import Game.GameSession;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {

    static JDA jda;

    static GameSession gameSession;

    public static void main(String[] args) {

        //Get Bot Token from Codespaces if using GitHub Codespaces

        JDABuilder builder = JDABuilder.create(
            System.getenv("DISCORD_ADVENTURE_GAME_BOT_TOKEN"),
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
            GatewayIntent.SCHEDULED_EVENTS
        ).setMemberCachePolicy(MemberCachePolicy.ALL);

        builder.addEventListeners( new Listener() );

        try {
            jda = builder.build();
            jda.awaitReady();

            getDiscordGuild().upsertCommand("create-character", "Creates a character to join the current game.")
                .addOption(OptionType.STRING, "character-name", "The name of your new character.", true)
            .queue();

            getDiscordGuild().upsertCommand("show-buttons", "Send the commonly used actions in chat.").queue();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Guild getDiscordGuild() {
        return jda.getGuilds().get(0);
    }
}