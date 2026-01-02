import http from 'k6/http';
import {check} from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 5 },
        { duration: '20s', target: 10 },
        { duration: '10s', target: 10 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<3000'],
    },
};

const LOGIN_URL = "http://143.47.57.150:4677/users/api/public/login";

export default function () {
    const username = "maria@gmail.com";
    const password = "Maria!123";

    const payload = JSON.stringify({ username, password });
    const params = { headers: { "Content-Type": "application/json" } };

    const res = http.post(LOGIN_URL, payload, params);

    check(res, {
        'Status Is 200': (r) => r.status === 200,
    });
}
