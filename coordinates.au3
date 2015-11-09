#include <Color.au3>

HotKeySet("{F10}", "Kill")
Func Kill()
   MouseUp("right")
   Exit
EndFunc


while 1
	$pixel = _ColorGetRGB(PixelGetColor(MouseGetPos(0) - 5, MouseGetPos(1) - 5))
	;ToolTip(StringFormat("%s %s %s", $pixel[0],$pixel[1],$pixel[2]))
	ToolTip(StringFormat("%s %s ", MouseGetPos(0),MouseGetPos(1)))
    Sleep(50)    
WEnd