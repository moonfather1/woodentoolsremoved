package moonfather.woodentoolsremoved.other.dynamic_datapack;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class OurServerPack implements PackResources
{
    private static final Collection<ResourceLocation> EMPTY = new ArrayList<>();

    private final PackMetadataSection packMetadata;
    private final Map<ResourceLocation, String> dataCache = new ConcurrentHashMap<>();
    private boolean initialized = false;

    public OurServerPack()
    {
        this.packMetadata = new PackMetadataSection(Component.literal(this.packId()), SharedConstants.DATA_PACK_FORMAT);
    }

    protected void buildResources()
    {
        if (this.initialized) { return; }
        this.dataCache.put(new ResourceLocation("tconstruct", "tinkering/materials/stats/bone.json"), tc_bone);
        this.dataCache.put(new ResourceLocation("tconstruct", "tinkering/materials/stats/flint.json"), tc_flint);
        this.dataCache.put(new ResourceLocation("silentgear", "silentgear_materials/bone.json"), sg_bone);
        this.dataCache.put(new ResourceLocation("silentgear", "silentgear_materials/flint.json"), sg_flint);
        this.initialized = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private static final String tc_bone = """
{
  "stats": {
    "tconstruct:binding": {},
    "tconstruct:grip": {
      "accuracy": 0.05,
      "durability": -0.25,
      "melee_damage": 1.25
    },
    "tconstruct:handle": {
      "durability": -0.25,
      "melee_damage": 0.0,
      "melee_speed": 0.1,
      "mining_speed": 0.0
    },
    "tconstruct:head": {
      "durability": 52,
      "melee_attack": 1.25,
      "mining_speed": 2.5,
      "mining_tier": "minecraft:wood"
    },
    "tconstruct:limb": {
      "accuracy": 0.05,
      "draw_speed": 0.05,
      "durability": 100,
      "velocity": -0.05
    },
    "tconstruct:skull": {
      "armor": 0,
      "durability": 100
    }
  }
}    """;
    private static final String tc_flint = """
{
  "stats": {
    "tconstruct:extra": {},
    "tconstruct:handle": null,
    "tconstruct:head": {
      "durability": 82,
      "melee_attack": 1.25,
      "mining_speed": 3.5,
      "mining_tier": "minecraft:wood"
    }
  }
}    """;
    private static final String sg_bone = """
{
  "type": "silentgear:standard",
  "simple": true,
  "availability": {
    "tier": 1,
    "categories": [
      "organic"
    ],
    "visible": true,
    "gear_blacklist": [],
    "can_salvage": true
  },
  "crafting_items": {
    "main": {
      "item": "minecraft:bone_block"
    },
    "subs": {
      "rod": {
        "item": "minecraft:bone"
      }
    }
  },
  "name": {
    "translate": "material.silentgear.bone"
  },
  "stats": {
    "main": {
      "durability": 92.0,
      "armor_durability": 4.0,
      "enchantment_value": 5.0,
      "rarity": 8.0,
      "charging_value": 0.9,
      "harvest_level": 0.0,
      "harvest_speed": 4.0,
      "melee_damage": 2.0,
      "magic_damage": 1.0,
      "attack_speed": 0.1,
      "ranged_damage": 1.0,
      "ranged_speed": 0.0,
      "projectile_speed": 0.9,
      "projectile_accuracy": 1.0,
      "armor": 5.0,
      "armor/helmet": 1.0,
      "armor/chestplate": 2.0,
      "armor/leggings": 1.0,
      "armor/boots": 1.0,
      "magic_armor": 1.0
    },
    "rod": {
      "melee_damage": {
        "mul2": 0.2
      }
    }
  },
  "traits": {
    "main": [
      {
        "name": "silentgear:chipping",
        "level": 2
      }
    ],
    "rod": [
      {
        "name": "silentgear:flexible",
        "level": 2
      }
    ]
  },
  "model": {
    "main/all": [
      {
        "texture": "silentgear:main_generic_lc",
        "color": "#FCFBED"
      }
    ],
    "main/fragment": [
      {
        "texture": "silentgear:metal",
        "color": "#FCFBED"
      }
    ],
    "rod/all": [
      {
        "texture": "silentgear:rod_generic_lc",
        "color": "#FCFBED"
      }
    ],
    "rod/part": [
      {
        "texture": "silentgear:rod",
        "color": "#FCFBED"
      }
    ]
  }
}    """;
    private static final String sg_flint = """
{
  "type": "silentgear:standard",
  "simple": true,
  "availability": {
    "tier": 1,
    "categories": [
      "rock"
    ],
    "visible": true,
    "gear_blacklist": [],
    "can_salvage": true
  },
  "crafting_items": {
    "main": {
      "item": "minecraft:flint"
    }
  },
  "name": {
    "translate": "material.silentgear.flint"
  },
  "stats": {
    "main": {
      "durability": 60.0,
      "armor_durability": 4.0,
      "enchantment_value": 3.0,
      "rarity": 6.0,
      "charging_value": 0.8,
      "harvest_level": 0.0,
      "harvest_speed": 5.0,
      "melee_damage": 2.0,
      "attack_speed": -0.1,
      "ranged_damage": 1.0,
      "ranged_speed": -0.3,
      "projectile_speed": 1.0,
      "projectile_accuracy": 1.0,
      "armor": 4.0,
      "armor/helmet": 0.5,
      "armor/chestplate": 2.0,
      "armor/leggings": 1.0,
      "armor/boots": 0.5
    },
    "rod": {
      "durability": {
        "mul2": -0.3
      }
    },
    "adornment": {}
  },
  "traits": {
    "main": [
      {
        "name": "silentgear:jagged",
        "level": 3
      }
    ],
    "rod": [
      {
        "name": "silentgear:brittle",
        "level": 3
      },
      {
        "name": "silentgear:jagged",
        "level": 2
      }
    ]
  },
  "model": {
    "main/all": [
      {
        "texture": "silentgear:main_generic_hc",
        "color": "#969696"
      }
    ],
    "main/fragment": [
      {
        "texture": "silentgear:metal",
        "color": "#969696"
      }
    ],
    "rod/all": [
      {
        "texture": "silentgear:rod_generic_hc",
        "color": "#969696"
      }
    ],
    "rod/part": [
      {
        "texture": "silentgear:rod",
        "color": "#969696"
      }
    ],
    "adornment/all": [
      {
        "texture": "silentgear:adornment_generic",
        "color": "#969696"
      }
    ],
    "adornment/part": [
      {
        "texture": "silentgear:adornment",
        "color": "#969696"
      },
      {
        "texture": "silentgear:adornment_highlight"
      }
    ]
  }
}    """;
    /////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Set<String> getNamespaces(PackType type)
    {
        if (type != PackType.SERVER_DATA) return Set.of();
        return namespaces;
    }
    private static final Set<String> namespaces = Set.of("tconstruct", "silentgear");

    ///////////////////////////////////////////

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... fileName)
    {
        this.buildResources();
        return null;
    }

    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location)
    {
        if (type != PackType.SERVER_DATA) { return null; }
        if (! namespaces.contains(location.getNamespace())) { return null; }
        this.buildResources();
        if (this.dataCache.containsKey(location))
        {
            return supplierForPath(location);
        }
        return null;
    }

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput output)
    {
        if (path.equals("dynamic_asset_generator")) { return; }
        if (type != PackType.SERVER_DATA) { return; }
        if (! namespaces.contains(namespace)) { return; }
        this.buildResources();
        this.dataCache.keySet()
                             .stream()
                             .filter(loc -> loc.getNamespace().equals(namespace))
                             .filter(loc -> loc.getPath().startsWith(path))
                             .forEach(loc -> output.accept(loc, supplierForPath(loc)));
    }



    private IoSupplier<InputStream> supplierForPath(ResourceLocation loc)
    {
        return () -> new ByteArrayInputStream(this.dataCache.get(loc).getBytes(StandardCharsets.UTF_8));
    }



    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException
    {
        if (deserializer == PackMetadataSection.TYPE)
        {
            return (T) this.packMetadata;
        }
        return null;
    }

    @Override
    public void close() { }

    @Override
    public boolean isHidden()
    {
        return true;
    }

    @Override
    public boolean isBuiltin()
    {
        return true;
    }

    @Override
    public String packId() { return "WTAD - auto-generated data files"; }
}