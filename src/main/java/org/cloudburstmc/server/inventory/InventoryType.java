package org.cloudburstmc.server.inventory;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public enum InventoryType {
    CHEST(27, "Chest", 0),
    ENDER_CHEST(27, "Ender Chest", 0),
    DOUBLE_CHEST(27 + 27, "Double Chest", 0),
    PLAYER(41, "Player", -1), //36 CONTAINER, 4 ARMOR, 1 OFFHAND - TODO: Set to 12
    FURNACE(3, "Furnace", 2),
    CRAFTING(5, "Crafting", 1), //4 CRAFTING slots, 1 RESULT
    WORKBENCH(10, "Crafting", 1), //9 CRAFTING slots, 1 RESULT
    BREWING_STAND(5, "Brewing", 4), //1 INPUT, 3 POTION, 1 fuel
    ANVIL(3, "Anvil", 5), //2 INPUT, 1 OUTPUT
    ENCHANT_TABLE(2, "Enchantment Table", 3), //1 INPUT/OUTPUT, 1 LAPIS
    DISPENSER(9, "Dispenser", 6), //9 CONTAINER
    DROPPER(9, "Dropper", 7), //9 CONTAINER
    HOPPER(5, "Hopper", 8), //5 CONTAINER
    UI(1, "UI", -1),
    SHULKER_BOX(27, "Shulker Box", 0),
    BEACON(1, "Beacon", 13),
    BLAST_FURNACE(3, "Blast Furnace", 27),
    SMOKER(3, "Smoker", 28),
    BARREL(27, "Barrel", 0);

    private final int size;
    private final String title;
    private final int typeId;

    InventoryType(int defaultSize, String defaultBlockEntity, int typeId) {
        this.size = defaultSize;
        this.title = defaultBlockEntity;
        this.typeId = typeId;
    }

    public int getDefaultSize() {
        return size;
    }

    public String getDefaultTitle() {
        return title;
    }

    public int getNetworkType() {
        return typeId;
    }
}
