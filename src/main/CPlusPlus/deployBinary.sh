#!/bin/bash
BASEDIR=$(dirname $0)
gsbucket=gs://pi_calculation/echo/macos
echo "deploy Echo, EchoAgain into ${gsbucket}"
gsutil cp ${BASEDIR}/build/Echo ${gsbucket}
gsutil cp ${BASEDIR}/build/EchoAgain ${gsbucket}