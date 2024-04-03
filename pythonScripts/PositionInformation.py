faces = 6
vertices = 8
edges = 12
    
single_positions = {
    "count": vertices,
    "single": {
        "count": vertices,
        "chiral": False,
        "positions": (
            0b00000001, 
            0b00000010,
            0b00000100,
            0b00001000,
            0b00010000,
            0b00100000,
            0b01000000,
            0b10000000
        )
    }
}
two_positions = {
    "count": 28,
    "straight": {
        "count": edges,
        "chiral": False,
        "positions": (
            0b00000011,
            0b00001100,
            0b00000101,
            0b00001010,
            0b00010001,
            0b00100010,
            0b01000100,
            0b10001000,
            0b00110000,
            0b01010000,
            0b10100000,
            0b11000000
        )
    },
    "diagonal": {
        "count": faces * 2,
        "chiral": False,
        "positions": (
            0b00000110,
            0b00001001,
            0b00010010,
            0b00010100,
            0b00100001,
            0b00101000,
            0b01000001,
            0b01001000,
            0b01100000,
            0b10000010,
            0b10000100,
            0b10010000
        )
    },
    "opposite": {
        "count": 4,
        "chiral": False,
        "positions": (
            0b00011000,
            0b00100100,
            0b01000010,
            0b10000001
        )
    }
}
three_positions = {
    "count": 56,
    "corner": {
        "count": faces * 4,
        "chiral": False,
        "positions": (
            0b00000111,
            0b00001110,
            0b00001011,
            0b00001101,
            0b00010011,
            0b00010101,
            0b00100011,
            0b00101010,
            0b00110001,
            0b00110010,
            0b01000101,
            0b01001100,
            0b01010001,
            0b01010100,
            0b01110000,
            0b10001010,
            0b10001100,
            0b10100010,
            0b10101000,
            0b10110000,
            0b11000100,
            0b11001000,
            0b11010000,
            0b11100000
        )
    },
    "straight_plus_single": {
        "count": two_positions["straight"]["count"] * 2,
        "chiral": False,
        "positions": (
            0b00011001,
            0b00011010,
            0b00011100,
            0b00100101,
            0b00100110,
            0b00101100,
            0b00110100,
            0b00111000,
            0b01000011,
            0b01000110,
            0b01001010,
            0b01010010,
            0b01011000,
            0b01100010,
            0b01100100,
            0b10000011,
            0b10000101,
            0b10001001,
            0b10010001,
            0b10011000,
            0b10100001,
            0b10100100,
            0b11000001,
            0b11000010
        )
    },
    "diagonal_plus_single": {
        "count": vertices,
        "chiral": False,
        "positions": (
            0b00010110,
            0b00101001,
            0b01001001,
            0b01100001,
            0b01101000,
            0b10000110,
            0b10010010,
            0b10010100
        )
    }
}
four_positions = {
    "count": 70,
    "slab": {
        "count": faces,
        "chiral": False,
        "positions": (
            0b00001111,
            0b00110011,
            0b01010101,
            0b10101010,
            0b11001100,
            0b11110000
        )
    },
    "full_corner": {
        "count": vertices,
        "chiral": False,
        "positions": (
            0b00010111,
            0b00101011,
            0b01001101,
            0b01110001,
            0b10001110,
            0b10110010,
            0b11010100,
            0b11101000
        )
    },
    "corner_plus_single": {
        "count": three_positions["corner"]["count"],
        "chiral": False,
        "positions": (
            0b00011110,
            0b00101101,
            0b00110110,
            0b00111001,
            0b01001011,
            0b01010110,
            0b01011001,
            0b01100011,
            0b01100101,
            0b01101010,
            0b01101100,
            0b01111000,
            0b10000111,
            0b10010011,
            0b10010101,
            0b10011010,
            0b10011100,
            0b10100110,
            0b10101001,
            0b10110100,
            0b11000110,
            0b11001001,
            0b11010010,
            0b11100001
        )
    },
    "lightning_bolt_right": {
        "count": edges,
        "chiral": True,
        "positions": (
            0b00111010,
            0b01010011,
            0b10101100,
            0b11000101,
            0b00011101,
            0b01110010,
            0b01110100,
            0b10001101,
            0b10110001,
            0b10111000,
            0b11011000,
            0b11100010
        )
    },
    "lightning_bolt_left": {
        "count": edges,
        "chiral": True,
        "positions": (
            0b00100111,
            0b00110101,
            0b01011100,
            0b10100011,
            0b11001010,
            0b00011011,  
            0b00101110,
            0b01000111,
            0b01001110,
            0b10001011,
            0b11010001,
            0b11100100
        )
    },
    "2xdiagonal": {
        "count": faces,
        "chiral": False,
        "positions": (
            0b00111100,
            0b01011010,
            0b01100110,
            0b10011001,
            0b10100101,
            0b11000011
        )
    },
    "opposites": {
        "count": 2,
        "chiral": False,
        "positions": (
            0b01101001,
            0b10010110
        )
    }
}  
five_positions = {
    "count": 56,
    "outer_stair": {
        "count": four_positions["slab"]["count"] * 4,
        "chiral": False,
        "positions": (
            0b00011111,
            0b00101111,
            0b00110111,
            0b00111011,
            0b01001111,
            0b01010111,
            0b01011101,
            0b01110011,
            0b01110101,
            0b10001111,
            0b10101011,
            0b10101110,
            0b10110011,
            0b10111010,
            0b11001101,
            0b11001110,
            0b11010101,
            0b11011100,
            0b11101010,
            0b11101100,
            0b11110001,
            0b11110010,
            0b11110100,
            0b11111000
        )
    },
    "2xdiagonal_plus_single": {
        "count": four_positions["2xdiagonal"]["count"] * 4,
        "chiral": False,
        "positions": (
            0b00111101,
            0b00111110,
            0b01011011,
            0b01011110,
            0b01100111,
            0b01101110,
            0b01110110,
            0b01111010,
            0b01111100,
            0b10011011,
            0b10011101,
            0b10100111,
            0b10101101,
            0b10110101,
            0b10111001,
            0b10111100,
            0b11000111,
            0b11001011,
            0b11010011,
            0b11011001,
            0b11011010,
            0b11100011,
            0b11100101,
            0b11100110
        )
    },
    "full_corner_plus_single": {
        "count": four_positions["full_corner"]["count"],
        "chiral": False,
        "positions" : (
            0b01101011,
            0b01101101,
            0b01111001,
            0b10010111,
            0b10011110,
            0b10110110,
            0b11010110,
            0b11101001
        )
    }
}
six_positions = {
    "count": 28,
    "stair": {
        "count": edges,
        "chiral": False,
        "positions": (
            0b00111111,
            0b01011111,
            0b01110111,
            0b10101111,
            0b10111011,
            0b11001111,
            0b11011101,
            0b11101110,
            0b11110011,
            0b11110101,
            0b11111010,
            0b11111100
        )
    },
    "slab_plus_diagonal": {
        "count": four_positions["slab"]["count"] * 2,
        "chiral": False,
        "positions": (
            0b01101111,
            0b01111011,
            0b01111101,
            0b10011111,
            0b10110111,
            0b10111110,
            0b11010111,
            0b11011110,
            0b11101011,
            0b11101101,
            0b11110110,
            0b11111001
        )
    },
    "2xdiagonal_plus_opposite": {
        "count": vertices / 2,
        "chiral": False,
        "positions": (
            0b01111110,
            0b10111101,
            0b11011011,
            0b11100111
        )
    }
}
seven_positions = {
    "count": vertices,
    "inner_stair": {
        "count": vertices,
        "chiral": False,
        "positions": (
            0b01111111,
            0b10111111,
            0b11011111,
            0b11101111,
            0b11110111,
            0b11111011,
            0b11111101,
            0b11111110
        )
    }
}
eight_positions = {
    "count": 1,
    "full_block": {
        "count": 1,
        "chiral": False,
        "positions": (
            0b11111111,
        )
    }
}

