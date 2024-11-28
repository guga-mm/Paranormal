package io.github.qMartinz.paranormal.block;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.curses.CurseHelper;
import io.github.qMartinz.paranormal.api.curses.CurseInstance;
import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import io.github.qMartinz.paranormal.networking.ParticleMessages;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.rendering.particle.Easing;

public class CurseTable extends BlockWithEntity {
	public static final BooleanProperty HAS_ITEM;
	public static final IntProperty FUEL;

	public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public static VoxelShape SHAPE = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

	public final ParanormalElement element;

	public CurseTable(ParanormalElement element) {
		super(Settings.copy(Blocks.ENCHANTING_TABLE));
		this.element = element;
		this.setDefaultState(this.stateManager.getDefaultState().with(HAS_ITEM, false));
		this.setDefaultState(this.stateManager.getDefaultState().with(FUEL, 0));
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().rotateYClockwise());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(HAS_ITEM);
		builder.add(FUEL);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CurseTableEntity(pos, state, element);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof Inventory) {
				ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient() || !(world.getBlockEntity(pos) instanceof CurseTableEntity curseTableEntity) || hand != Hand.MAIN_HAND) return super.onUse(state, world, pos, player, hand, hit);

		if (!curseTableEntity.getItem().isEmpty() && player.getMainHandStack().isEmpty()){
			Paranormal.LOGGER.info("Taking Item");
			ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), curseTableEntity.getItem());
			world.spawnEntity(item);
			curseTableEntity.setItem(ItemStack.EMPTY);
			curseTableEntity.markDirty();
			world.updateListeners(pos, state, state, 0);
			return ActionResult.CONSUME;
		}

		if (!player.getMainHandStack().isEmpty() && curseTableEntity.getItem().isEmpty()){
			Paranormal.LOGGER.info("Setting item");
			curseTableEntity.setItem(player.getMainHandStack());
			curseTableEntity.markDirty();
			world.updateListeners(pos, state, state, 0);
			player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
			return ActionResult.CONSUME;
		}

		boolean hasCursableItemInSlot = CurseHelper.getCurses(curseTableEntity.getItem()).stream().filter(inst -> !inst.getCurse().isTemporary()).toList().size() < 4;

		boolean hasCursedItemInHand = CurseHelper.getCurses(player.getMainHandStack()).stream().anyMatch(inst ->
				inst.getCurse().canCurse(curseTableEntity.getItem()) &&
						inst.getCurse().getMaxUses() == 0 &&
						CurseHelper.getCurses(curseTableEntity.getItem()).stream().noneMatch(inst2 -> inst.getCurse() == inst2.getCurse()) &&
						inst.getCurse().getElement() == element);

		boolean accept = !curseTableEntity.getItem().isEmpty() &&
				hasCursableItemInSlot && hasCursedItemInHand && curseTableEntity.getFuel() >= 4;

		if (!player.getMainHandStack().isEmpty() && !curseTableEntity.getItem().isEmpty() && accept){
			Paranormal.LOGGER.info("Cursing item");
			ItemStack result = curseTableEntity.getItem().copy();

			for (CurseInstance curse : CurseHelper.getCurses(player.getMainHandStack())) {
				if (curse.getCurse().getElement() == element &&
						CurseHelper.getCurses(curseTableEntity.getItem()).stream().noneMatch(inst -> inst.getCurse() == curse.getCurse())) {
					CurseHelper.addCurse(result, curse);
				}
			}

			for (CurseInstance curse : CurseHelper.getCurses(player.getMainHandStack())) {
				if (curse.getCurse().getElement() == element &&
						CurseHelper.getCurses(curseTableEntity.getItem()).stream().noneMatch(inst -> inst.getCurse() == curse.getCurse())) {
					CurseHelper.removeCurse(player.getMainHandStack(), curse.getCurse());
				}
			}

			curseTableEntity.setItem(result);
			curseTableEntity.setFuel(curseTableEntity.getFuel() - 4);
			curseTableEntity.markDirty();
			world.updateListeners(pos, state, state, 0);

			if (world instanceof ServerWorld serverWorld) curseParticles(serverWorld, pos);

			return ActionResult.CONSUME;
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	public void curseParticles(ServerWorld world, BlockPos pos){
		if (element == ParanormalElement.DEATH){
			ParticleMessages.spawnLumitransparentCircle(world, ModParticleRegistry.GLOWING_PARTICLE, pos.getX() + 0.5d,
					pos.getY() + 1.25d, pos.getZ() + 0.5d, 0.5d, 12, 0d, 0.25d, 0d,
					element.particleColorS(), element.particleColorE(), 1f, 0f, -1f,
					1f, Easing.LINEAR, Easing.LINEAR, 0.3f, 0f, -1f, 1f,
					Easing.LINEAR, Easing.LINEAR, 36, 1f);
		} else {
			ParticleMessages.spawnAdditiveCircle(world, ModParticleRegistry.GLOWING_PARTICLE, pos.getX() + 0.5d,
					pos.getY() + 1.25d, pos.getZ() + 0.5d, 0.5d, 12, 0d, 0.25d, 0d,
					element.particleColorS(), element.particleColorE(), 1f, 0f, -1f,
					1f, Easing.LINEAR, Easing.LINEAR, 0.3f, 0f, -1f, 1f,
					Easing.LINEAR, Easing.LINEAR, 36, 1f);
		}
	}

	static {
		HAS_ITEM = BooleanProperty.of("has_cursable_item");
		FUEL = IntProperty.of("curse_fuel", 0, 64);
	}
}
