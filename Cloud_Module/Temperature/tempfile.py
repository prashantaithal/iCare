'''
import os 

#Get CPU temperature using 'vcgencmd measure_temp'                                      
def measure_temp():
    temp = os.popen('vcgencmd measure_temp').readline()
    return(temp.replace("temp=","").replace("'C\n",""))

#while(1):
#	print(measure_temp())
'''



# Import the ISStreamer module
from ISStreamer.Streamer import Streamer
# Import time for delays
import time

streamer = Streamer(bucket_name="Temperature", bucket_key="temp", access_key="lFjWSIFLuWiP9lNHNSICbzehy2ZkDB0i")


#for num in range(1, 500):
 #       time.sleep(0.1)
        #streamer.log("My Numbers", num)
#       streamer.log("My Number",random.randint(1,500))

while (1):
        time.sleep(1)
        streamer.log("Temperature",measure_temp())

# Once you're finished, close the stream to properly dispose
streamer.close()
