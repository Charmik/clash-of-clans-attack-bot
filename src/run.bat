cd D:\Dropbox\prog\archers_attack_notebook\src
javac attack\*.java  
java -server -XX:+AggressiveOpts -XX:+PrintGCDetails -Xloggc:gc.log -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps attack.Main
#-XX:+PrintCompilation
pause