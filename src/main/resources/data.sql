-- ============================================================
-- Lumora Platform - Seed Data (H2 & MySQL compatible)
-- Run automatically via spring.sql.init.mode=always
-- ============================================================

-- ─── COURSES ─────────────────────────────────────────────
MERGE INTO courses (id, title, description)
    KEY(id)
    VALUES (1, 'Python Programming', 'Learn Python from scratch to advanced level');
MERGE INTO courses (id, title, description)
    KEY(id)
    VALUES (2, 'Java Development', 'Core Java and OOP concepts');
MERGE INTO courses (id, title, description)
    KEY(id)
    VALUES (3, 'Data Structures', 'Arrays, Trees, Graphs and Algorithms');
MERGE INTO courses (id, title, description)
    KEY(id)
    VALUES (4, 'Networking', 'Computer Networks and Protocols');
MERGE INTO courses (id, title, description)
    KEY(id)
    VALUES (5, 'Cybersecurity', 'Security principles and best practices');

-- ─── TOPICS ──────────────────────────────────────────────
MERGE INTO topics (id, title, content, notes_url, course_id)
    KEY(id) VALUES (1, 'Python', 'Core Python fundamentals', null, 1);
MERGE INTO topics (id, title, content, notes_url, course_id)
    KEY(id) VALUES (2, 'Java', 'Java OOP and core libraries', null, 2);
MERGE INTO topics (id, title, content, notes_url, course_id)
    KEY(id) VALUES (3, 'Data Structures', 'Arrays, linked lists, trees, graphs', null, 3);
MERGE INTO topics (id, title, content, notes_url, course_id)
    KEY(id) VALUES (4, 'Networking', 'OSI model, TCP/IP, DNS, protocols', null, 4);
MERGE INTO topics (id, title, content, notes_url, course_id)
    KEY(id) VALUES (5, 'Cybersecurity', 'Encryption, hashing, threats, defense', null, 5);

