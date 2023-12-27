package fr.siriuss7777.hardcorespeedchrono.events;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import fr.siriuss7777.hardcorespeedchrono.HardcoreSpeedChrono;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.broadcast;
import static org.bukkit.Bukkit.broadcastMessage;

public class HardcoreSpeedEvents implements Listener {

    private final HardcoreSpeedChrono hsc;

    public HardcoreSpeedEvents () {
        this.hsc = HardcoreSpeedChrono.INSTANCE;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer;
        LivingEntity entity = event.getEntity();
        List<ItemStack> drops;
        EntityType entityType = entity.getType();

        // ###### PLAYER DEATH ######

        // ###### BOSS DEATH ######
        if(hsc.isGameStarted) {
            if (entityType == EntityType.ENDER_DRAGON) {
                if(!(hsc.aliveBosses.contains("ender_dragon"))) return;
                hsc.updateScoreboard(hsc.ENDER_DRAGON, ChatColor.GRAY + "" +  ChatColor.STRIKETHROUGH + "Ender Dragon" + ChatColor.RESET + " (tué en " + hsc.currentTimeValue + ")");
                try{
                    hsc.aliveBosses.remove("ender_dragon");
                } catch (Exception ignored){}
                if (hsc.aliveBosses.isEmpty()){
                    hsc.endGame();
                    return;
                }
                else{
                    for (Player player : hsc.alivePlayers){
                        player.sendTitle(ChatColor.DARK_GREEN + "Boss tué !", ChatColor.GRAY + "Vous avez tué l'Ender Dragon en " + hsc.currentTimeValue, 10, 70, 20);
                        player.playSound(player.getLocation(), "minecraft:ui.toast.challenge_complete", 1, 2f);
                    }
                }
            }

            if (entityType == EntityType.WITHER) {
                if(!(hsc.aliveBosses.contains("wither_boss"))) return;
                hsc.updateScoreboard(hsc.WITHER_BOSS, ChatColor.GRAY + "" +  ChatColor.STRIKETHROUGH + "Wither Boss" + ChatColor.RESET + " (tué en " + hsc.currentTimeValue + ")");
                try{
                    hsc.aliveBosses.remove("wither_boss");
                } catch (Exception ignored){}
                if (hsc.aliveBosses.isEmpty()){
                    hsc.endGame();
                    return;
                } else {
                    for (Player player : hsc.alivePlayers){
                        player.playSound(player.getLocation(), "minecraft:ui.toast.challenge_complete", 1, 2f);
                        player.sendTitle(ChatColor.DARK_GREEN + "Boss tué !", ChatColor.GRAY + "Vous avez tué le Wither en " + hsc.currentTimeValue, 10, 70, 20);
                    }
                }
            }

            if (entityType == EntityType.ELDER_GUARDIAN) {
                if(!(hsc.aliveBosses.contains("elder_guardian"))) return;
//                hsc.updateScoreboard(hsc.ELDER_GUARDIAN, ChatColor.GRAY + "" +  ChatColor.STRIKETHROUGH + "Elder Guardian" + ChatColor.RESET + " (tué en " + hsc.currentTimeValue + ")");
                try{
                    hsc.aliveBosses.remove("elder_guardian");
                } catch (Exception ignored){}
                int elderGuardiansLeft = Collections.frequency(hsc.aliveBosses, "elder_guardian");
                if (elderGuardiansLeft != 0){
                    hsc.updateScoreboard(hsc.ELDER_GUARDIAN, ChatColor.AQUA + "" + ChatColor.BOLD + "Elder Guardian" + ChatColor.RESET + " (" + elderGuardiansLeft + "x)");
                    hsc.ELDER_GUARDIAN = ChatColor.AQUA + "" + ChatColor.BOLD + "Elder Guardian" + ChatColor.RESET + " (" + elderGuardiansLeft + "x)";
                    for (Player player : hsc.alivePlayers){
                        player.playSound(player.getLocation(), "minecraft:entity.player.levelup", 1, 0.5f);
                    }
                    broadcastMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Elder Guardian tué en " + hsc.currentTimeValue + " !");
                    broadcastMessage(ChatColor.GRAY + "Il en reste " + elderGuardiansLeft + " à tuer");
                    return;
                }
                if (hsc.aliveBosses.isEmpty()){
                    hsc.endGame();
                } else {
                    for (Player player : hsc.alivePlayers){
                        player.sendTitle(ChatColor.DARK_GREEN + "Boss tué !", ChatColor.GRAY + "Vous avez tué l'Elder Guardian en " + hsc.currentTimeValue, 10, 70, 20);
                        player.playSound(player.getLocation(), "minecraft:ui.toast.challenge_complete", 1, 2f);
                    }
                }
                hsc.updateScoreboard(hsc.ELDER_GUARDIAN, ChatColor.GRAY + "" +  ChatColor.STRIKETHROUGH + "Elder Guardian" + ChatColor.RESET + " (tué en " + hsc.currentTimeValue + ")");
            }
        }


        // ###### MOB DEATH ######
        if ((drops = event.getDrops()).isEmpty()) return;
        if ((killer = entity.getKiller()) == null) return;
        if (killer.getType() != EntityType.PLAYER) return;

        if (entity.getType() == EntityType.COW ||
                entity.getType() == EntityType.CHICKEN ||
                entity.getType() == EntityType.PIG ||
                entity.getType() == EntityType.SHEEP ||
                entity.getType() == EntityType.RABBIT ||
                entity.getType() == EntityType.COD ||
                entity.getType() == EntityType.SALMON ||
                entity.getType() == EntityType.TROPICAL_FISH ||
                entity.getType() == EntityType.HOGLIN) {

            for (ItemStack item : drops) {

                if (item.getType() == Material.CHICKEN) {
                    item.setType(Material.COOKED_CHICKEN);
                } else if (item.getType() == Material.BEEF) {
                    item.setType(Material.COOKED_BEEF);
                } else if (item.getType() == Material.PORKCHOP) {
                    item.setType(Material.COOKED_PORKCHOP);
                } else if (item.getType() == Material.MUTTON) {
                    item.setType(Material.COOKED_MUTTON);
                } else if (item.getType() == Material.RABBIT) {
                    item.setType(Material.COOKED_RABBIT);
                } else if (item.getType() == Material.SALMON) {
                    item.setType(Material.COOKED_SALMON);
                } else if (item.getType() == Material.COD) {
                    item.setType(Material.COOKED_COD);
                } else if (item.getType() == Material.TROPICAL_FISH) {
                    item.setType(Material.COOKED_COD);
                }
            }
        }

        if (entityType == EntityType.SHEEP){
            ItemStack woolItem = new ItemStack(Material.WHITE_WOOL, 1);
            event.getDrops().add(woolItem);
        }

        boolean foundArrows = false;
        if (entityType == EntityType.CHICKEN) {
            for (ItemStack item : drops){
                if (item.getType() == Material.FEATHER){
                    item.setType(Material.ARROW);
                    item.setAmount(item.getAmount() + 1);
                    foundArrows = true;
                }
            }
            if(!foundArrows){
                ItemStack arrowItem = new ItemStack(Material.ARROW, 2);
                event.getDrops().add(arrowItem);
            }
        }

        if (entityType == EntityType.WITHER_SKELETON){
            if (Math.random() < 0.15) {
                ItemStack witherSkeletonSkullItem = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
                event.getDrops().add(witherSkeletonSkullItem);
            }
        }

        if (entityType == EntityType.ENDERMAN){
            if (Math.random() < 0.50) {
                ItemStack enderPearlItem = new ItemStack(Material.ENDER_PEARL, 1);
                event.getDrops().add(enderPearlItem);
            }
        }

//        ExperienceOrb experienceOrb = entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class);
//        experienceOrb.setExperience(5);

    }

