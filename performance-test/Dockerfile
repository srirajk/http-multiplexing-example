# Use the official Python image from the Docker Hub
FROM python:3.9

# Set the working directory in the Docker image
WORKDIR /app

# Copy the 'requirements.txt' file into the Docker image
COPY performance-test/requirements.txt .

# Install the Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application into the Docker image
COPY performance-test/ .

# Set the command to run when the Docker image is started
CMD ["locust", "-f", "app.py", "-H", "http://localhost:8080"]