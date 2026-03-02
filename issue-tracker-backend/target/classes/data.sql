-- =============================================
-- DATA SEED: 14 Realistic Issues
-- Uses ON CONFLICT on a unique index on title to avoid duplicate inserts on restart
-- =============================================

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Login page crashes on invalid email',
 'When a user enters an email without @ symbol, the login form throws an unhandled exception instead of showing a validation message.',
 'Alpha', 'CRITICAL', 'OPEN', 'Alice', NOW() - INTERVAL '10 days', NOW() - INTERVAL '10 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Dashboard loading time exceeds 5 seconds',
 'The main dashboard takes over 5 seconds to load when there are more than 100 issues. Need to implement pagination or lazy loading.',
 'Alpha', 'HIGH', 'IN_PROGRESS', 'Bob', NOW() - INTERVAL '9 days', NOW() - INTERVAL '2 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Add dark mode toggle to settings',
 'Users have requested a dark mode option. Need to implement theme switching in the settings panel and persist the preference.',
 'Beta', 'LOW', 'OPEN', 'Carol', NOW() - INTERVAL '8 days', NOW() - INTERVAL '8 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('API rate limiting not enforced',
 'The REST API does not enforce rate limits, allowing potential abuse. Need to implement throttling at 100 requests per minute per user.',
 'Gamma', 'CRITICAL', 'IN_PROGRESS', 'David', NOW() - INTERVAL '7 days', NOW() - INTERVAL '1 day')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('File upload fails for PDFs over 10MB',
 'Users report that uploading PDF files larger than 10MB results in a 413 error. Need to increase the upload limit or add chunked uploads.',
 'Delta', 'HIGH', 'OPEN', 'Eve', NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Update user profile endpoint returns stale data',
 'After updating the user profile, the GET endpoint returns old data until the cache expires. Need to invalidate cache on update.',
 'Alpha', 'MEDIUM', 'RESOLVED', 'Alice', NOW() - INTERVAL '14 days', NOW() - INTERVAL '3 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Notification emails sent in wrong timezone',
 'Email notifications display timestamps in UTC instead of the user''s local timezone. Need to convert times based on user preferences.',
 'Beta', 'MEDIUM', 'IN_PROGRESS', 'Bob', NOW() - INTERVAL '12 days', NOW() - INTERVAL '4 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Search indexing fails silently',
 'The Elasticsearch indexing job fails without logging errors when it encounters special characters in issue titles. Need better error handling.',
 'Gamma', 'HIGH', 'OPEN', 'Carol', NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Mobile navigation menu overlaps content',
 'On screens smaller than 768px, the navigation hamburger menu overlays the main content area instead of pushing it down.',
 'Delta', 'MEDIUM', 'RESOLVED', 'David', NOW() - INTERVAL '15 days', NOW() - INTERVAL '5 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Add CSV export for issue reports',
 'Product team needs the ability to export filtered issue lists as CSV files for monthly reporting and stakeholder meetings.',
 'Alpha', 'LOW', 'CLOSED', 'Eve', NOW() - INTERVAL '20 days', NOW() - INTERVAL '7 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Password reset token expires too quickly',
 'Password reset tokens expire after 15 minutes, which is too short for users who check email infrequently. Extend to 24 hours.',
 'Beta', 'MEDIUM', 'CLOSED', 'Alice', NOW() - INTERVAL '18 days', NOW() - INTERVAL '10 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Database connection pool exhaustion under load',
 'During peak hours, the app runs out of database connections causing 503 errors. Need to tune HikariCP pool settings and add connection monitoring.',
 'Gamma', 'CRITICAL', 'RESOLVED', 'Bob', NOW() - INTERVAL '11 days', NOW() - INTERVAL '2 days')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Implement role-based access control',
 'Currently all users have the same permissions. Need to implement RBAC with Admin, Manager, and Developer roles with appropriate access levels.',
 'Delta', 'HIGH', 'IN_PROGRESS', 'Carol', NOW() - INTERVAL '4 days', NOW() - INTERVAL '1 day')
ON CONFLICT (title) DO NOTHING;

INSERT INTO issues (title, description, project, priority, status, assignee, created_at, updated_at) VALUES
('Unit test coverage below 60 percent',
 'The codebase has dropped below 60% test coverage. Need to add tests for the service layer and repository classes to meet the 80% target.',
 'Gamma', 'LOW', 'OPEN', 'David', NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days')
ON CONFLICT (title) DO NOTHING;
