#!/bin/sh

mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies site

