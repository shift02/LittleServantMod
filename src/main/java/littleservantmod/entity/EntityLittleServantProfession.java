package littleservantmod.entity;

import littleservantmod.api.profession.IProfession;
import littleservantmod.entity.ai.EntityAISit;
import littleservantmod.profession.ProfessionDispatcher;
import littleservantmod.profession.ProfessionEventHandler;
import littleservantmod.profession.ProfessionManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
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
public class EntityLittleServantProfession extends EntityLittleServantFakePlayer {

	public static ProfessionDispatcher professions;

	protected static final DataParameter<String> PROFESSION = EntityDataManager.<String> createKey(EntityLittleServantBase.class, DataSerializers.STRING);

	public EntityLittleServantProfession(World worldIn) {
		super(worldIn);

		//System.out.println(professions.g);

	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PROFESSION, ProfessionEventHandler.kyeUnemployed.toString());

		professions = ProfessionManager.gatProfessions(this);

	}

	//職業の初期化の関係で独自メソッド
	@Override
	protected void initEntityAI() {

		//排他処理メモ
		//1 = 適当にウロウロ, 2 = 何かを見つめる, 3 = 何かについていく, 5 = 停止

		// うろうろ移動するAIの追加
		//this.wander = new EntityAIWander(this, interpTargetPitch);
		//this.tasks.addTask(1, this.wander);
		IProfession tmp = professions.getDefaultProfession();
		this.changeAI(tmp);

	}

	@Override
	public void addAI(int priority, EntityAIBase task) {

		this.tasks.addTask(priority, task);

	}

	public void changeProfession(IProfession profession) {

		this.setProfession(profession);
		if (!this.world.isRemote) this.changeAI(profession);

	}

	protected void changeAI(IProfession profession) {

		this.tasks.taskEntries.clear();

		this.aiSit = new EntityAISit(this);

		this.tasks.addTask(102, this.aiSit);

		profession.initAI(this);

	}

	protected void setProfession(IProfession profession) {

		this.professions.setCurrentProfession(profession);

		if (!this.world.isRemote) this.dataManager.set(PROFESSION, profession.getRegistryName().toString());

	}

	public IProfession getProfession() {
		return professions.getProfession(new ResourceLocation(this.dataManager.get(PROFESSION)));
	}

	private IProfession getProfession(ResourceLocation resourceLocation) {
		return professions.getProfession(resourceLocation);
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);

		if (PROFESSION.equals(key) && this.world.isRemote) {

			//初期起動時にNullの時があるから
			if (this.professions != null) this.setProfession(getProfession());

			//クライアント側を変える必要があるかわからないけど一応
			//追記　必要なかった...
			//this.changeAI(getProfession());

		}

	}

	/*
	 * NBT
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		if (this.professions != null) compound.setString("Profession", getProfession().getRegistryName().toString());

		if (this.professions != null) compound.setTag("LMSProfessions", this.professions.serializeNBT());

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (this.professions != null && compound.hasKey("Profession")) {
			this.setProfession(this.getProfession(new ResourceLocation(compound.getString("Profession"))));
		} else {
			this.setProfession(this.getProfession(ProfessionEventHandler.keyChores));
		}

		if (this.professions != null && compound.hasKey("LMSProfessions")) this.professions.deserializeNBT(compound.getCompoundTag("ForgeCaps"));

	}

	@Override
	public EntityLiving getEntityInstance() {
		return this;
	}

}
