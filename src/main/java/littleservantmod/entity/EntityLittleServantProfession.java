package littleservantmod.entity;

import littleservantmod.api.profession.IProfession;
import littleservantmod.api.profession.behavior.IBehavior;
import littleservantmod.api.profession.mode.IMode;
import littleservantmod.entity.ai.EntityAISit;
import littleservantmod.profession.ProfessionDispatcher;
import littleservantmod.profession.ProfessionEventHandler;
import littleservantmod.profession.ProfessionManager;
import littleservantmod.profession.mode.ModeEventHandler;
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

	protected static final DataParameter<String> MODE = EntityDataManager.<String> createKey(EntityLittleServantBase.class, DataSerializers.STRING);

	protected static final DataParameter<String> BEHAVIOR = EntityDataManager.<String> createKey(EntityLittleServantBase.class, DataSerializers.STRING);

	public EntityLittleServantProfession(World worldIn) {
		super(worldIn);

		//System.out.println(professions.g);

	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PROFESSION, ProfessionEventHandler.kyeUnemployed.toString());
		this.dataManager.register(MODE, ModeEventHandler.kyeDefault.toString());
		this.dataManager.register(BEHAVIOR, ModeEventHandler.kyeDefault.toString());

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
		this.changeProfessionAI(tmp);

	}

	@Override
	public void addAI(int priority, EntityAIBase task) {

		this.tasks.addTask(priority, task);

	}

	@Override
	public void addTargetAI(int priority, EntityAIBase task) {

		this.targetTasks.addTask(priority, task);

	}

	/* ================================
	 *   IProfession
	 * ================================ */
	public void changeProfession(ResourceLocation profession) {
		this.changeProfession(getProfession(profession));
	}

	protected void changeProfession(IProfession profession) {

		this.setProfession(profession);
		if (!this.world.isRemote) this.changeProfessionAI(profession);

	}

	protected void changeProfessionAI(IProfession profession) {
		changeAI(profession, null, null);
	}

	private void setProfession(IProfession profession) {

		this.professions.setCurrentProfession(profession);

		if (!this.world.isRemote) {
			this.dataManager.set(PROFESSION, profession.getRegistryName().toString());

			//モードがある時. fix: Modeは必ずある
			//if (profession.hasMode(this)) {
			IMode mode = profession.getDefaultMode(this);
			this.dataManager.set(MODE, mode.getRegistryName().toString());
			//}

			IBehavior behavior = profession.getDefaultBehavior(this);
			this.dataManager.set(BEHAVIOR, behavior.getRegistryName().toString());

		}

	}

	public IProfession getProfession() {
		return professions.getProfession(new ResourceLocation(this.dataManager.get(PROFESSION)));
	}

	public IProfession[] getProfessions() {
		return professions.getProfessions();
	}

	private IProfession getProfession(ResourceLocation resourceLocation) {
		return professions.getProfession(resourceLocation);
	}

	private void loadProfession(ResourceLocation professionL, ResourceLocation modeL, ResourceLocation behaviorL) {

		IProfession profession = this.getProfession(professionL);
		IMode mode = null;
		IBehavior behavior = null;

		if (modeL != null) {
			mode = this.getMode(professionL, modeL);
		} else {
			mode = profession.getDefaultMode(this);
		}

		if (behaviorL != null) {
			behavior = this.getBehavior(professionL, behaviorL);
		} else {
			behavior = profession.getDefaultBehavior(this);
		}

		this.professions.setCurrentProfession(profession);

		if (!this.world.isRemote) {

			this.dataManager.set(PROFESSION, profession.getRegistryName().toString());
			this.dataManager.set(MODE, mode.getRegistryName().toString());
			this.dataManager.set(BEHAVIOR, behavior.getRegistryName().toString());

			this.changeAI(profession, mode, behavior);

		}

	}

	/* ================================
	 *   IMode
	 * ================================ */
	public void changeMode(ResourceLocation mode) {
		this.changeMode(getMode(mode));
	}

	protected void changeMode(IMode mode) {

		this.setMode(mode);
		if (!this.world.isRemote) this.changeModeAI(mode);

	}

	protected void changeModeAI(IMode mode) {
		changeAI(this.getProfession(), mode, this.getBehavior());
	}

	private void setMode(IMode mode) {

		if (!this.world.isRemote) {

			this.dataManager.set(MODE, mode.getRegistryName().toString());

		}

	}

	public IMode getMode() {

		return professions.getMode(
				new ResourceLocation(this.dataManager.get(PROFESSION)),
				new ResourceLocation(this.dataManager.get(MODE)));

	}

	public IMode[] getModes() {
		return professions.getModes(new ResourceLocation(this.dataManager.get(PROFESSION)));
	}

	private IMode getMode(ResourceLocation resourceLocation) {
		return this.getMode(new ResourceLocation(this.dataManager.get(PROFESSION)), resourceLocation);
	}

	private IMode getMode(ResourceLocation pResourceLocation, ResourceLocation resourceLocation) {
		return professions.getMode(pResourceLocation, resourceLocation);
	}

	/* ================================
	 *   IBehavior
	 * ================================ */
	public void changeBehavior(ResourceLocation behavior) {
		this.changeBehavior(getBehavior(behavior));
	}

	protected void changeBehavior(IBehavior behavior) {

		this.setBehavior(behavior);
		if (!this.world.isRemote) this.changeBehaviorAI(behavior);

	}

	protected void changeBehaviorAI(IBehavior behavior) {
		changeAI(this.getProfession(), this.getMode(), behavior);
	}

	private void setBehavior(IBehavior behavior) {

		if (!this.world.isRemote) {

			this.dataManager.set(BEHAVIOR, behavior.getRegistryName().toString());

		}

	}

	public IBehavior getBehavior() {

		return professions.getBehavior(
				new ResourceLocation(this.dataManager.get(PROFESSION)),
				new ResourceLocation(this.dataManager.get(BEHAVIOR)));

	}

	public IBehavior[] getBehaviorList() {
		return professions.getBehaviorList(new ResourceLocation(this.dataManager.get(PROFESSION)));
	}

	private IBehavior getBehavior(ResourceLocation resourceLocation) {
		return this.getBehavior(new ResourceLocation(this.dataManager.get(PROFESSION)), resourceLocation);
	}

	private IBehavior getBehavior(ResourceLocation bResourceLocation, ResourceLocation resourceLocation) {
		return professions.getBehavior(bResourceLocation, resourceLocation);
	}

	/* ================================
	 *   共通
	 * ================================ */
	private void changeAI(IProfession profession, IMode mode, IBehavior behavior) {

		if (mode == null) {
			mode = profession.getDefaultMode(this);
		}
		if (behavior == null) {
			behavior = profession.getDefaultBehavior(this);
		}

		EntityAISit oldSit = this.aiSit;

		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();

		if (oldSit == null) {
			this.aiSit = new EntityAISit(this);
		} else {
			this.aiSit = oldSit;
		}

		this.tasks.addTask(102, this.aiSit);

		profession.initAI(this);

		//TODO 親の初期化とかできるように
		//モードがある時
		//モードは必ずある
		//if (profession.hasMode(this)) {
		mode.initAI(this);

		behavior.initAI(this);
		behavior.startBehavior(this);

		//}

	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);

		if (PROFESSION.equals(key) && this.world.isRemote) {

			//初期起動時にNullの時があるから
			if (this.professions != null) this.setProfession(getProfession());

		}

	}

	/* ================================
	 *   NBT
	 * ================================ */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		if (this.professions != null) compound.setString("Profession", getProfession().getRegistryName().toString());
		if (this.professions != null && getMode() != null) compound.setString("Mode", getMode().getRegistryName().toString());
		if (this.professions != null && getBehavior() != null) compound.setString("Behavior", getBehavior().getRegistryName().toString());

		if (this.professions != null) compound.setTag("LMSProfessions", this.professions.serializeNBT());

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (this.professions != null && compound.hasKey("Profession")) {

			ResourceLocation profession = new ResourceLocation(compound.getString("Profession"));
			ResourceLocation mode = compound.hasKey("Mode") ? new ResourceLocation(compound.getString("Mode")) : null;
			ResourceLocation behavior = compound.hasKey("Behavior") ? new ResourceLocation(compound.getString("Behavior")) : null;

			this.loadProfession(
					profession,
					mode,
					behavior);
		} else {
			this.changeProfession(this.getProfession(ProfessionEventHandler.keyChores));
		}

		if (this.professions != null && compound.hasKey("LMSProfessions")) {
			this.professions.deserializeNBT(compound.getCompoundTag("LMSProfessions"));
		}

	}

	@Override
	public EntityLiving getEntityInstance() {
		return this;
	}

}
