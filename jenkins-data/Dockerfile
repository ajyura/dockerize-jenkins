FROM debian:jessie

MAINTAINER adam.j.yura

# add jenkins user
RUN useradd -d "/var/jenkins_home" -u 1000 -m -s /bin/bash jenkins

# add jenkins log folder + permissions
RUN mkdir -p /var/log/jenkins
RUN chown -R jenkins:jenkins /var/log/jenkins

# make log folder a mount
VOLUME ["/var/log/jenkins", "/var/jenkins_home"]

# add in jobs
COPY ./jobs/ /var/jenkins_home

USER jenkins

# output message confirmation
CMD ["echo", "Data container for Jenkins"]
