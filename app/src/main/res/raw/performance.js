(function () {
  if (window.__nobookPerformanceActive) return;
  window.__nobookPerformanceActive = true;

  try {
    var style = document.createElement('style');
    style.setAttribute('data-nobook-performance', 'true');
    style.textContent = 'div[role="article"], div[data-pagelet^="FeedUnit"] { content-visibility: auto; contain-intrinsic-size: 600px 400px; }';
    (document.head || document.documentElement).appendChild(style);

    var observed = new WeakSet();
    var observer = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        var video = entry.target;
        if (entry.isIntersecting && entry.intersectionRatio > 0.25) {
          video.preload = 'auto';
          if (video.dataset.nobookWasPlaying === '1') {
            var playResult = video.play();
            if (playResult && playResult.catch) playResult.catch(function () {});
          }
        } else {
          video.dataset.nobookWasPlaying = video.paused ? '0' : '1';
          if (!video.paused) video.pause();
          video.preload = 'none';
        }
      });
    }, { rootMargin: '200px 0px', threshold: [0, 0.25] });

    function observeVideos() {
      document.querySelectorAll('video').forEach(function (video) {
        if (!observed.has(video)) {
          observed.add(video);
          observer.observe(video);
        }
      });
    }

    observeVideos();
    new MutationObserver(observeVideos).observe(document.body, { childList: true, subtree: true });
  } catch (error) {
    console.error('[Nobook] performance optimization failed', error);
  }
})();
