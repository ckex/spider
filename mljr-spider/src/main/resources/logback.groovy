//
// Built on Wed Dec 07 05:57:42 UTC 2016 by logback-translator
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

import static ch.qos.logback.classic.Level.*

scan("300 seconds")
def LOG_HOME = System.getenv("OUTPUT_HOME")
if (!LOG_HOME) {
  LOG_HOME = "/data/var/log/mljr"
}

def APP_NAME = System.getenv("APP_NAME")
if (!APP_NAME) {
  APP_NAME = "mljr"
}

def BASIC_DATA_PATH = "/data/backup/"

def SAIGE_DATA_HOME =System.getenv("SAIGE_DATA_HOME")
if (!SAIGE_DATA_HOME) {
  SAIGE_DATA_HOME = BASIC_DATA_PATH+"saige"
}

def JUHE_DATA_HOME =System.getenv("JUHE_DATA_HOME")
if (!JUHE_DATA_HOME) {
  JUHE_DATA_HOME = BASIC_DATA_PATH+"juhe"
}

def GUA_DATA_HOME =System.getenv("GUA_DATA_HOME")
if (!GUA_DATA_HOME) {
  GUA_DATA_HOME = BASIC_DATA_PATH+"guabu"
}

def HCP_DATA_HOME =System.getenv("HCP_DATA_HOME")
if (!HCP_DATA_HOME) {
  HCP_DATA_HOME = BASIC_DATA_PATH+"hcp"
}

def CHA67_DATA_HOME =System.getenv("CHA67_DATA_HOME")
if (!CHA67_DATA_HOME) {
  CHA67_DATA_HOME = BASIC_DATA_PATH+"cha67"
}

def YHK388_DATA_HOME =System.getenv("YHK388_DATA_HOME")
if (!YHK388_DATA_HOME) {
  YHK388_DATA_HOME = BASIC_DATA_PATH+"yhk388"
}

def CHAYHK_DATA_HOME =System.getenv("CHAYHK_DATA_HOME")
if (!CHAYHK_DATA_HOME) {
  CHAYHK_DATA_HOME = BASIC_DATA_PATH+"chayhk"
}

def AMAP_DATA_HOME =System.getenv("AMAP_DATA_HOME")
if (!AMAP_DATA_HOME) {
  AMAP_DATA_HOME = BASIC_DATA_PATH+"amap"
}

def BAIDU_DATA_HOME =System.getenv("BAIDU_DATA_HOME")
if (!BAIDU_DATA_HOME) {
  BAIDU_DATA_HOME = BASIC_DATA_PATH+"baidu"
}

def IDCARD_DATA_HOME =System.getenv("IDCARD_DATA_HOME")
if (!IDCARD_DATA_HOME) {
  IDCARD_DATA_HOME = BASIC_DATA_PATH+"idCard"
}
def CARHOEM_DATA_HOME=System.getenv("CARHOEM_DATA_HOME")
if (!CARHOEM_DATA_HOME) {
  CARHOEM_DATA_HOME = BASIC_DATA_PATH+"carHome"
}
def DOWNLOADER_LISTENER_HOME =System.getenv("DOWNLOADER_LISTENER_HOME")
if (!DOWNLOADER_LISTENER_HOME) {
  DOWNLOADER_LISTENER_HOME = "/data/listener"
}

