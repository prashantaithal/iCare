# Simple demo of reading each analog input from the ADS1x15 and printing it to
# the screen.
# Author: Tony DiCola
# License: Public Domain
import time

# Import the ADS1x15 module.
import Adafruit_ADS1x15

#Import Matplotlib for Real-time graphing
import matplotlib.pyplot as plt

# Create an ADS1115 ADC (16-bit) instance.
adc = Adafruit_ADS1x15.ADS1115()

# Or create an ADS1015 ADC (12-bit) instance.
#adc = Adafruit_ADS1x15.ADS1015()

# Note you can change the I2C address from its default (0x48), and/or the I2C
# bus by passing in these optional parameters:
adc = Adafruit_ADS1x15.ADS1015(address=0x48, busnum=1)

# Choose a gain of 1 for reading voltages from 0 to 4.09V.
# Or pick a different gain to change the range of voltages that are read:
#  - 2/3 = +/-6.144V
#  -   1 = +/-4.096V
#  -   2 = +/-2.048V
#  -   4 = +/-1.024V
#  -   8 = +/-0.512V
#  -  16 = +/-0.256V
# See table 3 in the ADS1015/ADS1115 datasheet for more info on gain.
GAIN = 1

# Plot the ADC measurement
fig = plt.figure()
ax = fig.gca()
ax.set_title('Real-Time ECG Monitoring')
ax.set_xlabel('Time-->')
ax.set_ylabel('Voltage')

# Enable Interactive Plotting 
plt.ion()

#print('Reading ADS1x15 values, press Ctrl-C to quit...')
# Print nice channel column headers.
#print('| {0:>6} | {1:>6} | {2:>6} | {3:>6} |'.format(*range(4)))
#print('-' * 37)
# Main loop.
i=0
values = []
x = list()
y = list()
try:
    while True:
	#print( adc.read_adc(0,gain= GAIN))
#        ax.cla()
	ax.grid(True)
        x.append(i)
	y.append(adc.read_adc(0,gain= GAIN))
	ax.plot(x,y)
	plt.pause(0.01) # Pause for half a second
        plt.show()
        i+=1

except KeyboardInterrupt:
    print('Exiting Program')

except:
    print('Error Occurs,Exiting Program')

finally:
    plt.ioff()
#    plt.show()
    plt.close()      
