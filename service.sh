#!/usr/bin/env bash

# This script will run under html_analyzer folder irrespective of where it was invoked from
cd $(dirname $0)

dev_build() {
  mvn package
}

dev_run() {
  java -jar target/html_analyzer-0.0.1-SNAPSHOT.jar $*
}

usage() {
  cat <<EOF
Usage:
  $0 <command> <args>
Local machine commands:
  dev_build        : builds and packages your app
  dev_run          : starts your app in the foreground
EOF
}

action=$1
action=${action:-"usage"}
action=${action/help/usage}
shift
if type -t $action >/dev/null; then
  echo "Invoking: $action"
  $action $*
else
  echo "Unknown action: $action"
  usage
  exit 1
fi
