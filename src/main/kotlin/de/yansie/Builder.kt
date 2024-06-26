package de.yansie
import net.axay.kspigot.extensions.bukkit.toComponent
import net.axay.kspigot.items.name
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.jetbrains.annotations.Nullable
import java.util.*

class Builder(m : Material,a:Int? = null) {


    private var itemStack = ItemStack(m, a ?: 1)
    var itemMeta: ItemMeta = itemStack.itemMeta


    fun setDurability(@Nullable d: Short): Builder {
        itemStack.durability = d
        return this
    }

    fun setName(n: String): Builder {
        itemMeta.name = n.toComponent()
        itemStack.itemMeta = itemMeta
        return this
    }

    fun addEnchantment(@Nullable h: HashMap<Enchantment, Int>): Builder {
        for (it in h.entries) {
            itemMeta.addEnchant(it.key, it.value,true)
            itemStack.itemMeta = itemMeta
        }
        return this
    }
    fun addEnchantment(e:Enchantment?,i:Int): Builder {
        if (e==null) return this
        itemMeta.addEnchant(e, i,true)
        itemStack.itemMeta = itemMeta
        return this
    }
    fun addEnchantment(e:Enchantment): Builder {
        itemMeta.addEnchant(e, 1,true)
        itemStack.itemMeta = itemMeta
        return this
    }
    fun hideFlag(i:ItemFlag): Builder {
        itemMeta.itemFlags.add(i)
        itemStack.itemMeta = itemMeta
        return this
    }
    fun hideAllFlag(): Builder {
        itemMeta.itemFlags.add(ItemFlag.HIDE_ENCHANTS)
        itemMeta.itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)
        itemMeta.itemFlags.add(ItemFlag.HIDE_DESTROYS)
        itemMeta.itemFlags.add(ItemFlag.HIDE_DYE)
        itemMeta.itemFlags.add(ItemFlag.HIDE_PLACED_ON)
        itemMeta.itemFlags.add(ItemFlag.HIDE_POTION_EFFECTS)
        itemMeta.itemFlags.add(ItemFlag.HIDE_UNBREAKABLE)
        itemStack.itemMeta = itemMeta
        return this
    }
    fun removeEnchantment(@Nullable e: Enchantment): Builder {
        itemMeta.removeEnchant(e)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun setSkullOwner(owner: OfflinePlayer?): Builder {
        val skullMeta = itemStack.itemMeta as SkullMeta
        skullMeta.owningPlayer = owner
        itemStack.itemMeta = skullMeta
        return this
    }
    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}