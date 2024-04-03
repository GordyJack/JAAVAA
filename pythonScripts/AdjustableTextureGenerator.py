from PIL import Image

# Load the initial tile
initial_image_path = r"src/main/resources/assets/jaavaa/textures/block/adjustable_redstone_lamp.png"
initial_image = Image.open(initial_image_path)

# Get the size of the initial tile
tile_count = 16
image_width, image_height = initial_image.size
tile_height = image_height // tile_count

# Create a new image with the same size as the initial tile
new_image = Image.new("RGBA", (image_width, image_height))
new_image.paste(initial_image)

# Crop the initial tile to get unlit and fully lit tiles
unlit_tile = new_image.crop((0, 0, image_width, tile_height))
full_lit_tile = new_image.crop((0, image_height - tile_height, image_width, image_height))

# Generate light levels
light_levels = [Image.blend(unlit_tile, full_lit_tile, i / 15.0) for i in range(1, 15)]

# Paste unlit and fully lit tiles to the new image
new_image.paste(unlit_tile)
new_image.paste(full_lit_tile, (0, image_height - tile_height))

# Add light levels to new_image at appropriate heights
for i, level in enumerate(light_levels):
    new_image.paste(level, (0, tile_height * (i + 1)))

# Save the new image
new_image.save(initial_image_path)
