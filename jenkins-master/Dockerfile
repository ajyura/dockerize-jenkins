FROM jenkins:latest

MAINTAINER adam.j.yura

# elevate priveleges
USER root

# set a log folder
RUN mkdir /var/log/jenkins
# set a cache folder
RUN mkdir /var/cache/jenkins
# enable jenkins to write to cache folder
RUN chown -R  jenkins:jenkins /var/cache/jenkins
# enable jenkins to write to log folder
RUN chown -R  jenkins:jenkins /var/log/jenkins
# set user to jenkins
USER jenkins

# set some environment variables - 8 GB memory pool & increasing connection pool
ENV JAVA_OPTS="-Xmx8192m"
ENV JENKINS_OPTS="--logfile=/var/log/jenkins/jenkins.log --webroot=/var/cache/jenkins/war"
#--handlerCountStartup=100 --handlerCountMax=300 --logfile=/var/log/jenkins/jenkins.log

# replace init.groovy file with custom
RUN rm /usr/share/jenkins/ref/init.groovy.d/tcp-slave-agent-port.groovy
COPY init.groovy /usr/share/jenkins/ref/init.groovy.d/tcp-slave-agent-port.groovy