context.name = "${APP_NAME}"
appender("STDOUT", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = DEBUG
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/spider-debug-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("A1", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_HOME}/spider-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("JUHE-MOBILE-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/juhe-mobile-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("BAIDU-MOBILE-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/baidu-mobile-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("SOGOU-MOBILE-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/sogou-mobile-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("TIANYANCHA-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/tianyancha-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("SAIGE-GPS-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/saige-gps-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("GUABU-BC-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/guabu-bc-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("HCP-BC-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/hcp-bc-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("CHA67-BC-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/cha67-bc-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("YHK388-BC-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/yhk388-bc-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("CHAYHK-BC-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/chayhk-bc-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-AMAP-GEO-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/lbs-amap-geo-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-AMAP-REGEO-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/lbs-amap-regeo-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-BAIDU-GEO-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/lbs-baidu-geo-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-BAIDU-REGEO-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/lbs-baidu-regeo-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}

appender("BLACK_IDCARD_LISTEREN_ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/IDCARD-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}

appender("GPS-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${SAIGE_DATA_HOME}/GPS-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("JUHE-MOBILE-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${JUHE_DATA_HOME}/MOBILE-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("GUABU-BC-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${GUA_DATA_HOME}/BC-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("HCP-BC-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${HCP_DATA_HOME}/BC-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("CHA67-BC-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${CHA67_DATA_HOME}/BC-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("YHK388-BC-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${YHK388_DATA_HOME}/BC-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("CHAYHK-BC-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${CHAYHK_DATA_HOME}/BC-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-AMAP-GEO-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${AMAP_DATA_HOME}/GEO-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-AMAP-REGEO-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${AMAP_DATA_HOME}/REGEO-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-BAIDU-GEO-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${BAIDU_DATA_HOME}/GEO-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("LBS-BAIDU-REGEO-DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${BAIDU_DATA_HOME}/REGEO-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}

appender("BLACK_IDCARD_LOG_DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${IDCARD_DATA_HOME}/REGEO-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("QQZONE-SHUOSHUO-ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyy-MM-dd/HH:mm:ss.SSS} %level [%thread] %logger{20}:%line - %msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/qqzone-shuoshuo-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
//汽车之家日志数据 car_home_net_log_data
appender("CAR_HOME_NET_LOG_DATA", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${CARHOEM_DATA_HOME}/carHome-info-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
appender("CAR_HOME_NET_LISTEREN_ERR", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${DOWNLOADER_LISTENER_HOME}/carHome-info-%d{yyyy-MM-dd-HH}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "100MB"
    }
    maxHistory = 300
  }
}
logger("com.alibaba.druid.filter.stat.StatFilter", INFO)
logger("com.alibaba.druid.pool.DruidDataSource", INFO)
logger("com.alibaba.druid", INFO)
logger("druid.sql", WARN)
logger("druid.sql.DataSource", WARN)
logger("druid.sql.Connection", WARN)
logger("druid.sql.Statement", WARN)
logger("org.springframework.security", WARN)
logger("org.springframework", WARN)
logger("com.ibatis", INFO)
logger("java.sql", INFO)
logger("org.apache.ibatis", INFO)
logger("org.mybatis.spring", INFO)
logger("java.sql.ResultSet", INFO)
logger("java.sql.Connection", INFO)
logger("java.sql.Statement", INFO)
logger("java.sql.PreparedStatement", INFO)
logger("com.ucloud.umq", INFO)
logger("org.apache.http", INFO)
logger("org.I0Itec.zkclient", INFO)
logger("us.codecraft.webmagic", INFO)
logger("io.grpc", INFO)
logger("com.mljr.spider.scheduler.manager.Manager", DEBUG)
logger("com.mljr.spider.scheduler.manager", DEBUG)
logger("com.mljr.spider.scheduler", DEBUG)
logger("com.mljr.spider", DEBUG)
logger("juhe-mobile-downloader", INFO, ["JUHE-MOBILE-ERR"], false)
logger("baidu-mobile-downloader", INFO, ["BAIDU-MOBILE-ERR"], false)
logger("sogou-mobile-downloader", INFO, ["SOGOU-MOBILE-ERR"], false)
logger("tianyancha-downloader", INFO, ["TIANYANCHA-ERR"], false)
logger("saige-gps-downloader", INFO, ["SAIGE-GPS-ERR"], false)
logger("guabu-bc-downloader", INFO, ["GUABU-BC-ERR"], false)
logger("hcp-bc-downloader", INFO, ["HCP-BC-ERR"], false)
logger("cha67-bc-downloader", INFO, ["CHA67-BC-ERR"], false)
logger("yhk388-bc-downloader", INFO, ["YHK388-BC-ERR"], false)
logger("chayhk-bc-downloader", INFO, ["CHAYHK-BC-ERR"], false)
logger("lbs-amap-geo-downloader", INFO, ["LBS-AMAP-GEO-ERR"], false)
logger("lbs-amap-regeo-downloader", INFO, ["LBS-AMAP-REGEO-ERR"], false)
logger("lbs-baidu-geo-downloader", INFO, ["LBS-BAIDU-GEO-ERR"], false)
logger("lbs-baidu-regeo-downloader", INFO, ["LBS-BAIDU-REGEO-ERR"], false)
logger("gps-data", INFO, ["GPS-DATA"], false)
logger("juhe-mobile-data", INFO, ["JUHE-MOBILE-DATA"], false)
logger("guabu-bc-data", INFO, ["GUABU-BC-DATA"], false)
logger("hcp-bc-data", INFO, ["HCP-BC-DATA"], false)
logger("cha67-bc-data", INFO, ["CHA67-BC-DATA"], false)
logger("yhk388-bc-data", INFO, ["YHK388-BC-DATA"], false)
logger("chayhk-bc-data", INFO, ["CHAYHK-BC-DATA"], false)
logger("lbs-amap-geo-data", INFO, ["LBS-AMAP-GEO-DATA"], false)
logger("lbs-amap-regeo-data", INFO, ["LBS-AMAP-REGEO-DATA"], false)
logger("lbs-baidu-geo-data", INFO, ["LBS-BAIDU-GEO-DATA"], false)
logger("lbs-baidu-regeo-data", INFO, ["LBS-BAIDU-REGEO-DATA"], false)
logger("black_idCard_log_data", INFO, ["BLACK_IDCARD_LOG_DATA"], false)
logger("blackidcard-downloader", INFO, ["BLACK_IDCARD_LISTEREN_ERR"], false)
logger("qqzone-downloader", INFO, ["QQZONE-SHUOSHUO-ERR"], false)
logger("car_home_net_log_data", INFO, ["CAR_HOME_NET_LOG_DATA"], false)
logger("carhome-downloader", INFO, ["CAR_HOME_NET_LISTEREN_ERR"], false)
root(DEBUG, ["A1", "STDOUT"])