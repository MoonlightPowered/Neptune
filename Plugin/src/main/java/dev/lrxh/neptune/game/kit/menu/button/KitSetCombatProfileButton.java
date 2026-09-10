package dev.lrxh.neptune.game.kit.menu.button;

import dev.lrxh.neptune.game.kit.Kit;
import dev.lrxh.neptune.game.kit.KitService;
import dev.lrxh.neptune.game.kit.menu.KitManagementMenu;
import dev.lrxh.neptune.utils.CC;
import dev.lrxh.neptune.utils.ItemBuilder;
import dev.lrxh.neptune.utils.menu.Button;
import dev.lrxh.neptune.utils.sign.SignInputMenu;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitSetCombatProfileButton extends Button {
    private final Kit kit;

    public KitSetCombatProfileButton(int slot, Kit kit) {
        super(slot, false);
        this.kit = kit;
    }

    @Override
    public void onClick(ClickType type, Player player) {
        player.closeInventory();
        SignInputMenu.open(player, kit.getDisplayName(), "Enter combat profile key", input -> {
            try {
                kit.setCombatProfileKey(Key.key(input));
                player.sendMessage(CC.success("Combat profile assigned"));
                new KitManagementMenu(kit).open(player);
                KitService.get().save();
            } catch (InvalidKeyException exception) {
                player.sendMessage(CC.error("Invalid combat profile key: " + input));
            }
        });
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.NAME_TAG)
                .name("&eAssign combat profile &7(" + kit.getDisplayName() + "&7)")
                .lore("&7Click to set a combat profile for this kit")
                .build();
    }

}
