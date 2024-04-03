import json
from dataclasses import dataclass, field
import os

def getFormatCode(name: str) -> str:
    return f"$({name})"

def getId(name: str) -> str:
    return f"jaavaa:{name}"   

def getImageId(name: str) -> str:
    return getId(f"textures/patchouli/{name}.png")

def getPatchouliId(name: str) -> str:
    return f"patchouli:{name}"

def getLink(linkpath: str) -> str:
    return f"$(l:{linkpath})"

def getTooltip(tooltipText: str) -> str:
    return f"$(t:{tooltipText})"

def write_category (category):
    data = category.to_dict()["category"]
    path = f"{output_path}/categories/{category.key}"
    write_json(data, path)
    
def write_entry (entry):
    data = entry.to_dict()["entry"]
    folder = entry.category.replace("jaavaa:", "")
    path = f"{output_path}/entries/{folder}/{entry.key}"
    write_json(data, path)

def write_json(data: dict, path: str):
    path += ".json"
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as file:
        json.dump(data, file, indent=4)

@dataclass
class BookEntry:
    key: str
    category: str
    name: str = None
    icon: str = None
    pages: list[dict] = field(default_factory=list)
    
    def __post_init__(self):
        if self.name == None:
            self.name = ' '.join(word.capitalize() for word in self.key.replace('_', ' ').split())
        if self.icon == None:
            self.icon = getId(self.key)
            
    def to_dict(self) -> dict:
        return {
            "key": self.key,
            "entry": {
                "name": self.name,
                "icon": self.icon,
                "category": self.category,
                "pages": self.pages
            }
        }

@dataclass
class BookCategory:
    key: str
    description: str
    icon: str = None
    name: str = None
    id: str = None
    entries: list[BookEntry] = field(default_factory=list)

    def __post_init__(self):
        if self.name == None:
            self.name = ' '.join(word.capitalize() for word in self.key.replace('_', ' ').split())
        if self.icon == None:
            self.icon = getId(self.key)
        if self.id == None:
            self.id = getId(self.key)
    
    def to_dict(self) -> dict:
        return {
            "key": self.key,
            "category": {
                "name": self.name,
                "icon": self.icon,
                "description": self.description
            },
            "entries": self.entries
        }

output_path = "src/main/generated/assets/jaavaa/patchouli_books/jaavaa_guide/en_us"

#keys
images = "images"
item = "item"
recipe = "recipe"
recipe2 = "recipe2"
text = "text"
title = "title"
type = "type"

#page types
type_crafting = getPatchouliId("crafting")
type_image = getPatchouliId("image")
type_spotlight = getPatchouliId("spotlight")
type_text = getPatchouliId("text")

#format codes
br = getFormatCode("br")
br2 = getFormatCode("br2")
clear = getFormatCode("clear")
end_link = getFormatCode("/l")
end_tooltip = getFormatCode("/t")
italic = getFormatCode("italic")
list = getFormatCode("li")
thing = getFormatCode("thing")

#Categories
creative_only = BookCategory(
    "creative_only", 
    "Blocks and Items only accessible via creative. Or cheating.",
    getId("creative_coal")
)
functional_blocks = BookCategory(
    "functional_blocks",
    "Functional blocks, like fancy hoppers and other redstone components.",
    getId("personal_allay_collector")
)
materials = BookCategory(
    "materials",
    "New materials for crafting.",
    getId("allay_essence")
)
vanilla_style_blocks = BookCategory(
    "vanilla_style_blocks",
    "Blocks that are missing from the vanilla game.",
    getId("glass_slab")
)

