package io.github.qMartinz.paranormal.block;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.curses.CurseHelper;
import io.github.qMartinz.paranormal.api.curses.CurseInstance;
import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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

		if (world.isClient() || !(world.getBlockEntity(pos) instanceof CurseTableEntity curseTableEntity)) return ActionResult.PASS;

		if (!curseTableEntity.getStack(0).isEmpty() && player.getStackInHand(hand).isEmpty()){
			ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), curseTableEntity.getStack(0));
			world.spawnEntity(item);
			curseTableEntity.setStack(0, ItemStack.EMPTY);
			return ActionResult.SUCCESS;
		}

		if (!player.getStackInHand(hand).isEmpty() && curseTableEntity.getStack(0).isEmpty()){
			curseTableEntity.setStack(0, player.getInventory().removeStack(player.getInventory().selectedSlot, 1));
			return ActionResult.SUCCESS;
		}

		boolean hasCursableItemInSlot = CurseHelper.getCurses(curseTableEntity.getStack(0)).stream().filter(inst -> !inst.getCurse().isTemporary()).toList().size() < 4;

		boolean hasCursedItemInHand = CurseHelper.getCurses(player.getStackInHand(hand)).stream().anyMatch(inst ->
				inst.getCurse().canCurse(curseTableEntity.getStack(0)) &&
						inst.getCurse().getMaxUses() == 0 &&
						CurseHelper.getCurses(curseTableEntity.getStack(0)).stream().noneMatch(inst2 -> inst.getCurse() == inst2.getCurse()) &&
						inst.getCurse().getElement() == element);

		boolean accept = !curseTableEntity.getStack(0).isEmpty() &&
				hasCursableItemInSlot && hasCursedItemInHand && curseTableEntity.getFuel() >= 4;

		if (!player.getStackInHand(hand).isEmpty() && !curseTableEntity.getStack(0).isEmpty() && accept){
			ItemStack result = curseTableEntity.getStack(0).copy();

			for (CurseInstance curse : CurseHelper.getCurses(player.getStackInHand(hand))) {
				if (curse.getCurse().getElement() == element &&
						CurseHelper.getCurses(curseTableEntity.getStack(0)).stream().noneMatch(inst -> inst.getCurse() == curse.getCurse())) {
					CurseHelper.addCurse(result, curse);
				}
			}

			for (CurseInstance curse : CurseHelper.getCurses(player.getStackInHand(hand))) {
				if (curse.getCurse().getElement() == element &&
						CurseHelper.getCurses(curseTableEntity.getStack(0)).stream().noneMatch(inst -> inst.getCurse() == curse.getCurse())) {
					CurseHelper.removeCurse(player.getStackInHand(hand), curse.getCurse());
				}
			}

			curseTableEntity.setStack(0, result);
			curseTableEntity.setFuel(curseTableEntity.getFuel() - 4);

			curseParticles();
		}

		return ActionResult.PASS;
	}

	public void curseParticles(){
		for (int i = 0; i < 360; i++) {
			if (i % 20 == 0) {
				// TODO Curse particles
			}
		}
	}

	static {
		HAS_ITEM = BooleanProperty.of("has_cursable_item");
		FUEL = IntProperty.of("curse_fuel", 0, 64);
	}
}
