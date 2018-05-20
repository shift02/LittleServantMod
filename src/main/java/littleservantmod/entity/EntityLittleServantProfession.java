package littleservantmod.entity;

import littleservantmod.ProfessionManager;
import littleservantmod.entity.ai.EntityAISit;
import littleservantmod.profession.ProfessionBase;
import littleservantmod.profession.ProfessionUnemployed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 職業を設定するクラス
 * @author shift
 */
public class EntityLittleServantProfession extends EntityLittleServantBase {

	private ProfessionBase professionBase = new ProfessionUnemployed();

	public static ProfessionManager PM = ProfessionManager.getInstance();

	protected static final DataParameter<String> PROFESSION = EntityDataManager.<String> createKey(EntityLittleServantBase.class, DataSerializers.STRING);

	public EntityLittleServantProfession(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PROFESSION, ProfessionUnemployed.resourceLocation.toString());
	}

	@Override
	protected void initEntityAI() {

		//排他処理メモ
		//1 = 適当にウロウロ, 2 = 何かを見つめる, 3 = 何かについていく, 5 = 停止

		// うろうろ移動するAIの追加
		//this.wander = new EntityAIWander(this, interpTargetPitch);
		//this.tasks.addTask(1, this.wander);
		this.changeAI(PM.getProfession(ProfessionUnemployed.resourceLocation));

	}

	public void changeProfession(ProfessionBase profession) {

		this.setProfession(profession);
		this.changeAI(profession);

	}

	protected void changeAI(ProfessionBase profession) {

		this.tasks.taskEntries.clear();

		this.aiSit = new EntityAISit(this);

		this.tasks.addTask(102, this.aiSit);

		profession.initAI(this);

	}

	public void setProfession(ProfessionBase profession) {
		this.dataManager.set(PROFESSION, profession.getID().toString());
	}

	public ProfessionBase getProfession() {
		return PM.getProfession(new ResourceLocation(this.dataManager.get(PROFESSION)));
	}

	/*
	 * NBT
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setString("Profession", getProfession().toString());

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.setProfession(ProfessionManager.getInstance().getProfession(new ResourceLocation(compound.getString("Profession"))));

	}

}
