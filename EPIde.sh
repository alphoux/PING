#!/bin/sh
if [ ${#} -eq 1 ] && [ ${1} == 'speech' ]; then
    echo "yes"
fi

(cd java-backend && mvn spring-boot:run ) &
(cd react-frontend/ping-react-frontend && npm run dev )