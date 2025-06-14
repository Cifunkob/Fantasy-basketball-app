# 🏀 Fantasy Basketball App

A JavaFX desktop application that simulates a fantasy basketball league, developed as a final year project. Users can create and join leagues, manage player rosters, view stats, and simulate matches with dynamic performance tracking.

---

## 📦 Project Structure

This project uses the standard Maven structure and is organized into clear packages:
demo/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── entiteti/ # Core domain models: User, League, Player, Stats, etc.
│ │ │ ├── baza/ # Database access logic
│ │ │ ├── iznimke/ # Custom exceptions
│ │ │ └── hr.tvz.pejkunovic.demo/ # JavaFX controllers
│ │ └── resources/
│ │ └── hr.tvz.pejkunovic.demo/ # FXML files for UI
├── dat/ # Database connection properties
├── pom.xml # Maven build file

---

## 🚀 Features

- 👥 **User Authentication**: Register and log in securely.
- 🏆 **League Management**: Create or join fantasy leagues.
- 📊 **Live Stats & Simulations**: Simulate games and see player performance.
- 📈 **Leaderboards**: Real-time rankings for leagues.
- 🎮 **Custom Team Selection**: Choose your fantasy team lineup.
- 🧠 **Exception Handling**: Robust handling for login, user creation, and in-game logic.

---

## 💡 Technologies Used

- **Java 19**
- **JavaFX**
- **Maven**
- **FXML** for UI layouts
- **Custom database layer** (via `BazaPodataka.java`)
- **Custom encryption & validation logic**
