FROM centos:7

MAINTAINER adam.j.yura

# isntall updates & clean
RUN yum update -y && \
         yum clean all

# install necessary packages
RUN yum install -y git && \
         yum install -y wget && \
         yum install -y openssh-server && \
         yum install -y java-1.8.0-openjdk && \
         yum install -y sudo && \
         yum clean all

# gen dummy keys
RUN /usr/bin/ssh-keygen -A

# set SSH configuration to allow remote logins without /proc write access
RUN sed -ri 's/^session\s+required\s+pam_loginuid.so$/session optional pam_loginuid.so/' /etc/pam.d/sshd

# create Jenkins user
RUN useradd jenkins -m -s /bin/bash

# copy public key for Jenkins login
RUN mkdir /home/jenkins/.ssh
COPY /files/authorized_keys /home/jenkins/.ssh/authorized_keys
RUN chown -R jenkins /home/jenkins
RUN chgrp -R jenkins /home/jenkins
RUN chmod 600 /home/jenkins/.ssh/authorized_keys
RUN chmod 700 /home/jenkins/.ssh

# add the jenkins user to sudoers
RUN echo "jenkins  ALL=(ALL)  ALL" >> etc/sudoers

# set name servers
COPY /files/resolv.conf /etc/resolv.conf

#!/bin/bash

# generate ssh key pair and move to authorized_keys
RUN ssh-keygen -t rsa -b 4096 -P "passphrase" -f "./id_rsa" -q &&\
    mv -f id_rsa.pub authorized_keys

# expose ssh port 22 and run ssh server
EXPOSE 22
CMD ["/usr/sbin/sshd","-D"]
