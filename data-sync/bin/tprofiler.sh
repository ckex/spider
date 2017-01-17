#!/bin/bash

# https://github.com/alibaba/TProfiler/wiki/TProfiler%E9%85%8D%E7%BD%AE%E9%83%A8%E7%BD%B2

# enable or disable
tprofiler_switch="disable"

tprofiler_path="$DEPLOY_HOME/lib/tprofiler-1.0.1.jar"
tprofiler_properties_path="$DEPLOY_HOME/bin/profile.properties"

tprofiler=" -javaagent:$tprofiler_path -Dprofile.properties=$tprofiler_properties_path "