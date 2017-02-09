//
// Built on Sun Jan 22 08:04:40 UTC 2017 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.WARN

scan("300 seconds")
def APP_NAME = "mljr"
def LOG_HOME = "/var/log/mljr"
context.name = "${APP_NAME}"
appender("STDOUT", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = DEBUG
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/gpsmonitor-debug-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 30
  }
}
logger("com.mljr", DEBUG)
logger("org.apache.http", DEBUG)
logger("com.jayway.jsonpath", WARN)
root(DEBUG, ["A1", "STDOUT"])