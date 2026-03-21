# Stage 1: Build backend
FROM maven:3.9-eclipse-temurin-21 AS backend-build
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline
COPY backend/src ./src
RUN mvn package -DskipTests

# Stage 2: Build frontend
FROM node:20-alpine AS frontend-build
WORKDIR /app
COPY frontend/package*.json .
RUN npm ci
COPY frontend/ .
RUN npm run build

# Stage 3: Runtime — nginx + Java in one container
FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache nginx supervisor

# Copy backend jar
WORKDIR /app
COPY --from=backend-build /app/target/*.jar app.jar

# Copy frontend build
COPY --from=frontend-build /app/dist /usr/share/nginx/html

# Config files
RUN mkdir -p /run/nginx
COPY nginx.conf /etc/nginx/http.d/default.conf
COPY supervisord.conf /etc/supervisord.conf

# Render sets PORT env var (default 10000). Sed it into nginx at startup.
# Use a startup script to handle the dynamic port.
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

EXPOSE 10000
CMD ["/app/start.sh"]
