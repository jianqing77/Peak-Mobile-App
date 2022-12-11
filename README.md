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

## A8 Stick It To 'Em

For assignment 8, we implemented a sticker messaging app using Firebase Realtime Database.

### Demo Video
[demo videos](https://drive.google.com/drive/folders/16C-pjb0t0p8WViBVeEyviP4ALY0hjgJH?usp=sharing)

### Login/Sign Up

- Input
  - Username (required)
    - Users must sign up if the username doesn't exist in the database
  - Password (NOT REQUIRED)
    - For this assignment, no password needed for users to Login/Signup
- Output
  - If user already registered, jump to the main dashboard to add friend and send stickers

### Add Friend

- Input
  - Friend's Username (required)
    - Users will be prompted if the username does not exist in the database

- Output
  - An addition to the friends list with the friend's username and icon

### View Sticker History

- Stickers that user have SENT: on the top of main page after login
- When users click the "Sticker History" button, they will be shown a list of stickers RECEIVED by the user, including the following information:
  - Sticker Image
  - Sticker Name
  - Sent by (username)
  - Sent on (timestamp)

### Notification

- Users will receive a push notification when they receive a sticker from a friend.
  - Notifications are pushed when the app is in the foreground or background, but not when the app is not running.
  - Notifications include:
    - Sticker Name
    - Sent by (username)
    - Sticker Image
    
-----

## A7 At Your Service

For assignment 7, we implemented a move/series search service using a publicly available API called [OMDB API](https://www.omdbapi.com/).

### User Input

- The title of the movie/series (required)
- The year of the movie/series (optional)
- The type (optional): 
    - movie 
    - series
    - no preference

### Output

- A list of movie/series match results
    - Poster
    - Title
    - Year
    - Type
    - IMDB ID

### Challenges

- Different UI controls for data input: EditText, Button, RadioGroup, RadioButton, NumberPicker.
- A list of movie/series match results gets displayed in a RecyclerView.
- Posters are generated from URLs and displayed in the match results.

### Demo

Click the image below to see the demo video.

[![A7 At Your Service Demo](https://github.com/jaimesi/NUMADFa22_Team15/blob/master/app/src/main/AtYourServiceScreenshot.png)](https://youtube.com/shorts/5BUv1S5vinA?feature=share)
