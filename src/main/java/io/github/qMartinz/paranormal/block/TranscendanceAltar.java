package io.github.qMartinz.paranormal.block;

import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.networking.ModMessages;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class TranscendanceAltar extends Block {
	public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0, 0, 1, 0.1875, 1),
			VoxelShapes.cuboid(0.25, 0.1875, 0.25, 0.75, 0.9375, 0.75),
			VoxelShapes.cuboid(0.75, 0.75, 0.03125, 1, 1, 0.96875),
			VoxelShapes.cuboid(0.5, 0.828125, 0.03125, 0.75, 1.078125, 0.96875),
			VoxelShapes.cuboid(0.25, 0.9375, 0.03125, 0.5, 1.1875, 0.96875),
			VoxelShapes.cuboid(0.03125, 1.03125, 0.03125, 0.25, 1.28125, 0.96875));
	private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0, 0, 1, 0.1875, 1),
			VoxelShapes.cuboid(0.25, 0.1875, 0.25, 0.75, 0.9375, 0.75),
			VoxelShapes.cuboid(0, 0.75, 0.03125, 0.25, 1, 0.96875),
			VoxelShapes.cuboid(0.25, 0.828125, 0.03125, 0.5, 1.078125, 0.96875),
			VoxelShapes.cuboid(0.5, 0.9375, 0.03125, 0.75, 1.1875, 0.96875),
			VoxelShapes.cuboid(0.75, 1.03125, 0.03125, 0.96875, 1.28125, 0.96875));
	private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0, 0, 1, 0.1875, 1),
			VoxelShapes.cuboid(0.25, 0.1875, 0.25, 0.75, 0.9375, 0.75),
			VoxelShapes.cuboid(0.03125, 0.75, 0, 0.96875, 1, 0.25),
			VoxelShapes.cuboid(0.03125, 0.828125, 0.25, 0.96875, 1.078125, 0.5),
			VoxelShapes.cuboid(0.03125, 0.9375, 0.5, 0.96875, 1.1875, 0.75),
			VoxelShapes.cuboid(0.03125, 1.03125, 0.75, 0.96875, 1.28125, 0.96875));
	private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0, 0, 1, 0.1875, 1),
			VoxelShapes.cuboid(0.25, 0.1875, 0.25, 0.75, 0.9375, 0.75),
			VoxelShapes.cuboid(0.03125, 0.75, 0.75, 0.96875, 1, 1),
			VoxelShapes.cuboid(0.03125, 0.828125, 0.5, 0.96875, 1.078125, 0.75),
			VoxelShapes.cuboid(0.03125, 0.9375, 0.25, 0.96875, 1.1875, 0.5),
			VoxelShapes.cuboid(0.03125, 1.03125, 0.03125, 0.96875, 1.28125, 0.25));

	public TranscendanceAltar(Settings settings) {
		super(settings);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = state.get(FACING);
		return switch (direction){
			default -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			case EAST -> EAST_SHAPE;
		};
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().rotateYClockwise());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{FACING});
	}
}
