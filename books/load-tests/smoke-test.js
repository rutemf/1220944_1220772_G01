import http from "k6/http";
import { check, fail } from "k6";

export const options = {
    vus: 1,
    iterations: 1,
    thresholds: {
        http_req_duration: ["p(95)<5000"],
    },
};

const BASE_URL = 'http://143.47.57.150:4677/books/api/books';
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
    const isbn = '9789720706386';
    const url = `${BASE_URL}/${encodeURIComponent(isbn)}`;

    const res = http.get(url, {
        headers: { Authorization: data.token },
    });

    check(res, { "Status Is 200": (r) => r.status === 200 });
}
