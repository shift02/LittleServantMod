package littleservantmod.entity;

import java.util.Arrays;

/**
 * サーヴァントの見た目を判定するクラス
 */
public enum ServantSkin {

    SKIN_STEVE("steve"), SKIN_ALEX("alex");

    private String name;

    ServantSkin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ServantSkin from(String name) {

        return Arrays.stream(values()).filter(v -> v.name.equals(name)).findFirst().orElse(ServantSkin.SKIN_STEVE);

    }

}
