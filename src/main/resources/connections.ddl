CREATE TABLE Industry ( 
    ID INTEGER PRIMARY KEY NOT NULL, 
    Industry VARCHAR(50) NOT NULL 
); 
 
CREATE TABLE Company ( 
    ID INTEGER PRIMARY KEY NOT NULL, 
    Name VARCHAR(50) NOT NULL, 
    IndustryID INTEGER, 
    FOREIGN KEY (IndustryID) REFERENCES Industry(ID) 
); 
 
CREATE TABLE User ( 
    FriendCode INTEGER PRIMARY KEY NOT NULL, 
    Name VARCHAR(100) NOT NULL, 
    CompanyID INTEGER, 
    FOREIGN KEY (CompanyID) REFERENCES Company(ID) 
); 
 
CREATE TABLE LoginInfo ( 
    FriendCode INTEGER NOT NULL, 
    Email VARCHAR(100) NOT NULL UNIQUE, 
    Password VARCHAR(100) NOT NULL, 
    FOREIGN KEY (FriendCode) REFERENCES User(FriendCode) 
); 
 
CREATE TABLE Connections ( 
    FriendCodeFrom INTEGER NOT NULL, 
    FriendCodeTo INTEGER NOT NULL,
    RelationshipStrength INTEGER NOT NULL,
    PRIMARY KEY (FriendCodeFrom, FriendCodeTo), 
    FOREIGN KEY (FriendCodeFrom) REFERENCES User(FriendCode), 
    FOREIGN KEY (FriendCodeTo) REFERENCES User(FriendCode) 
); 