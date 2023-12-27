package fr.siriuss7777.hardcorespeedchrono.commands;

import fr.siriuss7777.hardcorespeedchrono.HardcoreSpeedChrono;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HSCCommands implements CommandExecutor {
    HardcoreSpeedChrono hsc = HardcoreSpeedChrono.INSTANCE;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("hsc")) {
            if (strings.length == 0) {
                commandSender.sendMessage("§cUsage: /hsc <timer|game|scoreboard|help>");
                return true;
            }
            String subcommand = strings[0];
//            strings = new String[strings.length - 1];
//            System.arraycopy(strings, 1, strings, 0, strings.length);

            if (subcommand.equalsIgnoreCase("timer")){
                switch (strings[1]) {
                    case "start":
                        hsc.startTimer();
                        break;
                    case "resume":
                        hsc.resumeTimer();
                        commandSender.sendMessage("§aTimer relancé");
                        break;
                    case "stop":
                    case "pause":
                        hsc.pauseTimer();
                        commandSender.sendMessage("§aTimer arrêté");
                        break;
                    case "reset":
                        hsc.resetTimer();
                        commandSender.sendMessage("§aTimer réinitialisé");
                        break;
                    case "restart":
                        hsc.restartTimer();
                        commandSender.sendMessage("§aTimer redémarré");
                        break;
                    case "set":
                        hsc.setTimer(strings[2]);
                        commandSender.sendMessage("§aTimer défini à " + strings[2]);
                        break;
                    default:
                        commandSender.sendMessage("§cUsage: /hsc timer <start|resume|stop|pause|reset|restart>");
                        break;
                }
            }
            else if (subcommand.equalsIgnoreCase("game")){
                switch (strings[1]) {
                    case "start":
                        if(hsc.startGame()){
                            commandSender.sendMessage("§aDébut de la partie");
                        }
                        else {
                            commandSender.sendMessage("§cLa partie est déjà commencée");
                        }
                        break;
                    case "end":
                    case "stop":
                        if(hsc.defeatGame()){
                            commandSender.sendMessage("§aFin de la partie");
                        }
                        else {
                            commandSender.sendMessage("§cLa partie n'est pas encore commencée");
                        }
                        break;
                    case "reset":
                        if(hsc.resetGame()){
                            commandSender.sendMessage("§aPartie réinitialisée");
                        }
                        else {
                            commandSender.sendMessage("§cLa partie n'est pas encore commencée");
                        }
                        break;
                    default:
                        commandSender.sendMessage("§cUsage: /hsc game <start|end|stop|reset>");
                        break;
                }
            }
            else if (subcommand.equalsIgnoreCase("scoreboard")) {

                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage("§cVous devez être un joueur pour exécuter cette commande");
                    return true;
                }
                Player player = (Player) commandSender;

                if(strings.length == 1){
                    player.sendMessage("§cUsage: /hsc scoreboard <reload|reset>");
                    return true;
                }
                switch (strings[1]) {
                    case "reload":
                        player.setScoreboard(hsc.getScoreboard());
                        player.sendMessage("§aScoreboard rechargé");
                        break;
                    case "reset":
                        hsc.initScoreboardLines();
                        player.sendMessage("§aScoreboard réinitialisé");
                        break;
                    default:
                        player.sendMessage("§cUsage: /hsc scoreboard <reload|reset>");
                        break;
                }
            }
            else if (subcommand.equalsIgnoreCase("bosskill") ||
                     subcommand.equalsIgnoreCase("kill")) {
                switch (strings[1]){
                    case "elder":
                    case "elder_guardian":
                    case "ElderGuardian":
                        commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "summon elder_guardian");
                        commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "kill @e[type=elder_guardian]");
                        break;
                    case "wither":
                    case "wither_boss":
                    case "WitherBoss":
                        commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "summon wither");
                        commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "kill @e[type=wither]");
                        break;
                    case "ender":
                    case "ender_dragon":
                    case "EnderDragon":
                        commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "summon ender_dragon");
                        commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "kill @e[type=ender_dragon]");
                        break;
                    default:
                        commandSender.sendMessage("§cUsage: /hsc bosskill <elder|wither|ender>");
                        break;
                }
            }
            else if (subcommand.equalsIgnoreCase("help")){
                commandSender.sendMessage("§a/hsc timer <start|resume|stop|pause|reset|restart>");
                commandSender.sendMessage("§a/hsc game <start|stop|end>");
                commandSender.sendMessage("§a/hsc scoreboard <reload|reset>");
            }
            else {
                commandSender.sendMessage("§cUsage: /hsc <timer|game|help>");
            }

        }

        return true;
    }
}
