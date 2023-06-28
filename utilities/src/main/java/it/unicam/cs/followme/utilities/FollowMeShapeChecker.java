package it.unicam.cs.followme.utilities;

import java.util.stream.IntStream;
/*
MIT License

Copyright (c) 2023 Michele Loreti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
@FunctionalInterface
public interface FollowMeShapeChecker {

    boolean checkParameters(String[] args);

    FollowMeShapeChecker DEFAULT_CHECKER = (args) ->
            (args.length>2)&&
                    (((args[1].equals("CIRCLE"))&&(args.length==5)) ||((args[1].equals("RECTANGLE"))&&(args.length==6)))
                    &&(IntStream.range(2,args.length).allMatch(i -> isDouble(args[i])));

    private static boolean isDouble(String str) {
        try {
            return Double.isFinite(Double.parseDouble(str));
        } catch (NumberFormatException e){
            return false;
        }
    }

}
