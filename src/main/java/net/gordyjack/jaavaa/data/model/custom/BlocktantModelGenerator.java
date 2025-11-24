package net.gordyjack.jaavaa.data.model.custom;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.custom.*;
import net.gordyjack.jaavaa.block.util.*;
import net.gordyjack.jaavll.util.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.state.properties.*;

import java.util.*;

public class BlocktantModelGenerator extends JAAVAAModelGenerator<Blocktant> {
    public BlocktantModelGenerator(BlockModelGenerators bmGen) {
        super(bmGen);
    }

    public void generate(Blocktant blocktant, ResourceLocation side, ResourceLocation down, ResourceLocation up) {
        TextureMapping tMap = TextureMapping.cube(side);
        tMap.put(TextureSlot.DOWN, down);
        tMap.put(TextureSlot.UP, up);

        ModelTemplate[] posTemplates = new ModelTemplate[8];
        for (int i = 0; i < 8; i++) {
            posTemplates[i] = createBlocktantModelTemplate("block/blocktant_template_" + i);
        }

        MultiVariant[] mv = new MultiVariant[8];
        for (int i = 0; i < 8; i++) {
            mv[i] = plainVariant(posTemplates[i].create(
                    JAAVAA.id("block/" + IDUtil.nameFromId(blocktant) + "_" + i),
                    tMap,
                    this.modelOutput
            ));
        }

        MultiPartGenerator mpGen = MultiPartGenerator.multiPart(blocktant);

        addFacingParts(mpGen, JAAVAABlockProperties.POS_1_ON, mv[0]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_2_ON, mv[1]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_3_ON, mv[2]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_4_ON, mv[3]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_5_ON, mv[4]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_6_ON, mv[5]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_7_ON, mv[6]);
        addFacingParts(mpGen, JAAVAABlockProperties.POS_8_ON, mv[7]);

        this.blockStateOutput.accept(mpGen);
        this.registerSimpleItemModel(blocktant, ModelLocationUtils.getModelLocation(blocktant, "_0"));
    }
    private ModelTemplate createBlocktantModelTemplate(String name) {
        return new ModelTemplate(Optional.of(JAAVAA.id(name)), Optional.empty(), TextureSlot.SIDE, TextureSlot.DOWN, TextureSlot.UP);
    }
    private void addFacingParts(MultiPartGenerator mpGen,
                                BooleanProperty posProperty,
                                MultiVariant mVar) {
        mpGen.with(
                new ConditionBuilder()
                        .term(posProperty, true)
                        .term(Blocktant.FACING, Direction.DOWN),
                mVar.with(NOP)
        );
        mpGen.with(
                new ConditionBuilder()
                        .term(posProperty, true)
                        .term(Blocktant.FACING, Direction.UP),
                mVar.with(X_ROT_180)
        );
        mpGen.with(
                new ConditionBuilder()
                        .term(posProperty, true)
                        .term(Blocktant.FACING, Direction.NORTH),
                mVar.with(X_ROT_270)
        );
        mpGen.with(
                new ConditionBuilder()
                        .term(posProperty, true)
                        .term(Blocktant.FACING, Direction.SOUTH),
                mVar.with(X_ROT_270).with(Y_ROT_180)
        );
        mpGen.with(
                new ConditionBuilder()
                        .term(posProperty, true)
                        .term(Blocktant.FACING, Direction.WEST),
                mVar.with(X_ROT_270).with(Y_ROT_270)
        );
        mpGen.with(
                new ConditionBuilder()
                        .term(posProperty, true)
                        .term(Blocktant.FACING, Direction.EAST),
                mVar.with(X_ROT_270).with(X_ROT_90)
        );
    }
}