def count_bits(num):
    """Counts the number of bits set to 1 in a number."""
    return bin(num).count("1")

def validate_data(dict, expected_bits):
    variant_count = 0  # Reset variant_count for each configuration category
    for K, V in dict.items():
        if K == "count":
            variant_expected_count = V
            continue
        print(f"\t\t{K}")
        expected_count = V["count"]
        positions_length = len(V["positions"])
        if positions_length != expected_count:
            print(f"\t\t\033[31m[ERROR]: Position amount ({positions_length}) does not equal expected amount of {expected_count}.\033[0m")
        else:
            print("\t\t[OK]: Position amount matches expected.")
            
        for position in V["positions"]:
            variant_count += 1  # Count positions for variant_expected_count validation
            bit_count = count_bits(position)
            if bit_count != expected_bits:
                print(f"\033[31m[ERROR] bits in {bin(position)} don't match expected bits of {expected_bits}.\033[0m")
        
        # Validate total count for each variant at the end of processing each dict
    if variant_count != variant_expected_count:
        print(f"\t\033[31m[ERROR]: Variant position count ({variant_count}) does not equal expected count of {variant_expected_count}.\033[0m")
    else:
        print("\t[OK]: Variant position count matches expected.")

def validate_duplicates(data):
    # Track occurrences of each position
    position_occurrences = {}
    
    # Aggregate all positions
    all_positions = [position for config_dict in data for category_info in config_dict.values() if isinstance(category_info, dict) for position in category_info.get("positions", [])]
    
    # Count occurrences
    for position in all_positions:
        if position in position_occurrences:
            position_occurrences[position] += 1
        else:
            position_occurrences[position] = 1
    
    # Identify and report duplicates
    duplicates = {pos: count for pos, count in position_occurrences.items() if count > 1}
    if duplicates:
        for pos, count in duplicates.items():
            print(f"[ERROR]: Duplicate position {bin(pos)} found {count} times.")
    else:
        print("[OK]: No duplicates found.")
        
def print_diagnostics(data):
    total_variant_count = 0
    for config_dict in data:
        # Calculate the total number of variants, excluding the 'count' key
        variants = [key for key in config_dict if key != "count"]
        
        # Iterate through each variant to print its name and the number of positions
        for variant in variants:
            total_variant_count += 1
            variant_info = config_dict[variant]
            variant_count = variant_info.get("count", 0)
            print(f"Variant: {variant}, Number of positions: {variant_count}")
    print(f"Total Variant Count: {total_variant_count}")

def main():
    data = (single_positions, two_positions, three_positions, four_positions, 
            five_positions, six_positions, seven_positions, eight_positions)
    for i, dict in enumerate(data):
        expected_bits = i + 1
        print(expected_bits)
        validate_data(dict, expected_bits)
    validate_duplicates(data)
    print_diagnostics(data)
            
        

# Assuming the data structures are defined outside this snippet...

if __name__ == "__main__":
    main()
