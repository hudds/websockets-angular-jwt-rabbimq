FROM node:lts-alpine AS builder

WORKDIR /app

# Copy package.json and package-lock.json (ou yarn.lock)
COPY package*.json ./

# Install dependencies
RUN npm install --legacy-peer-deps

# Copy the rest of the application code
COPY . .

# Build the Angular application for production
ARG configuration=docker
RUN npm run build --build-arg --output-path=dist --configuration=$configuration --base-href=/

# Stage 2: Serve the built Angular application using a lightweight server
FROM nginx:stable-alpine

RUN rm -rf /usr/share/nginx/html/*
# Copy the built application from the builder stage
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html


# Expose the default Nginx port
EXPOSE 80

# Optionally, you can add a custom nginx configuration file
COPY ./nginx.conf /etc/nginx/nginx.conf

# Start Nginx when the container starts
CMD ["nginx", "-g", "daemon off;"]
