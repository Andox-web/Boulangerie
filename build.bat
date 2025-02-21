REM Define paths
set PROJECT_NAME=Boulangerie
set SRC_DIR=src\main\java
set WEBAPP_DIR=src\main\webapp
set BUILD_DIR=build
set LIB_DIR=lib
set TOMCAT_HOME=E:\logiciel\tomcat
set RESOURCES_DIR=src\main\resources
set ASSETS_DIR=assets

REM Clean build directory
if exist %BUILD_DIR% rd /s /q %BUILD_DIR%
mkdir %BUILD_DIR%\WEB-INF\classes

dir /S /B %SRC_DIR%\*.java > sources.txt

REM Compile Java files with -parameters option
javac -d %BUILD_DIR%\WEB-INF\classes -cp "%LIB_DIR%\*" -parameters @sources.txt

REM Copy resources
xcopy %RESOURCES_DIR% %BUILD_DIR%\WEB-INF\classes /E /I

del /f /q sources.txt

REM Copy web resources
xcopy %WEBAPP_DIR% %BUILD_DIR%\WEB-INF\ /E /I

REM Copy lib
xcopy %LIB_DIR% %BUILD_DIR%\WEB-INF\lib /E /I

REM Copy asstes
xcopy %ASSETS_DIR% %BUILD_DIR%\assets /E /I

REM Package WAR
cd %BUILD_DIR%
jar -cvf %PROJECT_NAME%.war *
cd ..

@REM REM Start Tomcat
@REM start %TOMCAT_HOME%\bin\shutdown.bat

REM Deploy to Tomcat (copy the WAR file to Tomcat webapps)
copy %BUILD_DIR%\%PROJECT_NAME%.war %TOMCAT_HOME%\webapps\

@REM REM Start Tomcat
@REM start %TOMCAT_HOME%\bin\startup.bat
