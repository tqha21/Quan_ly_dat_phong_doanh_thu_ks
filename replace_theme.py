import os
import re

template_dir = r"d:\BTL_quan_ly_dat_phong_doanh_thu_ks\src\main\resources\templates"

replacements = {
    # Text replacements
    r"Booking\.com": "HTDBooking.com",
    r"Booking<span": "HTDBooking<span",
    
    # Colors
    r"#003b95": "#004d40", # Dark Teal (Headers, Navbar)
    r"#00224f": "#00251a", # Darker Teal
    r"#006ce4": "#00897b", # Primary Teal (Buttons, links)
    r"#0057b8": "#00695c", # Hover Teal
    r"#cce0ff": "#b2dfdb", # Light teal border
    r"#f0f6fd": "#e0f2f1", # Light teal background
    r"#ffb700": "#f57c00", # Orange (Search bar, badges, stars)
    
    # Optional: adjust light blue badges
    r"#cce5ff": "#b2dfdb",
    r"#004085": "#004d40"
}

def replace_in_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
        
    new_content = content
    for pattern, repl in replacements.items():
        new_content = re.sub(pattern, repl, new_content)
        
    if new_content != content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Updated {filepath}")

for root, dirs, files in os.walk(template_dir):
    for file in files:
        if file.endswith(".html"):
            replace_in_file(os.path.join(root, file))

print("Done replacing.")
