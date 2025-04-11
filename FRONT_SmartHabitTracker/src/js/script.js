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

// Instruction cards data - this would typically come from an API or database
const instructionCardsData = [
    {
        number: 1,
        title: "Setting Up Your Account",
        content: "Launch the application and create your personal profile. You can customize your settings, themes, and notification preferences from the settings menu.",
        steps: [
            "Download and install the application",
            "Open the app and click 'Create Account'",
            "Fill in your details and preferences",
            "Save your settings"
        ]
    },
    {
        number: 2,
        title: "Creating Your First Habit",
        content: "Add a new habit by clicking the '+' button. Give your habit a name and set your target frequency (daily, weekly, etc.).",
        steps: [
            "Click the '+' button in the bottom-right corner",
            "Enter a name for your habit",
            "Select frequency (daily, specific days, etc.)",
            "Set reminders if needed"
        ]
    },
    {
        number: 3,
        title: "Tracking Daily Progress",
        content: "Each day, open the app and mark your habits as completed. This builds your streak and keeps you motivated.",
        steps: [
            "Open the app to view your habits for today",
            "Tap the checkbox next to completed habits",
            "View your current streak for motivation",
            "Check weekly summary for insights"
        ]
    },
    {
        number: 4,
        title: "Viewing Your Statistics",
        content: "Navigate to the Statistics tab to see your progress over time. This helps you understand your habits and identify patterns.",
        steps: [
            "Click on the 'Statistics' tab",
            "Select a habit to view detailed metrics",
            "Use filters to analyze specific time periods",
            "Export data if needed for external analysis"
        ]
    },
    {
        number: 5,
        title: "Setting Up Reminders",
        content: "Configure reminders to help you stay on track with your habits. You can set specific times for notifications.",
        steps: [
            "Go to habit settings by clicking the gear icon",
            "Toggle 'Reminders' option to ON",
            "Set preferred time for notifications",
            "Choose repeat options (daily, weekly, etc.)"
        ]
    },
    {
        number: 6,
        title: "Customizing Your Habits",
        content: "Make your habits more personal by adding notes, changing colors, or setting specific goals for each one.",
        steps: [
            "Long press on a habit to edit",
            "Tap 'Customize' from the menu",
            "Choose colors, icons, or add notes",
            "Set specific targets if applicable"
        ]
    }
];

// Number of instruction cards to display initially and load each time
const cardsToDisplay = 3;
let currentCardIndex = 0;

// Function to create instruction card HTML
function createInstructionCard(cardData) {
    const card = document.createElement('div');
    card.className = 'instruction-card';
    
    const stepsHTML = cardData.steps.map(step => 
        `<li><i class="fas fa-arrow-right"></i> ${step}</li>`
    ).join('');
    
    card.innerHTML = `
        <div class="instruction-number">${cardData.number}</div>
        <div class="instruction-content">
            <h3>${cardData.title}</h3>
            <p>${cardData.content}</p>
            <div class="instruction-steps">
                <ul>
                    ${stepsHTML}
                </ul>
            </div>
        </div>
    `;
    
    return card;
}

// Initialize instruction cards
const instructionsGrid = document.getElementById('instructions-grid');
const loadingIndicator = document.getElementById('loading-indicator');

// Add blue background to instructions section
document.addEventListener('DOMContentLoaded', () => {
    // Apply blue background to instructions section
    const instructionsSection = document.getElementById('instructions');
    instructionsSection.style.backgroundColor = 'var(--secondary-color)'; // Using the secondary color from CSS variables
    
    // Add some padding for better appearance
    instructionsSection.style.paddingTop = '5rem';
    instructionsSection.style.paddingBottom = '5rem';
    instructionsSection.style.marginTop = '0';
    instructionsSection.style.marginBottom = '0';
});

