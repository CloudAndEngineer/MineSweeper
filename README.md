# 💣 MineSweeper Project

> A Full-Stack Minesweeper Web Application built with **Spring Boot** and **Vue 3 (Pinia)**.

---

## 1. Synopsis

* A full-stack toy project built to practice layered architecture and RESTful API synchronization.
* **Backend:** Spring Boot with pure domain-driven logic (Board, Cell state management, Chording algorithm).
* **Frontend:** Vue 3 Composition API with Pinia for state management and reactive board updates.
* **AI Collaboration:** Developed with guidance from Gemini as a technical partner, while designing the core backend domain logic independently.

---

## 2. Key Features

- 🎮 **Difficulty Options:** Easy (9x9), Medium (16x16), Hard (30x16), and Custom (User-defined width/height/mine count).
- 🚩 **Flag Management:** Synchronized `remainingFlags` counter with server-side validation to prevent flag limits from being exceeded.
- ⚡ **Chording (Auto-Open):** Clicking an open cell with matching adjacent flags auto-reveals surrounding unflagged cells.
- 🛡️️ **First-Click Protection:** Guaranteed safety on the very first click by generating mines afterward.

---

## 3. Tech Stack

| Domain       | Technologies                         |
|:-------------|:-------------------------------------|
| **Backend**  | Java 25, Spring Boot 3.x             |
| **Frontend** | Vue 3 (Composition API), Pinia, Vite |
| **Protocol** | RESTful API (JSON)                   |

---

## 4. How to Download and Run

### Prerequisites

* Java 25+
* Node.js >= 20.x

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/CloudAndEngineer/MineSweeper.git
   cd <repo-path>
   ```

2. **Frontend Setup**

   ```bash
   cd frontend
   npm install
   ```

3. **Run Backend Application**

   * Open the project in IntelliJ IDEA (or VS Code) and run `MineSweeperApplication.java`.
   * Backend runs on `http://localhost:8080`.

4. **Run Frontend Application**

   ```bash
   npm run dev
   ```
   * Frontend runs on `http://localhost:5173`

5. **Open your browser and navigate to `http://localhost:5173`.**

---

## 5. How to Play

1. Select your preferred difficulty from the dropdown menu (Easy, Medium, Hard, Custom).

2. Click the "New Game" button to generate a new board.

3. **Left Click**: Open a cell.

4. **Right Click**: Toggle a flag on a closed cell.

5. **Click Opened Cell**: If adjacent flags match the cell's number, surrounding non-flagged cells will automatically open.

---

## 6. Communication and Support

   * Bug Report: Please open an issue in the [GitHub Issues tab](https://github.com/CloudAndEngineer/MineSweeper/issues).
   * Contact: fjdksla6@gmail.com