    @EventHandler
    public void onPiglinBarter(PiglinBarterEvent event){
        if (event.getEntityType() != EntityType.PIGLIN) return;
        if (Math.random() < 0.10) {
            event.getOutcome().clear();
            int amount = (int) (Math.random() * 3 + 2);
            ItemStack enderPearlItem = new ItemStack(Material.ENDER_PEARL, amount);
            event.getOutcome().add(enderPearlItem);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(hsc.isGameStarted){
            if (!hsc.alivePlayers.contains(player)) player.setGameMode(GameMode.SPECTATOR);
            else {
                player.setGameMode(GameMode.SURVIVAL);
            }
            return;
        }

        player.setScoreboard(hsc.getScoreboard());

        // Execute console command "/playercompass playername"

        event.getPlayer().getServer().dispatchCommand(event.getPlayer(), "trackingcompass");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        hsc.alivePlayers.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
//        if (!hsc.isGameStarted){
//            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
//                event.setCancelled(true);
//            }
//        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        // cancel tnt damage
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
            event.setDamage(0);
            return;
        }

        if (!hsc.isGameStarted && event.getEntity() instanceof Player){
            event.setCancelled(true);
            return;
        }

        if(event.getEntity() instanceof Player && !(event.getCause().name().startsWith("ENTITY_"))){
            Player player = (Player) event.getEntity();

            if (event.getCause().name().startsWith("ENTITY_")){ return; }
            if ((player.getHealth() - event.getDamage()) > 0) {
                return;
            }

            hsc.alivePlayers.remove(player);
            player.playSound(player.getLocation(), "minecraft:entity.player.death", 1, 1);
            player.setGameMode(GameMode.SPECTATOR);

            broadcastMessage(ChatColor.RED + player.getName() + " est mort(e). (" + event.getCause().name() + ")");
            endGameIfPlayerIsLastThenKill(event, player);
        }
    }