// Function to load more cards
function loadMoreCards() {
    if (currentCardIndex >= instructionCardsData.length) {
        // If we've loaded all cards, hide the loading indicator
        loadingIndicator.classList.remove('visible');
        return;
    }
    
    // Show loading indicator
    loadingIndicator.classList.add('visible');
    
    // Simulate loading delay
    setTimeout(() => {
        // Calculate how many cards to load (up to cardsToDisplay or remaining cards)
        const cardsToLoad = Math.min(cardsToDisplay, instructionCardsData.length - currentCardIndex);
        
        // Load the cards
        for (let i = 0; i < cardsToLoad; i++) {
            if (currentCardIndex < instructionCardsData.length) {
                const card = createInstructionCard(instructionCardsData[currentCardIndex]);
                instructionsGrid.appendChild(card);
                currentCardIndex++;
                
                // Add animation for card appearance
                card.style.opacity = '0';
                card.style.transform = 'translateY(20px)';
                
                setTimeout(() => {
                    card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, 50 * i); // Stagger the animations
            }
        }
        
        // Hide loading indicator after loading
        loadingIndicator.classList.remove('visible');
    }, 800); // Simulate network delay
}

// Initial load of cards
document.addEventListener('DOMContentLoaded', () => {
    loadMoreCards();
    
    // Add scroll event listener for infinite scroll
    window.addEventListener('scroll', () => {
        // Check if we're near the bottom of the instructions section
        const instructionsSection = document.getElementById('instructions');
        const rect = instructionsSection.getBoundingClientRect();
        const isNearBottom = rect.bottom <= window.innerHeight + 300; // 300px before the end
        
        if (isNearBottom && !loadingIndicator.classList.contains('visible') && 
            currentCardIndex < instructionCardsData.length) {
            loadMoreCards();
        }
    });
});

// Animation for feature cards
const featureCards = document.querySelectorAll('.feature-card');
window.addEventListener('scroll', () => {
    featureCards.forEach(card => {
        const cardTop = card.getBoundingClientRect().top;
        const windowHeight = window.innerHeight;
        
        if (cardTop < windowHeight - 100) {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }
    });
});

// Initialize the feature cards with animation properties
featureCards.forEach(card => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(20px)';
    card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
});

// Keyboard shortcuts functionality
document.addEventListener('keydown', (e) => {
    // Check if Ctrl key is pressed
    if (e.ctrlKey) {
        switch (e.key.toLowerCase()) {
            case 'n':
                alert('New Habit shortcut triggered');
                e.preventDefault();
                break;
            case 't':
                alert('Today\'s View shortcut triggered');
                e.preventDefault();
                break;
            case 's':
                alert('Statistics shortcut triggered');
                e.preventDefault();
                break;
            case 'e':
                alert('Export Data shortcut triggered');
                e.preventDefault();
                break;
            case 'i':
                alert('Import Data shortcut triggered');
                e.preventDefault();
                break;
            case 'h':
                alert('Help shortcut triggered');
                e.preventDefault();
                break;
        }
    }
});

// Add CSS class to highlight scroll position in navigation
function highlightNavOnScroll() {
    const scrollPosition = window.scrollY;
    
    // Get all sections
    const sections = document.querySelectorAll('section[id]');
    
    sections.forEach(section => {
        const sectionTop = section.offsetTop - 100; // Offset for header
        const sectionBottom = sectionTop + section.offsetHeight;
        const sectionId = section.getAttribute('id');
        
        if (scrollPosition >= sectionTop && scrollPosition < sectionBottom) {
            // Remove active class from all nav links
            document.querySelectorAll('.nav-link').forEach(link => {
                link.classList.remove('nav-active');
            });
            
            // Add active class to corresponding nav link
            const activeLink = document.querySelector(`.nav-link[href="#${sectionId}"]`);
            if (activeLink) {
                activeLink.classList.add('nav-active');
            }
        }
    });
}

// Call highlight function on scroll
window.addEventListener('scroll', highlightNavOnScroll);

// Call once on load to set initial state
document.addEventListener('DOMContentLoaded', highlightNavOnScroll);

// Make keyboard shortcuts section stand out with a subtle blue border
document.addEventListener('DOMContentLoaded', () => {
    const keyboardShortcuts = document.querySelector('.keyboard-shortcuts');
    if (keyboardShortcuts) {
        keyboardShortcuts.style.borderLeft = '4px solid var(--primary-color)';
        keyboardShortcuts.style.paddingLeft = '2rem';
    }
});