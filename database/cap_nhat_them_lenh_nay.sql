

 -- 1. Thêm 2 c?t m?i (Phone và Email) vào b?ng Users
ALTER TABLE Users ADD Phone VARCHAR(15);
ALTER TABLE Users ADD Email VARCHAR(100);
GO

-- 2. C?p nh?t d? li?u Email và S? di?n tho?i cho các tài kho?n hi?n có
-- C?p nh?t cho tài kho?n admin (Id = 1)
UPDATE Users 
SET Phone = '0912345678', 
    Email = 'admin@hethong.com',
    UpdatedAt = GETDATE()
WHERE Id = 1;

-- C?p nh?t cho tài kho?n manager (Id = 2)
UPDATE Users 
SET Phone = '0987654321', 
    Email = 'manager@hethong.com',
    UpdatedAt = GETDATE()
WHERE Id = 2;

-- C?p nh?t cho tài kho?n staff (Id = 3)
UPDATE Users 
SET Phone = '0905111222', 
    Email = 'staff@hethong.com',
    UpdatedAt = GETDATE()
WHERE Id = 3;

-- C?p nh?t cho tài kho?n trieu (Id = 4)
UPDATE Users 
SET Phone = '0944555666', 
    Email = 'trieu@hethong.com',
    UpdatedAt = GETDATE()
WHERE Id = 4;
GO

-- 3. Ki?m tra l?i k?t qu? xem d? li?u dã du?c n?p d? chua
SELECT Id, Username, FullName, Role, Phone, Email FROM Users;
