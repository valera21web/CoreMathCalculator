package varel.core.calculator;

import java.util.List;

public class Search {

	public static boolean InList(int value, List<Integer> list) {
		if(!list.isEmpty()) {
			try {
				for(int i: list) {
               if(value == i)
                  return true;
            }
			} catch (Exception ex) {
				return false;
			}
		}
	   return false;
	}

	public static boolean InList(String value, List<String> list) {
		if(!list.isEmpty()) {
            try {
                for(String l: list) {
                    if(value.equals(l))
                       return true;
                }
            } catch (Exception ex) {
                return false;
            }
        }
	    return false;
	}

	public static boolean InList(double value, List<Double> list) {
		if(!list.isEmpty()) {
            try{
                for(double d: list) {
                    if(value == d)
                       return true;
                }
            } catch (Exception ex){
                return false;
            }
        }
	    return false;
	}
	
	public static boolean InArray(int value, int[] arrayInt) {
		try {
			for(int i: arrayInt) {
                if(value == i)
                   return true;
            }
		} catch (Exception ex){
			return false;
		}
	    return false;
	}

	public static boolean InArray(String value, String[] arrayStr) {
		try {
			for(String str: arrayStr) {
            if(value.equals(str))
               return true;
         }
		} catch (Exception ex){
			return false;
		}
	    return false;
	}

	public static boolean InArray(double value, double[] arrayDouble) {
		try {
			for(double d: arrayDouble) {
            if(value == d)
               return true;
         }
		} catch (Exception ex){
			return false;
		}
	    return false;
	}

	public static boolean InArray(char value, char[] arrayChar){
		try {
			for(char c: arrayChar) {
            if(value == c)
               return true;
         }
		} catch (Exception ex){
			return false;
		}
	   return false;
	}

   public static List<Integer> aSort(List<Integer> list) {
      int t, j;
      for (int i = 0; i < list.size(); i++) {
         t = Integer.parseInt(list.get(i).toString());
         for (j = i - 1; j >= 0 && Integer.parseInt(list.get(j).toString()) < t; j--) {
            list.set(j + 1, list.get(j));
         }
         list.set(j + 1, t);
      }
      return list;
   }

	public static boolean Are(String refun, String fun) {
		boolean result = false;
		if(refun.length() == fun.length()) {
			char[] val1 = refun.toCharArray();
			char[] val2 = fun.toCharArray();
			int count = refun.length();
			int k = 0;
			for(int i = 0; i < count; ++i){
				if((int) val1[i] == (int) val2[i]) {
					++k;
				}
			}
			if(k == count) {
				result = true;
			}
		}
	   return result;
	}
	
	public static boolean is_integer(String val) {
		char[] strChar =  val.trim().toCharArray();
		for(int i: strChar) {
			if((i < 48 || i > 57) && i != 46) {
				return false;
			}
		}		
		return true;
	}
}
