package io.github.qMartinz.paranormal.block.entities;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.block.CurseTable;
import io.github.qMartinz.paranormal.block.entities.inventory.CurseTableInventory;
import io.github.qMartinz.paranormal.datagen.ParanormalDatagen;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurseTableEntity extends BlockEntity implements CurseTableInventory {
	private final DefaultedList<ItemStack> inventory;

	public final ParanormalElement element;
	public CurseTableEntity(BlockPos pos, BlockState state, ParanormalElement element) {
		super(switch(element) {
			case FEAR -> ModBlockRegistry.WISDOM_TABLE_ENTITY;
			case BLOOD -> ModBlockRegistry.BLOOD_TABLE_ENTITY;
			case WISDOM -> ModBlockRegistry.WISDOM_TABLE_ENTITY;
			case DEATH -> ModBlockRegistry.DEATH_TABLE_ENTITY;
			case ENERGY -> ModBlockRegistry.ENERGY_TABLE_ENTITY;
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
		markDirty();
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

	@Override
	public void markDirty() {
		world.updateListeners(pos, getCachedState(), getCachedState(), 3);
	}

	public static void tick(World world, BlockPos pos, BlockState state, CurseTableEntity be) {
		if (world.isClient() && be.getFuel() >= 4){
			// TODO fueled VFX
		}

		TagKey<Item> acceptedItems = switch(be.element){
			default -> null;
			case BLOOD -> ParanormalDatagen.ItemTagProvider.BLOOD_FUEL;
			case WISDOM -> ParanormalDatagen.ItemTagProvider.WISDOM_FUEL;
			case DEATH -> ParanormalDatagen.ItemTagProvider.DEATH_FUEL;
			case ENERGY -> ParanormalDatagen.ItemTagProvider.ENERGY_FUEL;
		};

		List<ItemEntity> fuelItems = new ArrayList<>();
		if(acceptedItems != null) fuelItems = world.getEntitiesByClass(ItemEntity.class, new Box(pos.up()).offset(0, -0.5, 0),
				e -> e.getStack().isIn(acceptedItems));
		fuelItems.forEach(i -> {
			if (be.getFuel() < 64){
				i.getStack().decrement(1);
				be.setFuel(be.getFuel() + 1);
			}
		});
	}

	@Override
	public void readNbtImpl(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		inventory.clear();
		Inventories.readNbt(nbt, inventory, lookupProvider);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		Inventories.writeNbt(nbt, inventory, lookupProvider);
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	@Override
	public NbtCompound toSyncedNbt(HolderLookup.Provider lookupProvider) {
		NbtCompound nbtCompound = new NbtCompound();
		Inventories.writeNbt(nbtCompound, inventory, lookupProvider);
		return nbtCompound;
	}
}
