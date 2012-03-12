#!/usr/local/bin/gawk -f
#FUNCTIONS
function printRecords(array) {
  	if( array[0] != "" ) {
    	asort(array);
	    #after ordering array resets to first key = 0
	    for( j = 1; j < length(array) + 1; j++ ) {
	      print array[j];
	    }  
	    delete array;
	    #i = 0;
	  }
}

#MAIN
BEGIN {
  keyRegex = "[ ]{2}KEY.*";
  constraintRegex = "[ ]{2}CONSTRAINT.*";
  aKeyIndex = 0;
  aConstraintIndex = 0;
  aKey[0] = "";
  aConstraint[0] = "";
}
{ 
  if( match($0, keyRegex) ) {
    aKey[aKeyIndex++] = $0;
  } else if( match($0, constraintRegex) ) {
    aConstraint[aConstraintIndex++] = $0;
  } else {
    printRecords(aKey);
	printRecords(aConstraint);
	aKeyIndex = 0;
	aConstraintIndex = 0;
    print $0;
  }
}
END { 
} 
