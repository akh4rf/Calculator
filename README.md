# Calculator

My first GitHub repo
  
Project Info:  
  
Originally intended to be a simple four-function calculator, this program can now handle additional operations, including factorial, square, and square root. More operations are on the way.  
  
Update Notes:  
  
Version 0.1  
Created GUI and added buttons for numbers and basic operators  
  
Version 0.2  
Added listeners to buttons, implemented basic operations and equations (moves left-to-right, not PEMDAS)  
  
Version 0.3  
Switched loop types in calc method and added comments  
  
Version 0.4  
Flipped number pad (realized it was backwards, thanks Teagan)  
  
Version 0.5.0  
Added functional buttons for CLR, x^2, SQRT, and factorial  
  
Version 0.5.1  
Addressed issue where factorial returns "infinity" when input is > 170  
  
Version 0.5.2  
Fixed typo that triggered "LIMIT EXCEEDED" output at lower-than-intended input value  
Separated out CLR scenario from rest of button actions to avoid repetition  
  
Version 0.5.3  
Fixed bug caused by separating CLR scenario  
  
Version 0.5.4  
Fixed bug encountered when executing: "=" -> "(x^2)/(SQRT)/(x!)" -> "+" -> "num" -> "="  
  
Version 0.6 (In Progress)  
Add sin(x), cos(x), tan(x), and Pi  