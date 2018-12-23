package littleservantmod.entity.ai;

import java.util.List;

import littleservantmod.entity.EntityLittleServantFakePlayer;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * アイテムを収集する
 * @author Shift02
 *
 */
public class EntityAICollectEntityItem extends EntityAIBase {

    protected EntityLittleServantFakePlayer servant;
    protected double moveSpeed;
    protected double distance;
    protected EntityItem targetEntityItem;

    private int delayCounter;

    public EntityAICollectEntityItem(EntityLittleServantFakePlayer servant, double moveSpeed, double distance) {
        this.servant = servant;
        this.moveSpeed = moveSpeed;
        this.distance = distance;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {

        this.delayCounter--;
        if (this.delayCounter > 0) {
            return false;
        }

        this.delayCounter = 10;

        float rangeOrigin = (float) (this.distance * this.distance * this.distance * 2);

        for (EntityItem entityItem : getAroundEntityItem()) {
            if (this.canCollectEntityItem(entityItem)) {

                float range = this.servant.getDistance(entityItem);

                if (range < rangeOrigin) {
                    rangeOrigin = range;

                    targetEntityItem = entityItem;

                }

            }

        }

        return this.targetEntityItem != null;

    }

    @Override
    public void startExecuting() {
        this.delayCounter = 0;
    }

    @Override
    public void resetTask() {
        super.resetTask();

        this.targetEntityItem = null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (targetEntityItem != null) {
            return true;
        }

        return false;
    }

    @Override
    public void updateTask() {
        //TODO: 他のsetLookPositionWithEntityもgetHorizontalFaceSpeedを使うやつに変更する
        this.servant.getLookHelper().setLookPositionWithEntity(this.servant, this.servant.getHorizontalFaceSpeed(), this.servant.getVerticalFaceSpeed());

        this.delayCounter--;
        if (this.delayCounter > 0) {
            return;
        }

        this.delayCounter = 10;

        if (this.servant.getDistance(this.targetEntityItem) < 1.5D) {

            for (EntityItem entityItem : this.getAroundEntityItem()) {

                if (entityItem.equals(this.targetEntityItem) && this.canCollectEntityItem(entityItem)) {

                    TileEntityHopper.putDropInInventoryAllSlots((IInventory) null, this.servant.getInventory(), entityItem);

                    this.targetEntityItem = null;

                    return;
                }

            }

        } else {
            this.servant.getNavigator().tryMoveToEntityLiving(this.targetEntityItem, this.moveSpeed);
        }
    }

    private boolean canCollectEntityItem(EntityItem entityItem) {

        // アイテムの可視判定
        return this.servant.getEntitySenses().canSee(entityItem);

    }

    private List<EntityItem> getAroundEntityItem() {

        return this.servant.world.<EntityItem> getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.servant.getPosition()).grow(this.distance, this.distance, this.distance));
    }

}
