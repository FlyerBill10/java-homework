package checkParentheses;

/**
 * @author Bill Farrell
 * class to ensure parentheses match up (())
 * also checks for closed parenthesis before an open 
 *
 *
 */
public class ParenChecker {
   /*
    * Driver to accept arg and call checker
    */
    
	public static void main(String[] args) {
		    String inputArg = args[0];
		    System.out.println("Input String was" + inputArg);
		    ParenCheckerBool bool = new ParenCheckerBool();
		    boolean retValue = bool.checkParens(inputArg);
		    String msg = (retValue) ? "Valid String" : "Invalid String";
			System.out.println(msg);
		}

		

	

}
