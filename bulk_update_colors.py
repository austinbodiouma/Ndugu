import os
import glob
import re

feature_dir = r"d:\Android\Projects\Ndugu\feature"
kt_files = glob.glob(os.path.join(feature_dir, "**", "*.kt"), recursive=True)

replacements = {
    "CWPremiumTealContainer": "MaterialTheme.colorScheme.primaryContainer",
    "CWPremiumTeal": "MaterialTheme.colorScheme.primary",
    "CWPremiumSurface": "MaterialTheme.colorScheme.surface",
    "CWPremiumOnTealContainer": "MaterialTheme.colorScheme.onPrimaryContainer",
    "CWPremiumOutlineVariant": "MaterialTheme.colorScheme.outlineVariant",
    "CWPremiumTertiaryContainer": "MaterialTheme.colorScheme.tertiaryContainer",
    "CWPremiumOnTertiaryContainer": "MaterialTheme.colorScheme.onTertiaryContainer"
}

for file_path in kt_files:
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    
    # Apply replacements
    for old, new in replacements.items():
        content = content.replace(old, new)
        
    # Remove old imports
    lines = content.split('\n')
    new_lines = []
    for line in lines:
        if line.startswith("import com.example.ndugu.core.designsystem.theme.CWPremium"):
            continue
        new_lines.append(line)
        
    content = '\n'.join(new_lines)
    
    if content != original_content:
        # Check if MaterialTheme import is needed
        if "MaterialTheme.colorScheme" in content and "import androidx.compose.material3.MaterialTheme" not in content:
            import_statement = "import androidx.compose.material3.MaterialTheme\n"
            import_index = content.find("import ")
            if import_index != -1:
                content = content[:import_index] + import_statement + content[import_index:]
            else:
                content = import_statement + "\n" + content
                
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Updated colors in {file_path}")
