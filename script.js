const gif = document.querySelector('.animated-gif');

function toggleGifPlayback(entries) {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            gif.src = 'path/to/your/animated.gif'; // Запускає GIF
        } else {
            gif.src = ''; // Зупиняє GIF, очищаючи src
        }
    });
}

// Створення обсерверу для відстеження видимості GIF
const observer = new IntersectionObserver(toggleGifPlayback, {
    threshold: 0.5
});

observer.observe(gif);
