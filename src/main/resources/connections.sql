--Fill the Industry table with predetermined data (if not already filled): 
INSERT INTO Industry (Industry) VALUES ('Finance');
INSERT INTO Industry (Industry) VALUES ('Technology');
INSERT INTO Industry (Industry) VALUES ('Healthcare');
INSERT INTO Industry (Industry) VALUES ('Manufacturing');
INSERT INTO Industry (Industry) VALUES ('Retail');
INSERT INTO Industry (Industry) VALUES ('Education');
INSERT INTO Industry (Industry) VALUES ('Entertainment');
INSERT INTO Industry (Industry) VALUES ('Other');

--Select all connections:
SELECT * FROM Connections;

--Get a User's info given their Friend Code:
SELECT * FROM User
WHERE FriendCode = " + FriendCode + ";

--Get a User's info given their Email and Password:
SELECT * FROM User, LoginInfo
WHERE User.FriendCode = LoginInfo.FriendCode
  AND LoginInfo.Email = " + Email + "
  AND LoginInfo.Password = " + Password + ";

--Add User, given FriendCode, Name, Company ID, Email, Password:
INSERT INTO User (FriendCode, Name, CompanyID)
VALUES (NULL, " + Name + ", (SELECT ID FROM Company WHERE Name = " + CompanyName + "));
INSERT INTO LoginInfo (FriendCode, Email, Password)
VALUES (LAST_INSERT_ID(), " + Email + ", " + Password + ");

--Delete User, given Friend Code:
DELETE FROM Connections
WHERE FriendCodeFrom = " + FriendCode + " OR FriendCodeTo = " + FriendCode + ";
DELETE FROM LoginInfo
WHERE FriendCode = " + FriendCode + ";
DELETE FROM User
WHERE FriendCode = " + FriendCode + ";

--Add Connection, given from and to Friend Codes:
INSERT INTO Connections (FriendCodeFrom, FriendCodeTo)
VALUES (" + FriendCodeFrom + ", " + FriendCodeTo + ");

--Delete Connection, given from and to Friend Codes:
DELETE FROM Connections
WHERE FriendCodeFrom = " + FriendCodeFrom + "
  AND FriendCodeTo = " + FriendCodeTo + ";

--Modify Password, given Friend Code:
UPDATE LoginInfo
SET Password = " + NewPassword + "
WHERE FriendCode = " + FriendCode + ";

--Modify Email, given Friend Code:
UPDATE LoginInfo
SET Email = " + NewEmail + "
WHERE FriendCode = " + FriendCode + ";

--Modify Name, given Friend Code:
UPDATE User
SET Name = " + NewName + "
WHERE FriendCode = " + FriendCode + ";

--Add Company, given Name and Industry:
INSERT INTO Company (Name, IndustryID)
VALUES (" + CompanyName + ", (SELECT ID FROM Industry WHERE Industry = " + IndustryName + "));

--Get all the Friend Codes of people who work in a specified industry:
SELECT FriendCode
FROM User, Company, Industry
WHERE User.CompanyID = Company.ID
  AND Company.IndustryID = " + IndustryID + ";

--Get all the Friend Codes of people who work at a specified company: 
SELECT FriendCode
FROM User, Company
WHERE User.CompanyID = " + CompanyID + ";