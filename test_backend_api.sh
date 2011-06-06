#/bin/sh -x
# SWAG Startup Script for NotificationAPITest
# Make sure to run the backend on localhost before running this.

CLASSPATH=.:bin:lib/* java backend.test.NotificationAPITest

