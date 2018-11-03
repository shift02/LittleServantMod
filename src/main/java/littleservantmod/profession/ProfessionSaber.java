package littleservantmod.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIAttackMelee2;
import littleservantmod.entity.ai.EntityAIEquipShield;
import littleservantmod.entity.ai.EntityAIEquipTool;
import littleservantmod.entity.ai.target.EntityAINearestAttackableTarget2;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;

public class ProfessionSaber extends ProfessionLSMBase {

    @Override
    public void initAI(IServant servant) {

        super.initAI(servant);

        //シールドを持っているときは持ち変える
        servant.addAI(200, new EntityAIEquipShield((EntityLittleServant) servant.getEntityInstance()));

        //剣を持ち帰る
        servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ServantToolManager.saber));

        //攻撃
        servant.addAI(500, new EntityAIAttackMelee2((EntityLittleServant) servant.getEntityInstance(), 0.8d, false));

        //Target うまい具合に分離したい
        servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntitySpider.class, true));
        servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntityZombie.class, true));
        servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntitySkeleton.class, true));
        servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntitySlime.class, true));
        servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntityMagmaCube.class, true));
        servant.addTargetAI(200, new EntityAINearestAttackableTarget2((EntityLittleServant) servant.getEntityInstance(), EntitySilverfish.class, true));

    }

    @Override
    public boolean hasOption(IServant servant) {
        return true;
    }

}
