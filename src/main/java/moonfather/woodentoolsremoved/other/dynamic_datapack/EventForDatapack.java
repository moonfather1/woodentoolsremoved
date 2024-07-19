package moonfather.woodentoolsremoved.other.dynamic_datapack;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import moonfather.woodentoolsremoved.OptionsWrapper;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;

public class EventForDatapack
{
    public static void addServerPack(AddPackFindersEvent event)
    {
        if (! OptionsWrapper.IsResolvedModeHard())
        {
            return;
        }
        if (event.getPackType() == PackType.SERVER_DATA)
        {
            event.addRepositorySource((packConsumer) ->
            {
                @SuppressWarnings("resource")
                PackResources pack = new OurServerPack();
                packConsumer.accept(Pack.create(
                        Constants.MODID + "_server",
                        Component.literal("Datapack adjusting modded tools"),
                        true,
                        (s) -> pack,
                        new Pack.Info(
                                Component.literal(pack.packId()),
                                SharedConstants.DATA_PACK_FORMAT,
                                SharedConstants.RESOURCE_PACK_FORMAT,
                                FeatureFlagSet.of(),
                                true
                        ),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        true,
                        PackSource.DEFAULT
                ));
            });
        }
    }
}