# Use an official Maven image with OpenJDK 11
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
COPY pom.xml /app
COPY src /app/src

# Download the dependencies without running tests
RUN mvn dependency:go-offline -B

# Run Xvfb in the background and then run the tests
ENTRYPOINT ["sh", "-c", "Xvfb :99 -ac & mvn test"]
