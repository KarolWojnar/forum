insert into forum.comments (id, content, createDate, updateDate, parent_comment_id, post_id, user_id)
values  (1, 'sadasd asdasd asda', '2025-02-19 17:11:15.000000', '2025-02-19 17:11:25.000000', null, 1, 1),
        (2, 'asdsaxsaas a', '2025-02-19 17:11:31.000000', '2025-02-19 17:11:40.000000', 1, 1, 2),
        (3, 'sd asadasd ', '2025-02-19 17:11:32.000000', '2025-02-19 17:11:39.000000', null, 2, 2),
        (4, 'asd sa', '2025-02-19 17:11:35.000000', '2025-02-19 17:11:38.000000', null, 2, 1),
        (5, 'as dasd asd', '2025-02-19 17:11:36.000000', '2025-02-19 17:11:37.000000', null, 2, 2),
        (6, 'Practice coding challenges daily on LeetCode or HackerRank.', '2025-02-19 18:00:00', '2025-02-19 18:00:00', null, 6, 3),
        (7, 'That''s great advice! Which difficulty level do you recommend starting with?', '2025-02-19 18:01:00', '2025-02-19 18:01:00', 6, 6, 4),
        (8, 'Start with easy problems and gradually move up.', '2025-02-19 18:02:00', '2025-02-19 18:02:00', 7, 6, 3),
        (9, 'Reading clean code by Robert Martin helped me a lot.', '2025-02-19 18:03:00', '2025-02-19 18:03:00', null, 6, 5),
        (10, 'Code reviews also helped me improve significantly.', '2025-02-19 18:04:00', '2025-02-19 18:04:00', null, 6, 6),
        (11, 'Head First Java is great for beginners!', '2025-02-19 18:05:00', '2025-02-19 18:05:00', null, 7, 7),
        (12, 'Effective Java by Joshua Bloch is a must-read.', '2025-02-19 18:06:00', '2025-02-19 18:06:00', null, 7, 8),
        (13, 'Which edition would you recommend?', '2025-02-19 18:07:00', '2025-02-19 18:07:00', 12, 7, 9),
        (14, 'The latest 3rd edition covers up to Java 11.', '2025-02-19 18:08:00', '2025-02-19 18:08:00', 13, 7, 8),
        (15, 'Spring in Action is also very helpful.', '2025-02-19 18:09:00', '2025-02-19 18:09:00', null, 7, 10),
        (16, 'How did you handle security in your application?', '2025-02-19 18:10:00', '2025-02-19 18:10:00', null, 8, 1),
        (17, 'I used Spring Security with JWT tokens.', '2025-02-19 18:11:00', '2025-02-19 18:11:00', 16, 8, 4),
        (18, 'Did you face any challenges with database integration?', '2025-02-19 18:12:00', '2025-02-19 18:12:00', null, 8, 2),
        (19, 'JPA made it pretty straightforward.', '2025-02-19 18:13:00', '2025-02-19 18:13:00', 18, 8, 4),
        (20, 'What about testing?', '2025-02-19 18:14:00', '2025-02-19 18:14:00', null, 8, 3),
        (21, 'IntelliJ IDEA has amazing plugins!', '2025-02-19 18:15:00', '2025-02-19 18:15:00', null, 9, 5),
        (22, 'Which ones do you recommend?', '2025-02-19 18:16:00', '2025-02-19 18:16:00', 21, 9, 6),
        (23, 'SonarLint is a must-have.', '2025-02-19 18:17:00', '2025-02-19 18:17:00', 22, 9, 5),
        (24, 'GitToolBox is also very useful.', '2025-02-19 18:18:00', '2025-02-19 18:18:00', 22, 9, 7),
        (25, 'Rainbow Brackets makes code more readable.', '2025-02-19 18:19:00', '2025-02-19 18:19:00', 21, 9, 8),
        (26, 'Pattern matching in instanceof is game-changing!', '2025-02-19 18:20:00', '2025-02-19 18:20:00', null, 10, 9),
        (27, 'Records are really useful for DTOs.', '2025-02-19 18:21:00', '2025-02-19 18:21:00', null, 10, 10),
        (28, 'How do you handle backward compatibility?', '2025-02-19 18:22:00', '2025-02-19 18:22:00', 27, 10, 1),
        (29, 'We maintain multiple versions.', '2025-02-19 18:23:00', '2025-02-19 18:23:00', 28, 10, 10),
        (30, 'Sealed classes are interesting too.', '2025-02-19 18:24:00', '2025-02-19 18:24:00', null, 10, 2),
        (31, 'Microservices can be challenging to debug.', '2025-02-19 18:25:00', '2025-02-19 18:25:00', null, 11, 3),
        (32, 'Using distributed tracing helps a lot.', '2025-02-19 18:26:00', '2025-02-19 18:26:00', 31, 11, 4),
        (33, 'Jaeger or Zipkin?', '2025-02-19 18:27:00', '2025-02-19 18:27:00', 32, 11, 5),
        (34, 'We use Jaeger with good results.', '2025-02-19 18:28:00', '2025-02-19 18:28:00', 33, 11, 4),
        (35, 'Service mesh might help too.', '2025-02-19 18:29:00', '2025-02-19 18:29:00', 31, 11, 6),
        (36, 'HTTPS is mandatory for production.', '2025-02-19 18:30:00', '2025-02-19 18:30:00', null, 12, 7),
        (37, 'Don''t forget about CORS configuration!', '2025-02-19 18:31:00', '2025-02-19 18:31:00', 36, 12, 8),
        (38, 'How do you handle XSS protection?', '2025-02-19 18:32:00', '2025-02-19 18:32:00', null, 12, 9),
        (39, 'Input validation is crucial.', '2025-02-19 18:33:00', '2025-02-19 18:33:00', 38, 12, 10),
        (40, 'We use Content Security Policy headers.', '2025-02-19 18:34:00', '2025-02-19 18:34:00', 38, 12, 1),
        (41, 'Docker is perfect for small projects.', '2025-02-19 18:35:00', '2025-02-19 18:35:00', null, 13, 2),
        (42, 'Kubernetes seems overkill for simple apps.', '2025-02-19 18:36:00', '2025-02-19 18:36:00', 41, 13, 3),
        (43, 'But great for scaling!', '2025-02-19 18:37:00', '2025-02-19 18:37:00', 42, 13, 4),
        (44, 'Learning curve is steep though.', '2025-02-19 18:38:00', '2025-02-19 18:38:00', null, 13, 5),
        (45, 'Worth it for large applications.', '2025-02-19 18:39:00', '2025-02-19 18:39:00', 44, 13, 6),
        (46, 'JUnit 5 has great features!', '2025-02-19 18:40:00', '2025-02-19 18:40:00', null, 14, 7),
        (47, 'Mockito makes testing so much easier.', '2025-02-19 18:41:00', '2025-02-19 18:41:00', 46, 14, 8),
        (48, 'TestContainers for integration tests.', '2025-02-19 18:42:00', '2025-02-19 18:42:00', null, 14, 9),
        (49, 'How do you handle test data?', '2025-02-19 18:43:00', '2025-02-19 18:43:00', 48, 14, 10),
        (50, 'We use dedicated test databases.', '2025-02-19 18:44:00', '2025-02-19 18:44:00', 49, 14, 1),
        (51, 'ELK stack is amazing for log analysis.', '2025-02-19 18:45:00', '2025-02-19 18:45:00', null, 15, 2),
        (52, 'How about performance impact?', '2025-02-19 18:46:00', '2025-02-19 18:46:00', 51, 15, 3),
        (53, 'Async logging helps with that.', '2025-02-19 18:47:00', '2025-02-19 18:47:00', 52, 15, 4),
        (54, 'Log rotation is important too.', '2025-02-19 18:48:00', '2025-02-19 18:48:00', null, 15, 5),
        (55, 'We use logback for that.', '2025-02-19 18:49:00', '2025-02-19 18:49:00', 54, 15, 6),
        (56, 'Version your APIs from the start!', '2025-02-19 18:50:00', '2025-02-19 18:50:00', null, 16, 7),
        (57, 'URI vs Header versioning?', '2025-02-19 18:51:00', '2025-02-19 18:51:00', 56, 16, 8),
        (58, 'URI is more explicit.', '2025-02-19 18:52:00', '2025-02-19 18:52:00', 57, 16, 9),
        (59, 'Documentation is crucial.', '2025-02-19 18:53:00', '2025-02-19 18:53:00', null, 16, 10),
        (60, 'Swagger makes it easier.', '2025-02-19 18:54:00', '2025-02-19 18:54:00', 59, 16, 1),
        (61, 'Indexing is key for performance.', '2025-02-19 18:55:00', '2025-02-19 18:55:00', null, 17, 2),
        (62, 'But too many indexes can hurt!', '2025-02-19 18:56:00', '2025-02-19 18:56:00', 61, 17, 3),
        (63, 'Query optimization is important.', '2025-02-19 18:57:00', '2025-02-19 18:57:00', null, 17, 4),
        (64, 'Explain plan helps a lot.', '2025-02-19 18:58:00', '2025-02-19 18:58:00', 63, 17, 5),
        (65, 'Connection pooling too.', '2025-02-19 18:59:00', '2025-02-19 18:59:00', null, 17, 6),
        (66, 'Jenkins pipelines are powerful.', '2025-02-19 19:00:00', '2025-02-19 19:00:00', null, 18, 7),
        (67, 'GitHub Actions is easier to set up.', '2025-02-19 19:01:00', '2025-02-19 19:01:00', 66, 18, 8),
        (68, 'What about GitLab CI?', '2025-02-19 19:02:00', '2025-02-19 19:02:00', 67, 18, 9),
        (69, 'All about team preference.', '2025-02-19 19:03:00', '2025-02-19 19:03:00', 68, 18, 10),
        (70, 'Automation is key regardless.', '2025-02-19 19:04:00', '2025-02-19 19:04:00', null, 18, 1),
        (71, 'Watch out for memory leaks in collections.', '2025-02-19 19:05:00', '2025-02-19 19:05:00', null, 19, 2),
        (72, 'JProfiler helps identify issues.', '2025-02-19 19:06:00', '2025-02-19 19:06:00', 71, 19, 3),
        (73, 'HeapDump analysis is crucial.', '2025-02-19 19:07:00', '2025-02-19 19:07:00', null, 19, 4),
        (74, 'GC tuning can be tricky.', '2025-02-19 19:08:00', '2025-02-19 19:08:00', null, 19, 5),
        (75, 'Start with default settings first.', '2025-02-19 19:09:00', '2025-02-19 19:09:00', 74, 19, 6),
        (76, 'Be constructive in feedback.', '2025-02-19 19:10:00', '2025-02-19 19:10:00', null, 20, 7),
        (77, 'Code style matters a lot.', '2025-02-19 19:11:00', '2025-02-19 19:11:00', 76, 20, 8),
        (78, 'Automated tools help.', '2025-02-19 19:12:00', '2025-02-19 19:12:00', 77, 20, 9),
        (79, 'Regular reviews are important.', '2025-02-19 19:13:00', '2025-02-19 19:13:00', null, 20, 10),
        (80, 'Pair programming helps too.', '2025-02-19 19:14:00', '2025-02-19 19:14:00', 79, 20, 1),
        (81, 'Quarkus startup time is amazing.', '2025-02-19 19:15:00', '2025-02-19 19:15:00', null, 21, 2),
        (82, 'Spring has better ecosystem.', '2025-02-19 19:16:00', '2025-02-19 19:16:00', 81, 21, 3),
        (83, 'Native compilation is game changer.', '2025-02-19 19:17:00', '2025-02-19 19:17:00', null, 21, 4),
        (84, 'Learning curve difference?', '2025-02-19 19:18:00', '2025-02-19 19:18:00', 83, 21, 5),
        (85, 'Spring is more beginner friendly.', '2025-02-19 19:19:00', '2025-02-19 19:19:00', 84, 21, 6),
        (86, 'GitFlow works well for us.', '2025-02-19 19:20:00', '2025-02-19 19:20:00', null, 22, 7),
        (87, 'Trunk-based is simpler.', '2025-02-19 19:21:00', '2025-02-19 19:21:00', 86, 22, 8),
        (88, 'Feature flags are important.', '2025-02-19 19:22:00', '2025-02-19 19:22:00', null, 22, 9),
        (89, 'Branch naming conventions?', '2025-02-19 19:23:00', '2025-02-19 19:23:00', 88, 22, 10),
        (90, 'We use Jira ticket numbers.', '2025-02-19 19:24:00', '2025-02-19 19:24:00', 89, 22, 1),
        (91, 'WebFlux learning curve is steep.', '2025-02-19 19:25:00', '2025-02-19 19:25:00', null, 23, 2),
        (92, 'But great for high concurrency.', '2025-02-19 19:26:00', '2025-02-19 19:26:00', 91, 23, 3),
        (93, 'Debugging can be challenging.', '2025-02-19 19:27:00', '2025-02-19 19:27:00', null, 23, 4),
        (94, 'Tools are improving though.', '2025-02-19 19:28:00', '2025-02-19 19:28:00', 93, 23, 5),
        (95, 'Project Reactor is powerful.', '2025-02-19 19:29:00', '2025-02-19 19:29:00', null, 23, 6),
        (96, 'AWS has most services.', '2025-02-19 19:30:00', '2025-02-19 19:30:00', null, 24, 7),
        (97, 'Azure integrates well with .NET.', '2025-02-19 19:31:00', '2025-02-19 19:31:00', 96, 24, 8),
        (98, 'GCP has best ML offerings.', '2025-02-19 19:32:00', '2025-02-19 19:32:00', null, 24, 9),
	(99, 'Follow SOLID principles for clean code.', '2025-02-19 20:00:00', '2025-02-19 20:00:00', null, 26, 1),
	(100, 'What does SOLID stand for?', '2025-02-19 20:01:00', '2025-02-19 20:01:00', 99, 26, 2),
	(101, 'Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.', '2025-02-19 20:02:00', '2025-02-19 20:02:00', 100, 26, 1),
	(102, 'Also, write meaningful variable names.', '2025-02-19 20:03:00', '2025-02-19 20:03:00', null, 26, 3),
	(103, 'Avoid magic numbers and hardcoding.', '2025-02-19 20:04:00', '2025-02-19 20:04:00', 102, 26, 4),
	(104, 'Use dependency injection frameworks like Spring.', '2025-02-19 20:05:00', '2025-02-19 20:05:00', null, 27, 5),
	(105, 'What about manual DI?', '2025-02-19 20:06:00', '2025-02-19 20:06:00', 104, 27, 6),
	(106, 'Manual DI can be error-prone and harder to maintain.', '2025-02-19 20:07:00', '2025-02-19 20:07:00', 105, 27, 5),
	(107, 'NoSQL is great for scalability.', '2025-02-19 20:08:00', '2025-02-19 20:08:00', null, 28, 7),
	(108, 'But lacks ACID transactions.', '2025-02-19 20:09:00', '2025-02-19 20:09:00', 107, 28, 8),
	(109, 'Use Redux for state management in React.', '2025-02-19 20:10:00', '2025-02-19 20:10:00', null, 29, 9),
	(110, 'Context API is simpler for small apps.', '2025-02-19 20:11:00', '2025-02-19 20:11:00', 109, 29, 10),
	(111, 'Prometheus is great for monitoring.', '2025-02-19 20:12:00', '2025-02-19 20:12:00', null, 30, 1),
	(112, 'Grafana for visualization.', '2025-02-19 20:13:00', '2025-02-19 20:13:00', 111, 30, 2),
	(113, 'New Relic is also popular.', '2025-02-19 20:14:00', '2025-02-19 20:14:00', null, 30, 3),
	(114, 'But can be expensive.', '2025-02-19 20:15:00', '2025-02-19 20:15:00', 113, 30, 4),
	(115, 'Use Jaeger for distributed tracing.', '2025-02-19 20:16:00', '2025-02-19 20:16:00', null, 30, 5),
	(116, 'Zipkin is a good alternative.', '2025-02-19 20:17:00', '2025-02-19 20:17:00', 115, 30, 6);
