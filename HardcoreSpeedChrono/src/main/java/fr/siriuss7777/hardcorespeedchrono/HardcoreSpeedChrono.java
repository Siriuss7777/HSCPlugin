package fr.siriuss7777.hardcorespeedchrono;

import fr.siriuss7777.hardcorespeedchrono.commands.HSCCommands;
import fr.siriuss7777.hardcorespeedchrono.events.HardcoreSpeedEvents;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.*;

public final class HardcoreSpeedChrono extends JavaPlugin {

    // TODO: TPRandom, scoreboard (reset & pregame), cancel most events, what happens after game ends
    private Scoreboard scoreboard;
    private Objective objective;

    private final UpdateTimer timerTask = new UpdateTimer(this);

    public static HardcoreSpeedChrono INSTANCE;

    public List<Player> alivePlayers;
    public List<String> aliveBosses;

    public boolean isGameStarted = false;

    public HashMap<Integer, String> scoreboardLines = new HashMap<>();
    public int getKeyByValue(String value) {
        for (Map.Entry<Integer, String> entry : this.scoreboardLines.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1000;
    }

    public String SCOREBOARD_TITLE = ChatColor.YELLOW + "HardcoreSpeedChrono";
    public String ELDER_GUARDIAN = ChatColor.AQUA + "" + ChatColor.BOLD + "Elder Guardian" + ChatColor.RESET + " (x3)";
    public String WITHER_BOSS = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Wither Boss";
    public String ENDER_DRAGON = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Ender Dragon";
    public String BOSS_SEPARATOR = ChatColor.GRAY + "+-----------Boss-----------+";
    public String TIMER_SEPARATOR = ChatColor.GRAY + "+-----------Time-----------+";
    public String CURRENT_TIME = ChatColor.GOLD  + "Temps actuel:";
    public String currentTimeValue = "00:00:00";
    public String BEST_TIME = ChatColor.GOLD  + "Meilleur temps:";
    public String BEST_TIME_VALUE = ChatColor.GRAY + "Pas encore implémenté...";
    public String SEPARATOR = ChatColor.GRAY + "+--------------------------+";

    public HardcoreSpeedChrono() {
        alivePlayers = new ArrayList<>();
        aliveBosses = new ArrayList<>();
    }


    @Override
    public void onEnable() {

        INSTANCE = this;

        initScoreboard();
        getServer().getPluginManager().registerEvents(new HardcoreSpeedEvents(), this);
        Objects.requireNonNull(getCommand("hsc")).setExecutor(new HSCCommands());

        getLogger().info("HardcoreSpeedChrono est chargé 😈");
    }


    @Override
    public void onDisable() {
    }


    private void initScoreboard() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        this.scoreboard = scoreboardManager.getNewScoreboard();

        this.objective = scoreboard.registerNewObjective("test", "dummy", SCOREBOARD_TITLE);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        initScoreboardLines();

//        updateScoreboard(currentTimeValue, "En attente du début...");

        timerTask.runTaskTimerAsynchronously(this, 0, 20);
        for (Player player: Bukkit.getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
        getLogger().info("Timer initialized");


    }

    public void initScoreboardLines() {

        this.objective.unregister();
        this.scoreboard = null;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("test", "dummy", SCOREBOARD_TITLE);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

//        for (int i = 0; i<scoreboardLines.size(); i++){
//            Score score = objective.getScore(scoreboardLines.get(i));
//            score.resetScore();
//            scoreboardLines.remove(i);
//        }

        this.scoreboardLines.put(9, BOSS_SEPARATOR.toString());
        ELDER_GUARDIAN = ChatColor.AQUA + "" + ChatColor.BOLD + "Elder Guardian" + ChatColor.RESET + " (x3)";
        this.scoreboardLines.put(8, ELDER_GUARDIAN.toString());
        this.scoreboardLines.put(7, WITHER_BOSS.toString());
        this.scoreboardLines.put(6, ENDER_DRAGON.toString());
        this.scoreboardLines.put(5, TIMER_SEPARATOR.toString());
        this.scoreboardLines.put(4, CURRENT_TIME.toString());
        if (isGameStarted){
            this.scoreboardLines.put(3, "00:00:00");
            currentTimeValue = "00:00:00";
        }else{
            this.scoreboardLines.put(3, "En attente du début...");
            currentTimeValue = "En attente du début...";
        }
        this.scoreboardLines.put(2, BEST_TIME.toString());
        this.scoreboardLines.put(1, BEST_TIME_VALUE.toString());
        this.scoreboardLines.put(0, SEPARATOR.toString());

        for (Map.Entry<Integer, String> entry : this.scoreboardLines.entrySet()) {
            Score score = objective.getScore(entry.getValue());
            score.setScore(entry.getKey());
        }

        for(Player player : Bukkit.getOnlinePlayers()){
            player.setScoreboard(scoreboard);
        }
    }

    public void updateScoreboard(String scoreboardLine, String newText){
        Score score = objective.getScore(scoreboardLine);
        score.resetScore();
        score = objective.getScore(newText);
        int key = getKeyByValue(scoreboardLine);
        score.setScore(getKeyByValue(scoreboardLine));
        scoreboardLines.remove(key);
        scoreboardLines.put(key, newText);

        if (scoreboardLine.equals(currentTimeValue)){
            currentTimeValue = newText;
        }
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    public void resumeTimer() {
        timerTask.resume();
        getLogger().info("Timer resumed");
    }
    public void pauseTimer(){
        timerTask.pause();
        getLogger().info("Timer paused");

    }
    public void resetTimer(){
        timerTask.pause();
        updateScoreboard(currentTimeValue, "00:00:00");
        getLogger().info("Timer reset");
    }
    public void restartTimer(){
        timerTask.pause();
        updateScoreboard(currentTimeValue, "00:00:00");
        timerTask.resume();
        getLogger().info("Timer restarted");
    }

    public void startTimer() {
        timerTask.start();
        restartTimer();
    }

    public boolean startGame() {
        if (isGameStarted){
            return false;
        }
        isGameStarted = true;

        for (Player player: Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.playSound(player.getLocation(), "minecraft:entity.wither.spawn", 1, 1);
            player.sendTitle(ChatColor.GOLD + "Début de la partie !", ChatColor.GRAY + "Bonne chance...", 10, 70, 20);
            alivePlayers.add(player);
        }
        aliveBosses.add("elder_guardian");
        aliveBosses.add("elder_guardian");
        aliveBosses.add("elder_guardian");
        aliveBosses.add("wither_boss");
        aliveBosses.add("ender_dragon");
        initScoreboardLines();
        timerTask.resume();

        return true;
    }

    public boolean endGame() {
        if (!isGameStarted){
            return false;
        }
        timerTask.pause();
        isGameStarted = false;
        for (Player player: Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), "minecraft:ui.toast.challenge_complete", 1, 0.6f);
            player.sendTitle(ChatColor.GOLD + "Victoire !", ChatColor.GRAY + "Vous avez terminé le jeu en " + currentTimeValue + " !", 10, 70, 20);
        }
        // Display the message in the middle of the screen
        getLogger().info("Game ended");

        return true;
    }

