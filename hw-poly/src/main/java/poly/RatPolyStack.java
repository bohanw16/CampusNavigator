/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package poly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 *
 * <p>Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty stack, [p1] is a
 * one element stack containing the Poly 'p1', and so on. RatPolyStacks can also be described
 * constructively, with the append operation, ':'. such that [p1]:S is the result of putting p1 at
 * the front of the RatPolyStack S.
 *
 * <p>A finite sequence has an associated size, corresponding to the number of elements in the
 * sequence. Thus, the size of [] is 0, the size of [p1] is 1, the size of [p1, p1] is 2, and so on.
 */
@SuppressWarnings("JdkObsolete")
public final class RatPolyStack implements Iterable<RatPoly> {

    /**
     * Stack containing the RatPoly objects.
     */
    private final Stack<RatPoly> polys;

    // Abstraction Function:
    // AF(this) = A LIFO stack where the top of this.polys is the top of this,
    // and the bottom of this.polys is the bottom of this (with the elements in between
    // in insertion order, the newest closer to the top).
    //
    // RepInvariant:
    // polys != null &&
    // forall i such that (0 <= i < polys.size(), polys.get(i) != null

    /**
     * Constructs a new RatPolyStack.
     *
     * @spec.effects Constructs a new RatPolyStack, [].
     */
    public RatPolyStack() {
        // TODO: Fill in this method, then remove the RuntimeException
        polys = new Stack<>();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.polys != null) : "polys should never be null.";

        for(RatPoly p : this.polys) {
            assert (p != null) : "polys should never contain a null element.";
        }
    }

    /**
     * Returns the number of RatPolys in this RatPolyStack.
     *
     * @return the size of this sequence.
     */
    public int size() {
    	// TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        return polys.size();
    }

    /**
     * Pushes a RatPoly onto the top of this.
     *
     * @param p the RatPoly to push onto this stack
     * @spec.requires p != null
     * @spec.modifies this
     * @spec.effects this_post = [p]:this.
     */
    public void push(RatPoly p) {
    	// TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        polys.push(p);
        checkRep();
    }

    /**
     * Removes and returns the top RatPoly.
     *
     * @return p where this = [p]:S.
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = S.
     */
    public RatPoly pop() {
       // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp = polys.pop();
        checkRep();
        return rp;
    }

    /**
     * Duplicates the top RatPoly on this.
     *
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = [p, p]:S.
     */
    public void dup() {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp = polys.peek();
        polys.push(rp);
        checkRep();
    }

    /**
     * Swaps the top two elements of this.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p2, p1]:S.
     */
    public void swap() {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp1 = pop();
        RatPoly rp2 = pop();
        push(rp1);
        push(rp2);
        checkRep();
    }

    /**
     * Clears the stack.
     *
     * @spec.modifies this
     * @spec.effects this_post = [].
     */
    public void clear() {
       // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        polys.clear();
        checkRep();
    }

    /**
     * Returns the RatPoly that is 'index' elements from the top of the stack.
     *
     * @param index the index of the RatPoly to be retrieved
     * @return if this = S:[p]:T where S.size() = index, then returns p.
     * @spec.requires {@code index >= 0 && index < this.size()}
     */
    public RatPoly getNthFromTop(int index) {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        Stack<RatPoly> stk = new Stack<>();
        for (int i = 0; i <= index; i++) {
            stk.push(pop());
        }
        RatPoly result = stk.peek();
        for (int i = 0; i <= index; i++) {
            push(stk.pop());
        }
        checkRep();
        return result;
    }

    /**
     * Pops two elements off of the stack, adds them, and places the result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2.
     */
    public void add() {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp1 = pop();
        RatPoly rp2 = pop();
        push(rp1.add(rp2));
        checkRep();
    }

    /**
     * Subtracts the top poly from the next from top poly, pops both off the stack, and places the
     * result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1.
     */
    public void sub() {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp1 = pop();
        RatPoly rp2 = pop();
        push(rp2.sub(rp1));
        checkRep();
    }

    /**
     * Pops two elements off of the stack, multiplies them, and places the result on top of the
     * stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2.
     */
    public void mul() {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp1 = pop();
        RatPoly rp2 = pop();
        push(rp1.mul(rp2));
        checkRep();
    }

    /**
     * Divides the next from top poly by the top poly, pops both off the stack, and places the
     * result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1.
     */
    public void div() {
        // TODO: Fill in this method, then remove the RuntimeException
        checkRep();
        RatPoly rp1 = pop();
        RatPoly rp2 = pop();
        push(rp2.div(rp1));
        checkRep();
    }

    /**
     * Returns an iterator of the elements contained in the stack.
     *
     * @return an iterator of the elements contained in the stack in order from the bottom of the
     * stack to the top of the stack.
     */
    @Override
    public Iterator<RatPoly> iterator() {
        return this.polys.iterator();
    }
}
