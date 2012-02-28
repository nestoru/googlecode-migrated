#!/bin/bash
#
#  remote-delete-trades.sh - Delete Geneva Trades remotely
#  
#  @author Nestor Urquiza
#  @date 20110228
# 
# Preconditions:
#    REMOTE_GENEVA_PATH must exist
#

START=$(date +%s)
REMOTE_GENEVA_PATH=/usr/advent/geneva-6.2.0

set -e 

# Functions
function usage() {
  echo ""
  echo "Usage: `basename $0` <user> <host> <outputFileName> <loaderUser> <loaderPassword> <portfolioName> <startDate> <endDate>"
  echo ""
}

function runRemote {
  command=$1
  echo "Running: $command"
  ssh $user@$host "$command"
}

#Main
if [ $# -ne "8" ];
then
	usage
    exit 1 
fi

user=$1
host=$2
outputFileName=$3
loaderUser=$4
loaderPassword=$5
portfolioName=$6
startDate=$7 
endDate=$8

#create the bcp file with the trades to delete
rptCmd="runfile newtransmaker_rev.rsl -p $portfolioName -ps $startDate -pe $endDate --Deletetransmaker yes -f bcp -o $REMOTE_GENEVA_PATH/lib/loader/data/$outputFileName"
cmd="echo $rptCmd | runrep -n -u $loaderUser -w $loaderPassword -b"
runRemote "$cmd"

#remove remote log files for this output filename
cmd="rm -f $REMOTE_GENEVA_PATH/lib/loader/exceptions/${outputFileName}.log"
runRemote "$cmd"
cmd="rm -f $REMOTE_GENEVA_PATH/lib/loader/status/${outputFileName}.log"
runRemote "$cmd"

#delete trades specified in file
cmd="loader -u $loaderUser -w $loaderPassword -f $outputFileName --id"
runRemote "$cmd"

END=$(date +%s)
DIFF=$(( $END - $START ))
date
echo "Finished in $DIFF seconds"

exit 0