// Mobile navigation toggle
const menuToggle = document.querySelector('.menu-toggle');
const nav = document.querySelector('nav');

menuToggle.addEventListener('click', () => {
    nav.classList.toggle('active');
});

// Close mobile menu when clicking outside
document.addEventListener('click', (e) => {
    if (!e.target.closest('nav') && !e.target.closest('.menu-toggle') && nav.classList.contains('active')) {
        nav.classList.remove('active');
    }
});

// Close mobile menu when clicking on a nav link
const navLinks = document.querySelectorAll('.nav-link');
navLinks.forEach(link => {
    link.addEventListener('click', () => {
        if (nav.classList.contains('active')) {
            nav.classList.remove('active');
        }
    });
});

// Tabs functionality for demo section
const tabBtns = document.querySelectorAll('.tab-btn');
const tabContents = document.querySelectorAll('.tab-content');

tabBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        // Remove active class from all buttons and contents
        tabBtns.forEach(b => b.classList.remove('active'));
        tabContents.forEach(content => content.classList.remove('active'));
        
        // Add active class to clicked button
        btn.classList.add('active');
        
        // Show corresponding content
        const tabId = btn.getAttribute('data-tab');
        document.getElementById(tabId).classList.add('active');
    });
});

// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();
        const targetId = this.getAttribute('href');
        
        if (targetId === '#') return;
        
        const targetElement = document.querySelector(targetId);
        if (targetElement) {
            // Get header height to adjust scroll position
            const headerHeight = document.querySelector('header').offsetHeight;
            const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset - headerHeight;
            
            window.scrollTo({
                top: targetPosition,
                behavior: 'smooth'
            });
        }
    });
});

// Add active class to nav links based on scroll position
window.addEventListener('scroll', () => {
    const sections = document.querySelectorAll('section[id]');
    const scrollPosition = window.pageYOffset + 100;
    
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.offsetHeight;
        const sectionId = section.getAttribute('id');
        
        if (scrollPosition >= sectionTop && scrollPosition < sectionTop + sectionHeight) {
            document.querySelector(`.nav-link[href="#${sectionId}"]`).classList.add('active');
        } else {
            document.querySelector(`.nav-link[href="#${sectionId}"]`).classList.remove('active');
        }
    });
});

// Add interactive demo functionality
// This simulates the app functionality for demonstration purposes
document.addEventListener('DOMContentLoaded', () => {
    // Replace placeholder images with more descriptive content
    const demoImages = document.querySelectorAll('.demo-image img');
    if (demoImages.length > 0) {
        // You would replace these with actual images in production
        // For now, we'll add borders and styling to make placeholders more meaningful
        demoImages.forEach(img => {
            img.style.border = '1px solid #e2e8f0';
            img.style.borderRadius = '8px';
        });
    }
    
    // Add download button functionality
    const downloadBtn = document.querySelector('.download-btn');
    if (downloadBtn) {
        downloadBtn.addEventListener('click', (e) => {
            // In a real app, this would download the file
            // For demo purposes, we'll show an alert
            if (!confirm('This is a demo. In a real application, this would download the Smart Habit Tracker JAR file. Continue?')) {
                e.preventDefault();
            }
        });
    }
    
    // Add keyboard shortcut functionality demonstration
    const shortcuts = {
        'KeyN': { key: 'Ctrl+N', action: 'Add new habit' },
        'KeyT': { key: 'Ctrl+T', action: 'Today\'s view' },
        'KeyS': { key: 'Ctrl+S', action: 'Statistics page' },
        'KeyE': { key: 'Ctrl+E', action: 'Export data' },
        'KeyI': { key: 'Ctrl+I', action: 'Import data' },
        'KeyH': { key: 'Ctrl+H', action: 'Help page' }
    };
    
    // Create a notification element for shortcut demonstrations
    const notification = document.createElement('div');
    notification.className = 'shortcut-notification';
    notification.style.cssText = `
        position: fixed;
        bottom: 20px;
        right: 20px;
        background-color: var(--primary-color);
        color: white;
        padding: 12px 20px;
        border-radius: var(--radius);
        box-shadow: var(--shadow-lg);
        display: none;
        z-index: 1000;
        transition: all 0.3s ease;
    `;
    document.body.appendChild(notification);
    
    // Listen for keyboard shortcuts
    document.addEventListener('keydown', (e) => {
        if ((e.ctrlKey || e.metaKey) && shortcuts[e.code]) {
            e.preventDefault();
            const shortcut = shortcuts[e.code];
            
            // Show notification
            notification.textContent = `${shortcut.key}: ${shortcut.action}`;
            notification.style.display = 'block';
            
            // Hide notification after 2 seconds
            setTimeout(() => {
                notification.style.display = 'none';
            }, 2000);
        }
    });
    
    // Add animation to instruction cards
    const instructionCards = document.querySelectorAll('.instruction-card');
    instructionCards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            card.style.transform = 'translateY(-10px)';
        });
        
        card.addEventListener('mouseleave', () => {
            card.style.transform = 'translateY(0)';
        });
    });
});

// Add CSS fix for placeholder images
document.addEventListener('DOMContentLoaded', function() {
    // Style placeholder images to make them more visually appealing
    const placeholderImages = document.querySelectorAll('img[src^="/api/placeholder"]');
    placeholderImages.forEach(img => {
        img.style.backgroundColor = '#f0f4ff';
        img.style.border = '1px solid #e2e8f0';
        
        // Create and overlay text to indicate this is a placeholder
        const parent = img.parentElement;
        parent.style.position = 'relative';
        
        const overlay = document.createElement('div');
        overlay.style.cssText = `
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: rgba(76, 111, 255, 0.1);
            color: var(--primary-color);
            padding: 10px 15px;
            border-radius: 4px;
            font-weight: 500;
            pointer-events: none;
        `;
        
        // Determine what placeholder text to show based on image context
        if (img.alt.includes('Dashboard')) {
            overlay.textContent = 'App Dashboard Preview';
        } else if (img.alt.includes('Adding')) {
            overlay.textContent = 'New Habit Form';
        } else if (img.alt.includes('Tracking')) {
            overlay.textContent = 'Daily Tracking View';
        } else if (img.alt.includes('Viewing')) {
            overlay.textContent = 'Progress Statistics';
        } else {
            overlay.textContent = 'Image Preview';
        }
        
        parent.appendChild(overlay);
    });
});