package checkParentheses;

public class ParenCheckerBool {
	/* class to check for valid string with correct formatted string when it comes to 
	 * parentheses. Checks for equal number of open/closed parens and to make sure they're
	 * not out of order i.e. closed before paren
	 */
	protected boolean checkParens(String arg) {
		int parenTotal = 0;
		char openParen = '(';
		char closedParen = ')';
		for (int i=0;i < arg.length(); i++) {
			//properly formatted string should equal zero
			if (arg.charAt(i) == openParen){
				parenTotal++;
			}
			else if (arg.charAt(i) == closedParen){
				parenTotal--;
			};
			// if we have a closed before an open, throw error and get out
			if (parenTotal < 0) {
				break;
			}
		}
		return (parenTotal == 0);
	}

}
