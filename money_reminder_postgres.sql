-- ==========================================================
-- Money Reminder & EMI Tracker - PostgreSQL Production DDL
-- Execute this script in pgAdmin 4 (Query Tool)
-- ==========================================================

-- 1. Create Database (Run separately if not already inside money_reminder_db)
-- CREATE DATABASE money_reminder_db;

-- 2. Users Table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(160) UNIQUE NOT NULL,
    phone VARCHAR(30),
    password_hash VARCHAR(128) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. Loans Table (User Scoped)
CREATE TABLE IF NOT EXISTS loans (
    id VARCHAR(64) PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    lender_name VARCHAR(150) NOT NULL,
    total_principal NUMERIC(15, 2) NOT NULL,
    emi_amount NUMERIC(15, 2) NOT NULL,
    interest_rate NUMERIC(5, 2) DEFAULT 0.00,
    total_emis INT NOT NULL,
    remaining_emis INT NOT NULL,
    due_day INT NOT NULL,
    start_date DATE NOT NULL,
    reminder_offsets JSONB DEFAULT '[7, 2, 1, 0]'::jsonb,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, COMPLETED, ARCHIVED
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 4. Transactions Table (User Scoped: Credit, Debit, Receivable, Payable)
CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(64) PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(180) NOT NULL,
    person_name VARCHAR(120),
    phone_number VARCHAR(30),
    amount NUMERIC(15, 2) NOT NULL,
    type VARCHAR(20) NOT NULL, -- CREDIT, DEBIT, RECEIVABLE, PAYABLE, EMI
    due_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, PAID, RECEIVED, OVERDUE, CANCELLED
    is_recurring BOOLEAN DEFAULT FALSE,
    recurrence_frequency VARCHAR(20) DEFAULT 'NONE', -- NONE, MONTHLY, WEEKLY
    loan_id VARCHAR(64) REFERENCES loans(id) ON DELETE SET NULL,
    category VARCHAR(60),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 5. Reminders Table
CREATE TABLE IF NOT EXISTS reminders (
    id VARCHAR(64) PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    reference_id VARCHAR(64) NOT NULL,
    reference_type VARCHAR(30) NOT NULL, -- LOAN, TRANSACTION
    title VARCHAR(180) NOT NULL,
    message TEXT NOT NULL,
    scheduled_time TIMESTAMP WITH TIME ZONE NOT NULL,
    offset_days INT NOT NULL,
    channel VARCHAR(30) NOT NULL DEFAULT 'LOCAL_NOTIFICATION', -- LOCAL_NOTIFICATION, WHATSAPP, ALARM
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED', -- SCHEDULED, SENT, DISMISSED, CANCELLED
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 6. Payment History Table
CREATE TABLE IF NOT EXISTS payment_history (
    id VARCHAR(64) PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    reference_id VARCHAR(64) NOT NULL,
    type VARCHAR(40) NOT NULL, -- EMI_PAYMENT, RECEIVABLE_RECEIVED, PAYABLE_PAID
    amount NUMERIC(15, 2) NOT NULL,
    paid_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    note TEXT
);

-- 7. App / User Settings Table
CREATE TABLE IF NOT EXISTS app_settings (
    user_id VARCHAR(64) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    setting_key VARCHAR(60) NOT NULL,
    setting_value TEXT NOT NULL,
    PRIMARY KEY (user_id, setting_key)
);

-- Performance Indexes
CREATE INDEX IF NOT EXISTS idx_loans_user_status ON loans(user_id, status);
CREATE INDEX IF NOT EXISTS idx_tx_user_due_status ON transactions(user_id, due_date, status);
CREATE INDEX IF NOT EXISTS idx_tx_user_type ON transactions(user_id, type);
CREATE INDEX IF NOT EXISTS idx_reminders_user_scheduled ON reminders(user_id, scheduled_time, status);
CREATE INDEX IF NOT EXISTS idx_payment_history_user ON payment_history(user_id, reference_id);
