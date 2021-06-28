const config = {
    'localhost': '/api',
    '127.0.0.1': '/api',
    'bsp-ai-ui.dev.k8s.chehejia.com': 'https://bsp-rule-service.dev.k8s.chehejia.com',
    'bsp-ai-ui.test.k8s.chehejia.com': 'https://bsp-rule-service.test.k8s.chehejia.com'
}

const domain = new URL(window.location.href).hostname;

window._server = config[domain] ? config[domain] : 'https://bsp-rule-service.dev.k8s.chehejia.com' + '/urule';
window._project = 'projectName';
