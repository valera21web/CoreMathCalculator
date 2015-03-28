package varel.core.calculator;

public class Validation {

   private static char[] strChar = new char[]{'+','-','*','/','^','%',';'};

	public static int Valid(String str) {
      char[] char_str = str.toCharArray();
      if(Search.InArray(char_str[char_str.length - 1], new char[] {'+','-','*','/','^','%'})) {
         return 0;
      }
      int brackets = 0;

      for(char _char: char_str){
         if((int) _char == V.CHAR_INT_LEFT_BRACKET) {
            ++brackets;
         } else if((int) _char == V.CHAR_INT_RIGHT_BRACKET) {
            --brackets;
         }
      }

      if(brackets != 0)
         return 3; // error with brackets
      return 1;
   }

   /**
    *
    * @param str @String - string for optimization
    * @param Select @int- pole.getActiveSelect
    * @return @String - [0] - optimization string; [1] - new selection for text
    */
	public static String[] Optimization(String str, int Select) {
		char[] char_str = ReOperand(str).toCharArray();
		
		String left_str = "";
		String right_str = "";
		
		int count_str = char_str.length;
		
		for(int i = 1; i < count_str - 1; ++i) {
			if((int)char_str[i] >= 0x41 && (int)char_str[i] <= 0x7A && (int)char_str[i] != V.CHAR_INT_DEGREE) {
				if(!Search.InArray(char_str[i - 1], strChar)) {
					if((int)char_str[i - 1] < 0x41 && (int)char_str[i - 1] != V.CHAR_INT_LEFT_BRACKET) {
						if(i < Select) {
                     ++Select;
                  }
						left_str = str.substring(0, i) + V.CHAR_STR_MULTIPLICATION;
						right_str = str.substring(i, count_str);
						str = left_str + right_str;
						char_str = str.toCharArray();
						count_str = char_str.length;
					}
				}
			} else if((int)char_str[i]  == V.CHAR_INT_LEFT_BRACKET) {
				if(!Search.InArray(char_str[i - 1], strChar)) {
					if((int)char_str[i - 1] < 0x41 && char_str[i - 1] != V.CHAR_INT_LEFT_BRACKET) {
                  if(i < Select) {
                     ++Select;
                  }
						left_str = str.substring(0, i) + V.CHAR_STR_MULTIPLICATION;
						right_str = str.substring(i, count_str);
						str = left_str + right_str;
						char_str = str.toCharArray();
						count_str = char_str.length;
					}
				}
			} else if((int)char_str[i]  == V.CHAR_INT_LEFT_BRACKET){
				if(!Search.InArray(char_str[i + 1], strChar)){
					if((int)char_str[i + 1]  != V.CHAR_INT_RIGHT_BRACKET){
                  if(i < Select) {
                     ++Select;
                  }
						left_str = str.substring(0, i + 1) + V.CHAR_STR_MULTIPLICATION;
						right_str = str.substring(i + 1, count_str);
						str = left_str + right_str;
						char_str = str.toCharArray();
						count_str = char_str.length;
					}
				}
			}
		}
		return (new String[] {str, String.valueOf(Select)});
	}

   public static String[] Optimization(String str) {
      char[] char_str = ReOperand(str).toCharArray();

      String left_str = "";
      String right_str = "";

      int count_str = char_str.length;

      for(int i = 1; i < count_str - 1; ++i) {
         if((int)char_str[i] >= 0x41 && (int)char_str[i] <= 0x7A && (int)char_str[i] != V.CHAR_INT_DEGREE) {
            if(!Search.InArray(char_str[i - 1], strChar)) {
               if((int)char_str[i - 1] < 0x41 && (int)char_str[i - 1] != V.CHAR_INT_LEFT_BRACKET) {
                  left_str = str.substring(0, i) + V.CHAR_STR_MULTIPLICATION;
                  right_str = str.substring(i, count_str);
                  str = left_str + right_str;
                  char_str = str.toCharArray();
                  count_str = char_str.length;
               }
            }
         } else if((int)char_str[i]  == V.CHAR_INT_LEFT_BRACKET) {
            if(!Search.InArray(char_str[i - 1], strChar)) {
               if((int)char_str[i - 1] < 0x41 && char_str[i - 1] != V.CHAR_INT_LEFT_BRACKET) {
                  left_str = str.substring(0, i) + V.CHAR_STR_MULTIPLICATION;
                  right_str = str.substring(i, count_str);
                  str = left_str + right_str;
                  char_str = str.toCharArray();
                  count_str = char_str.length;
               }
            }
            if(!Search.InArray(char_str[i + 1], strChar)){
               if((int)char_str[i + 1]  != V.CHAR_INT_RIGHT_BRACKET){
                  left_str = str.substring(0, i + 1) + V.CHAR_STR_MULTIPLICATION;
                  right_str = str.substring(i + 1, count_str);
                  str = left_str + right_str;
                  char_str = str.toCharArray();
                  count_str = char_str.length;
               }
            }
         }
      }
      return (new String[] {str});
   }

	protected static String ReOperand(String str) {
      if(str == null || str.length() == 0)
         return "";

      boolean fl = true;
      while(fl) {
         fl= false;
         if(str.contains("--")) {
            str = str.replace("--", "+");
            fl = true;
         }

         if(str.contains("++")) {
            str = str.replace("++", "+");
            fl = true;
         }

         if(str.contains("+-")) {
            str = str.replace("+-", "-");
            fl = true;
         }

         if(str.contains("-+")) {
            str = str.replace("-+", "-");
            fl = true;
         }
      }

	   return str;
	}
}