#Entries
creative_only.entries = [
    BookEntry("creative_coal", creative_only.id, pages=[
        {
            type: type_spotlight,
            item: {
                item: getId("creative_coal")
            },
            text: [
                "It's a piece of coal that lasts forever.",
                f"{br2} It accomplishes this by setting burn time to {thing}MAX_INT{clear}, which somehow glitches out the furnace and makes it's burn time negative.",
                " So this might not work in other more robust blocks."
            ]
        }
    ])
]
functional_blocks.entries = [
    BookEntry("adjustable_redstone_lamp", functional_blocks.id, pages=[
        {
            type: type_text,
            text: [
                "The Adjustable Redstone Lamp is a very useful block. You can adjust the brightness of the lamp in two ways:",
                f"{list}Right clicking will increase the brightness of the lamp by 1.",
                f"{list}Providing a redstone signal will lock the lamp at a brightness equal to the signal strength."
            ]
        },
        {
            type: type_crafting,
            title: " ",
            recipe: getId("adjustable_redstone_lamp"),
            recipe2: getId("adjustable_redstone_lamp_alt")
        },
        {
            type: type_text,
            title: "More Details",
            text: [
                "The lamp will emit a comparator signal equal to it's brightness level.",
                " However, due to limitations in Minecraft, lamps of brightness 14 and 15 output the same amount of light.",
                " Redstone functionality is still retained appropriately though."
            ]
        },
        {
            type: type_text,
            text: [
                f"Speaking of Redstone functionality, {getTooltip("Like the Copper Bulb should have") + thing}the lamp updates every tick.{end_tooltip + clear}",
                " Which allows for much smoother animation and more precise timing for redstone contraptions."
            ]
        }
    ]),
    BookEntry("advanced_repeater", functional_blocks.id, pages=[
        {
            type: type_text,
            text: [
                "The Advanced Repeater functions similarly to a regular repeater except the pulse and delay are decoupled and can be adjusted independently.",
                f"{list}Right clicking the output side will cycle the pulse",
                f"{list}Right clicking the input side will cycle the delay"
            ]
        },
        {
            type: type_crafting,
            recipe: getId("advanced_repeater")
        }
    ]),
    BookEntry("collectors", functional_blocks.id, icon=getId("personal_allay_collector"), pages=[
        {
            type: type_text,
            text: [
                "The Allay Collector is a block that uses the power of the Allay to collect items in an area around it.",
                " It has a range of 8 blocks in all directions, and will collect items within that range.",
                " When in the inventory it can also be used as a magnet. To obtain it, first you need to craft an Empty Personal Collector."
            ]
        },
        {
            type: type_crafting,
            title: "Empty Collector",
            recipe: getId("empty_personal_collector"),
            text: [
                "Due to it's Netherite/Obsidian construction, the Collector is quite durable, even in lava.",
                f"You'll also require an {getLink(f"{materials.key}/allay_essence")}Allay Essence{clear}."
            ]
        },
        {
            type: type_text,
            title: "Capturing an Allay",
            text: [
                "Once you have the Empty Personal Collector, you need to capture an Allay.",
                f"{br} To do this, you need to find an Allay and use the Empty Personal Collector on it. This will capture the Allay into your Personal Allay Collector."
            ]
        },
        {
            type: type_image,
            images: [
                getImageId("allay_capture")
            ]
        },
        {
            type: type_text,
            title: "Using the Collector",
            text: [
                "Once you have captured an Allay, the Collector will then be able to be used as a magnet. Simply use it while sneaking to activate it. This effect has a range of 8 blocks.",
                f"{br} You are also able to set the Collector down in the world where it will have an immediate effect on nearby items, sucking them in and depositing them into the inventory it is facing."
            ]
        },
        {
            type: type_image,
            images: [
                getImageId("allay_collector_range")
            ]
        },
        {
            type: type_text,
            title: "Filtering Items",
            text: [
                "When placed in the world, the Collector can be filtered to only collect certain items.",
                f"{br} To do this, simply use an item on the Collector. It will then only collect that type of item. Interact with it again to clear the filter.",
                f"{br} A Collector will also output a comparator signal of 15 if it has a filter."
            ]
        },
        {
            type: type_image,
            images: [
                getImageId("allay_collector_filtered")
            ]
        },
        {
            type: type_text,
            title: "An Odd Distortion",
            text: [
                "It turns out the Collector can capture more than just Allays.",
                " Apparently, if you collect an Enderman with it, it will morph the frame of the Collector into a substance resembling the Dragon Egg.",
                " It also gains a purplish tint.",
                f"{br} Besides these cosmetic changes the Collector functions differently.",
                " It has twice the effective range and teleports items instead of attracting them, making it a lot faster than the original."
            ]
        },
        {
            type: type_spotlight,
            title: "Ender Collector",
            item: {
                item: getId("personal_ender_collector"),
            },
            text: f"{italic}It's a... different aesthetic."
        }
    ]),
    BookEntry("encased_redstone_pillars", functional_blocks.id, icon=getId("quartz_encased_redstone_pillar"), pages=[
        {
            type: type_text,
            text: [
                "The Encased Redstone Pillar is a block that acts exactly as a Redstone Block, but only along its axis.",
                " This means that it will only power blocks that are directly touching the ends of it, and not to the sides.",
                " This can be useful for creating more compact redstone contraptions, or for creating more complex redstone circuits.",
                f"{br}It comes in two variants:",
                f"{list}Quartz: For standard use cases. It's pretty.",
                f"{list}Ancient Debris: For when you need a little more durability. It's not so pretty."
            ]
        },
        {
            type: type_crafting,
            title: " ",
            recipe: getId("quartz_encased_redstone_pillar"),
            recipe2: getId("ancient_debris_encased_redstone_pillar")
        }
    ])
]
materials.entries = [
    BookEntry("allay_essence", materials.id, pages=[
        {
            type: type_spotlight,
            item: {
                item: getId("allay_essence")
            },
            text: [
                "Allay Essence is a relatively uncommon drop from the Allay. But you can get it faster with looting.",
                f"{br}It is used to craft the {getLink(f"{functional_blocks.key}/collectors")}Empty Personal Collector{clear}."
            ]
        },
        {
            type: type_crafting,
            recipe: getId("empty_personal_collector")
        }
    ]),
    BookEntry("starsteel", materials.id, icon=getId("starsteel_ingot"), pages=[
        {
            type: type_spotlight,
            title: "Starsteel",
            item: {
                item: getId("starsteel_ingot")
            },
            text: "Starsteel is a rare and powerful material that can be used to craft powerful tools and armor."
        },
        {
            type: type_crafting,
            recipe: getId("starsteel_ingot"),
            text: "It is created by infusing Netherite Ingots with a Nether Star."
        },
        {
            type: type_crafting,
            recipe: getId("starsteel_block_from_starsteel_ingot"),
            text: "When crafted into a block, this Starsteel is nearly indestructible. It is both Wither and Dragon proof and no mobs will spawn on top of it."
        }
    ])
]
vanilla_style_blocks.entries = [
    
]

categories = [creative_only, functional_blocks, materials, vanilla_style_blocks]

for category in categories:
    write_category(category)
    for entry in category.entries:
        write_entry(entry)
