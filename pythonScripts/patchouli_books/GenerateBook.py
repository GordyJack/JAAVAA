import json
import os
from BookEntry import  BookEntry
from BookCategory import BookCategory

def get_format_code(name: str) -> str:
    return f"$({name})"

def get_id(name: str) -> str:
    return f"jaavaa:{name}"   

def get_image_id(name: str) -> str:
    return get_id(f"textures/patchouli/{name}.png")

def get_patchouli_id(name: str) -> str:
    return f"patchouli:{name}"

def get_link(linkpath: str) -> str:
    return f"$(l:{linkpath})"

def get_tooltip(tooltip_text: str) -> str:
    return f"$(t:{tooltip_text})"

def write_category (category: BookCategory):
    data = category.to_dict()["category"]
    path = f"{output_path}/categories/{category.key}"
    write_json(data, path)
    
def write_entry (entry: BookEntry):
    data = entry.to_dict()["entry"]
    folder = entry.category.replace("jaavaa:", "")
    path = f"{output_path}/entries/{folder}/{entry.key}"
    write_json(data, path)

def write_json(data: dict, path: str):
    path += ".json"
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as file:
        json.dump(data, file, indent=4)
        print(f"Written to {file}")
    with open(path, "r") as file:
        print(file.read())
        print(file)
        

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
type_crafting = get_patchouli_id("crafting")
type_image = get_patchouli_id("image")
type_spotlight = get_patchouli_id("spotlight")
type_text = get_patchouli_id("text")

#format codes
br = get_format_code("br")
br2 = get_format_code("br2")
clear = get_format_code("clear")
end_link = get_format_code("/l")
end_tooltip = get_format_code("/t")
italic = get_format_code("italic")
list = get_format_code("li")
thing = get_format_code("thing")

#Categories
creative_only = BookCategory(
    "creative_only", 
    "Blocks and Items only accessible via creative. Or cheating.",
    get_id("creative_coal")
)
functional_blocks = BookCategory(
    "functional_blocks",
    "Functional blocks, like fancy hoppers and other redstone components.",
    get_id("personal_allay_collector")
)
materials = BookCategory(
    "materials",
    "New materials for crafting.",
    get_id("allay_essence")
)
vanilla_style_blocks = BookCategory(
    "vanilla_style_blocks",
    "Blocks that are missing from the vanilla game.",
    get_id("glass_slab")
)

#Entries
creative_only.entries = [
    BookEntry(cc := "creative_coal", creative_only.id, pages=[
        {
            type: type_spotlight,
            item: {
                item: get_id(cc)
            },
            text: [
                "It's a piece of coal that lasts forever.",
                f"{br2} It accomplishes this by setting burn time to {thing}MAX_INT{clear}, which somehow glitches out the furnace and makes it's burn time negative.",
                " So this might not work in other more robust furnace blocks."
            ]
        }
    ])
]

functional_blocks.entries = [
    BookEntry(arl := "adjustable_redstone_lamp", functional_blocks.id, pages=[
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
            recipe: get_id(arl),
            recipe2: get_id(f"{arl}_alt")
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
                f"Speaking of Redstone functionality, {get_tooltip("Like the Copper Bulb should have") + thing}the lamp updates every tick.{end_tooltip + clear}",
                " Which allows for much smoother animation and more precise timing for redstone contraptions."
            ]
        }
    ]),
    BookEntry(ar := "advanced_repeater", functional_blocks.id, pages=[
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
            recipe: get_id(ar)
        }
    ]),
    BookEntry("collectors", functional_blocks.id, icon=get_id("personal_allay_collector"), pages=[
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
            recipe: (epc := get_id("empty_personal_collector")),
            text: [
                "Due to it's Netherite/Obsidian construction, the Collector is quite durable, even in lava.",
                f"You'll also require an {get_link(f"{materials.key}/allay_essence")}Allay Essence{clear}."
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
                get_image_id("allay_capture")
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
                get_image_id("allay_collector_range")
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
                get_image_id("allay_collector_filtered")
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
                item: get_id("personal_ender_collector"),
            },
            text: f"{italic}It's a... different aesthetic."
        }
    ]),
    BookEntry("encased_redstone_pillars", functional_blocks.id, icon=(qerp := get_id("quartz_encased_redstone_pillar")), pages=[
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
            recipe: qerp,
            recipe2: get_id("ancient_debris_encased_redstone_pillar")
        }
    ])
]

materials.entries = [
    BookEntry(ae := "allay_essence", materials.id, pages=[
        {
            type: type_spotlight,
            item: {
                item: get_id(ae)
            },
            text: [
                "Allay Essence is a relatively uncommon drop from the Allay. But you can get it faster with looting.",
                f"{br}It is used to craft the {get_link(f"{functional_blocks.key}/collectors")}Empty Personal Collector{clear}."
            ]
        },
        {
            type: type_crafting,
            recipe: epc
        }
    ]),
    BookEntry("starsteel", materials.id, icon=get_id("starsteel_ingot"), pages=[
        {
            type: type_spotlight,
            title: "Starsteel",
            item: {
                item: get_id("starsteel_ingot")
            },
            text: "Starsteel is a rare and powerful material that can be used to craft powerful tools and armor."
        },
        {
            type: type_crafting,
            recipe: get_id("starsteel_ingot"),
            text: "It is created by infusing Netherite Ingots with a Nether Star."
        },
        {
            type: type_crafting,
            recipe: get_id("starsteel_block_from_starsteel_ingot"),
            text: "When crafted into a block, this Starsteel is nearly indestructible. It is both Wither and Dragon proof and no mobs will spawn on top of it."
        }
    ])
]
vanilla_style_blocks.entries = [
    
]

if __name__ == "__main__":
    categories = [creative_only, functional_blocks, materials, vanilla_style_blocks]

    for category in categories:
        write_category(category)
        for entry in category.entries:
            write_entry(entry)