-- ─── QUESTIONS: Python (topic_id = 1) ───────────────────
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(1, 'What is the output of print(type([]))?', '<class list>', '<class ''list''>', '<list>', '<class array>', 'B', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(2, 'Which keyword is used to define a function in Python?', 'func', 'def', 'function', 'define', 'B', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(3, 'What does GIL stand for in Python?', 'General Interface Layer', 'Global Interpreter Lock', 'Global Input Library', 'General Input Lock', 'B', 'MEDIUM', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(4, 'Which of the following is immutable in Python?', 'List', 'Dictionary', 'Tuple', 'Set', 'C', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(5, 'What is the output of 2 ** 10 in Python?', '20', '1024', '210', '100', 'B', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(6, 'Which method adds an element at the end of a list?', 'add()', 'insert()', 'append()', 'push()', 'C', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(7, 'What does "self" represent in a Python class?', 'A global variable', 'The class itself', 'The current instance', 'A reserved word', 'C', 'MEDIUM', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(8, 'How do you open a file for reading in Python?', 'open("f","w")', 'open("f","r")', 'file.open("f")', 'read("f")', 'B', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(9, 'Which is NOT a Python data type?', 'int', 'float', 'char', 'bool', 'C', 'EASY', 1);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(10, 'What is a lambda function?', 'A named function', 'An anonymous function', 'A built-in function', 'A recursive function', 'B', 'MEDIUM', 1);

-- ─── QUESTIONS: Java (topic_id = 2) ──────────────────────
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(11, 'How do you declare an integer variable in Java?', 'int x;', 'x int;', 'variable x;', 'declare int x;', 'A', 'EASY', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(12, 'What is the parent class of all Java classes?', 'Base', 'Super', 'Object', 'Main', 'C', 'EASY', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(13, 'Which keyword is used to inherit a class in Java?', 'implements', 'inherits', 'extends', 'super', 'C', 'EASY', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(14, 'What is the default value of an int in Java?', 'null', '0', '-1', 'undefined', 'B', 'EASY', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(15, 'Which is NOT a Java access modifier?', 'public', 'private', 'protected', 'shared', 'D', 'MEDIUM', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(16, 'What does JVM stand for?', 'Java Virtual Module', 'Java Visual Machine', 'Java Virtual Machine', 'Java Variable Management', 'C', 'EASY', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(17, 'How do you create an object of class Car in Java?', 'Car obj = Car();', 'Car obj = new Car();', 'new Car obj;', 'obj Car = new();', 'B', 'EASY', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(18, 'Which interface must be implemented to create a thread?', 'Runnable', 'Threadable', 'Parallel', 'Executor', 'A', 'MEDIUM', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(19, 'What is autoboxing in Java?', 'Automatic memory allocation', 'Converting primitives to wrapper types', 'Auto-importing libraries', 'Box-like data structures', 'B', 'MEDIUM', 2);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(20, 'Which collection maintains insertion order and allows duplicates?', 'HashSet', 'TreeSet', 'ArrayList', 'HashMap', 'C', 'MEDIUM', 2);

-- ─── QUESTIONS: Data Structures (topic_id = 3) ───────────
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(21, 'Time complexity of accessing an array element by index?', 'O(n)', 'O(log n)', 'O(1)', 'O(n2)', 'C', 'EASY', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(22, 'Which data structure uses LIFO order?', 'Queue', 'Stack', 'Linked List', 'Tree', 'B', 'EASY', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(23, 'Which data structure uses FIFO order?', 'Stack', 'Graph', 'Queue', 'Heap', 'C', 'EASY', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(24, 'Worst-case time complexity of QuickSort?', 'O(n log n)', 'O(n)', 'O(n2)', 'O(log n)', 'C', 'MEDIUM', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(25, 'In a BST, what is the rule for node placement?', 'All nodes have two children', 'Left < parent < right', 'A balanced tree', 'Leaf nodes only', 'B', 'MEDIUM', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(26, 'Which traversal visits Root → Left → Right?', 'Inorder', 'Postorder', 'Preorder', 'Level-order', 'C', 'MEDIUM', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(27, 'Space complexity of Merge Sort?', 'O(1)', 'O(log n)', 'O(n)', 'O(n2)', 'C', 'MEDIUM', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(28, 'Which structure is best for a priority queue?', 'Stack', 'Heap', 'Array', 'Linked List', 'B', 'MEDIUM', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(29, 'What is a hash collision?', 'Two keys map to same hash value', 'Hash function fails', 'Memory overflow', 'Hash table is full', 'A', 'HARD', 3);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(30, 'Average time complexity of Hash Table search?', 'O(n)', 'O(log n)', 'O(n log n)', 'O(1)', 'D', 'EASY', 3);

-- ─── QUESTIONS: Networking (topic_id = 4) ─────────────────
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(31, 'How many layers does the OSI model have?', '5', '6', '7', '8', 'C', 'EASY', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(32, 'Which OSI layer is responsible for routing?', 'Data Link', 'Transport', 'Network', 'Session', 'C', 'MEDIUM', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(33, 'What does DNS stand for?', 'Data Network System', 'Domain Name System', 'Dynamic Name Service', 'Digital Network Service', 'B', 'EASY', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(34, 'Which protocol is used to send email?', 'FTP', 'HTTP', 'SMTP', 'SSH', 'C', 'EASY', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(35, 'What is the size of an IPv4 address?', '64 bits', '32 bits', '128 bits', '256 bits', 'B', 'EASY', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(36, 'Default port for HTTPS?', '80', '8080', '443', '22', 'C', 'EASY', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(37, 'Which protocol provides reliable, connection-oriented communication?', 'UDP', 'ICMP', 'ARP', 'TCP', 'D', 'MEDIUM', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(38, 'What is a subnet mask used for?', 'Encrypting data', 'Dividing a network into subnets', 'Routing external traffic', 'Assigning MAC addresses', 'B', 'MEDIUM', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(39, 'Which protocol maps IP to MAC addresses?', 'DNS', 'DHCP', 'ARP', 'NAT', 'C', 'MEDIUM', 4);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(40, 'What does NAT stand for?', 'Network Address Translation', 'Node Access Table', 'Network Access Token', 'Node Address Transfer', 'A', 'MEDIUM', 4);

-- ─── QUESTIONS: Cybersecurity (topic_id = 5) ──────────────
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(41, 'What does CIA stand for in cybersecurity?', 'Control Integrity Access', 'Confidentiality Integrity Availability', 'Cybersecurity Information Access', 'Control Identity Auth', 'B', 'EASY', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(42, 'What attack floods a server to crash it?', 'Phishing', 'MITM', 'DoS/DDoS', 'SQL Injection', 'C', 'EASY', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(43, 'What is a firewall?', 'A physical barrier', 'Software/hardware filtering network traffic', 'An antivirus tool', 'A type of encryption', 'B', 'EASY', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(44, 'What does SSL/TLS provide?', 'Authentication only', 'Encrypted communication over the network', 'Data compression', 'Network routing', 'B', 'MEDIUM', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(45, 'What is phishing?', 'Brute-force cracking', 'Tricking users into revealing sensitive info', 'Port scanning', 'SQL injection', 'B', 'EASY', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(46, 'What is a zero-day vulnerability?', 'Bug fixed day 0', 'Security flaw unknown to vendor', 'Vulnerability with no fix', 'A test vulnerability', 'B', 'HARD', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(47, 'Why do we hash passwords?', 'Store them in readable form', 'Speed up login', 'One-way transform so original cannot be retrieved', 'Send passwords securely', 'C', 'MEDIUM', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(48, 'Which is a symmetric encryption algorithm?', 'RSA', 'AES', 'ECC', 'Diffie-Hellman', 'B', 'MEDIUM', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(49, 'What is a MITM attack?', 'Attacker intercepts communication between two parties', 'Brute-force login', 'Ransomware execution', 'DNS flooding', 'A', 'MEDIUM', 5);
MERGE INTO questions (id, text, option_a, option_b, option_c, option_d, correct_option, difficulty, topic_id) KEY(id) VALUES
(50, 'What is two-factor authentication (2FA)?', 'Two passwords', 'A password + biometric/OTP', 'Two email addresses', 'Encrypted login', 'B', 'EASY', 5);
