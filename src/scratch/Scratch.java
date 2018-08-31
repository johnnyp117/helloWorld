package scratch;

/**
 * @author johnn
 *
 */
public class Scratch 
{
/**
 * @param args
 */
public static void main(String[] args) 
{
int x=4;

if (x ==4) 
{
	System.out.println("Yes");
	System.out.println("Redundant statement");
}
else 
{
	System.out.println("Methinks somethin be afoot");
}
switch(x) 
{
case 0: System.out.println("It's zero");
break;
case 1: System.out.println("It's one");
break;
case 2: System.out.println("It's two");
break;
case 3: System.out.println("It's three");
break;
case 4: System.out.println("It's four");
break;
default: System.out.println("SOMETHING");
}
}
}
