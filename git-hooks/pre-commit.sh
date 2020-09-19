##!/bin/bash
#
#git stash -q --keep-index
#
#echo "Running git pre-commit hook"
#
#./gradlew ktlint
#
#RESULT=$?
#
#git stash pop -q
#
## return 1 exit code if running checks fails
#[ $RESULT -ne 0 ] && exit 1
#exit 0


#!/bin/sh
#
# This hook is for Android project git repos.
#
# You can use git config variables to customize this hook.
# -----------------------------------------------------------
# Change hooks.lintTargetDirectory to point at a non . directory
# Change hooks.lintArgs to add any custom lint arguments you prefer

# Get custom info
dirToLint=$(git config hooks.lintTargetDirectory)
lintArgs=$(git config hooks.lintArgs)
projectDir=$(git rev-parse --show-toplevel)
echo "Project Directory ""$projectDir"
lintReportPath = "${projectDir}/app/build/reports/lint-report.html"

# If user has not defined a preferred directory to lint against, make it .
if [ -z "$dirToLint" ]
  then
  dirToLint="${projectDir}/app/"
fi

# Perform lint check
echo "Performing pre-commit lint check of ""$dirToLint"
#cd ${projectDir}/
#if [OperatingSystem.current.isWindows]
#then
#    gradlew lint --daemon
#else
#    ./gradlew ktlint
lintStatus=$?

if [ "$lintStatus" -ne 0 ]
then
  echo "Lint failure, git push aborted."
  echo "Open ${lintReportPath} in a browser to see Lint Report"
  exit 1
fi

exit ${lintStatus}