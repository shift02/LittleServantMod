package littleservantmod.api.profession;

import littleservantmod.api.IServant;
import littleservantmod.entity.ai.EntityAITempt;
import littleservantmod.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * サーヴァントの職業クラス
 * @author shift02
 */
public abstract class ProfessionBase implements IProfession {

	protected double defaultSpeed = 0.5D;

	protected ResourceLocation resourceLocation;
	protected String unlocalizedName;

	@SideOnly(Side.CLIENT)
	protected TextureAtlasSprite icon;

	public void init() {
	}

	@Override
	public void initAI(IServant servant) {

		this.initDefaultAI(servant);

	}

	protected void initDefaultAI(IServant servant) {

		//泳ぐ
		servant.addAI(100, new EntityAISwimming(servant.getEntityInstance()));

		//砂糖についてくる
		servant.addAI(300, new EntityAITempt(servant.getEntityInstance(), defaultSpeed, Items.SUGAR, false));

		//ウロウロ
		servant.addAI(800, new EntityAIWanderAvoidWater(servant.getEntityInstance(), defaultSpeed));

		//プレイヤーを見る
		servant.addAI(900, new EntityAIWatchClosest(servant.getEntityInstance(), EntityPlayer.class, 8.0F));
		// 見回すAIの追加
		servant.addAI(900, new EntityAILookIdle(servant.getEntityInstance()));

	}

	@SideOnly(Side.CLIENT)
	public ProfessionBase setIcon(TextureAtlasSprite icon) {
		this.icon = icon;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(IServant servant) {

		if (icon == null) {
			//Missing
			return net.minecraft.client.Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		}

		return icon;
	}

	public ProfessionBase setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
		return this;
	}

	public String getUnlocalizedName() {
		return "profession." + this.unlocalizedName;
	}

	public String getUnlocalizedName(IServant servant) {
		return "profession." + this.unlocalizedName;
	}

	@Override
	public String getProfessionDisplayName(IServant servant) {
		return I18n.translateToLocal(this.getUnlocalizedName(servant) + ".name").trim();
	}

	@Override
	public ResourceLocation getRegistryName() {
		return this.resourceLocation;
	}

	public ProfessionBase setRegistryName(ResourceLocation resourceLocation) {
		this.resourceLocation = resourceLocation;
		return this;
	}

}
