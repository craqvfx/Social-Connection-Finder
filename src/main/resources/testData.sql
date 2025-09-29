INSERT INTO Company (Name, IndustryID)
VALUES ('David Hi-Fi', 2),
       ('Tripathy ltd', 2),
       ('NVIDIA', 4),
       ('Apple', 2),
       ('Alphabet (Google)', 2),
       ('Amazon', 2),
       ('Saudi Aramco', 8),
       ('Broadcom', 4),
       ('JPMorgan Chase', 1);

INSERT INTO User (Name, CompanyID)
VALUES ('Christian Boland Ross', 9),
       ('Arnav Tripathy', 2),
       ('Bob Smith', 1),
       ('David Brown', 6),
       ('Eva Green', 8),
       ('Alice Johnson', 3),
       ('John Doe', 4),
       ('Jane Smith', 5),
       ('Michael Lee', 7);

INSERT INTO LoginInfo (ID, Email, Password)
VALUES (1, 'christian.boland@gmail.com', 'a'),
       (2, 'arnav.t@hotmail.com', 'Arnav@2001'),
       (3, 'bob.smith@outlook.com', 'Bob$Dog42'),
       (4, 'david.brown@yahoo.com', 'Brownie#12'),
       (5, 'eva.green@gmail.com', 'EvaGreen_07'),
       (6, 'alice.johnson@outlook.com', 'Alice*Cat!'),
       (7, 'john.doe@gmail.com', 'JDoe_1985'),
       (8, 'jane.smith@hotmail.com', 'Jane!Smith88'),
       (9, 'michael.lee@outlook.com', 'MLee@1234');

INSERT INTO Connections (SourceID, TargetID, RelationshipStrength)
VALUES (1, 2, 4),
       (1, 4, 3),
       (2, 3, 2),
       (3, 5, 2),
       (3, 6, 5),
       (4, 5, 1),
       (4, 7, 2),
       (5, 8, 5),
       (6, 9, 1);