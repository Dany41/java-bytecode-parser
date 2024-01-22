import requests
from bs4 import BeautifulSoup
import re
import os

def check_file_exists(file_path):
    return os.path.isfile(file_path)

file_path = "instructions_with_opcodes.txt"

if not check_file_exists(file_path):
    url = "https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-6.html"
    response = requests.get(url)

    result_map = {}  # Dictionary to store results
    pattern = r'(\w+) = (\d+) \(\w+\)'

    if response.status_code == 200:
        html = response.text

        soup = BeautifulSoup(html, 'html.parser')

        # Find all divs with class 'section-execution'
        aaload_sections = soup.find_all('div', class_='section-execution')

        # Skip the first div with class 'section'
        for i, instruction_section in enumerate(aaload_sections[1:], start=1):
            # Extracting information from the 'Forms' section
            forms_sections = instruction_section.find_all('div', class_='section')[2].find_all('p', class_='norm')
            for forms_section in forms_sections:
                instruction_info = forms_section.text.strip()
                # Save the result into the dictionary
                match = re.match(pattern, instruction_info)
                if match:
                    # Extract values using group() method
                    mnemonic = match.group(1)
                    opcode = match.group(2)
                    result_map[mnemonic] = opcode
            forms_sections_second_try = instruction_section.find_all('div', class_='section')[3].find_all('p', class_='norm')
            for forms_section_second_try in forms_sections_second_try:
                instruction_info = forms_section_second_try.text.strip()
                # Save the result into the dictionary
                match = re.match(pattern, instruction_info)
                if match:
                    # Extract values using group() method
                    mnemonic = match.group(1)
                    opcode = match.group(2)
                    result_map[mnemonic] = opcode

    # Sort the dictionary by values
    sorted_result = dict(sorted(result_map.items(), key=lambda item: int(item[1])))

    # Save the results to a text file
    with open(file_path, 'w') as file:
        for key, value in sorted_result.items():
            if key and value:
                file.write(f"{key} {value}\n")
