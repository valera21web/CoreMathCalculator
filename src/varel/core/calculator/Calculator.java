package varel.core.calculator;


public abstract class Calculator {

    /**
    * Change size of text in input
    * @param lengthMathString [int] - length of math string from input
    */
    public abstract void reSize(int lengthMathString);

    /**
    * Get position selection from input
    */
    public abstract int getSelectionStart();

    /**
    * Set position in input
    */
    public abstract int setSelection(int selection);

    /**
    * Set text to input
    */
    public abstract void setText(String text);

    /**
    * Show message about error and other messages
    */
    public abstract void showMessage(String text);

    /**
    *
    * @param mathFormula [String] - math formula what to be calculated
    * @return String - return result in string format
    */
    public String answer(String mathFormula)
    {
        String resultString = V.DEFAULT_RESULT;
        if(mathFormula.length() == 0)
            return resultString;

        if(mathFormula.length() != (Validation.ReOperand(mathFormula).length()))
        {
            int SelectStart = getSelectionStart();
            String leftFormula = mathFormula.substring(0, SelectStart);
            String rightFormula = mathFormula.substring(SelectStart, mathFormula.length());

            leftFormula = Validation.ReOperand(leftFormula);
            rightFormula = Validation.ReOperand(rightFormula);

            mathFormula = leftFormula +""+ rightFormula;
            setText(mathFormula);
            setSelection(leftFormula.length());
        }

        this.reSize(mathFormula.length());

        switch(Validation.Valid(mathFormula))
        {
            case 0:
                resultString = V.ERROR;
                break;

            case 1:
                String[] tmp = Validation.Optimization(mathFormula, getSelectionStart());
                mathFormula = tmp[0];
                setText(mathFormula);
                setSelection(Integer.parseInt(tmp[1]));
                try {
                    mathFormula = RePlace.Pi(mathFormula);
                    mathFormula = RePlace.E(mathFormula);

                    mathFormula = mathFormula.toLowerCase();

                    mathFormula = RePlace.MyMathFunction(mathFormula);                  //rePlace all MyMath Function [ mod(;),  .... ]
                    mathFormula = RePlace.MathFunction(mathFormula);                  //rePlace all Math Function [ sin(), cos(), ln() .... ]
                    mathFormula = RePlace.Factorial(mathFormula);                     //rePlace all Factorial [ ! ]
                    mathFormula = RePlace.Modulo(mathFormula);                        //rePlace all Modulo [ % ]
                    mathFormula = RePlace.Brackets(mathFormula);                      //rePlace all Brackets [ ( ) ]
                    mathFormula = RePlace.Degree(mathFormula);                        //rePlace all Degree[ x^y ]
                    mathFormula = RePlace.Multiplication(mathFormula);                //rePlace all Multiplication [ * ]
                    mathFormula = RePlace.Division(mathFormula);                      //rePlace all Division [ / ]
                    java.math.BigDecimal res = RePlace.EasyStr(mathFormula);                    //summ(mathFormula)
                    res = res.setScale(10, java.math.BigDecimal.ROUND_HALF_DOWN);               // round
                    resultString = Double.toString(res.doubleValue());
                } catch(Exception ex) {
                    showMessage("error_format");
                    resultString = V.ERROR;
                }
                break;

            case 2:
                showMessage("error_with_operator");
                resultString = V.ERROR;
                break;

            case 3:
                showMessage("error_with_brackets");
                resultString = V.ERROR;
                break;
        }
        return resultString;
    }

    public static String answerStatic(String mathFormula)
    {
        String resultString = V.DEFAULT_RESULT;
        if(mathFormula.length() == 0)
            return resultString;

        String math_valid;
        if(mathFormula.equals(math_valid = Validation.ReOperand(mathFormula)))
            mathFormula = math_valid;

        switch(Validation.Valid(mathFormula))
        {
            case 0:
                resultString = V.ERROR;
                break;

            case 1:
                String[] tmp = Validation.Optimization(mathFormula);
                mathFormula = tmp[0];
                try {
                    mathFormula = RePlace.Pi(mathFormula);
                    mathFormula = RePlace.E(mathFormula);

                    mathFormula = mathFormula.toLowerCase();

                    mathFormula = RePlace.MyMathFunction(mathFormula);                  //rePlace all MyMath Function [ mod(;),  .... ]
                    mathFormula = RePlace.MathFunction(mathFormula);                  //rePlace all Math Function [ sin(), cos(), ln() .... ]
                    mathFormula = RePlace.Factorial(mathFormula);                     //rePlace all Factorial [ ! ]
                    mathFormula = RePlace.Modulo(mathFormula);                        //rePlace all Modulo [ % ]
                    mathFormula = RePlace.Brackets(mathFormula);                      //rePlace all Brackets [ ( ) ]
                    mathFormula = RePlace.Degree(mathFormula);                        //rePlace all Degree[ x^y ]
                    mathFormula = RePlace.Multiplication(mathFormula);                //rePlace all Multiplication [ * ]
                    mathFormula = RePlace.Division(mathFormula);                      //rePlace all Division [ / ]
                    java.math.BigDecimal res = RePlace.EasyStr(mathFormula);                    //summ(mathFormula)
                    res = res.setScale(10, java.math.BigDecimal.ROUND_HALF_DOWN);               // round
                    resultString = Double.toString(res.doubleValue());
                } catch(Exception ex) {
                    resultString = V.ERROR + ": error_format;";
                }
                break;
            case 2:
                resultString = V.ERROR + ": error_with_operator;";
                break;
            case 3:
                resultString = V.ERROR + ": error_with_brackets;";
                break;
        }
        return resultString;
    }
}
