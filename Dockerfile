# Use an official Maven image with OpenJDK 11
#FROM ldr.lifafa.team:5000/maven-3.6.3-openjdk-11:base
FROM maven:3.6.3-openjdk-11

# Install Firefox and dependencies
RUN apt-get update && \
    apt-get install -y \
    firefox-esr \
    xvfb \
    libgtk-3-0 \
    libdbus-glib-1-2 \
    libxt6 \
    libxcomposite1 \
    libasound2

# Set the DISPLAY environment variable for Xvfb
ENV DISPLAY=:99

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and the src directory to the container
COPY ./ /app

# Download the dependencies without running tests
RUN mvn dependency:go-offline -B

# Copy the entrypoint script to the container
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Set the entrypoint
ENTRYPOINT ["/app/entrypoint.sh"]
