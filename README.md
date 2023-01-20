# Team 15 Group Project

## Project: Peak

### Note

Everyone on the team fully participated in the development, and all participated in recording the tour video. Since our app uses SQLite, we don't have any test accounts for the graders.

### Demo Video
[App Video Tour](https://youtu.be/zGaMZyUh3pA)

### App Slogan

Building better spending habits

### Description

Managing money has never been easier. Whether you are a beginner or seasoned budgeter, Peak’s goal is to help you save more and spend less. With its user-friendly and simple interface, Peak allows you to record your expenses and capture pictures of your receipts in only a few taps.

Peak allows you to customize your budget from the start. Set up your first budget in as little as two minutes, allocate specific amounts of money to each spending category per month. At the end of each month, all unspent money will be automatically added to a virtual piggy bank, which can be accessed as emergency funds or allocated to spending categories for future use.

Going out for drinks or a meal? We’ve got you covered. Peak offers a tipping calculator where you can quickly calculate the tip amount. If you’re out with friends and splitting the bill, input the total bill amount or just your portion of the meal. Choose from predetermined tip percentages and know your tip amount.

### Target Users

Young adults (college age 18 - 26, recent graduates, those with limited income) who are just beginning their budgeting journey or already have some experience with budgeting.

### Features

Our implementation meets the requirements set out in our project proposal. The UI is smooth and user-friendly. The app aims to keep users engaged. After extensive testing, the app is proven to be robust. Peak includes the following features and more:

#### Basic Features

- Lock Screen Page
  - Passcode Login
    - Users need to unlock the app with their custom passcode
  - Quick Add
    - Users can quickly add a transaction record without unlocking the app

- Home Page
  - Personalized Greeting Message
  - Monthly Summary (expenses and balance)
  - View Transactions
    - The date picker button allows users to view their transaction records on any given day
    - Clicking on any transaction record will display the transaction details (i.e., expense, category, date, description, and receipt picture)
  - Add Transactions (tip calculator, capture receipt pictures, etc.)

- Graph Page
  - Receipts Collection
    - Clicking on the Receipts button will display all the receipt pictures for that month
  - Pie Chart
    - The pie chart shows the break down of the expenses by category
  - Monthly Expense by Category

- Savings Page
  - Add/Edit Saving Goal
  - Saving Tracker
    - Each month's unused funds will be automatically added to the virtual piggy bank
  - Confetti
    - When users reach their saving goals, a confetti will pop up to congratulate the users 

- Profile Page
  - Edit Profile
  - Edit Budget
  - Change Passcode
  - Security Settings
    - This feature allows users to set or modify permissions in the App Info setting
  - Terms and Conditions
  - Reset Account
    - This feature allows users to erase all existing transaction history, budget plan, and saving goal  

#### Sensor Feature

For both features below, users can take a picture with their device camera or pick an image from their photo gallery.

- Custom Profile Picture
- Receipt Picture

#### Notification Feature

- With user's permission, a daily notification will pop up at the end of the day if users have not added any spendings for that day.

#### Security Feature

- To prevent others from retrieving user's info, users need to unlock the app with their custom passcode.