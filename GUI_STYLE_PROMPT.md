# Master GUI Style Prompt

Use this prompt when implementing or updating any screen in the Employee Management System. It is the shared visual specification for all AI tools and contributors working on separate screens.

---

## Prompt

You are implementing one screen of the existing **Employee Management System** desktop application. Match the visual language below exactly. Treat the provided Department Management reference screenshot and the existing Department Management screen as the source of truth. Reuse the existing Swing packages, classes, managers, models, and app structure. Build the requested screen as part of the same application; do not invent a new design system.

### 1. Visual priority

- Reproduce the reference layout, proportions, alignment, colors, spacing, typography, and icon style as closely as Swing allows.
- Keep the application recognizable across every screen: ivory content area, forest-green left sidebar, white typography, cream selected navigation item, restrained green separators, and simple icons.
- Keep the interface practical and readable. Do not add gradients, shadows, rounded modern cards, dashboard statistics, decorative illustrations, charts, extra fields, or other elements that are not requested or shown in the screen reference.
- Do not let the active Look and Feel replace the specified colors with gray defaults. Use a UI delegate or explicit renderer when necessary; verify text remains readable against every button background.
- Do not use emoji as icons.

### 2. Window and layout

- Native desktop `JFrame` title: **Employee Management System**.
- Standalone preview size: **1160 × 760 logical pixels**.
- Minimum window size: **900 × 600 logical pixels**.
- Keep the window resizable. Let the central content and table grow with the window; do not scale the sidebar with the content.
- Use Java Swing layout managers (`BorderLayout`, `BoxLayout`, `GridBagLayout`, `FlowLayout`, or equivalent). Do not position controls with absolute coordinates.
- Use one fixed-width left sidebar of **250 px** and a flexible right content area.
- The content area uses an ivory background and outer padding of **24 px top, 34 px left/right, and 18 px bottom**.
- Preserve the screen's component order and alignment from its own reference. Keep the table as the flexible center element and keep bottom-right actions anchored at the lower right.

### 3. Color tokens

Use these exact RGB/HEX values unless the user provides a new reference:

| Token | Value | Use |
|---|---:|---|
| Forest green | `#075A38` | Sidebar, primary Add/Refresh buttons |
| Dark green | `#064B30` | Text/icons on selected cream navigation item |
| Deep title green | `#123D2A` | Main screen title |
| Main ivory | `#FBF9F1` | Main content background |
| Selected cream | `#FFF8DF` | Active sidebar navigation background |
| Light table green | `#E3EBD5` | Table header background |
| Sidebar separator green | `#79A38C` | Thin divider above Logout |
| Main separator green | `#83A88F` | Thin title separator |
| Grid/border gray | `#D9D9D3` | Table grid and subtle borders |
| Alternate row | `#F5F5F2` | Every other table row |
| Selection background | `#D9E8D9` | Selected table row |
| Selection text | `#173B28` | Selected table row text |
| Input border | `#9CB3C4` | Text field outline |
| Delete background | `#F1EFE8` | Secondary Delete button |
| Delete text | `#222222` | Secondary button label/icon |
| Delete border | `#C9C9C2` | Secondary button outline |
| Normal text | `#202020` | Table/body text |
| White | `#FFFFFF` | Sidebar labels/icons and primary button labels/icons |

Do not use low-contrast white text on light gray, nor dark text on forest green. Inactive sidebar buttons stay forest green with white text. The selected item uses cream with dark-green text and icon.

### 4. Typography

- Primary font family: **Segoe UI**. Use Swing's logical fallback if unavailable, but keep the same hierarchy and weights.
- Sidebar application name: **17 pt bold**, white, centered on three lines: “Employee / Management / System”.
- Main screen title: **30 pt bold**, `#123D2A`.
- Navigation labels: **14 pt bold**.
- Form labels: **14 pt bold**.
- Text fields: **15 pt regular**.
- Table body: **15 pt regular**.
- Table headers: **15 pt bold**, centered.
- Action buttons: **14 pt bold**.
- Keep text labels short and use the exact wording/capitalization from the relevant screen reference.

### 5. Sidebar shell

Use a forest-green (`#075A38`) vertical sidebar, **250 px** wide. Use **22 px** top, **18 px** horizontal, and **18 px** bottom inset. Keep the order below:

1. White group/application icon, centered, **48 × 48 px**.
2. Application name on three centered lines; **5 px** gap after the icon.
3. **22 px** gap before navigation.
4. Dashboard
5. View Employees
6. Add Employee
7. Search / Update
8. Departments
9. Thin divider, then Logout

Navigation buttons:

