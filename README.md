# dockerize-jenkins

### Objective
To build a  fully operational Jenkins environment in Docker containers.

### Overview
The four Docker images that should be created are:
  - Jenkins-master: the primary Jenkins engine that stores plugins & data, coordinates jobs, and serves the web UI
  - Jenkins-data: the container volume serving as the data persistence layer for the Jenkins environment
  - Jenkins-slave: the build slave which can be spun up and down by the Jenkins-master application as required
  - Jenkins-nginx: the web proxy for the Jenkins-master
  
  The docker-compose.yml file defines the project 'jenkins' based on the above.
  
  Makefile invokes the defined YML file to build the images, run the containers, stop the containers, and clean the environment.

### Instructions
1. Clone this repo to /usr (if another directory is requiredd, you must change the docker-compose.yml file to reflect)
2. Ensure that docker is running and docker-compose is installed
3. 'make build' to build the images
4. 'make run' to start the Jenkins-master, -data, and -nginx containers
5. Connect to the host IP on port 80
6. **Do not install any plugins**
7. Log in to Jenkins using *admin* / *password*
