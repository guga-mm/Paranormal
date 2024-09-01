package io.github.qMartinz.paranormal.block.entities;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.block.CurseTable;
import io.github.qMartinz.paranormal.block.entities.inventory.CurseTableInventory;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import net.fabricmc.fabric.api.event.client.player.ClientPickBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CurseTableEntity extends BlockEntity implements CurseTableInventory {
	private final DefaultedList<ItemStack> inventory;

	public final ParanormalElement element;
	public CurseTableEntity(BlockPos pos, BlockState state, ParanormalElement element) {
		super(switch(element) {
			case FEAR -> ModBlockRegistry.CURSE_TABLE_WISDOM_ENTITY;
			case BLOOD -> ModBlockRegistry.CURSE_TABLE_BLOOD_ENTITY;
			case WISDOM -> ModBlockRegistry.CURSE_TABLE_WISDOM_ENTITY;
			case DEATH -> ModBlockRegistry.CURSE_TABLE_DEATH_ENTITY;
			case ENERGY -> ModBlockRegistry.CURSE_TABLE_ENERGY_ENTITY;
		}, pos, state);
		this.element = element;
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
	}

	private void setHasItem(@Nullable Entity entity, boolean hasItem) {
		if (this.world.getBlockState(this.getPos()) == this.getCachedState()) {
			this.world.setBlockState(this.getPos(), this.getCachedState().with(CurseTable.HAS_ITEM, hasItem), 2);
			this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Context.create(entity, this.getCachedState()));
		}
	}

	public void setFuel(int fuel) {
		if (this.world.getBlockState(this.getPos()) == this.getCachedState()) {
			this.world.setBlockState(this.getPos(), this.getCachedState().with(CurseTable.FUEL, fuel), 2);
			this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Context.create(null, this.getCachedState()));
		}
	}

	public int getFuel() {
		if (this.world.getBlockState(this.getPos()) == this.getCachedState()) {
			return this.world.getBlockState(this.getPos()).get(CurseTable.FUEL);
		}
		return 0;
	}

	@Override
	public ItemStack getStack(int slot) {
		return this.inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Objects.requireNonNullElse(this.inventory.get(slot), ItemStack.EMPTY);
		this.inventory.set(slot, ItemStack.EMPTY);
		if (!itemStack.isEmpty()) this.setHasItem(null, false);
		return itemStack;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		if (this.world != null) {
			this.inventory.set(slot, stack);
			this.setHasItem(null, true);
		}
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return Inventory.canPlayerUse(this, player);
	}

	public static void tick(World world, BlockPos pos, BlockState state, CurseTableEntity be) {
		/* TODO curse table tick method
		if (pLevel.isClientSide() && pBlockEntity.getFuel() >= 4){
            for (int i = 0; i < 3; i++) {
                pLevel.addParticle(AbilitiesParticleOptions.createData(pBlockEntity.element.getParticleColor(),
                                pBlockEntity.element != ParanormalElement.MORTE),
                        pPos.getX() + 0.5d + (pLevel.random.nextInt(-20, 20) / 100d),
                        pPos.getY() + 0.9d,
                        pPos.getZ() + 0.5d + (pLevel.random.nextInt(-20, 20) / 100d),
                        0d, 0.02d, 0d);
            }
        }

        TagKey<Item> acceptedItems = switch(pBlockEntity.element){
            default -> null;
            case SANGUE -> OPTags.BLOOD_FUEL;
            case CONHECIMENTO -> OPTags.KNOWLEDGE_FUEL;
            case MORTE -> OPTags.DEATH_FUEL;
            case ENERGIA -> OPTags.ENERGY_FUEL;
        };

        List<ItemEntity> fuelItems = new ArrayList<>();
        if(acceptedItems != null) fuelItems = pLevel.getEntitiesOfClass(ItemEntity.class, new AABB(pPos.above()).move(0, -0.5, 0),
                e -> e.getItem().is(acceptedItems));
        fuelItems.forEach(i -> {
            if (pBlockEntity.getFuel() < pBlockEntity.getMaxFuelSize()){
                i.getItem().shrink(1);
                pBlockEntity.setFuel(pBlockEntity.getFuel() + 1);
            }
        });
		 */
	}
}
