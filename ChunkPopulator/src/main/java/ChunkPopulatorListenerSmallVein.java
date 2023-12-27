import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class ChunkPopulatorListenerSmallVein extends BlockPopulator {
    private Material mat;
    private final int chance;

    public ChunkPopulatorListenerSmallVein(Material mat, int chance){
        this.mat = mat;
        this.chance = chance;
    }

    @Override
    public void populate(World world, Random rand, Chunk chunk) {
        Block block;
        for (int x = 0; x < 16; x++) {
            for (int y = 1; y < 70; y++) {
                for (int z = 0; z < 16; z++) {
                    block = chunk.getBlock(x, y, z);
                    int r = (int) (Math.random() * chance);
                    if (r == 3){
                        if (block.getType().compareTo(Material.STONE) == 0) {
                            generateVein(chunk, x, y, z);
                        }
                        else if (block.getType().compareTo(Material.DEEPSLATE) == 0) {

                            if(mat == Material.DIAMOND_ORE){
                                mat = Material.DEEPSLATE_DIAMOND_ORE;
                            }
                            else if(mat == Material.GOLD_ORE){
                                mat = Material.DEEPSLATE_GOLD_ORE;
                            }
                            else if(mat == Material.LAPIS_ORE){
                                mat = Material.DEEPSLATE_LAPIS_ORE;
                            }

                            generateVein(chunk, x, y, z);
                        }
                    }
                }
            }
        }
    }

    private void generateVein(Chunk chunk, int x, int y, int z) {
        switch((int) (Math.random() * (7))) {
            case 0: vein1(chunk, x, y, z);
            case 1: vein2(chunk, x, y, z);
            case 2: vein3(chunk, x, y, z);
            case 3: vein4(chunk, x, y, z);
            case 4: vein5(chunk, x, y, z);
            case 5: vein6(chunk, x, y, z);
            case 6: vein7(chunk, x, y, z);
        }
    }

    private void vein1(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y, z).setType(mat, false); }
        catch (Exception ignored){}
        try { chunk.getBlock(x-1, y, z).setType(mat, false); }
        catch (Exception ignored){}
        try { chunk.getBlock(x, y, z+1).setType(mat, false); }
        catch (Exception ignored){}
        try {  chunk.getBlock(x, y, z-1).setType(mat, false); }
        catch (Exception ignored){}
    }
    private void vein2(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y+1, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y-1, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y+1, z+1).setType(mat, false); }
        catch(Exception ignored){}
    }
    private void vein3(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y+1, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x-1, y, z).setType(mat, false); }
        catch(Exception ignored){}
    }
    private void vein4(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y, z+1).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y, z+1).setType(mat, false); }
        catch(Exception ignored){}
    }
    private void vein5(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x+1, y, z).setType(mat, false); }
        catch(Exception ignored){}
    }
    private void vein6(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y+1, z).setType(mat, false); }
        catch(Exception ignored){}
    }
    private void vein7(Chunk chunk, int x, int y, int z) {
        try { chunk.getBlock(x, y, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y+1, z).setType(mat, false); }
        catch(Exception ignored){}
        try { chunk.getBlock(x, y+1, z+1).setType(mat, false); }
        catch(Exception ignored){}
    }
}
