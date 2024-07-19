# Connect 3 Game

This project is an Android application that implements a Connect 3 game. The app includes a splash screen, a welcome page, and the game itself, following the Model-View-ViewModel (MVVM) architecture.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)

## Features
- **Splash Screen**: A white background with text/logo displayed for 3 seconds.
- **Welcome Page**: An interface to enter the user's name and start the game.
- **Connect 3 Game Play**:
  - 3x3 grid
  - Token animation
  - Win or draw detection
- **End Game**:
  - Option to play again
  - Displays a congratulatory message with the user's name
  - Quit option takes the user back to the welcome page

## Technologies Used
- **MVVM Architecture**: Ensures separation of concerns and easy management of UI-related data.
- **LiveData**: For observable data holder class.
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
- **Animations**: For token movements and transitions.

## Requirements
- **Android SDK**: API level 26 to 33
- **Gradle**: For building the project

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/connect-3-game.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Run the app on an emulator or physical device.

## Usage
1. Launch the application.
2. Wait for the splash screen to disappear.
3. Enter your name on the welcome page.
4. Press "Start Game" to begin playing.
5. Play the Connect 3 game on the 3x3 grid.
6. After the game ends, choose to play again or quit.

