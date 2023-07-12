# note-taking-be

The backend system for the note-taking application is built using Java.

## Hosting
The backend application is hosted on https://note-taking-be.onrender.com
Please note that this is a free service and sometimes the server is put down. Please contact me if you cannot reach the server.

## Database
This application is connected to a PostgreSQL database (also hosted on note-taking-be.onrender.com)

ERD:
![image](https://github.com/chiaatt/note-taking-be/assets/8133444/0a082ee2-6ff3-461c-9850-d52b728ab768)

NOTE: Due to time limitations the user table was not linked with the note table. For now, the user table is used for authentication purposes.

## APIs
For testing purposes, all the following endpoints can be loaded into [Postman](https://www.postman.com/). Please find the Postman collection in this project under the folder [**resources**](https://github.com/chiaatt/note-taking-be/tree/main/src/main/resources).

### User Registration
This endpoint is used to register a new user: `POST /api/v1/auth/register`

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/ae2ad1b5-d0d7-4b20-9e4c-5176f7bb3d89)

### User Login
This endpoint is used to authenticate a user: `POST /api/v1/auth/login`
Authentication is implemented using JWT authentication with Spring Security.

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/90b6661f-dd2d-4976-ba60-57ac175001c8)

#### User with login: testUser1 and password: Password123! was already created for testing

### Create Note (Authenticated)
This endpoint is used to create a new note: `POST /api/v1/note`
A note may contain no or many labels.

*Please note that the Bearer token must be provided in the Authentication header. This token can be retrieved from the User Login endpoint.*

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/4c18d532-e5a9-4006-b7b1-21b2592d62e1)


### Update Note (Authenticated)
This endpoint is used to update a note: `PATCH /api/v1/note`

*Please note that the Bearer token must be provided in the Authentication header. This token can be retrieved from the User Login endpoint.*

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/a21c6e3c-4974-4a01-a90e-0b5e5f75f590)


### Delete Note (Authenticated)
This endpoint is used to delete a note: `DELETE /api/v1/note/{:id}`
Any 'unused' labels are deleted as well to keep the data clean.

*Please note that the Bearer token must be provided in the Authentication header. This token can be retrieved from the User Login endpoint.*

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/ea12757b-a968-4690-9eca-643c624b5d21)

### Get All Notes (Authenticated)
This endpoint is used to get a list of all notes: `GET /api/v1/note`

*Please note that the Bearer token must be provided in the Authentication header. This token can be retrieved from the User Login endpoint.*

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/768811b1-854c-402b-89ce-598016dd45b3)

### Filter (Search) (Authenticated)
This endpoint is used to allow the users to search for a particular note or label. The text search is used to match either title, note content, or label: `POST /api/v1/note/search`

*Please note that the Bearer token must be provided in the Authentication header. This token can be retrieved from the User Login endpoint.*

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/0158dddf-d4dd-4c9f-bde7-003ff527ce2e)

### Labels Quick Access (Get Notes by Label) (Authenticated)
This endpoint is used to allow the users to retrieve only those notes that have a particular label: `POST /api/v1/note/filter`

*Please note that the Bearer token must be provided in the Authentication header. This token can be retrieved from the User Login endpoint.*

![image](https://github.com/chiaatt/note-taking-be/assets/8133444/2c192c43-29ca-4ce9-9685-f5828b7713be)

# Improvements
- Link the notes with the users





