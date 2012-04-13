#!/bin/bash
# 
# Name: snoopylogger-install.sh
# Description: Plain Old Bash ( POB ) Recipe to install SnoopyLogger
# Hint: Check /var/log/auth.log for commands run in your system, user, pwd and tty
# Author: Nestor Urquiza 
# Date: 20120413
#
 
set -e
 
curl -k -O --location https://sourceforge.net/projects/snoopylogger/files/snoopy-1.8.0.tar.gz
tar xvf snoopy-1.8.0.tar.gz
cd snoopy-1.8.0/
./configure
make
sudo make install
sudo make enable
