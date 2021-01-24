from turtle import Screen, Turtle
import Tkinter

#global variables
screen = Screen()
screen.setup(500, 500)
maxSize = 1
nOrder = 1
t = Turtle()

"""
Hilbert method use the turtle library  to draw the curve. 
Using turtle 
@param t, The turtle we are using 
@param length, is the length of the lines to be drawn
@param parity, is the number we are multiplying by the angle 
@param nOrder, the current order of the curve. Using the recursive 
algorithm we draw part of the curve and then recursively call
"""
def hilbert(t, length, parity, nOrder):
   if nOrder < 1:
      return
   t.right(parity * 90)
   t.dot(3)
   hilbert(t, length, - parity, nOrder - 1)
   t.dot(3)
   t.forward(length)
   t.dot(3)
   t.left(parity * 90)
   t.dot(3)
   hilbert(t, length, parity, nOrder - 1)
   t.forward(length)
   t.dot(3)
   hilbert(t, length, parity, nOrder - 1)
   t.left(parity * 90)
   t.dot(3)
   t.forward(length)
   t.dot(3)
   hilbert(t, length, - parity, nOrder - 1)
   t.right(parity * 90)
   t.dot(3)


"""
Rescaling the image.
Updates the worldcoordinates of the screen, which will rescale the 
drawing. 
Ontimer recursively calls refresh every 1000 milliseconds
"""
def refresh():
   global screen
   screen.setworldcoordinates(-1,-1, maxSize+1, maxSize+1)
   screen.update()
   screen.ontimer(refresh, 1000)

   
"""
The main function for the program.
This takes user input

"""
def main():
   global screen
   global maxSize
   global nOrder
   global t

   while 1:
      try:
         nOrder = int(raw_input("Enter the N order curve required: "))
         if(nOrder > 0):
            break
         print "Enter a an N order 1 or larger"
         print "Enter only digit."
         continue

   while 1:
      try:
         print("Enter the speed of the drawing: ")
         speed = int(raw_input("1 = slow, 2 = fast"))
         if(speed == 1 or speed == 2):
               break
      except ValueError: 
         print "Enter digit"
         continue

   maxSize = (2**nOrder)-1
   screen.setworldcoordinates(-1,-1, maxSize+1, maxSize+1)
   t.hideturtle()
   t.ht()
   t.penup()
   t.goto(0,maxSize)
   t.pendown()
   if speed == 1:
      t.tracer(5,5)
   elif speed == 2:
      t.tracer(10000,1)
   hilbert(t, 1, 1, nOrder)
   screen.update()
main()
refresh()
Tkinter.mainloop()
