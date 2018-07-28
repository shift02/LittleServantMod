package littleservantmod.entity;

import javax.annotation.Nullable;

import littleservantmod.LittleServantMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

/**
 * サーヴァントの見た目を管理する
 * */
public class EntityLittleServantSkin extends EntityLittleServantProfession {

	private static final ResourceLocation MAID_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_maid/mob_littlemaid.png");
	private static final ResourceLocation TAMED_MAID_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_maid/mob_littlemaid_tamed.png");

	private static final ResourceLocation BUTLER_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_butler/mob_little_butler.png");
	private static final ResourceLocation TAMED_BUTLER_TEXTURES = new ResourceLocation(LittleServantMod.MOD_ID, "textures/entitys/little_butler/mob_little_butler_tamed.png");

	protected static final DataParameter<String> SKIN = EntityDataManager.<String> createKey(EntityLittleServantBase.class, DataSerializers.STRING);

	public static final String SKIN_STEVE = "steve";
	public static final String SKIN_ALEX = "alex";

	public EntityLittleServantSkin(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SKIN, SKIN_STEVE);

	}

	public void setSkin(String skin) {
		this.dataManager.set(SKIN, skin);
	}

	public String getSkin() {
		return this.dataManager.get(SKIN);
	}

	public ResourceLocation getEntityTexture(EntityLittleServant entity) {

		if (this.getSkin() == SKIN_STEVE) {

			if (entity.isTamed()) {
				return TAMED_MAID_TEXTURES;
			}

			return MAID_TEXTURES;

		} else {

			if (entity.isTamed()) {
				return TAMED_BUTLER_TEXTURES;
			}

			return BUTLER_TEXTURES;

		}

	}

	/**
	 * スポーン時に見た目の決定をする
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	 * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		//this.setFleeceColor(getRandomSheepColor(this.world.rand));

		this.setSkin(rand.nextBoolean() ? SKIN_STEVE : SKIN_ALEX);

		return livingdata;
	}

	/* ================================
	 *   NBT
	 * ================================ */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setString("SkinType", getSkin());

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (compound.hasKey("SkinType")) {
			this.setSkin(compound.getString("SkinType"));
		}

	}

}
