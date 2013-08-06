Miij-Framework
====

Miij is a Java framework with an intuitive and straight forward layout manager (FlexLayout)
and some other utility classes.
The layout manager FlexLayout is very easy to use. Just add the layout to the JPanel (or any other container):
```java
   JPanel pnl = new JPanel();
   pnl.setLayout(new FlexLayout());
```

The FlexLayout works like you would naturally position your components. You start positioning the corner positions of
a component. Maybe you want a JLabel to be 10px away from the left side of your panel as well as from the top side.
It's width is 200px and it' height 25px. All this can be done in one simple self explaining line:
```java
   JLabel lbl = new JLabel("some text:");
   // ...
   pnl.add(lbl, new FlexConstraint().left(10).top(10).width(100).height(25));
```

You can also specify dependencies on components. Let's say we want a text field to be next to the JLabel with a gap of 10px
between them:
```java
   JTextField txt = new JTextField();
   // ...
   // Position the component txt next to the right side of the component lbl with a gap of 10px between them.
   // The right corner of the text field shall be 10 pixel beside the right side of the JPanel.
   pnl.add(txt, new FlexConstraint().left(lbl, M.RIGHT, 10).top(10).right(10).height(25));
```
The class **M** holds the constants for the Layout.

You are able to recalculate the corners and width and height individually and define a listener for recalculation
(in case of resizing the parent container):
```java
   pnl.add(new JTextArea(), new FlexConstraint().left(10).top(txt, M.BOTTOM, 10).width(new FlexRecalculateListener() {
   		@Override
			public int recalculate()
			{
				return lbl.getWidth() + 10 + txt.getWidth();
			}
		}));
```
Now every time the parent container is resized, the width of the text area will be recalculated!

Another feature is the notification about a component resize. You can define a listener, that will be called, if your
component is resizing:
```java
   pnl.add(new JLabel(), new FlexConstraint().left(10).right(10).top(10).bottom(10).notifyResize(new FlexNotifyResizeListener() {
   		@Override
			public void notifyResize(int newX, int newY, int newWidth, int newHeight)
			{
				System.out.println("my label has been resized!");
			}
		}));
```
