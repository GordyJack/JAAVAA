import json
from pathlib import Path

def generate_file_name(block_name: str):
    return f"{block_name}_mini_block"

def generate_model_name(block_name: str, base_position: int):
    return f"jamba:block/{generate_file_name(block_name)}_{base_position:08b}"

def rotation(x: int, y: int, uvlock=True) -> dict:
    valid_values = {0, 90, 180, 270}
    rotation_dict = {}
    
    if x in valid_values and x != 0:
        rotation_dict["x"] = x
    if y in valid_values and y != 0:
        rotation_dict["y"] = y
    rotation_dict["uvlock"] = uvlock
    
    return rotation_dict

def write_json(data: dict, file_name: str):
    # Ensure the output directory exists
    script_path = Path(__file__).parent
    project_root = script_path.parents[5]
    output_dir_path = Path(f'{project_root}/generated/assets/jamba/blockstates/')
    output_dir_path.mkdir(parents=True, exist_ok=True)
    
    # Write the composite model to a new file
    output_file_path = output_dir_path / f"{file_name}.json"
    with open(output_file_path, 'w') as file:
        json.dump(data, file, indent=4)

def generate_blockstate(block_name: str):
    single = generate_model_name(block_name, 0b00000001)
    straight = generate_model_name(block_name, 0b00000101)
    diagonal = generate_model_name(block_name, 0b00000110)
    
    data = {
        "variants": {
            #Single Models
            f"position={0b00000001}": {
                "model": single
            },
            f"position={0b00000010}": {
                "model": single,
                **rotation(0, 90)
            },
            f"position={0b00000100}": {
                "model": single,
                **rotation(0, 270)
            },
            f"position={0b00001000}": {
                "model": single,
                **rotation(0, 180)
            },
            f"position={0b00010000}": {
                "model": single,
                **rotation(180, 90)
            },
            f"position={0b00100000}": {
                "model": single,
                **rotation(180, 180)
            },
            f"position={0b01000000}": {
                "model": single,
                **rotation(180, 0)
            },
            f"position={0b10000000}": {
                "model": single,
                **rotation(180, 270)
            },
            #Straight Models
            f"position={0b00000011}": {
                "model": straight,
                **rotation(0, 90)
            },
            f"position={0b00000101}": {
                "model": straight
            },
            f"position={0b00001010}": {
                "model": straight,
                **rotation(0, 180)
            },
            f"position={0b00001100}": {
                "model": straight,
                **rotation(0, 270)
            },
            f"position={0b00010001}": {
                "model": straight,
                **rotation(90, 90)
            },
            f"position={0b00100010}": {
                "model": straight,
                **rotation(90, 180)
            },
            f"position={0b00110000}": {
                "model": straight,
                **rotation(180, 90)
            },
            f"position={0b01000100}": {
                "model": straight,
                **rotation(90, 0)
            },
            f"position={0b01010000}": {
                "model": straight,
                **rotation(180, 0)
            },
            f"position={0b10001000}": {
                "model": straight,
                **rotation(90, 270)
            },
            f"position={0b10100000}": {
                "model": straight,
                **rotation(180, 180)
            },
            f"position={0b11000000}": {
                "model": straight,
                **rotation(180, 270)
            },
            #Diagonal Models
            f"position={0b00000110}": {
                "model": diagonal
            },
            f"position={0b00001001}": {
                "model": diagonal,
                **rotation(0, 90)
            },
            f"position={0b00010010}": {
                "model": diagonal,
                **rotation(270, 0)
            },
            f"position={0b00010100}": {
                "model": diagonal,
                **rotation(270, 270)
            },
        }
    }
    write_json(data, generate_file_name(block_name))
    
def main():
    generate_blockstate("jamba_test")
    
if __name__ == "__main__":
    main()