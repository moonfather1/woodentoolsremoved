package moonfather.woodentoolsremoved.other.dynamic_datapack;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;

public class EventForDatapack
{
    public static void addServerPack(AddPackFindersEvent event)
    {
        if (! OptionsHolder.IsResolvedModeHard())
        {
            return;
        }
        if (event.getPackType() == PackType.SERVER_DATA)
        {
            event.addRepositorySource((packConsumer, packConstructor) ->
            {
                @SuppressWarnings("resource")
                PackResources pack = new OurServerPack();
                packConsumer.accept(Pack.create(
                        Constants.MODID + "_server",
                        true,
                        () -> pack,
                        packConstructor,
                        Pack.Position.TOP,
                        PackSource.DEFAULT
                ));
            });
        }
    }
}
