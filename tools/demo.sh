#! /bin/bash

if [ $# -ne 2 ]
then
    echo "Usage: `basename $0` DEVICE_UNIQUE_ID GPX_FILE"
    exit 1
fi

device_unique_id=$1
gpx_file=$2
current_date=$(date +f%Y%m%d%H%M%S+1)

while read -r line; do
    (echo -n -e "\$PGID,$device_unique_id*0F\r\n$line\r\n";) | nc -v localhost 5005
    echo $line
    sleep 1
done < <(gpsbabel -i gpx -f $gpx_file -x track,faketime=$current_date -x track,fix=3d -o nmea -F - | grep '$GPRMC')

