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

function renderSidebar() {
    const user = getCurrentUser();
    const isAdmin = user && user.roles && user.roles.includes('ROLE_ADMIN');
    
    const sidebar = document.querySelector('.sidebar');
    if (!sidebar) return;

    let html = `
        <a href="dashboard.html" class="sidebar-link ${window.location.pathname.includes('dashboard') ? 'active' : ''}">
            <i class="fas fa-home"></i> Dashboard
        </a>
        <a href="quiz.html" class="sidebar-link ${window.location.pathname.includes('quiz') ? 'active' : ''}">
            <i class="fas fa-brain"></i> Take Quiz
        </a>
    `;

    if (isAdmin) {
        html += `
            <a href="admin.html" class="sidebar-link ${window.location.pathname.includes('admin') ? 'active' : ''}">
                <i class="fas fa-cog"></i> Admin Panel
            </a>
        `;
    }

    sidebar.innerHTML = html;
}

// Global initialization
document.addEventListener('DOMContentLoaded', () => {
    // Render dynamic sidebar if it exists
    renderSidebar();

    // Attach logout behavior
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            logout();
        });
    }

    // Header hydration 
    const userNameEl = document.getElementById('header-user-name');
    if (userNameEl) {
        const user = getCurrentUser();
        userNameEl.textContent = user ? user.name : 'Guest';
    }
});
