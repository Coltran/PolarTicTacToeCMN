/**
 * 
 */
package game;

/**
 * @author
 * This class Implements the resolution based win checker
 */
public class WinCheck {
	
	int x, y;
	Board board;
	

	/**
	 * 
	 */
	public WinCheck(int xLoc, int yLoc, Board boardIn) {
		x = xLoc;
		y = yLoc;
		board = boardIn;
		
	}

	/**
	 * calculates and returns an integer heuristic value for a provided board state
	 * 2 in a row combinations get a value of 4 
	 * 3 in a row combinations get a value of 9+2*4=17
	 * @param board
	 * @return value
	 */
	private int Heuristic() {

		char[] win1 = new char[4];
		
		for (int i = 0; i < 4; i++)
		{
			win1[i] = board.theBoard[i][y];
		}
		
		return 0;
	}

}
//package game;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
////import aima.core.logic.fol.parsing.ast.FOLNode;
////import aima.core.logic.fol.parsing.ast.Function;
////import aima.core.logic.fol.parsing.ast.Term;
////import aima.core.logic.fol.parsing.ast.Variable;
//
//
//public class WinCheck {
//        //
//        private static SubstVisitor _substVisitor = new SubstVisitor();
//        private static VariableCollector _variableCollector = new VariableCollector();
//
//        public WinCheck() {
//
//        }
//
//        public Map<Variable, Term> unify(FOLNode x, FOLNode y) {
//                return unify(x, y, new LinkedHashMap<Variable, Term>());
//        }
//
//        public Map<Variable, Term> unify(FOLNode x, FOLNode y,
//                        Map<Variable, Term> theta) {
//                // if theta = failure then return failure
//                if (theta == null) {
//                        return null;
//                } else if (x.equals(y)) {
//                        // else if x = y then return theta
//                        return theta;
//                } else if (x instanceof Variable) {
//                        // else if VARIABLE?(x) then return UNIVY-VAR(x, y, theta)
//                        return unifyVar((Variable) x, y, theta);
//                } else if (y instanceof Variable) {
//                        // else if VARIABLE?(y) then return UNIFY-VAR(y, x, theta)
//                        return unifyVar((Variable) y, x, theta);
//                } else if (isCompound(x) && isCompound(y)) {
//                        // else if COMPOUND?(x) and COMPOUND?(y) then
//                        // return UNIFY(x.ARGS, y.ARGS, UNIFY(x.OP, y.OP, theta))
//                        return unify(args(x), args(y), unifyOps(op(x), op(y), theta));
//                } else {
//                        // else return failure
//                        return null;
//                }
//        }
//
//        // else if LIST?(x) and LIST?(y) then
//        // return UNIFY(x.REST, y.REST, UNIFY(x.FIRST, y.FIRST, theta))
//        public Map<Variable, Term> unify(List<? extends FOLNode> x,
//                        List<? extends FOLNode> y, Map<Variable, Term> theta) {
//                if (theta == null) {
//                        return null;
//                } else if (x.size() != y.size()) {
//                        return null;
//                } else if (x.size() == 0 && y.size() == 0) {
//                        return theta;
//                } else if (x.size() == 1 && y.size() == 1) {
//                        return unify(x.get(0), y.get(0), theta);
//                } else {
//                        return unify(x.subList(1, x.size()), y.subList(1, y.size()), unify(
//                                        x.get(0), y.get(0), theta));
//                }
//        }
//
//        //
//        // PROTECTED METHODS
//        //
//
//        // Note: You can subclass and override this method in order
//        // to re-implement the OCCUR-CHECK?() to always
//        // return false if you want that to be the default
//        // behavior, as is the case with Prolog.
//        // Note: Implementation is based on unify-bug.pdf document by Peter Norvig:
//        // http://norvig.com/unify-bug.pdf
//        protected boolean occurCheck(Map<Variable, Term> theta, Variable var,
//                        FOLNode x) {
//                // ((equal var x) t)
//                if (var.equals(x)) {
//                        return true;
//                // ((bound? x subst)
//                } else if (theta.containsKey(x)) {
//                        //  (occurs-in? var (lookup x subst) subst))
//                        return occurCheck(theta, var, theta.get(x));
//                // ((consp x) (or (occurs-in? var (first x) subst) (occurs-in? var (rest x) subst)))
//                } else if (x instanceof Function) {
//                        // (or (occurs-in? var (first x) subst) (occurs-in? var (rest x) subst)))
//                        Function fx = (Function) x;
//                        for (Term fxt : fx.getArgs()) {
//                                if (occurCheck(theta, var, fxt)) {
//                                        return true;
//                                }
//                        }
//                }
//                return false;
//        }
//
//        //
//        // PRIVATE METHODS
//        //
//
//        /**
//         * <code>
//         * function UNIFY-VAR(var, x, theta) returns a substitution
//         *   inputs: var, a variable
//         *       x, any expression
//         *       theta, the substitution built up so far
//         * </code>
//         */
//        private Map<Variable, Term> unifyVar(Variable var, FOLNode x,
//                        Map<Variable, Term> theta) {
//
//                if (!Term.class.isInstance(x)) {
//                        return null;
//                } else if (theta.keySet().contains(var)) {
//                        // if {var/val} E theta then return UNIFY(val, x, theta)
//                        return unify(theta.get(var), x, theta);
//                } else if (theta.keySet().contains(x)) {
//                        // else if {x/val} E theta then return UNIFY(var, val, theta)
//                        return unify(var, theta.get(x), theta);
//                } else if (occurCheck(theta, var, x)) {
//                        // else if OCCUR-CHECK?(var, x) then return failure
//                        return null;
//                } else {
//                        // else return add {var/x} to theta
//                        cascadeSubstitution(theta, var, (Term) x);
//                        return theta;
//                }
//        }
//
//        private Map<Variable, Term> unifyOps(String x, String y,
//                        Map<Variable, Term> theta) {
//                if (theta == null) {
//                        return null;
//                } else if (x.equals(y)) {
//                        return theta;
//                } else {
//                        return null;
//                }
//        }
//
//        private List<? extends FOLNode> args(FOLNode x) {
//                return x.getArgs();
//        }
//
//        private String op(FOLNode x) {
//                return x.getSymbolicName();
//        }
//
//        private boolean isCompound(FOLNode x) {
//                return x.isCompound();
//        }
//
//        // See:
//        // http://logic.stanford.edu/classes/cs157/2008/miscellaneous/faq.html#jump165
//        // for need for this.
//        private Map<Variable, Term> cascadeSubstitution(Map<Variable, Term> theta, Variable var,
//                        Term x) {
//                theta.put(var, x);
//                for (Variable v : theta.keySet()) {
//                        theta.put(v, _substVisitor.subst(theta, theta.get(v)));
//                }
//                // Ensure Function Terms are correctly updates by passing over them again
//                // Fix for testBadCascadeSubstitution_LCL418_1()
//                for (Variable v : theta.keySet()) {
//                        Term t = theta.get(v);
//                        if (t instanceof Function) {
//                                theta.put(v, _substVisitor.subst(theta, t));
//                        }
//                }
//                return theta;
//        }
//}
