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

# set some environment variables
ENV JAVA_OPTS="-Xmx8192m"
ENV JENKINS_OPTS="--logfile=/var/log/jenkins/jenkins.log --webroot=/var/cache/jenkins/war"
#--handlerCountStartup=100 --handlerCountMax=300 --logfile=/var/log/jenkins/jenkins.log

# create groovy hook scripts
RUN rm /usr/share/jenkins/ref/init.groovy.d/tcp-slave-agent-port.groovy
COPY config/master-slave.groovy /usr/share/jenkins/ref/init.groovy.d/slave-to-master-security-kill-switch.groovy
COPY config/init.groovy /usr/share/jenkins/ref/init.groovy.d/tcp-slave-agent-port.groovy
COPY config/setup-users.groovy /usr/share/jenkins/ref/init.groovy.d/setup-users.groovy

# install default plugins
COPY plugins.txt /tmp/plugins.txt
# RUN chmod +x /usr/local/bin/install-plugins.sh
RUN /usr/local/bin/install-plugins.sh $(cat /tmp/plugins.txt | tr '\n' ' ')

# copy seed job dsl script(s) - may be handles byu mounting host volume containing job data
# RUN mkdir /usr/share/jenkins/ref/jobs/SeedJob/workspace
# COPY dsl/*.groovy /usr/share/jenkins/ref/jobs/SeedJob/workspace/

# Tini as the entry point to manage zombie processes
ENTRYPOINT ["/bin/tini", "--", "/usr/local/bin/jenkins.sh"]
