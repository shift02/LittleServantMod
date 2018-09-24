package littleservantmod.api;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * サーヴァントのEntityに実装されるインターフェイス<br />
 * Entityのインスタンスを直接参照する場合は互換性の保証はできません
 * @author shift02
 *
 */
public interface IServant {

    public void addAI(int priority, EntityAIBase task);

    public void addTargetAI(int priority, EntityAIBase task);

    public void addBlockTargetAI(int priority, EntityAIBase task);

    public EntityLiving getEntityInstance();

    public EntityLivingBase getOwner();

}
