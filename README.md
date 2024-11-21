# Project-Management-App
Project Management App


## Naming conventions
Adopting consistent naming conventions in Android Studio ensures code readability, maintainability, and alignment with industry standards. Below are detailed naming conventions for Android development:

---

### **1. Packages**
- Use **lowercase letters only** and separate words with dots.
- Structure reflects app organization and follows reverse domain notation.
  - Example: `com.example.myapp.ui.login`

### **2. Classes and Interfaces**
- Use **PascalCase** (each word capitalized, no underscores).
- Classes: Should represent objects or behaviors.
  - Example: `MainActivity`, `UserProfile`
- Interfaces: Use descriptive names or prefix with `I` (optional).
  - Example: `OnClickListener`, `IDataProvider`

---

### **3. Variables**
- Use **camelCase** (first word lowercase, subsequent words capitalized).
- Descriptive names without abbreviations.
- Prefix with scope indicators when applicable:
  - **Local Variables:** Regular naming, e.g., `userName`.
  - **Instance Variables (Fields):** Prefix with `m` for member fields.
    - Example: `mUserName`.
  - **Constants:** All uppercase, words separated by underscores.
    - Example: `MAX_RETRY_COUNT`.

---

### **4. Methods**
- Use **camelCase**.
- Start with a verb to indicate the action.
  - Example: `getUserDetails()`, `calculateTotalCost()`
- Use descriptive names without abbreviations.

---

### **5. XML Resource IDs**
- Use **snake_case** with prefixes indicating the type of element.
  - Buttons: `btn_`
  - TextViews: `tv_`
  - EditTexts: `et_`
  - ImageViews: `iv_`
  - Layouts: `layout_`
  - Examples: `btn_submit`, `tv_user_name`, `layout_main`

---

### **6. XML Layout Files**
- Use **snake_case**, reflecting the activity, fragment, or UI component.
  - Activities: `activity_<name>.xml`
    - Example: `activity_main.xml`
  - Fragments: `fragment_<name>.xml`
    - Example: `fragment_dashboard.xml`
  - Dialogs: `dialog_<name>.xml`
    - Example: `dialog_confirmation.xml`
  - Recyclable components (e.g., items): `item_<name>.xml`
    - Example: `item_product.xml`

---

### **7. Drawables**
- Use **snake_case**, indicating type if necessary.
  - Icons: `ic_<name>`
    - Example: `ic_launcher`, `ic_settings`
  - Backgrounds: `bg_<name>`
    - Example: `bg_button`, `bg_card`
  - Selectors: `selector_<name>`
    - Example: `selector_button_state`

---

### **8. Strings**
- Use **snake_case**, reflecting purpose and screen context.
  - Example: `welcome_message`, `error_network_unavailable`

---

### **9. Colors**
- Use **snake_case**, indicating purpose.
  - Example: `color_primary`, `color_secondary`, `color_text_hint`

---

### **10. Styles**
- Use **PascalCase**.
- Prefix to indicate scope or type.
  - Example: `AppTheme`, `ButtonPrimary`

---

### **11. IDs**
- Use **snake_case** and descriptive names.
- Reflect component purpose.
  - Example: `submit_button`, `user_name_text`

---

### **12. ViewModel and LiveData**
- ViewModel: Use **PascalCase** with `ViewModel` suffix.
  - Example: `MainViewModel`
- LiveData: Use **camelCase** with `LiveData` or `MutableLiveData` postfix.
  - Example: `userDataLiveData`

---

### **13. Gradle Modules**
- Use **lowercase_letters_with_underscores**.
  - Example: `app`, `core`, `feature_login`

---

### **14. Tests**
- Unit Test Classes: Use the class name being tested with a `Test` suffix.
  - Example: `UserRepositoryTest`
- UI Test Classes: Use `UiTest` suffix or `ActivityTest`.
  - Example: `LoginActivityUiTest`

---

### **15. Miscellaneous**
- Enum: Use **PascalCase** for names and ALL_CAPS for constants.
  - Example: `UserStatus { ACTIVE, INACTIVE, PENDING }`
- Static Methods: Use **camelCase** and descriptive names.
  - Example: `createInstance()`

---

### **Common Practices**
- Avoid abbreviations unless they’re widely recognized (e.g., `URL`, `ID`).
- Use prefixes or suffixes for clarity:
  - Interfaces: `On`, e.g., `OnItemClickListener`
  - Async tasks: `Async`, e.g., `DownloadAsyncTask`
- Be consistent across the codebase. If working in a team, document and enforce standards.

This guide ensures clean, professional, and scalable Android projects.

## Tasks
### 1. **Task Management**
   - **Create, Edit, and Delete Tasks**: Let users add tasks, edit them, and delete them if necessary.
   - **Task Status**: Include options for marking tasks as "To Do," "In Progress," or "Completed."
   - **Due Dates**: Allow users to set due dates for tasks, with color indicators (e.g., red for overdue).
   - **Priority Levels**: Let users set task priorities, such as low, medium, and high (optional).

### 2. **Project and Team Management**
   - **Project Creation**: Users can create projects and assign tasks to specific projects.
   - **Assign Team Members**: Add the ability to assign tasks to team members and show who is responsible.
   - **Roles and Permissions**: Allow different levels of access, like "Admin" for team leaders and "Member" for team members.(optional)

### 3. **Simple User Interface**
   - **Dashboard Overview**: A homepage that shows an overview of all projects, upcoming tasks, and deadlines.
   - **Progress Tracking**: Add a progress bar or percentage tracker for each project.
   - **Notifications**: Simple notifications for deadlines(optional) or task updates can help users stay on track.

### 6. **Basic Analytics**
   - **Task Completion Statistics**: Show basic stats, like the number of tasks completed, tasks in progress, and total tasks.

### 8. **Search and Filter**
   - **Search Bar**: Let users search for tasks by name or keyword.
   - **Filter by Status, Priority, or Assignee**: Allow filtering tasks based on their status, priority (optional), or assigned team member(optional).

### 10. **User Authentication**
   - **Login/SignUp System**: Basic authentication for users to sign up and log in, allowing personalized data storage.