- Preferred size: **190 × 43 px**; maximum height **43 px**, fill the available sidebar inner width.
- Use **5 px vertical gaps** between the first five items.
- Use **10 px** internal padding and **12 px** between icon and label.
- Align icon and label to the left.
- Inactive items: forest-green background, white label and icon.
- Active item: cream (`#FFF8DF`) background, dark-green (`#064B30`) label and icon.
- Place the divider **24 px** after Departments, make it **1 px** high and approximately **180 px** wide; place Logout **15 px** below it.
- Sidebar actions are visual navigation affordances unless the task explicitly requests behavior. Do not implement unrelated navigation or logout logic.

### 6. Logo and icon rules

- The application group logo is the downloaded white Material Icons **Group / Outline** asset at `src/gui/assets/group-outline-white.png`.
- Use the existing asset. Do not redraw, approximate, replace with emoji, or apply a heavy stroke to the logo. Keep its transparent background and white mark.
- Source asset: `https://raw.githubusercontent.com/material-icons/material-icons-png/master/png/white/group/outline-2x.png`.
- The source project describes the icon set as derived from Google's Material Design icons and available under Apache License 2.0. See `https://github.com/material-icons/material-icons-png` and Google's licensing guide at `https://developers.google.com/fonts/docs/material_icons`.
- Use consistent simple line icons elsewhere: **22 × 22 px** for sidebar navigation, **18 × 18 px** inside action buttons, and **39 × 39 px** for the large screen heading icon.
- Keep icons optically centered and use the same foreground color as their adjacent label.
- Store any additional downloaded icon asset locally in the project so the app does not need internet access at runtime. Prefer a transparent PNG for Swing unless the project already has a supported SVG renderer.
- Do not mix unrelated icon families, emoji, or filled/outlined styles without a reference requiring it.

### 7. Department Management reference screen

Use this exact arrangement when implementing Department Management:

1. Main title row at the top-left of the content: **39 × 39 px** building icon, **16 px** icon-to-title gap, title “Department Management”.
2. Thin green separator below the title: **2 px** high. Leave **13 px** between title row and separator and **22 px** between separator and input row.
3. One horizontal input row: “Department Name:” label, one text field, green Add button with plus icon, light Delete button with trash icon. Do not add an ID field.
4. Table below the form with exactly two columns, in this order: **Department ID**, **Department Name**. Header height **38 px**. Body row height **34 px**. Preferred column widths: **210 px** and **460 px**; minimum widths: **130 px** and **180 px**.
5. Use a thin `#D9D9D3` grid, white/`#F5F5F2` alternating rows, and `#E3EBD5` centered header labels.
6. Green Refresh button with refresh icon at the bottom-right.

Department form sizing and styling:

- Text field preferred size **240 × 36 px**, minimum size **120 × 34 px**, white background, **1 px** `#9CB3C4` border, **4 px** vertical and **8 px** horizontal inner padding.
- Add and Refresh use `#075A38` with white text and icon.
- Delete uses `#F1EFE8`, `#222222` text/icon, `#C9C9C2` 1 px border.
- Buttons use **9 px vertical / 13 px horizontal** padding; icon-to-label gap **8 px**. Delete may use **8 px vertical / 12 px horizontal** padding inside its outlined border.
- Use square or nearly square Swing controls; do not add modern pill-shaped buttons.

### 8. Behavior and project boundaries

- Preserve all existing backend logic, class names, method signatures, model fields, and user workflows unless the task explicitly asks to change them.
- Use the existing managers and models; do not duplicate them or create parallel data stores.
- Never invent IDs, persistence rules, validation rules, or business behavior. If the current backend cannot support a requested operation, show/report that limitation and leave backend files unchanged unless explicitly authorized.
- Keep each AI task scoped to the requested GUI screen and the minimum package/import/resource changes required. Do not edit other screens, UML, backend classes, or data files incidentally.
- Do not add fields, columns, cards, or features absent from the screen request.
- Do not create duplicate production classes. Keep test/demo launchers clearly separated from production GUI classes.

### 9. Implementation and review checklist

Before finishing a screen:

1. Inspect the existing GUI, package names, API signatures, data model, and current design reference.
2. Confirm the class can access the managers/models from their current packages.
3. Implement only the requested GUI work and preserve unrelated code.
4. Compile all Java source files and fix only errors caused by the GUI task. Report pre-existing backend errors without broad cleanup.
5. Run the requested standalone screen or application entry point.
6. Compare the rendered window against the reference at a similar size. Inspect the sidebar width, content inset, title position, form row width, table header/columns, button colors, icon scale, and bottom-right alignment.
7. Confirm every label and icon has readable contrast. No inactive navigation/action button may appear as pale gray with white text.
8. Report changed files, validation performed, remaining backend limitations, and any visual comparison that could not be verified.

---

## Quick use

Paste the full **Prompt** section into the AI tool, then append:

> Implement only this screen: **[screen name]**. Use the attached reference for its content and exact component arrangement. Follow `GUI_STYLE_PROMPT.md`, inspect existing APIs before coding, and do not change backend behavior.
