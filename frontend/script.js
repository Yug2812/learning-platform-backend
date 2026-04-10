const API_BASE_URL = 'http://localhost:8080/api';

// --- Auth Utils ---
function getToken() {
    return localStorage.getItem('jwt');
}

function setToken(token) {
    localStorage.setItem('jwt', token);
}

function clearToken() {
    localStorage.removeItem('jwt');
}

function isAuthenticated() {
    return !!getToken();
}

function logout() {
    clearToken();
    localStorage.removeItem('user');
    window.location.href = 'login.html';
}

function getCurrentUser() {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
}

function setCurrentUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
}

// --- API Wrapper ---
async function apiCall(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    const token = getToken();
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        ...options,
        headers
    };

    try {
        const response = await fetch(url, config);
        
        if (response.status === 401) {
            // Unauthorized - token expired or invalid
            logout();
            throw new Error('Session expired. Please login again.');
        }

        const data = await response.json().catch(() => null);
        
        if (!response.ok) {
            const errorMsg = (data && data.message) ? data.message : 'An error occurred';
            throw new Error(errorMsg);
        }

        return data; 
    } catch (error) {
        console.error(`API Error on ${endpoint}:`, error);
        throw error;
    }
}

// --- UI Utils ---
function showAlert(elementId, message, type = 'error') {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = message;
    el.className = `alert alert-${type}`;
    el.style.display = 'block';
    
    // Auto hide after 5 seconds
    setTimeout(() => {
        el.style.display = 'none';
        el.className = 'alert';
    }, 5000);
}

function protectRoute() {
    if (!isAuthenticated()) {
        window.location.href = 'login.html';
    }
}

function renderHeader() {
    const headerContainer = document.getElementById('app-header');
    if (!headerContainer) return;

    const user = getCurrentUser();
    const userName = user ? user.name : 'Guest';
    const userRole = user && user.roles && user.roles.length > 0 ? user.roles[0].replace('ROLE_', '') : '';
    const userInitial = userName.charAt(0).toUpperCase();
    const isAdmin = user && user.roles && user.roles.includes('ROLE_ADMIN');

    // Determine active path
    const path = window.location.pathname;
    
    // Theme icon
    const currentTheme = localStorage.getItem('theme') || 'dark';
    const themeIcon = currentTheme === 'light' ? '🌙' : '🌞';

    headerContainer.innerHTML = `
        <div class="header-content" style="display: flex; justify-content: space-between; align-items: center; width: 100%;">
            <div class="header-brand" style="font-weight: 700; font-size: 1.5rem; color: var(--accent);">
                ✨ Lumora
            </div>
            
            <div class="header-nav" style="display: flex; align-items: center; gap: 1rem;">
                <a href="dashboard.html" class="btn-nav ${path.includes('dashboard') ? 'active' : ''}">Dashboard</a>
                <a href="quiz.html" class="btn-nav ${path.includes('quiz') ? 'active' : ''}">Take Quiz</a>
                ${isAdmin ? `<a href="admin.html" class="btn-nav ${path.includes('admin') ? 'active' : ''}">Admin Panel</a>` : ''}
                
                <div style="width: 1px; height: 24px; background: var(--glass-border); margin: 0 0.5rem;"></div>
                
                <button id="themeToggleBtn" class="btn-icon" title="Toggle Day/Night Mode">${themeIcon}</button>
                
                <div class="user-dropdown-container" style="display: flex; align-items: center; gap: 0.5rem; margin-left: 0.5rem;">
                    <span style="font-weight: 500; font-size: 1rem; color: var(--text-primary); cursor: pointer;" id="userNameBtn">${userName}</span>
                    <button class="user-profile-btn" id="userDropdownToggle">
                        ${userInitial}
                    </button>
                    <div class="dropdown-menu" id="userDropdownMenu" style="right: 0; left: auto;">
                        <div class="dropdown-header">
                            <strong style="color: var(--text-primary);">${userName}</strong>
                            <span style="display:block; font-size: 0.8rem; color: var(--text-secondary);">${userRole}</span>
                        </div>
                        <hr style="border-color: var(--glass-border); margin: 0.5rem 0;">
                        <a href="profile.html" class="dropdown-item">🧑‍🎓 My Profile</a>
                        <a href="settings.html" class="dropdown-item">⚙️ Settings</a>
                        <a href="#" class="dropdown-item text-danger" id="logout-btn-dynamic">🛑 Logout</a>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.getElementById('themeToggleBtn').addEventListener('click', () => {
        const root = document.documentElement;
        const currentTheme = root.getAttribute('data-theme');
        const newTheme = currentTheme === 'light' ? 'dark' : 'light';
        root.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);
        document.getElementById('themeToggleBtn').textContent = newTheme === 'light' ? '🌙' : '🌞';
    });

    const toggleDropdown = (e) => {
        e.stopPropagation();
        const menu = document.getElementById('userDropdownMenu');
        menu.classList.toggle('show');
    };

    document.getElementById('userDropdownToggle').addEventListener('click', toggleDropdown);
    document.getElementById('userNameBtn').addEventListener('click', toggleDropdown);

    document.addEventListener('click', (e) => {
        const menu = document.getElementById('userDropdownMenu');
        if (menu && menu.classList.contains('show') && !e.target.closest('.user-dropdown-container')) {
            menu.classList.remove('show');
        }
    });

    const logoutDynamic = document.getElementById('logout-btn-dynamic');
    if(logoutDynamic) {
        logoutDynamic.addEventListener('click', (e) => {
            e.preventDefault();
            logout();
        });
    }
}

function renderFooter() {
    const footerContainer = document.getElementById('app-footer');
    if (!footerContainer) return;

    footerContainer.innerHTML = `
        <div style="max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 1rem;">
            <div style="font-weight: 600; font-size: 1rem; color: var(--accent);">✨ Lumora</div>
            <div style="display: flex; gap: 1.5rem; color: var(--text-secondary); font-size: 0.9rem;">
                <a href="#" style="color: var(--text-secondary); text-decoration: none;">Help &amp; Support</a>
            </div>
            <div style="color: var(--text-secondary); font-size: 0.85rem;">&copy; ${new Date().getFullYear()} Lumora. All rights reserved.</div>
        </div>
    `;
}

// Global initialization
document.addEventListener('DOMContentLoaded', () => {
    // Initialize Theme
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
        document.documentElement.setAttribute('data-theme', savedTheme);
    }

    // Render global header and footer
    renderHeader();
    renderFooter();
});
