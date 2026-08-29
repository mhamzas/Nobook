(function () {
    'use strict';

    // This script only removes content that Facebook explicitly labels as sponsored.
    // It does not block requests or touch ordinary posts, links, or media.
    const BLOCKED_ATTRIBUTE = 'data-nobook-ad-hidden';
    const sponsoredLabels = [
        'sponsored', 'sponsor', 'gesponsert', 'sponsorlu', 'sponsorowane',
        'publicidad', 'sponsorisée', 'sponsorisé', 'sponsorizzato', 'gesponsord',
        'sponset', 'patrocinado', 'sponzorované', 'được tài trợ', '광고',
        '贊助', '赞助内容', '広告', 'реклама', 'במימון', 'سپانسرڈ', 'प्रायोजित'
    ];

    const labelPattern = new RegExp(
        `(?:^|\\s)(${sponsoredLabels.map(escapeRegExp).join('|')})(?:$|\\s|[·•|])`,
        'iu'
    );

    function escapeRegExp(value) {
        return value.replace(/[.*+?^${}()|[\\]\\]/g, '\\$&');
    }

    function isSponsoredText(text) {
        return Boolean(text && labelPattern.test(text.trim()));
    }

    function hideElement(element) {
        if (!(element instanceof HTMLElement) || element.hasAttribute(BLOCKED_ATTRIBUTE)) return;
        element.setAttribute(BLOCKED_ATTRIBUTE, 'true');
        element.style.setProperty('display', 'none', 'important');
        element.setAttribute('aria-hidden', 'true');
    }

    function inspect(root = document) {
        const candidates = root.querySelectorAll?.(
            'article, [role="article"], div[data-mcomponent="MContainer"], div[data-type="vscroller"] div[data-tracking-duration-id]'
        ) || [];

        candidates.forEach(candidate => {
            if (candidate.hasAttribute(BLOCKED_ATTRIBUTE)) return;

            // Keep the scan narrow: only inspect small metadata labels, not the whole post body.
            const labels = candidate.querySelectorAll(
                '[aria-label], .native-text > span, [data-mcomponent="TextArea"] .native-text > span'
            );

            for (const label of labels) {
                const text = label.getAttribute('aria-label') || label.textContent || '';
                if (isSponsoredText(text)) {
                    hideElement(candidate);
                    return;
                }
            }
        });

        // Desktop Facebook uses a stable sponsored-ad marker.
        root.querySelectorAll?.('div.sponsored_ad, article[data-ft*="sponsored_ad"]').forEach(hideElement);
    }

    function scheduleInspection(root) {
        if (scheduleInspection.pending) return;
        scheduleInspection.pending = requestAnimationFrame(() => {
            scheduleInspection.pending = null;
            inspect(root);
        });
    }

    inspect();

    const observer = new MutationObserver(mutations => {
        mutations.forEach(mutation => {
            mutation.addedNodes.forEach(node => {
                if (node instanceof HTMLElement) scheduleInspection(node);
            });
        });
    });

    if (document.body) {
        observer.observe(document.body, { childList: true, subtree: true });
    } else {
        document.addEventListener('DOMContentLoaded', () => {
            inspect();
            observer.observe(document.body, { childList: true, subtree: true });
        }, { once: true });
    }
})();
