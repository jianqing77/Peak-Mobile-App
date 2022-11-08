# Team 15 Group Project

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