    public boolean defeatGame() {
        if (!isGameStarted){
            return false;
        }
        timerTask.pause();
        isGameStarted = false;
        for (Player player: Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), "minecraft:entity.wither.death", 1, 1);
            player.sendTitle(ChatColor.DARK_RED + "" + ChatColor.GRAY + "Défaite !", ChatColor.GRAY + "Vous êtes tous morts...", 10, 70, 20);
        }
        // Display the message in the middle of the screen
        getLogger().info("Game ended");

        return true;
    }

    public boolean resetGame() {
        if (!isGameStarted){
            return false;
        }
        timerTask.pause();
        isGameStarted = false;
        alivePlayers.clear();
        aliveBosses.clear();
        for (Player player: Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.SURVIVAL);
        }
        initScoreboardLines();
        getLogger().info("Game reset");

        return true;
    }

    public void setTimer(String string) {
        updateScoreboard(currentTimeValue, string);
        currentTimeValue = string;
    }


    private static class UpdateTimer extends BukkitRunnable {
        private final HardcoreSpeedChrono hsc;
        protected boolean isRunning = false;
        public UpdateTimer(HardcoreSpeedChrono hsc) {
            this.hsc = hsc;
        }

        public void pause(){
            isRunning = false;
        }
        public void resume(){
            isRunning = true;
        }

        public void start(){
            hsc.updateScoreboard(hsc.currentTimeValue, "00:00:00");
            isRunning = true;
        }

        @Override
        public void run() {

            if (!isRunning) {
                return;
            }

            String currentTime = hsc.currentTimeValue;

            String[] currentTimeSplit = currentTime.split(":");
            int hours = Integer.parseInt(currentTimeSplit[0]);
            int minutes = Integer.parseInt(currentTimeSplit[1]);
            int seconds = Integer.parseInt(currentTimeSplit[2]);

            if (seconds == 59) {
                seconds = 0;
                if (minutes == 59) {
                    minutes = 0;
                    hours++;
                } else {
                    minutes++;
                }
            } else {
                seconds++;
            }

            String newTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            hsc.updateScoreboard(hsc.currentTimeValue, newTime);
            hsc.currentTimeValue = newTime;

        }
    }
}
