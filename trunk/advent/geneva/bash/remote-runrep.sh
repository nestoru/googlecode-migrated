#!/bin/bash
#
#  remote-runrep.sh - Runs a Geneva RSL report remotely
#  
#  @author Nestor Urquiza
#  @date 20110214
# 
# Preconditions:
#    REMOTE_PATH must exist
#	 LOCAL_PATH must exist
#

START=$(date +%s)
REMOTE_PATH=/export/home/geneva/async_tmp
LOCAL_PATH=/tmp

set -e 

# Functions
function usage() {
  echo ""
  echo "Usage: `basename $0` <user> <host> <mode: SYNC/ASYNC/ASYNC_RUN> <filename> <rptuser> <rptpassword> <rptname> <rptflags>"
  echo ""
  echo "	In SYNC mode it will wait until the report finishes then it will save the result in $LOCAL_PATH/<filename>"
  echo "	In ASYNC mode it will check if the file is ready in which case it will save it locally"
  echo "	In ASYNC_RUN mode it will ask Geneva to run the report and will return immediately "
  echo ""
}

function runRemote {
  command=$1

  sshCmd="ssh"
  if [ $mode != "SYNC" ]
  then
    sshCmd="ssh -f -n"
    #command="($command) 2> /dev/null &"
    #command="bash -c \"nohup $command > /dev/null 2>&1 &\""
    ssh -f -n $user@$host "$command"
  else
    ssh $user@$host "$command"
  fi
  #echo "Running $command"
  #eval "$command"
}

function moveFile () {
  #Move the file from the remote location to the local
  rsync -avz --remove-source-files --delete -e "ssh -i $HOME/.ssh/id_rsa" $user@$host:$remoteOutputFilePath $localOutputFilePath 2>/dev/null
}

#Main
if [ $# -ne "8" ];
then
	usage
    exit 1 
fi

user=$1
host=$2
mode=$3
localOutputFilePath=$LOCAL_PATH/$4
remoteOutputFilePath=$REMOTE_PATH/$4
rptuser=$5
rptpassword=$6
rptname=$7
rptflags=$8

#remove local file
rm -f $localOutputFilePath

rptCmd="runfile $rptname $rptflags -o $remoteOutputFilePath"
#echo $rptCmd
cmd="echo $rptCmd | runrep -n -u $rptuser -w $rptpassword -b"

if [ $mode == "ASYNC" ]; then
  echo "ASYNC mode"
  moveFile
elif [ $mode == "ASYNC_RUN" ]; then
  echo "ASYNC_RUN mode"
  #Run the remote report and return
  runRemote "$cmd"
elif [ $mode == "SYNC" ]; then
  echo "SYNC mode"
  #Run the remote report and wait to finish. Then move it locally
  runRemote "$cmd"
  moveFile
else
  echo "Nothing to do. Please use correct mode"
  usage
  exit 1
fi



END=$(date +%s)
DIFF=$(( $END - $START ))
date
echo "Finished in $DIFF seconds"

exit 0