package littleservantmod.profession;

import com.google.common.base.Predicate;

import littleservantmod.api.IServant;
import littleservantmod.entity.EntityLittleServant;
import littleservantmod.entity.ai.EntityAIEquipTool;
import littleservantmod.entity.ai.EntityAIUseItemForEntity;
import littleservantmod.entity.ai.target.EntityAINearestAttackableTarget2;
import net.minecraft.entity.passive.EntitySheep;

public class ProfessionRipper extends ProfessionLSMBase {

	@Override
	public void initAI(IServant servant) {

		super.initAI(servant);

		//ハサミを持ち帰る
		servant.addAI(200, new EntityAIEquipTool((EntityLittleServant) servant.getEntityInstance(), ProfessionToolManager.ripper));

		//毛を狩る
		servant.addAI(500, new EntityAIUseItemForEntity((EntityLittleServant) servant.getEntityInstance(), 0.8d, false));

		//Target
		servant.addTargetAI(200,
				new EntityAINearestAttackableTarget2(
						(EntityLittleServant) servant.getEntityInstance(),
						EntitySheep.class, 10, true, false,
						new Predicate<EntitySheep>() {

							@Override
							public boolean apply(EntitySheep sheep) {
								if (sheep == null) return false;

								if (sheep.getSheared()) return false;
								if (sheep.isChild()) return false;

								return true;
							}

						}));

	}
}
