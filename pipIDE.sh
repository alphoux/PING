#!/bin/sh
(cd java-backend && mvn spring-boot:run ) &
(cd react-frontend/ping-react-frontend && npm run dev )