#!/bin/bash

# generate ssh key pair and move to authorized_keys
cd ./jenkins-slave/files
ssh-keygen -t rsa -b 4096 yes "" -P "passphrase" -f "./id_rsa" -q
mv -f id_rsa.pub authorized_keys

make build
make run
