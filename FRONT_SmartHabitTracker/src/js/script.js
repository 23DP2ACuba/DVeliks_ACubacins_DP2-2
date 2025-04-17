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

// Instruction cards data - updated based on the application screenshot
const instructionCardsData = [
    {
        number: 1,
        title: "Launch the Application",
        content: "Start by downloading and launching the Smart Habit Tracker jar file. You'll see a clean, simple interface with a table ready for your habits.",
        steps: [
            "Download the jar file from our website",
            "Ensure you have Java installed on your system",
            "Double-click the jar file or run via command line",
            "The main interface will appear with an empty table"
        ]
    },
    {
        number: 2,
        title: "Adding Your First Habit",
        content: "Adding habits is straightforward with our simple interface. Just click the 'Add Habit' button and enter your habit name.",
        steps: [
            "Click the 'Add Habit' button at the top of the window",
            "Enter a name for your habit (e.g., 'Exercise', 'Read')",
            "Confirm to add the habit to your tracking table",
            "Your new habit will appear in the table with a status of 'Not Started'"
        ]
    },
    {
        number: 3,
        title: "Viewing Your Habits",
        content: "The main interface shows all your habits in a table format with their status and current streak count.",
        steps: [
            "The main table displays habit name, status, streak, and actions",
            "Use the 'View Habits' button to refresh or filter your habits",
            "See your total habits and completion count at the bottom",
            "Sort habits by clicking on column headers (if available)"
        ]
    },
    {
        number: 4,
        title: "Tracking Daily Progress",
        content: "Each day, mark your habits as complete to build your streak and stay consistent with your goals.",
        steps: [
            "For each completed habit, use the action buttons in the Actions column",
            "Mark a habit as complete to increase your streak count",
            "The status will update to show 'Completed'",
            "Your completion stats at the bottom will update automatically"
        ]
    },
    {
        number: 5,
        title: "Managing Your Habits",
        content: "You can easily modify, delete, or reset your habits as your goals and priorities change.",
        steps: [
            "Use action buttons in the Actions column to manage each habit",
            "Delete habits you no longer want to track",
            "Reset streaks if needed to start fresh",
            "Edit habit names or details as your goals evolve"
        ]
    },
    {
        number: 6,
        title: "Understanding Your Progress",
        content: "The simple summary at the bottom of the app helps you track your overall progress and completion rates.",
        steps: [
            "View total habit count to see how many habits you're tracking",
            "Check completion count to see how many habits you've completed today",
            "Compare these numbers to understand your daily success rate",
            "Use this information to adjust your habits if needed"
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