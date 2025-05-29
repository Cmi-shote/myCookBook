# My CookBook

A modern Android app for discovering, saving, and managing recipes. Built with Jetpack Compose and following MVVM architecture.

## Features

- Browse and search recipes
- Save favorite recipes
- Create grocery lists
- Track ingredients
- View recipe details and instructions
- Meal planning with calendar view

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Room Database
- DataStore Preferences
- Coil for image loading
- Retrofit for API calls
- Koin for dependency injection
- Material3 Design

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer
- JDK 11 or newer
- Android SDK 33 or newer

### Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/MyCookBook.git
```

2. Get a Spoonacular API Key:
   - Go to [Spoonacular API](https://spoonacular.com/food-api)
   - Sign up for a free account
   - Navigate to your profile
   - Copy your API key from the "API Key" section

3. Add your API Key:
   - Open `app/src/main/java/com/example/mycookbook/util/Constants.kt`
   - Replace the `API_KEY` value in constants file with your Spoonacular API key:
   ```kotlin
     val API_KEY = "your-api-key-here"
   ```

4. Build and run the project:
   - Open the project in Android Studio
   - Wait for the Gradle sync to complete
   - Click the "Run" button or press Shift+F10

## Project Structure

```
app/
├── data/
│   ├── database/         # Room database and entities
│   ├── model/           # Data models
│   └── repository/      # Repository implementations
├── di/                  # Dependency injection modules
├── presentation/
│   ├── components/      # Reusable UI components
│   ├── recipes/         # Recipe-related screens
│   ├── grocery/         # Grocery list screens
│   └── utils/           # Utility functions
└── util/                # Constants and other utilities
```

## API Usage

This app uses the Spoonacular API for recipe data. The free tier includes:
- 150 points per day
- Basic recipe information
- Search functionality
- Recipe details

For more information about API limits and features, visit [Spoonacular API Documentation](https://spoonacular.com/food-api/docs).

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Spoonacular API](https://spoonacular.com/food-api) for recipe data
- [Material Design](https://material.io/) for design guidelines
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern UI toolkit 