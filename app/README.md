# App de Gestión de Gastos

This is an Android application for managing personal expenses, built with modern Android development technologies.

## Project Configuration

### Prerequisites

- Android Studio Iguana | 2023.2.1 Patch 1 or higher
- JDK 11
- Android SDK 34

### Dependencies

The project uses the following key dependencies:

- **Jetpack Compose**: For building the user interface.
- **Room**: For local database storage.
- **Hilt**: For dependency injection.
- **AndroidX Navigation**: For navigating between screens.
- **Kotlin Coroutines**: For asynchronous programming.

### Build Instructions

1.  Clone the repository:
    ```bash
    git clone https://github.com/your-repository/Appdegestiondegastos.git
    ```
2.  Open the project in Android Studio.
3.  Let Android Studio sync the Gradle files.
4.  Run the application on an emulator or a physical device.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern and is structured into the following layers:

-   **Data Layer**: Contains the Room database, entities, DAOs, and the repository implementation.
-   **Domain Layer**: Contains the use cases that encapsulate the business logic.
-   **Presentation Layer**: Contains the UI (built with Jetpack Compose), ViewModels, and navigation graph.

### Key Components

-   **`TransactionBd`**: The Room database that stores the expenses and categories.
-   **`TransactionDao`**: The Data Access Object that defines the database queries.
-   **`TransactionRepository`**: The repository that abstracts the data source.
-   **Use Cases**: Classes that encapsulate a single business operation (e.g., `InsertExpenseUseCase`).
-   **`TransactionViewModel`**: The ViewModel that exposes the data to the UI and handles user actions.
-   **UI Screens**: Composable functions that build the user interface.
-   **`NavGraph`**: The navigation graph that defines the navigation flow of the application.
-   **Hilt Modules**: Modules that provide the necessary dependencies for the application.
