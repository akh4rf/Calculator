# Calculator

My first GitHub repository! Special thanks to my friend [Teagan](https://github.com/vnl6rj) for helping me with build automation, which probably would have taken me a while to figure out!
  
## Project Info:  
  
Originally intended to be a simple four-function calculator, this program can now handle additional operations, including factorial, square, square root, trig functions, exponentials, and more. This calculator also supports PEMDAS and grouping with parentheses, along with copy/paste functionality. More additions are on the way.  
  
## Update Notes:  
  
**Version 0.1**  
Created GUI and added buttons for numbers and basic operators  
  
**Version 0.2**  
Added listeners to buttons, implemented basic operations and equations (moves left-to-right, not PEMDAS)  
  
**Version 0.3**  
Switched loop types in calc method and added comments  
  
**Version 0.4**  
Flipped number pad (realized it was backwards, thanks Teagan)  
  
**Version 0.5.0**  
Added functional buttons for CLR, x^2, SQRT, and factorial  
  
**Version 0.5.1**  
Addressed issue where factorial returns "infinity" when input is > 170  
  
**Version 0.5.2**  
Fixed typo that triggered "LIMIT EXCEEDED" output at lower-than-intended input value  
Separated out CLR scenario from rest of button actions to avoid repetition  
  
**Version 0.5.3**  
Fixed bug caused by separating CLR scenario  
  
**Version 0.5.4**  
Fixed bug encountered when executing: "=" -> "(x^2)/(SQRT)/(x!)" -> "+" -> "num" -> "="  
  
**Version 0.6.0**  
Added sin(x), cos(x), tan(x), π, radian/degree switch button, e^x, ln(x), and e  
Added comments & fixed typos  
  
**Version 0.6.1**  
Changed functionality of π/e from original (multiplying) to final (replacing)  
Fixed issue where clicking π/e and then a number appends the number to the end of the approximation of π/e  
Fixed issue where clicking "=" and then a number appends the number to the end of the result  
  
**Version 0.7**  
Added arcsin(x), arccos(x), arctan(x), and +/- button  
  
**Version 0.8.0**  
Added 10^x and log10(x)  
Added parentheses buttons & implemented grouping with them using a Stack of ArrayLists  
Rearranged GUI buttons for a better user experience  
Implemented build automation with Gradle  
  
**Version 0.8.1**  
Finished implementing PEMDAS  
Fixed factorial function so it doesn't allow non-integer inputs  
  
**Version 0.9**  
Calculator now shows numbers and operations stored in memory  
Fixed edge cases for typing open parenthesis when displayed number is zero  
  
**Version 1.0**  
Implemented copy/paste  
Fixed bug with +/- button  