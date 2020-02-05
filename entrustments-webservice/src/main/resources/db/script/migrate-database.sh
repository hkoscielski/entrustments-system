#!/usr/bin/env bash

sourceDb=$1
destDb=$2

mvn compile liquibase:generateChangeLog -P$sourceDb
mvn compile liquibase:update -P$destDb