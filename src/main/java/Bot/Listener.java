package Bot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Game.GameSession;
import Game.PlayerCharacter;
import Game.World.Direction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Listener extends ListenerAdapter{

    private final Logger log = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onReady(ReadyEvent ev) {
        Main.gameSession = new GameSession();
        log.info("Listener is ready.");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent ev) {
        String[] commandSplit = ev.getFullCommandName().split("\\s+");
        switch (commandSplit[0]) {
            case "create-character" -> {
                String characterName = ev.getOption("character-name").getAsString();
                Main.gameSession.createCharacter(ev.getMember().getIdLong(), characterName);
                log.info(characterName + " is joining the game!");
                ev.reply(characterName + " has joined the game!").setEphemeral(true).queue();
            }
            case "show-buttons" -> {

                List<LayoutComponent> actionRows = new ArrayList<>();
                actionRows.add(ActionRow.of(
                    Button.primary("check-stats", "Check Stats")
                ));

                actionRows.add(ActionRow.of(
                    Button.success("check-hands", "Check Hands"),
                    Button.secondary("check-backpack", "Check Backpack")
                ));

                actionRows.add(ActionRow.of(
                    Button.success("check-location", "Check Location")
                ));

                ev.getChannel().sendMessage("What would you like to do?").setComponents(actionRows).queue();

                ev.reply("Buttons sent.").setEphemeral(true).queue();

            }
            case "move" -> {
                Direction dir = null;
                switch (ev.getOption("direction").getAsString()) {
                    case "north" -> dir = Direction.North;
                    case "northeast" -> dir = Direction.Northeast;
                    case "east" -> dir = Direction.East;
                    case "southeast" -> dir = Direction.Southeast;
                    case "south" -> dir = Direction.South;
                    case "southwest" -> dir = Direction.Southwest;
                    case "west" -> dir = Direction.West;
                    case "northwest" -> dir = Direction.Northwest;
                }
                String response = Main.gameSession.moveCharacter(ev.getMember().getIdLong(), dir);
                ev.reply(response).setEphemeral(true).queue();
            }
        }
    }

    public void onButtonInteraction(ButtonInteractionEvent ev) {
        switch (ev.getComponentId()) {
            case "check-stats" -> {
                PlayerCharacter character = Main.gameSession.getCharacter(ev.getMember().getIdLong());
                if (character == null) { ev.reply("You don't have a character!").setEphemeral(true).queue(); } else {
                    ev.reply(character.checkStats()).setEphemeral(true).queue();
                }
            }
            case "check-hands" -> {
                PlayerCharacter character = Main.gameSession.getCharacter(ev.getMember().getIdLong());
                if (character == null) { ev.reply("You don't have a character!").setEphemeral(true).queue(); } else {
                    ev.reply(character.checkHands()).setEphemeral(true).queue();
                }
            }
            case "check-backpack" -> {
                PlayerCharacter character = Main.gameSession.getCharacter(ev.getMember().getIdLong());
                if (character == null) { ev.reply("You don't have a character!").setEphemeral(true).queue(); } else {
                    ev.reply(character.checkBackpack()).setEphemeral(true).queue();
                }
            }
            case "check-location" -> {
                PlayerCharacter character = Main.gameSession.getCharacter(ev.getMember().getIdLong());
                if (character == null) { ev.reply("You don't have a character!").setEphemeral(true).queue(); } else {
                    ev.reply(character.checkLocation()).setEphemeral(true).queue();
                }
            }
        }
    }
}
