#!/usr/bin/perl
#
# amz_xml_to_tsv.pl
# Creates three table separated values files (products, product_category and categories) from Amazon xml feeds
# Preconditions: xgawk and perl installed, folders "output" and "xml-sources"
#
# author: Nestor Urquiza
# date: 11/15/2009

use File::Find;

my $PROJECT_HOME 					= "/Users/nurquiza/kk/amazon/amazon-feeds";
my $XML_SOURCES_FOLDER				= $PROJECT_HOME . "/xml-sources";
my $OUTPUT_FOLDER					= $PROJECT_HOME . "/output";
my $OUT_CATEGORIES					= "categories";
my $OUT_PRODUCTS					= "products";
my $OUT_PRODUCT_CATEGORY 			= "product_category";
my $XGAWK_CATEGORIES_SCRIPT			= $PROJECT_HOME . "/amz_categories.xgawk";
my $XGAWK_PRODUCTS_SCRIPT			= $PROJECT_HOME . "/amz_products.xgawk";
my $XGAWK_PRODUCT_CATEGORY_SCRIPT	= $PROJECT_HOME . "/amz_product_category.xgawk";
 
#Find all files inside current folder/subfolders
finddepth (\&wanted, $XML_SOURCES_FOLDER);

print "Waiting for all processes to finish ...\n";
foreach (@children) {
  waitpid($_, 0);
}

sub wanted {
  if($_ =~ m/.*\.xml/){
     $xmlFilePath = $File::Find::name;
     print "Found " . $xmlFilePath  . "\n";
	 my $fileName = "";
	 if ($xmlFilePath  =~ m/([^\/]*)\.xml/i){
	    $fileName  = $1;
	 }
     my $pid = fork();
     if ($pid) {
	    # parent
	    print "I'm the parent of $pid\n";
	    push(@children, $pid);
	  } elsif ($pid == 0) {
	    # child
	    print "I'm a child processing $fileName\n\n";
	
		$categoriesCommand = "xgawk -f $XGAWK_CATEGORIES_SCRIPT $xmlFilePath > $OUTPUT_FOLDER" . "/" . $fileName . "_" . $OUT_CATEGORIES . ".tsv";
		$productsCommand = "xgawk -f $XGAWK_PRODUCTS_SCRIPT $xmlFilePath > $OUTPUT_FOLDER" . "/" . $fileName . "_" . $OUT_PRODUCTS . ".tsv";
		$productCategoryCommand = "xgawk -f $XGAWK_PRODUCT_CATEGORY_SCRIPT $xmlFilePath > $OUTPUT_FOLDER" . "/" . $fileName . "_" . $OUT_PRODUCT_CATEGORY . ".tsv";
	    runCommand($categoriesCommand);
		runCommand($productsCommand);
		runCommand($productCategoryCommand);
	    
	    $catCategoriesCommand = "find $OUTPUT_FOLDER -name \"*" . $OUT_CATEGORIES . ".tsv\"|xargs cat > $OUTPUT_FOLDER" . "/" . $OUT_CATEGORIES;
		$catProductsCommand = "find $OUTPUT_FOLDER -name \"*" . $OUT_PRODUCTS . ".tsv\"|xargs cat > $OUTPUT_FOLDER" . "/" . $OUT_PRODUCTS;
		$catProductCategoryCommand = "find $OUTPUT_FOLDER -name \"*" . $OUT_PRODUCT_CATEGORY . ".tsv\"|xargs cat > $OUTPUT_FOLDER" . "/" . $OUT_PRODUCT_CATEGORY;
		runCommand($catCategoriesCommand);
		runCommand($catProductsCommand);
		runCommand($catProductCategoryCommand);
	
	    exit(0);
	  } else {
	    die "Probably no more resources and I couldn’t fork: $!\n";
	  }
  }
}

sub runCommand {
	$command = $_[0];
	#print "$command\n";
    system($command);
    my $message = "Failed processing command [ $command ]. Signal:" . $?;
    if($? != 0){
	   #continue processing and just inform about the error
       print "$message\n";
    }
}
