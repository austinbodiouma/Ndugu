import os
import re

base_dir = r"d:\Android\Projects\Ndugu\feature"

features = {
    "marketplace": ["home", "detail", "create", "seller", "dispute"],
    "messaging": ["history", "room"],
    "payment": ["scanner", "confirmation", "topup"]
}

preview_template = """package com.example.ndugu.feature.{feature}.presentation.{screen_pkg}

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun {ScreenName}Preview() {{
    CampusWalletTheme {{
        {ScreenName}Screen(
            state = {ScreenName}State(),
            onAction = {{}}
        )
    }}
}}
"""

for feature, pkgs in features.items():
    presentation_common_dir = os.path.join(base_dir, feature, "presentation", "src", "commonMain", "kotlin", "com", "example", "ndugu", "feature", feature, "presentation")
    presentation_android_dir = os.path.join(base_dir, feature, "presentation", "src", "androidMain", "kotlin", "com", "example", "ndugu", "feature", feature, "presentation")

    for pkg in pkgs:
        common_pkg_dir = os.path.join(presentation_common_dir, pkg)
        android_pkg_dir = os.path.join(presentation_android_dir, pkg)

        if not os.path.exists(common_pkg_dir):
            continue

        for file in os.listdir(common_pkg_dir):
            if file.endswith("Screen.kt"):
                screen_name = file.replace("Screen.kt", "")
                file_path = os.path.join(common_pkg_dir, file)

                with open(file_path, "r", encoding="utf-8") as f:
                    content = f.read()

                # Regex to match the Preview block and imports
                preview_import_regex = r"import org\.jetbrains\.compose\.ui\.tooling\.preview\.Preview\n"
                preview_fn_regex = r"@Preview\s*@Composable\s*fun " + screen_name + r"Preview\(\)\s*\{[\s\S]*?\}"

                new_content = re.sub(preview_import_regex, "", content)
                new_content = re.sub(preview_fn_regex, "", new_content)

                if new_content != content:
                    with open(file_path, "w", encoding="utf-8") as f:
                        f.write(new_content.strip() + "\n")

                # Create androidMain preview file
                os.makedirs(android_pkg_dir, exist_ok=True)
                preview_file_path = os.path.join(android_pkg_dir, f"{screen_name}ScreenPreview.kt")

                with open(preview_file_path, "w", encoding="utf-8") as f:
                    f.write(preview_template.format(feature=feature, screen_pkg=pkg, ScreenName=screen_name))

print("Previews migrated to androidMain.")
