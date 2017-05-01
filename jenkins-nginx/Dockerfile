FROM centos:centos7

MAINTAINER adamyura

# update OS
RUN yum -y update; yum clean all

# install nginx server for centos & make cache
RUN yum -y install http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm; yum -y makecache

# lock nginx to version 1.8.0
RUN yum -y install nginx-1.8.0

# remove excess config files
RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/conf.d/example_ssl.conf
# add our config files
COPY conf/jenkins.conf /etc/nginx/conf.d/jenkins.conf
COPY conf/nginx.conf /etc/nginx/nginx.conf

# expose http port 80
EXPOSE 80

# call nginx
CMD ["nginx"]
