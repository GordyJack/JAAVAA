import os

# Define the directory path
directory = "C:\\Users\\GordyJackPC\\Documents\\Coding\\Minecraft Modding\\JAAVAA\\src\\main\\resources\\assets\\jaavaa\\models\\block"

# Define the old and new names
old_name = "adjustable_lamp"
new_name = "adjustable_redstone_lamp"

# Iterate through all files in the directory
for filename in os.listdir(directory):
    # Check if the file is a model file
    if filename.endswith(".json"):
        # Read the contents of the file
        with open(os.path.join(directory, filename), "r") as file:
            contents = file.read()
        
        # Replace the old name with the new name
        new_contents = contents.replace(old_name, new_name)
        
        # Write the updated contents back to the file
        with open(os.path.join(directory, filename), "w") as file:
            file.write(new_contents)