    private void endGameIfPlayerIsLastThenKill(EntityDamageEvent event, Player player) {
        if(hsc.alivePlayers.isEmpty()){
            hsc.defeatGame();
        }else{
            broadcastMessage(ChatColor.RED + "Vous n'êtes plus que " + hsc.alivePlayers.size() + " survivant(s)...");
        }

        for (ItemStack item : player.getInventory()) {
            if (item != null) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }

        player.getInventory().clear();
        player.setExp(0);
        player.setLevel(0);

        ExperienceOrb xpOrb = player.getWorld().spawn(player.getLocation(), ExperienceOrb.class);
        xpOrb.setExperience(100);
        event.setCancelled(true);
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();

        if(!hsc.isGameStarted){
            if(damager.getType() == EntityType.PLAYER){
                Player player = (Player) damager;
                if ((player.getGameMode() != org.bukkit.GameMode.CREATIVE)) {
                    event.setCancelled(true);
                }
            } else if (event.getEntity() instanceof Player){
                event.setCancelled(true);
            }
            return;
        }

        Entity victim = event.getEntity();
        if (!(victim instanceof Player)) return;

        Player victimPlayer = (Player) victim;
        if ((victimPlayer.getHealth() - event.getDamage()) > 0) return;

        hsc.alivePlayers.remove(victimPlayer);
        victimPlayer.playSound(victimPlayer.getLocation(), "minecraft:entity.player.death", 1, 1);
        victimPlayer.setGameMode(GameMode.SPECTATOR);

        broadcastMessage(ChatColor.RED + victimPlayer.getName() + " s'est fait tuer par un(e) " + damager.getName());

        endGameIfPlayerIsLastThenKill(event, victimPlayer);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        ItemStack itemStack = event.getCurrentItem();
        assert itemStack != null;
        Material itemType = itemStack.getType();


        if (itemType == Material.WOODEN_PICKAXE ||
            itemType == Material.STONE_PICKAXE ||
            itemType == Material.IRON_PICKAXE ||
            itemType == Material.GOLDEN_PICKAXE ||
            itemType == Material.DIAMOND_PICKAXE ||
            itemType == Material.NETHERITE_PICKAXE){
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.DIG_SPEED, 2);
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 2);
//            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS, 1);
        }

        // The same for axes
        if (itemType == Material.WOODEN_AXE ||
                itemType == Material.STONE_AXE ||
                itemType == Material.IRON_AXE ||
                itemType == Material.GOLDEN_AXE ||
                itemType == Material.DIAMOND_AXE ||
                itemType == Material.NETHERITE_AXE){
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.DIG_SPEED, 2);
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 2);
//            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS, 1);
        }

        // The same for shovels
        if (itemType == Material.WOODEN_SHOVEL ||
                itemType == Material.STONE_SHOVEL ||
                itemType == Material.IRON_SHOVEL ||
                itemType == Material.GOLDEN_SHOVEL ||
                itemType == Material.DIAMOND_SHOVEL ||
                itemType == Material.NETHERITE_SHOVEL){
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.DIG_SPEED, 2);
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 2);
            itemStack.addEnchantment(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS, 1);
        }
    }

    @EventHandler
    public void onPlayerPickupExperience(PlayerPickupExperienceEvent event){
        int exp = event.getExperienceOrb().getExperience();
        event.getPlayer().giveExp(exp*3);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!hsc.isGameStarted && (!(event.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE))) {

            event.setCancelled(true);

            event.getPlayer().sendMessage(ChatColor.RED + "La partie n'a pas encore commencé !");

            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.getGameMode() == org.bukkit.GameMode.CREATIVE) return;

        if (block.getType() == Material.GRAVEL) {
            event.setDropItems(false);
            ItemStack flintItem = new ItemStack(Material.FLINT, 1);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), flintItem);
        }

        if (block.getType() == Material.SAND) {
            event.setDropItems(false);
            ItemStack sandItem = new ItemStack(Material.GLASS, 1);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), sandItem);
            event.getPlayer().giveExp(2);
            // Play XP noise with random pitch

            float pitch = (float) (Math.random() * 0.5 + 0.75);
            player.playSound(player.getLocation(), "minecraft:entity.experience_orb.pickup", 1, pitch);
            // XP
        }

        // Automatic smelting of ores

        // Different iron ores
        if (block.getType() == Material.IRON_ORE) {
            smeltOreAndDrop(event, Material.IRON_INGOT, 3);
        }

        if (block.getType() == Material.DEEPSLATE_IRON_ORE) {
            smeltOreAndDrop(event, Material.IRON_INGOT, 3);
        }

        // Different gold ores
        if (block.getType() == Material.GOLD_ORE) {
            smeltOreAndDrop(event, Material.GOLD_INGOT, 3);
        }

        if (block.getType() == Material.DEEPSLATE_GOLD_ORE) {
            smeltOreAndDrop(event, Material.GOLD_INGOT, 3);
        }

        if (block.getType() == Material.NETHER_GOLD_ORE){
            smeltOreAndDrop(event, Material.GOLD_INGOT, 3);
        }

        // Netherite ore
        if (block.getType() == Material.ANCIENT_DEBRIS){
            smeltOreAndDrop(event, Material.NETHERITE_SCRAP, 7);
        }

    }


    private void smeltOreAndDrop(BlockBreakEvent event, Material material, int xp){
        event.setDropItems(false);
        int fortuneLevel = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS);
        int amount = 1;
        double random = Math.random();
        switch (fortuneLevel){
            case 1:
                amount = random < 0.33 ? 2 : 1;
            case 2:
                if (random < 0.25) amount = 3;
                else if (random < 0.50) amount = 2;

            case 3:
                if (random < 0.20) amount = 4;
                else if (random < 0.40) amount = 3;
                else if (random < 0.60) amount = 2;
        }

        ItemStack item = new ItemStack(material, amount);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);

        ExperienceOrb xpOrb = event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class);
        xpOrb.setExperience(xp);
    }

}
