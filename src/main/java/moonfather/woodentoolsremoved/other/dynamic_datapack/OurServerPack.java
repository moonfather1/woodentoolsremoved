package moonfather.woodentoolsremoved.other.dynamic_datapack;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
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
        this.packMetadata = new PackMetadataSection(new TextComponent("WTaD resources"), SharedConstants.DATA_PACK_FORMAT);
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
    "tconstruct:skull": {
      "durability": 100,
      "armor": 0
    },
    "tconstruct:extra": {},
    "tconstruct:handle": {
      "durability": 0.75,
      "miningSpeed": 1.0,
      "attackSpeed": 1.1,
      "attackDamage": 1.0
    },
    "tconstruct:head": {
      "durability": 60,
      "miningSpeed": 2.5,
      "harvestTier": "minecraft:wood",
      "attack": 1.25
    },
    "tconstruct:limb": null,
    "tconstruct:grip": {
      "durability": 0.75,
      "accuracy": 0.05,
      "meleeAttack": 1.25
    }
  }
}    """;
    private static final String tc_flint = """
{
  "stats": {
    "tconstruct:extra": {},
    "tconstruct:handle": null,
    "tconstruct:head": {
      "durability": 85,
      "miningSpeed": 3.0,
      "harvestTier": "minecraft:wood",
      "attack": 1.25
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
      "durability": 80.0,
      "armor_durability": 4.0,
      "enchantability": 5.0,
      "harvest_level": 0.0,
      "harvest_speed": 4.0,
      "melee_damage": 2.0,
      "magic_damage": 1.0,
      "attack_speed": 0.1,
      "armor": 5.0,
      "armor/helmet": 1.0,
      "armor/chestplate": 2.0,
      "armor/leggings": 1.0,
      "armor/boots": 1.0,
      "magic_armor": 1.0,
      "ranged_damage": 1.0,
      "ranged_speed": 0.0,
      "projectile_speed": 0.9,
      "projectile_accuracy": 1.0,
      "rarity": 8.0,
      "chargeability": 0.9
    },
    "rod": {
      "melee_damage": {
        "mul2": 0.2
      },
      "rarity": 8.0
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
      "durability": 70.0,
      "armor_durability": 4.0,
      "enchantability": 3.0,
      "harvest_level": 0.0,
      "harvest_speed": 5.0,
      "melee_damage": 2.0,
      "magic_damage": 0.0,
      "attack_speed": -0.1,
      "armor": 4.0,
      "armor/helmet": 0.5,
      "armor/chestplate": 2.0,
      "armor/leggings": 1.0,
      "armor/boots": 0.5,
      "ranged_damage": 1.0,
      "ranged_speed": -0.3,
      "projectile_speed": 1.0,
      "projectile_accuracy": 1.0,
      "rarity": 6.0,
      "chargeability": 0.8
    },
    "rod": {
      "durability": {
        "mul2": -0.6
      },
      "rarity": 2.0
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


    @Override
    public String getName() { return "Wooden tools - modifications for 3rd party tools"; }

    ///////////////////////////////////////////

    @Nullable
    @Override
    public InputStream getRootResource(String p_10294_) throws IOException
    {
        this.buildResources();
        return null;
    }

    @Override
    public InputStream getResource(PackType type, ResourceLocation location) throws IOException
    {
        if (type != PackType.SERVER_DATA) { return null; }
        if (! namespaces.contains(location.getNamespace())) { return null; }
        this.buildResources();
        if (this.dataCache.containsKey(location))
        {
            return new ByteArrayInputStream(this.dataCache.get(location).getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String path, int maxDepth, Predicate<String> givenFilter)
    {
        if (path.equals("dynamic_asset_generator")) { return EMPTY; }
        if (type != PackType.SERVER_DATA) { return EMPTY; }
        if (! namespaces.contains(namespace)) { return EMPTY; }
        this.buildResources();
        return this.dataCache.keySet()
                             .stream()
                             .filter(loc -> loc.getNamespace().equals(namespace))
                             .filter(loc -> loc.getPath().startsWith(path))
                             .filter(loc -> givenFilter.test(loc.getPath()))
                             .toList();
    }

    @Override
    public boolean hasResource(PackType packType, ResourceLocation resourceLocation)
    {
        if (packType != PackType.SERVER_DATA) { return false; }
        if (! namespaces.contains(resourceLocation.getNamespace())) { return false; }
        this.buildResources();
        return this.dataCache.containsKey(resourceLocation);
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException
    {
        if (deserializer == PackMetadataSection.SERIALIZER)
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
}
