#!/bin/bash
#date:20181115
#autor:liukz
#this is newly increase version
source /etc/profile
start_time=`date +%Y-%m-%d\ %H:%M:%S`
echo start_time ${start_time}>>/segment1/logs/perform_index/newly_increased_auto_final/ftptohdfs_final_s.log

java -cp /usr/local/program/perform_index/newly_increase_auto_final/ftptohdfsautofinal.jar cn.hbwy.ftptohdfs.maincall.MainCall /usr/local/program/perform_index/newly_increase_auto_final/conf_java/parameter.properties

spark2-submit --master yarn --deploy-mode client --name fivedirtohdfs_v4 --driver-memory 2g --executor-memory 1g --num-executors 9 --class cn.hbwy.ftptohdfsscala.FiveTableToHive_v1 --queue root.default /usr/local/program/perform_index/newly_increase_auto_final/ftptohdfsautofinal.jar  /usr/local/program/perform_index/newly_increase_auto_final/conf_java/parameter.properties

end_time=`date +%Y-%m-%d\ %H:%M:%S`
echo end_time ${end_time}>>/segment1/logs/perform_index/newly_increased_auto_final/ftptohdfs_final_s.log
