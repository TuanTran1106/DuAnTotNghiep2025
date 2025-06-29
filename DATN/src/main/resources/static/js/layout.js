function toggleMenu() {
    const sidebar = document.getElementById('sidebarMenu');
    const overlay = document.getElementById('overlay');

    sidebar.classList.toggle('show');
    overlay.classList.toggle('show');
}
document.addEventListener('keydown', function (event) {
    if (event.key === 'Escape') {
        const sidebar = document.getElementById('sidebarMenu');
        const overlay = document.getElementById('overlay');

        if (sidebar.classList.contains('show')) {
            toggleMenu();
        }
    }
});
document.querySelector('.search-box input').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        const searchTerm = this.value;
        alert('Tìm kiếm: ' + searchTerm);
    }
});

document.querySelector('.search-btn').addEventListener('click', function () {
    const searchTerm = document.querySelector('.search-box input').value;
    alert('Tìm kiếm: ' + searchTerm);
});

// Add click animation effect
document.querySelectorAll('.blog-card').forEach(card => {
    card.addEventListener('click', function () {
        this.style.transform = 'scale(0.98)';
        setTimeout(() => {
            this.style.transform = '';
        }, 150);
    });
});

// Add loading animation
document.addEventListener('DOMContentLoaded', function () {
    const cards = document.querySelectorAll('.blog-card');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        setTimeout(() => {
            card.style.transition = 'all 0.6s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 200);
    });
});
function handleNewsletterSubmit(event) {
    event.preventDefault();
    const email = event.target.querySelector('input[type="email"]').value;

    // Simulate newsletter subscription
    if (email) {
        // Add success animation
        const btn = event.target.querySelector('.newsletter-btn');
        const originalText = btn.innerHTML;
        btn.innerHTML = '<i class="fas fa-check"></i> Đã đăng ký';
        btn.style.background = '#27ae60';

        setTimeout(() => {
            btn.innerHTML = originalText;
            btn.style.background = 'linear-gradient(135deg, #f39c12, #e67e22)';
            event.target.reset();
        }, 2000);

        // Show success message
        alert('Cảm ơn bạn đã đăng ký nhận tin từ WatchAura!');
    }
}
document.addEventListener('DOMContentLoaded', function () {
    const footerSections = document.querySelectorAll('.footer-section');
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    });

    footerSections.forEach(section => {
        section.style.opacity = '0';
        section.style.transform = 'translateY(20px)';
        section.style.transition = 'all 0.6s ease';
        observer.observe(section);
    });
});

// function handleNewsletterSubmit(event) {
//     event.preventDefault();
//     const email = event.target.querySelector('.newsletter-input').value;
//
//     // Show success message
//     const button = event.target.querySelector('.newsletter-btn');
//     const originalText = button.innerHTML;
//
//     button.innerHTML = '<i class="fas fa-check me-1"></i>Đã đăng ký!';
//     button.style.background = 'linear-gradient(45deg, #4caf50, #45a049)';
//
//     setTimeout(() => {
//         button.innerHTML = originalText;
//         button.style.background = 'linear-gradient(45deg, #64b5f6, #42a5f5)';
//         event.target.reset();
//     }, 2000);
//
//     console.log('Newsletter subscription:', email);
// }

// Add smooth scroll to footer links
document.querySelectorAll('.footer-section a[href^="#"]').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        // Add your scroll logic here
    });
}); 