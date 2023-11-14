#!/bin/bash

# .env import
echo "Importing environment..."

set -a; source .env; set +a

if [ $? -ne 0 ]
then
  echo "Import failed."
  exit 1
fi

echo "Import successful."

# mvn clean package
echo "Building..."

mvn clean package

if [ $? -ne 0 ]
then
  echo "Build failed."
  exit 2
fi

echo "Build successful."

# Deploy to pampero
echo "Deploying..."

scp webapp/target/webapp.war "${PAMPERO_USER}"@pampero.it.itba.edu.ar:~/app.war

ssh "${PAMPERO_USER}"@pampero.it.itba.edu.ar << EOF
  export SSHPASS="${PAW_PAMPERO_PASS}"
  sshpass -e sftp -oBatchMode=no -b - paw-2022a-10@10.16.1.110 << !
    cd web
    put app.war
    bye
  !
EOF

if [ $? -ne 0 ]
then
  echo "Deploy failed."
  exit 3
fi

echo "Deploy successful."
