#!/bin/sh

(cd java-backend && mvn spring-boot:run ) &
(cd react-frontend/ping-react-frontend && npm run dev ) &
(if [ ${#} -eq 1 ] && [ ${1} == 'speech' ]; then
    (festival --server)
fi)