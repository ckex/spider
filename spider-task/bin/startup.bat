@echo off
:: 设置延迟环境变量扩充
setlocal enabledelayedexpansion

:: java命令
set JAVA=%JAVA_HOME%\bin\java

:: jvm参数
set OPTS=-Xms512M -Xmx512M -Xss128k -XX:+AggressiveOpts -XX:+UseParallelGC -XX:NewSize=64M

:: **jar包所在的目录
set LIBPATH=..\lib

::properties文件目录
::set CONFIG=..\etc

::主函数类的包
set ENGINE=..\lib\iax-generator-0.0.1-SNAPSHOT.jar

::classpath
::set CP=%CONFIG%;%ENGINE%;
set CP=%ENGINE%;

::main class
set MAIN=com.iclick.adx.gen.main.Main

::循环加载jar包 or "java -cp /data/apps/ilb/* com.xxx.Main" no path/*.jar
for /f %%i in ('dir /b %LIBPATH%\*.jar^|sort') do (
   set CP=!CP!%LIBPATH%\%%i;
)

echo ===============================================================================
echo.
echo   Engine Startup Environment
echo.
echo   JAVA: %JAVA%
echo.
echo   CONFIG: %CONFIG%
echo.
echo   JAVA_OPTS: %OPTS%
echo.
echo   CLASSPATH: %CP%
echo.
echo ===============================================================================
echo.

:: start /b
start /b %JAVA% -Dfile.encoding=utf-8 -Duser.timezone="GMT+00:00" %OPTS% -cp %CP% %MAIN%  > ../stdout.log 2>&1 &

:: taskkill /pid %p /t /f