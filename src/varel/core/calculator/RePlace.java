package varel.core.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class RePlace extends Validation {

	private static Convert Data = new Convert();
	
	public RePlace(){
		super();
	}

   protected static String MyMathFunction(String str) {
      String tmpStr = "";
      BigDecimal tmpDouble;
      List<Integer> IndexsFun = Search.aSort(ReMyFun(str));
      for(int indexFun: IndexsFun) {
         tmpStr = str.substring(indexFun, str.length());
         char[] StrChar = tmpStr.toCharArray();
         int count = 1, j;
         int t = tmpStr.indexOf(V.CHAR_STR_LEFT_BRACKET);
         for(j = t + 1; count > 0 && j < tmpStr.length(); ++j){
            if((int)StrChar[j] == V.CHAR_INT_LEFT_BRACKET) {
               ++count;
            } else if((int)StrChar[j] == V.CHAR_INT_RIGHT_BRACKET) {
               --count;
            }
         }
         tmpStr = tmpStr.substring(0, j);
         tmpDouble = MyFunMath(tmpStr);
         str = str.replace(tmpStr, Data.add(tmpDouble));
      }
      str = ReOperand(str);
      return str;
   }
	
	protected static String MathFunction(String str) {
		String tmpStr = "";
		BigDecimal tmpDouble;
		List<Integer> IndexsFun = Search.aSort(ReFun(str));
		for(int indexFun: IndexsFun) {
			tmpStr = str.substring(indexFun, str.length());
			char[] StrChar = tmpStr.toCharArray();
			int count = 1, j;
			int t = tmpStr.indexOf(V.CHAR_STR_LEFT_BRACKET);
			for(j = t + 1; count > 0 && j < tmpStr.length(); ++j){
				if((int)StrChar[j] == V.CHAR_INT_LEFT_BRACKET) {
               ++count;
            } else if((int)StrChar[j] == V.CHAR_INT_RIGHT_BRACKET) {
               --count;
            }
			}
			tmpStr = tmpStr.substring(0, j);
         tmpDouble = FunMath(tmpStr);
         str = str.replace(tmpStr, Data.add(tmpDouble));
      }
		str = ReOperand(str);
		return str;
	}

   protected static List<Integer> ReMyFun(String str) {
      String templateForReplace = "aaaaaaaaaa";
      List<Integer> result = new ArrayList<Integer>();
      String[] allFunctions = new String[] {
              V.FUNCTION_MOD,
              V.FUNCTION_NWD,
              V.FUNCTION_MAX,
              V.FUNCTION_MIN
      };

      for(String function: allFunctions) {
         int lengthNameFunction = function.length();
         while(str.contains(function) && !Search.InList(str.indexOf(function), result)) {
            result.add(str.indexOf(function));
            str = str.replaceFirst(function, templateForReplace.substring(0, lengthNameFunction));
         }
      }
      return result;
   }
	
	protected static List<Integer> ReFun(String str) {
      String templateForReplace = "aaaaaaaaaa";
		List<Integer> result = new ArrayList<Integer>();
      String[] allFunctions = new String[] {
         V.FUNCTION_ASIN,
         V.FUNCTION_ACOS,
         V.FUNCTION_ATAN,
         V.FUNCTION_SIN,
         V.FUNCTION_COS,
         V.FUNCTION_TAN,
         V.FUNCTION_LOG,
         V.FUNCTION_LG,
         V.FUNCTION_LN,
         V.FUNCTION_EXP,
         V.FUNCTION_ABS
      };

      for(String function: allFunctions) {
         int lengthNameFunction = function.length();
         while(str.contains(function) && !Search.InList(str.indexOf(function), result)) {
            result.add(str.indexOf(function));
            str = str.replaceFirst(function, templateForReplace.substring(0, lengthNameFunction));
         }
      }
	   return result;
	}
	
	protected static BigDecimal FunMath(String arg) {
		double result = 0.0;
		String fun = arg.substring(0, arg.indexOf(V.CHAR_STR_LEFT_BRACKET));
		String paramStr = arg.substring(arg.indexOf(V.CHAR_STR_LEFT_BRACKET), arg.length());
      paramStr = Brackets(paramStr);
      BigDecimal paramDouble = EasyStr(paramStr);
      double radians = Math.toRadians(paramDouble.doubleValue());       // value in radians
      double intDouble = 0.0;
		double doubleDouble = 0.0;
		double deg = 0.0;

		if(Search.Are(fun, V.FUNCTION_SIN)) {
			result = Math.sin(radians);
		} else if(Search.Are(fun, V.FUNCTION_ASIN)) {
			if(paramDouble.doubleValue() > 1) {
				doubleDouble =  paramDouble.doubleValue() % 1;
				intDouble =  paramDouble.doubleValue() - doubleDouble;
				deg = 90 * intDouble;
				paramDouble = BigDecimal.valueOf(doubleDouble);
			}
			result = Math.toDegrees(Math.asin(paramDouble.doubleValue()));
			result += deg;
		} else if(Search.Are(fun, V.FUNCTION_COS)) {
			result = Math.cos(radians);
		} else if(Search.Are(fun, V.FUNCTION_ACOS)) {
			if(paramDouble.doubleValue() > 1) {
				doubleDouble =  paramDouble.doubleValue() % 1;
				intDouble =  paramDouble.doubleValue() - doubleDouble;
				deg = 90 * intDouble;
				paramDouble = BigDecimal.valueOf(doubleDouble);
			}
			result = Math.toDegrees(Math.acos(paramDouble.doubleValue()));
			result = intDouble % 2 == 1 ? deg + (90 - result) : deg + result ;
		} else if(Search.Are(fun, V.FUNCTION_TAN)) {
			paramDouble = BigDecimal.valueOf(paramDouble.doubleValue() == 90 ? 0 : Math.toRadians(paramDouble.doubleValue()));
			result = Math.tan(paramDouble.doubleValue());
		} else if(Search.Are(fun, V.FUNCTION_ATAN)) {
			result = Math.toDegrees(Math.atan(paramDouble.doubleValue()));
		} else if(Search.Are(fun, V.FUNCTION_LOG)) {
			result = Math.log1p(paramDouble.doubleValue());
		} else if(Search.Are(fun, V.FUNCTION_LG)) {
			result = Math.log10(paramDouble.doubleValue());
		} else if(Search.Are(fun, V.FUNCTION_LN)) {
			result = Math.log(paramDouble.doubleValue());
		} else if(Search.Are(fun, V.FUNCTION_EXP)) {
			result = Math.exp(paramDouble.doubleValue());
		} else if(Search.Are(fun, V.FUNCTION_ABS)) {
			result = Math.abs(doubleDouble);
		}

		return (new BigDecimal(result)).setScale(10, BigDecimal.ROUND_HALF_DOWN);
	}


   protected static BigDecimal MyFunMath(String arg) {
      BigDecimal result = BigDecimal.valueOf(0.0);
      String fun = arg.substring(0, arg.indexOf(V.CHAR_STR_LEFT_BRACKET));
      String paramStr = arg.substring(arg.indexOf(V.CHAR_STR_LEFT_BRACKET), arg.length());

      if(fun.equals(V.FUNCTION_MOD)) {
         String[] params = paramStr.substring(1, paramStr.length()-1).split(";");
         if(params.length == 2) {
            result = BigDecimal.valueOf(getVal(params[0].trim()).doubleValue() % getVal(params[0].trim()).doubleValue());
         }
      } else if(fun.equals(V.FUNCTION_NWD)) {
         String[] params = paramStr.substring(1, paramStr.length()-1).split(";");
         if(params.length == 2) {
            result = NWD(getVal(params[0].trim()), getVal(params[1].trim()));
         }
      }   else if(fun.equals(V.FUNCTION_MAX)) {
         String[] params = paramStr.substring(1, paramStr.length()-1).split(";");
         if(params.length > 0) {
            result = getVal(params[0].trim());
            for(String param: params) {
               BigDecimal bg_tmp = getVal(param.trim());
               if(bg_tmp.doubleValue() > result.doubleValue()) {
                  result = bg_tmp;
               }
            }
         }
      } else if(fun.equals(V.FUNCTION_MIN)) {
         String[] params = paramStr.substring(1, paramStr.length()-1).split(";");
         if(params.length > 0) {
            result = getVal(params[0].trim());
            for(String param: params) {
               BigDecimal bg_tmp = getVal(param.trim());
               if(bg_tmp.doubleValue() > result.doubleValue()) {
                  result = bg_tmp;
               }
            }
         }
      }/* else if(fun.equals(V.FUNCTION_IF)) {
         String[] params = paramStr.substring(1, paramStr.length()-1).split(";");
         if(params.length == 3) {

            if() {
               result = BigDecimal.valueOf(Double.valueOf(params[1]));
            } else {
               result = BigDecimal.valueOf(Double.valueOf(params[2]));
            }
         }
      }*/

      return result.setScale(10, BigDecimal.ROUND_HALF_DOWN);
   }

   private static BigDecimal getVal(String param) {
      param = MathFunction(param.trim());
      param = Brackets(param.trim());
      return EasyStr(param.trim());
   }

	protected static String Pi(String str){
		while(str.contains(V.VAR_PI))
			str = str.replace(V.VAR_PI, Data.add(BigDecimal.valueOf(Math.PI)));
	   return str;
	}

	protected static String E(String str){
		while(str.contains(V.VAR_E))
         str = str.replace(V.VAR_E, Data.add(BigDecimal.valueOf(Math.E)));
	   return str;
	}
	
	protected static String Factorial(String str) {
		int i, key = 0, t = 0;
      BigDecimal valD = BigDecimal.valueOf(0), resultDouble = BigDecimal.valueOf(0);
		String val = "",valSTR = "";
		char[] strChar =  str.trim().toCharArray();
		char[] strCharP = new char[] {'+', '-', '*', '/', '^', '%', '(', ')'};

		while((t = str.indexOf(V.CHAR_STR_FACTORIAL)) != -1) {
         t -= 1;
			if((int)strChar[t] != V.CHAR_INT_RIGHT_BRACKET){
				for(i = t; i >= 0; --i){
					if(!Search.InArray(strChar[i], strCharP)) {
                  key = i;
               } else
                  break;
				}
				val = str.substring(key, str.indexOf(V.CHAR_STR_FACTORIAL));
				valSTR = str.substring(key, str.indexOf(V.CHAR_STR_FACTORIAL));
			} else {
				int count = 1;
				for(i = t - 1; count > 0 && i >= 0; --i){
					if((int)strChar[i] == V.CHAR_INT_RIGHT_BRACKET) {
                  --count;
               } else if((int)strChar[i] == V.CHAR_INT_LEFT_BRACKET) {
                  ++count;
               }
				}
				val = Brackets(str.substring(i + 1, str.indexOf(V.CHAR_STR_FACTORIAL)));
				valSTR = str.substring(i + 1, str.indexOf(V.CHAR_STR_FACTORIAL));
			}
			valD = EasyStr(val);
			resultDouble = Factorial(valD.intValue());
			str = str.replace(valSTR + V.CHAR_STR_FACTORIAL, Data.add(resultDouble));
			str = ReOperand(str);
			strChar =  str.trim().toCharArray();
		}
	   return str;
	}
	
	protected static String Modulo(String str) {
		int i, keyL = 0,keyR = 0, t = 0;
      BigDecimal val1D = BigDecimal.valueOf(0),val2D = BigDecimal.valueOf(0), resultDouble = BigDecimal.valueOf(0);
		String val1 = "", val2 = "",valSTR1 = "",valSTR2 = "";
		char[] strChar =  str.trim().toCharArray();
		char[] strCharP = new char[] {'+', '-', '*', '/', '^', '%', '(', ')'};
		
		while(str.contains(V.CHAR_STR_MODULO)) {
			t = str.indexOf(V.CHAR_STR_MODULO)-1;
			if((int) strChar[t] != V.CHAR_INT_RIGHT_BRACKET) {
				for(i = t; i >= 0; --i) {
					if(!Search.InArray(strChar[i], strCharP))
						keyL = i;
					else
						break;
				}
				val1 = str.substring(keyL, str.indexOf(V.CHAR_STR_MODULO));
				valSTR1 = str.substring(keyL, str.indexOf(V.CHAR_STR_MODULO));
			} else {
				int count = 1;
				for(i = t - 1; count > 0 && i >= 0; --i) {
					if((int)strChar[i] == V.CHAR_INT_LEFT_BRACKET) {
						--count;
					} else if((int)strChar[i] == V.CHAR_INT_RIGHT_BRACKET) {
                  ++count;
               }
            }
				val1 = Brackets( str.substring(i + 1,str.indexOf(V.CHAR_STR_MODULO)) );
				valSTR1 = str.substring(i + 1, str.indexOf(V.CHAR_STR_MODULO));
			}
			
			if((int)strChar[t + 2] != V.CHAR_INT_LEFT_BRACKET) {
				for(i = t + 2; i < str.length(); ++i){
					if(!Search.InArray(strChar[i], strCharP)) {
                  keyR = i;
               } else
						break;
				}
				val2 = str.substring(t + 2, keyR + 1);
				valSTR2 = str.substring(t + 2, keyR + 1);
			} else {
				int count = 1;
				for(i = t + 3; count > 0 && i < str.length(); ++i) {
					if((int)strChar[i] == V.CHAR_INT_LEFT_BRACKET) {
                  ++count;
               } else if((int)strChar[i] == V.CHAR_INT_RIGHT_BRACKET) {
                  --count;
               }
				}
				val2 = Brackets(str.substring(t + 2, i));
				valSTR2 = str.substring(t + 2, i);
			}
			val1D = EasyStr(val1);
			val2D = EasyStr(val2);
			resultDouble = BigDecimal.valueOf(val1D.doubleValue() % val2D.doubleValue());
			str = str.replace(valSTR1 + V.CHAR_STR_MODULO + valSTR2, Data.add(resultDouble));
			str = ReOperand(str);
			strChar =  str.trim().toCharArray();
		} 
	  return str;
	}
	
	protected static String Brackets(String str){
		String strRep = "";
		char[] strChar =  str.trim().toCharArray();
		int i, t;
		int count = 0;

		while((t = str.indexOf(V.CHAR_STR_LEFT_BRACKET)) != -1) {
			count = 1;
			for(i = t + 1; count > 0 && i >= 0; ++i){
				if((int)strChar[i] == V.CHAR_INT_LEFT_BRACKET) {
               t = i;
            } else if((int)strChar[i] == V.CHAR_INT_RIGHT_BRACKET) {
					--count;
            }
			}
			strRep = str.substring(t, i);
			str = str.replace(strRep, Data.add(SubBrackets(strRep)));
			str = ReOperand(str);
			strChar =  str.trim().toCharArray();
		}
	  return str;
	}

	protected static BigDecimal SubBrackets(String str){
      BigDecimal result = BigDecimal.valueOf(0);
		str = str.substring(1, str.length() - 1);
		str = Factorial(str);         
		str = Modulo(str);            
		str = Degree(str);  
		str = Multiplication(str);
		str = Division(str);
		result = EasyStr(str);
	   return result;
	}

	protected static String Multiplication(String str){
		int i, key = 0, t = 0;
      BigDecimal val1D = BigDecimal.valueOf(0), val2D = BigDecimal.valueOf(0), resultDouble = BigDecimal.valueOf(0);
		String val1 = "", val2 = "";
		char[] strChar =  str.trim().toCharArray();
		char[] strCharP = new char[] {'+', '-', '*', '/', '^'};
		
		while(str.contains(V.CHAR_STR_MULTIPLICATION)) {
			t = str.indexOf(V.CHAR_STR_MULTIPLICATION) - 1;
			for(i = t; i >= 0; --i) {
				if(!Search.InArray(strChar[i],strCharP))
					key = i;
                else
					break;
			}
			val1 = str.substring(key,str.indexOf(V.CHAR_STR_MULTIPLICATION));
			val1D = EasyStr(val1);
			
			t = str.indexOf(V.CHAR_STR_MULTIPLICATION) + 1;
			key = t;
			for(int j = t + 1; j < str.length(); ++j){
				if(!Search.InArray(strChar[j], strCharP)) {
					key = j;
            } else {
					break;
            }
			}
			val2 = str.substring(str.indexOf(V.CHAR_STR_MULTIPLICATION) + 1, key + 1);
			val2D = EasyStr(val2);
			
			resultDouble = val1D.multiply(val2D);
			str = str.replace(val1 + V.CHAR_STR_MULTIPLICATION + val2, Data.add(resultDouble));
			str = ReOperand(str);
			strChar =  str.trim().toCharArray();
		} 
		
		return str;
	}
	
	protected static String Division(String str){
		int i, key = 0, t = 0;
      BigDecimal val1D = BigDecimal.valueOf(0), val2D = BigDecimal.valueOf(0), resultDouble = BigDecimal.valueOf(0);
		String val1 = "", val2 = "";
		char[] strChar =  str.trim().toCharArray();
		char[] strCharP = new char[] {'+', '-', '*' ,'/', '^'};

		while(str.contains(V.CHAR_STR_DIVISION)) {
			t = str.indexOf(V.CHAR_STR_DIVISION) - 1;
			for(i = t; i >= 0; --i) {
				if(!Search.InArray(strChar[i], strCharP)) {
					key = i;
            } else {
					break;
            }
			}
			val1 = str.substring(key,str.indexOf(V.CHAR_STR_DIVISION));
			val1D = EasyStr(val1);
			t = str.indexOf(V.CHAR_STR_DIVISION) + 1;
			key = t;
			for(int j = t + 1; j < str.length(); ++j) {
				if(!Search.InArray(strChar[j], strCharP))  {
					key = j;
            } else {
					break;
            }
			}
			val2 = str.substring(str.indexOf(V.CHAR_STR_DIVISION) + 1, key + 1);
			val2D = EasyStr(val2);
         resultDouble = BigDecimal.valueOf(val1D.doubleValue() / val2D.doubleValue());
			str = str.replace(val1 + V.CHAR_STR_DIVISION + val2,  Data.add(resultDouble));
			str = ReOperand(str);
			strChar =  str.trim().toCharArray();
		} 
		
		return str;
	}
	
	protected static String Degree(String str){
		int i, key = 0, korId = 0;
		char[] strChar =  str.trim().toCharArray();
		char[] strCharP = new char[]{'+', '-', '*', '/', '^'};
		while(str.contains(V.CHAR_STR_DEGREE)) {
         BigDecimal resQuad = BigDecimal.valueOf(1);
			String val = "",valAll = "", step = "",stepAll = "";
			BigDecimal valD = BigDecimal.valueOf(0), stepD = BigDecimal.valueOf(0);
			int t;
			t = str.indexOf(V.CHAR_STR_DEGREE) - 1;
			for(i = t; i >= 0; --i) {
				if(!Search.InArray(strChar[i],strCharP)) {
					key = i;
            } else {
					break;
            }
			}
			val = str.substring(key,str.indexOf(V.CHAR_STR_DEGREE) + korId);
			valAll = korId == -1 ? V.CHAR_STR_LEFT_BRACKET + val + V.CHAR_STR_RIGHT_BRACKET : val ;
			valD = EasyStr(val);
			
			t = str.indexOf(V.CHAR_STR_DEGREE) + 1;
			key = t;
            korId = 0;
			for(int j = t+1; j<str.length(); ++j) {
				if(!Search.InArray(strChar[j],strCharP)) {
					key = j;
            } else {
					break;
            }
			}
			step = str.substring(str.indexOf(V.CHAR_STR_DEGREE) + 1, key + 1);
			stepAll = korId == 1 ? V.CHAR_STR_LEFT_BRACKET + step + V.CHAR_STR_RIGHT_BRACKET : step ;
			stepD = EasyStr(step);
			
			resQuad = BigDecimal.valueOf(Math.pow(valD.doubleValue(), stepD.doubleValue()));
			str = str.replace(valAll + V.CHAR_STR_DEGREE + stepAll, Data.add(resQuad));
			str = ReOperand(str);
			strChar =  str.trim().toCharArray();
		}
		return str;
	}
	
	protected static BigDecimal EasyStr(String str) {
		int i;
      BigDecimal result = BigDecimal.valueOf(0);
		String tmp = "";
		char[] strChar =  str.trim().toCharArray();
		char[] CharArray = new char[]{'+', '-'};
		List<BigDecimal> listVal = new ArrayList<BigDecimal>();
		List<Integer> listOperandInt = new ArrayList<Integer>();
		List<String> listOperand = new ArrayList<String>();
		
		tmp += strChar[0];
		for(i = 1; i < strChar.length; ++i) {
			if(Search.InArray(strChar[i], CharArray)){
				if(tmp.contains(V.STACK_TEMPLATE)) {
					listVal.add(Data.get(tmp));
            } else {
					listVal.add(BigDecimal.valueOf(Double.parseDouble(tmp)));
            }

				listOperand.add(Character.toString(strChar[i]));
				listOperandInt.add((int)strChar[i]);
				tmp = "";
			} else {
				tmp += strChar[i];
         }
		}    
		if(tmp.contains(V.STACK_TEMPLATE)) {
			listVal.add(Data.get(tmp));
      } else {
			listVal.add(BigDecimal.valueOf(Double.parseDouble(tmp)));
      }
		
		result = result.add(listVal.get(0));
      for(i = 0; i < listOperand.size(); ++i) {
         if(listOperandInt.get(i) == V.CHAR_INT_PLUS) {
            result = result.add(listVal.get(i + 1));
         } else if(listOperandInt.get(i) == V.CHAR_INT_MINUS) {
            result = result.subtract(listVal.get(i + 1));
         }
      }
		return result;
	}

   public static BigDecimal Factorial(int val) {
      BigDecimal result = BigDecimal.valueOf(1);
      if(val > 1) {
         for(int i = 1; i <= val; ++i) {
            result = result.multiply(BigDecimal.valueOf(i));
         }
      } else if(val == 0) {
         result = BigDecimal.valueOf(0);
      }
      return result;
   }

   public static BigDecimal NWD(BigDecimal a, BigDecimal b) {
      if(b.doubleValue() == 0) {
         return a;
      } else {
         return NWD(b, BigDecimal.valueOf(a.doubleValue() % b.doubleValue()));
      }
   }
}