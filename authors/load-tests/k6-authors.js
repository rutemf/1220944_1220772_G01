import http from 'k6/http';
import {check} from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 5 },
        { duration: '30s', target: 10 },
        { duration: '10s', target: 10 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<3000'],
    },
};

const BASE_URL = 'http://143.47.57.150:4677/authors/api/authors';
const LOGIN_URL = "http://143.47.57.150:4677/users/api/public/login";

function extractAuthHeader(res) {
    return res.headers["Authorization"];
}

export function setup() {
    const username = "maria@gmail.com";
    const password = "Maria!123";

    const payload = JSON.stringify({ username, password });
    const params = { headers: { "Content-Type": "application/json" } };

    const res = http.post(LOGIN_URL, payload, params);

    const ok = check(res, { "Login Status Is 200": (r) => r.status === 200 });

    if (!ok) {
        fail(`Login Failed. Status=${res.status} Body=${res.body}`);
    }

    const auth = extractAuthHeader(res);
    if (!auth) {
        fail(
            `Login Succeeded But No Authorization Header Found. Headers=${JSON.stringify(
                res.headers
            )}`
        );
    }

    const token = auth.startsWith("Bearer ") ? auth : `Bearer ${auth}`;

    return { token };
}

export default function (data) {
    const authorName = 'Rute Maia';
    const url = `${BASE_URL}/${encodeURIComponent(authorName)}`;

    const res = http.get(url, {
        headers: {
            Authorization: data.token,
        },
    });

    check(res, {
        'Status Is 200': (r) => r.status === 200,
    });
}
