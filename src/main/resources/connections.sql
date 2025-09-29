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

--Get a User's info given their ID:
SELECT * FROM User
WHERE ID = " + ID + ";

--Get a User's info given their Email and Password:
SELECT * FROM User, LoginInfo
WHERE User.ID = LoginInfo.ID
  AND LoginInfo.Email = " + Email + "
  AND LoginInfo.Password = " + Password + ";

--Add User, given Name, Company ID, Email, Password:
INSERT INTO User (Name, CompanyID)
VALUES (" + Name + ", (SELECT ID FROM Company WHERE Name = " + CompanyName + "));
INSERT INTO LoginInfo (ID, Email, Password)
VALUES (LAST_INSERT_ID(), " + Email + ", " + Password + ");

--Delete User, given ID:
DELETE FROM Connections
WHERE SourceID = " + ID + " OR TargetID = " + ID + ";
DELETE FROM LoginInfo
WHERE ID = " + ID + ";
DELETE FROM User
WHERE ID = " + ID + ";

--Add Connection, given source and target IDs:
INSERT INTO Connections (SourceID, TargetID)
VALUES (" + SourceID + ", " + TargetID + ");

--Delete Connection, given source and target IDs:
DELETE FROM Connections
WHERE SourceID = " + SourceID + "
  AND TargetID = " + TargetID + ";

--Modify Password, given ID:
UPDATE LoginInfo
SET Password = " + NewPassword + "
WHERE LoginInfo.ID = " + ID + ";

--Modify Email, given ID:
UPDATE LoginInfo
SET Email = " + NewEmail + "
WHERE LoginInfo.ID = " + ID + ";

--Modify Name, given ID:
UPDATE User
SET Name = " + NewName + "
WHERE ID = " + ID + ";

--Add Company, given Name and Industry:
INSERT INTO Company (Name, IndustryID)
VALUES (" + CompanyName + ", (SELECT ID FROM Industry WHERE Industry = " + IndustryName + "));

--Get all the IDs of people who work in a specified industry:
SELECT User.ID
FROM User, Company
WHERE User.CompanyID = Company.ID
  AND Company.IndustryID = " + IndustryID + ";

--Get all the IDs of people who work at a specified company: 
SELECT User.ID
FROM User
WHERE User.CompanyID = " + CompanyID + ";
