package littleservantmod;

import java.util.List;

import com.google.common.collect.Lists;

import littleservantmod.api.ILSMRegister;
import littleservantmod.api.ISugarItemHandler;
import net.minecraft.item.ItemStack;

public class LSMRegister implements ILSMRegister {

    private List<ISugarItemHandler> list = Lists.newArrayList();

    @Override
    public void resistSugarItemHandler(ISugarItemHandler handler) {
        list.add(handler);
    }

    @Override
    public List<ISugarItemHandler> getISugarItemHandlerList() {
        return list;
    }

    public boolean isSugar(ItemStack item) {

        return list
                .stream()
                .filter(isih -> isih.canUse(item))
                .findAny()
                .isPresent();

    }

}
