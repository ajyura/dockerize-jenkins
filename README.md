# dockerize-jenkins

### Objective
To build a  fully operational Jenkins environment in Docker containers.

### Contents overview
The four Docker images that should be created are:
  - Jenkins-master: the primary Jenkins engine that stores plugins & data, coordinates jobs, and serves the web UI
  - Jenkins-data: the container volume serving as the data persistence layer for the Jenkins environment
  - Jenkins-slave: the build slave which can be spun up and down by the Jenkins-master application as required
  - Jenkins-nginx: the web proxy for the Jenkins-master
  
  
  ### Building Jenkins master
  The jenkins-master directory contains everything needed to build and run the master image, including the Dockerfile and DSL folder with the seed job (test)
  
  ##### Instructions for jenkins-master
   1. Clone the repo
   2. Ensure docker is running
   3. Build the jenkins image
   
          docker build -t jenkins ./jenkins-master
   4. **If you wish to map jenkins to a volume on the host, mount the volume at the host path: */var/lib/docker/volumes/jenkins_home*.** In order to port Jenkins configurations, job data, and credentials from another instance of Jenkins, the host volume must be a copy of the JENKINS_HOME directory from the original Jenkins instance.
   5. Run the jenkins-master container
   
          docker run -p 80:8080 -p 443:8443 -p 50000:50000 -v <full path to host volume>:/var/jenkins_home -d --name jenkins-master jenkins
   6. Connect to the jenkins instance in your browser via *http://<*host IP*>*:80
 
 
  #### docker-compose
  The docker-compose.yml file defines the project 'jenkins' based on the above.
  Makefile invokes the defined YML file to build the images, run the containers, stop the containers, and clean the environment.

  ##### Instructions for docker-compose
   1. Clone this repo to /usr (if another directory is requiredd, you must change the docker-compose.yml file to reflect)
   2. Ensure that docker is running and docker-compose is installed
   3. Build the images
   
          make build
   4. Start the Jenkins-master, -data, and -nginx containers
   
          make run
   5. Connect to the host IP on port 80
   6. **Do not install any plugins**
   7. Log in to Jenkins using *admin* / *password*
