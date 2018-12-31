package littleservantmod;

import com.google.common.base.Objects;

import littleservantmod.api.ISugarItemHandler;
import littleservantmod.api.LittleServantModAPI;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * サーヴァントの体力回復アイテムを登録する
 *
 * @author Shift02
 *
 */
public class LSMSugarItems {

    public static void registrySugarItem() {

        //砂糖
        LittleServantModAPI.register.resistSugarItemHandler(new ISugarItemHandler() {

            @Override
            public float useItem(ItemStack item) {
                item.splitStack(1);
                return 0.5F;
            }

            @Override
            public boolean canUse(ItemStack item) {
                return Objects.equal(item.getItem(), Items.SUGAR);
            }
        });

    }

}
