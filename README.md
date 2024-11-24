# Cincuentazo

**Cincuentazo** is a poker card game developed in Java where a human player competes against one, two, or three AI players(machines). The objective is to survive by playing cards strategically while keeping the sum on the table below 50.

## 🃏 Game Rules
1. **Setup:**
    - Each player is dealt 4 cards at the start.
    - One random card is placed on the table to initialize the sum.
    - The remaining cards form a facedown deck.

2. **Turns:**
    - On their turn, players select a card to play:
        - **Number cards (1-8 and 10):** Add their value.
        - **Cards "9":** No effect on the sum.
        - **Cards "J, Q, K":** Subtract 10.
        - **Cards "A":** Add 1 or 10, depending on the player's choice.
    - If a player cannot play any card without exceeding 50, they are eliminated.

3. **Game End:**
    - The last player remaining wins.

## 💻 Key Features
- Intuitive graphical interface built with **JavaFX** and **Scene Builder**.
- Options to play against 1, 2, or 3 AI opponents.
- Simulated AI turns with programmed logic.

## 🚀 System Requirements
- **Java Development Kit (JDK):** 11 or higher.
- **IntelliJ IDEA** (optional for development).
- **Git** to clone the repository.

## 📦 Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Juanctoro/Fifty-Points.git
   
## 👥 Authors
This project was developed by Juan Toro.
