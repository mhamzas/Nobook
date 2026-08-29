(function () {
  if (window.__nobookAntiReloadActive) return;
  window.__nobookAntiReloadActive = true;

  try {
    document.addEventListener('visibilitychange', function (event) {
      if (document.hidden) event.stopImmediatePropagation();
    }, true);
    window.addEventListener('pagehide', function (event) {
      event.stopImmediatePropagation();
    }, true);
  } catch (error) {
    console.error('[Nobook] anti-reload helper failed', error);
  }
})();
