package moonfather.woodentoolsremoved;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper;

import java.util.Optional;

// because we need options before forge initializes its config system.

public class OptionsWrapper
{
    public static boolean IsResolvedModeSimple()
    {
        initialize();
        Optional<Object> op1 = wrapper.getConfigElement("Obtaining stone", "Hard mode enabled");
        Optional<Object> op2 = wrapper.getConfigElement("Obtaining stone", "Simple mode enabled");
        if (op1.isEmpty() || op2.isEmpty())
        {
            return false;
        }
        return (boolean) op2.get() && ! (boolean) op1.get();
    }

    public static boolean IsResolvedModeHard()
    {
        initialize();
        return (boolean) wrapper.getConfigElement("Obtaining stone", "Hard mode enabled").orElse(true);
    }

    private static void initialize()
    {
        if (wrapper != null)
        {
            return;
        }
        FileConfig fc = FileConfig.of("config/woodentoolsremoved-common.toml");
        fc.load();
        fc.close();
        wrapper = new NightConfigWrapper(fc);
    }
    private static NightConfigWrapper wrapper = null;
}
