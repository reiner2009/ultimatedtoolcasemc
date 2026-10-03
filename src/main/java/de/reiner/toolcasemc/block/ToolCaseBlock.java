package de.reiner.toolcasemc.block;

import de.reiner.toolcasemc.stats.ModStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ToolCaseBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING;
    public static final Identifier CONTENTS;

    public ToolCaseBlock(final BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.UP));
    }

    @Override
    public BlockEntity newBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        return new ToolCaseBlockEntity(worldPosition, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel) {
            BlockEntity var8 = level.getBlockEntity(pos);
            if (var8 instanceof ToolCaseBlockEntity toolCaseBoxBlockEntity) {
                player.openMenu(toolCaseBoxBlockEntity);
                player.awardStat(ModStats.OPEN_TOOLCASE);
                PiglinAi.angerNearbyPiglins(serverLevel, player, true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    @Override
    public BlockState playerWillDestroy(final Level level, final BlockPos pos, final BlockState state, final Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ToolCaseBlockEntity toolCaseBoxBlockEntity) {
            if (!level.isClientSide() && player.preventsBlockDrops() && !toolCaseBoxBlockEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(state.getBlock());
                itemStack.applyComponents(blockEntity.collectComponents());
                ItemEntity entity = new ItemEntity(level, (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, itemStack);
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
            } else {
                toolCaseBoxBlockEntity.unpackLootTable(player);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected List<ItemStack> getDrops(final BlockState state, LootParams.Builder params) {
        BlockEntity blockEntity = (BlockEntity)params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof ToolCaseBlockEntity toolCaseBoxBlockEntity) {
            params = params.withDynamicDrop(CONTENTS, (output) -> {
                for(int i = 0; i < toolCaseBoxBlockEntity.getContainerSize(); ++i) {
                    output.accept(toolCaseBoxBlockEntity.getItem(i));
                }

            });
        }

        return super.getDrops(state, params);
    }

    @Override
    protected void affectNeighborsAfterRemoval(final BlockState state, final ServerLevel level, final BlockPos pos, final boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    protected boolean propagatesSkylightDown(final BlockState state) {
        return false;
    }

    @Override
    protected boolean hasAnalogOutputSignal(final BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(final BlockState state, final Level level, final BlockPos pos, final Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    protected BlockState rotate(final BlockState state, final Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    static {
        FACING = DirectionalBlock.FACING;
        CONTENTS = Identifier.withDefaultNamespace("contents");
    }
}
