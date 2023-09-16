package sfiomn.legendary_additions.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import sfiomn.legendary_additions.registry.ItemRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ForestKeyEntity extends KeyEntity {
    protected static final AnimationBuilder INSERTING = new AnimationBuilder().addAnimation("inserting", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder EJECTING = new AnimationBuilder().addAnimation("ejecting", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    public ForestKeyEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public <E extends IAnimatable> PlayState keyMovementPredicate(AnimationEvent<E> event) {
        if (getAnimation() == INSERT_KEY) {
            event.getController().setAnimation(INSERTING);
            if (event.getController().getAnimationState() == AnimationState.Stopped) {
                this.setAnimation(TRY_UNLOCK);
            }
        } else if (getAnimation() == EJECT_KEY) {
            event.getController().setAnimation(EJECTING);
            if (event.getController().getAnimationState() == AnimationState.Stopped) {
                this.setAnimation(DROP_KEY);
                return PlayState.STOP;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public Item getKeyItem() {
        return ItemRegistry.FOREST_KEY.get();
    }
}
