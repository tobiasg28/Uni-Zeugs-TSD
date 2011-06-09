#!/bin/sh
# run_sql.sh: Run a fucking SQL file on the database
# Hibernate Sucks. As Does Java. ARgh!

if [ $# -eq 0 ]; then
    echo "Usage: $0 somescript.sql"
    exit 1
fi

SQL_FILE=$1
USERNAME=swa
PASSWORD=swa11
SCHEMA=swa

echo "When asked for a password, enter '$PASSWORD'."
psql $SCHEMA $USERNAME <$SQL_FILE

