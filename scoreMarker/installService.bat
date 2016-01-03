set SERVICE_NAME=ScoreMarkerService

set APP_HOME=D:\service
set PR_INSTALL=%APP_HOME%\prunsrv.exe
 
REM Service log configuration
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=d:\logs
set PR_STDOUTPUT=d:\logs\stdout.txt
set PR_STDERROR=d:\logs\stderr.txt
set PR_LOGLEVEL=Error

REM Path to java installation
set PR_JVM=%JAVA_HOME%\jre\bin\server\jvm.dll
set PR_CLASSPATH=%APP_HOME%\ScoreMarker-2.0.0.jar
 
REM Startup configuration
set PR_STARTUP=auto
set PR_STARTMODE=jvm
set PR_STARTCLASS=com.examstack.scoremarker.ScoreMarkerWin
set PR_STARTMETHOD=start
 
REM Shutdown configuration
set PR_STOPMODE=jvm
set PR_STOPCLASS=%PR_STARTCLASS%
set PR_STOPMETHOD=stop
 
REM JVM configuration
set PR_JVMMS=256
set PR_JVMMX=1024
set PR_JVMSS=4000
set PR_JVMOPTIONS=-Duser.language=DE;-Duser.region=de
 
REM Install service
prunsrv.exe //IS//%SERVICE_NAME%