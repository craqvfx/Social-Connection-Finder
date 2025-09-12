--Fill the Industry table with predetermined data (if not already filled): 
INSERT INTO Industry (Industry) VALUES ('Finance');
INSERT INTO Industry (Industry) VALUES ('Technology');
INSERT INTO Industry (Industry) VALUES ('Healthcare');
INSERT INTO Industry (Industry) VALUES ('Manufacturing');
INSERT INTO Industry (Industry) VALUES ('Retail');
INSERT INTO Industry (Industry) VALUES ('Education');
INSERT INTO Industry (Industry) VALUES ('Entertainment');
INSERT INTO Industry (Industry) VALUES ('Other');

--Get all the Friend Codes of people who work at a specified company: 
SELECT FriendCode
FROM User, Company
WHERE FriendCode = " + FriendCode + "
  AND User.CompanyID = Company.ID 
  AND Company.Name = " + CompanyName + ";

--Get all the Friend Codes of people who work in a specified industry:
SELECT FriendCode
FROM User, Company, Industry
WHERE FriendCode = " + FriendCode + "
  AND User.CompanyID = Company.ID
  AND Company.IndustryID = Industry.ID
  AND Industry.Industry = " + IndustryName + ";

--Get the name, email and industry of somebody with a specific Friend Code:
SELECT Name, Email, Industry
FROM User, LoginInfo, Company, Industry
WHERE User.FriendCode = LoginInfo.FriendCode
  AND User.CompanyID = Company.ID
  AND Company.IndustryID = Industry.ID
  AND User.FriendCode = " + FriendCode + ";

--Update Name, given Friend Code:
UPDATE User
SET Name = " + NewName + "
WHERE FriendCode = " + FriendCode + ";

--Update Email, given Friend Code:
UPDATE LoginInfo
SET Email = " + NewEmail + "
WHERE FriendCode = " + FriendCode + ";

--Update Password, given Friend Code:
UPDATE LoginInfo
SET Password = " + NewPassword + "
WHERE FriendCode = " + FriendCode + ";

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