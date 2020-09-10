package org.cloudburstmc.server.block.behavior;

import com.nukkitx.math.vector.Vector3f;
import lombok.val;
import org.cloudburstmc.server.block.Block;
import org.cloudburstmc.server.block.BlockIds;
import org.cloudburstmc.server.block.BlockState;
import org.cloudburstmc.server.block.BlockTraits;
import org.cloudburstmc.server.item.ItemStack;
import org.cloudburstmc.server.item.behavior.ItemToolBehavior;
import org.cloudburstmc.server.math.Direction;
import org.cloudburstmc.server.player.Player;
import org.cloudburstmc.server.registry.BlockRegistry;
import org.cloudburstmc.server.utils.BlockColor;
import org.cloudburstmc.server.utils.Identifier;
import org.cloudburstmc.server.utils.data.TreeSpecies;

import java.util.EnumMap;
import java.util.Map;

public class BlockBehaviorLog extends BlockBehaviorSolid {

    protected static final Map<TreeSpecies, Identifier> STRIPPED_MAP = new EnumMap<>(TreeSpecies.class);

    static {
        STRIPPED_MAP.put(TreeSpecies.OAK, BlockIds.STRIPPED_OAK_LOG);
        STRIPPED_MAP.put(TreeSpecies.SPRUCE, BlockIds.STRIPPED_SPRUCE_LOG);
        STRIPPED_MAP.put(TreeSpecies.BIRCH, BlockIds.STRIPPED_BIRCH_LOG);
        STRIPPED_MAP.put(TreeSpecies.JUNGLE, BlockIds.STRIPPED_JUNGLE_LOG);
        STRIPPED_MAP.put(TreeSpecies.ACACIA, BlockIds.STRIPPED_ACACIA_LOG);
        STRIPPED_MAP.put(TreeSpecies.DARK_OAK, BlockIds.STRIPPED_DARK_OAK_LOG);
    }

    protected Identifier identifier = BlockIds.LOG;

    @Override
    public float getHardness() {
        return 2;
    }

    @Override
    public float getResistance() {
        return 10;
    }

    @Override
    public int getBurnChance() {
        return 5;
    }

    @Override
    public int getBurnAbility() {
        return 10;
    }

    public static void upgradeLegacyBlock(int[] blockState) {
        if ((blockState[1] & 0b1100) == 0b1100) { // old full bark texture
            blockState[0] = BlockRegistry.get().getLegacyId(BlockIds.WOOD);
            blockState[1] = blockState[1] & 0x03; // gets only the log type and set pillar to y
        }
    }

    @Override
    public boolean canBeActivated(Block block) {
        return true;
    }

    @Override
    public boolean onActivate(Block block, ItemStack item, Player player) {
        if (!item.isAxe() || !item.useOn(, block)) {
            return false;
        }

        val state = block.getState();
        val stripped = STRIPPED_MAP.get(state.ensureTrait(BlockTraits.TREE_SPECIES));
        block.set(BlockState.get(stripped).copyTrait(BlockTraits.AXIS, state), true);
        return true;
    }

    @Override
    public boolean place(ItemStack item, Block block, Block target, Direction face, Vector3f clickPos, Player player) {
        // Convert the old log bark to the new wood block
        placeBlock(block, item.getBlock().withTrait(BlockTraits.AXIS, face.getAxis()));
        return true;
    }

    @Override
    public int getToolType() {
        return ItemToolBehavior.TYPE_AXE;
    }

    @Override
    public BlockColor getColor(Block block) {
        switch (block.getState().ensureTrait(BlockTraits.TREE_SPECIES)) {
            default:
            case OAK:
                return BlockColor.WOOD_BLOCK_COLOR;
            case SPRUCE:
                return BlockColor.SPRUCE_BLOCK_COLOR;
            case BIRCH:
                return BlockColor.SAND_BLOCK_COLOR;
            case JUNGLE:
                return BlockColor.DIRT_BLOCK_COLOR;
        }
    }

    @Override
    public ItemStack toItem(Block block) {
        return ItemStack.get(block.getState().resetTrait(BlockTraits.AXIS));
    }
}
