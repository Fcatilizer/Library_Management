![Library Management](/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

## Library Management
#### Build on Material 3 UI

## Features
- [x] Add Book
- [x] SignUp/SignIn separated for both Admin and User
- [x] Issue Book to User

## Tech Stack
- [x] Kotlin
- [x] Google Material 3
- [x] Google Firebase

## Note
- [x] This is a simple library management app.
- [x] This app requires a Firebase project to be created and the google-services.json file to be added to the app module.
- [x] This app was built for learning purposes only. If you want to use it for production, you are responsible for it.
- [x] The Student Sign-In section is designed with regex to accept values like 'UG/02/BTCSEAIML/2021/003'. For further inspection check -> _app/src/main/java/com/example/librarymanagement/Signup.kt_
 ```kotlin
 val isRollNumber = rollNumber.text.toString().trim().matches(Regex("([A-Z])+/[0-9]+/[A-Z]+/[0-9]+/[0-9]+"))
 ```

## Screenshots
#### Home Screen
![Library Management](/Screenshots/1.png) 
![Library Management](/Screenshots/2.png)
![Library Management](/Screenshots/3.png)

#### Student Dashboard
![Library Management](/Screenshots/4.png)
![Library Management](/Screenshots/5.png)

#### Admin Dashboard
![Library Management](/Screenshots/6.png)
![Library Management](/Screenshots/7.png)
![Library Management](/Screenshots/8.png)
