package net.gordyjack.jaavaa.block.util;

import net.minecraft.world.level.block.state.properties.*;

public final class JAAVAABlockProperties {
    public static final BooleanProperty POS_1_ON = BooleanProperty.create("pos_1");
    public static final BooleanProperty POS_2_ON = BooleanProperty.create("pos_2");
    public static final BooleanProperty POS_3_ON = BooleanProperty.create("pos_3");
    public static final BooleanProperty POS_4_ON = BooleanProperty.create("pos_4");
    public static final BooleanProperty POS_5_ON = BooleanProperty.create("pos_5");
    public static final BooleanProperty POS_6_ON = BooleanProperty.create("pos_6");
    public static final BooleanProperty POS_7_ON = BooleanProperty.create("pos_7");
    public static final BooleanProperty POS_8_ON = BooleanProperty.create("pos_8");
    public static final EnumProperty<BeamCapType> BEAM_CAP_TYPE = EnumProperty.create("beam_cap_type", BeamCapType.class);
}
