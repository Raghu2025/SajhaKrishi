-- Add login attempt tracking columns to users table
ALTER TABLE `users` ADD COLUMN `failed_login_attempts` INT DEFAULT 0;
ALTER TABLE `users` ADD COLUMN `last_failed_login` BIGINT DEFAULT NULL;
ALTER TABLE `users` ADD COLUMN `account_locked_until` BIGINT DEFAULT NULL;
ALTER TABLE `users` ADD COLUMN `is_locked` CHAR(1) DEFAULT 'N';

-- Add index on email for faster lookups
ALTER TABLE `users` ADD INDEX `idx_email` (`email`);

-- Reset all existing users to have no failed attempts and unlocked status
UPDATE `users` SET 
    `failed_login_attempts` = 0,
    `last_failed_login` = NULL,
    `account_locked_until` = NULL,
    `is_locked` = 'N';
