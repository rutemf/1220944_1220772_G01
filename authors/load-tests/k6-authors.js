import http from 'k6/http';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 5 },
        { duration: '30s', target: 10 },
        { duration: '10s', target: 10 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],
    },
};

const BASE_URL = 'http://143.47.57.150:8081/authors/api/authors';

export default function () {
    const authorName = 'Miguel';
    const url = `${BASE_URL}/${encodeURIComponent(authorName)}`;

    const res = http.get(url);

    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}
