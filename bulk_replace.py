import os
import glob

# Search for all *Preview.kt files in the feature directory
feature_dir = r"d:\Android\Projects\Ndugu\feature"
preview_files = glob.glob(os.path.join(feature_dir, "**", "*Preview.kt"), recursive=True)

import_statement = "import com.example.ndugu.core.designsystem.theme.CampusWalletTheme\n"

for file_path in preview_files:
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Check if we need to modify
    if "MaterialTheme {" in content and "CampusWalletTheme {" not in content:
        # replace MaterialTheme { with CampusWalletTheme {
        content = content.replace("MaterialTheme {", "CampusWalletTheme {")
        
        # Add import if missing
        if "com.example.ndugu.core.designsystem.theme.CampusWalletTheme" not in content:
            # find where to inject the import
            import_index = content.find("import ")
            if import_index != -1:
                content = content[:import_index] + import_statement + content[import_index:]
            else:
                content = import_statement + "\n" + content
                
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Updated {file_path}")
    else:
        print(f"Skipped {file_path} (already updated or no MaterialTheme block)")
