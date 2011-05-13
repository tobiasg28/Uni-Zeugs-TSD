#!/bin/sh
# drop_tables.sh: Drop all tables and sequences in the DB
# This can be used to start testing from a clean state

SQL_FILE=drop_tables.sql
USERNAME=swa
PASSWORD=swa11
SCHEMA=swa

echo "When asked for a password, enter '$PASSWORD'."
psql $SCHEMA $USERNAME <$SQL_FILE

