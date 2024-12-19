package Bot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter{
    @Override
    public void onReady(ReadyEvent ev) {
        System.out.println("Listener is ready!");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent ev) {
        String[] commandSplit = ev.getFullCommandName().split("\\s+");
        switch (commandSplit[0]) {
            case "join-game" -> {
                System.out.println("Joining Game!");
                ev.reply("Joining Game!").queue();
            }
        }
    }
}
