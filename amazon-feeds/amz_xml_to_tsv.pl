#!/usr/bin/perl
#
# amz_xml_to_tsv.pl
# Creates three table separated values files (products, product_category and categories) from Amazon xml feeds
# Preconditions: xgawk and perl installed, folders "output" and "xml-sources"
#
# author: Nestor Urquiza
# date: 11/15/2009

use File::Find;

#my $GZIP_SOURCES_FOLDER				= "/Users/nurquiza/kk/amazon/amazon/feeds_symlinks";
my $GZIP_SOURCES_FOLDER				= "/Users/nurquiza/kk/amazon/amazon/downloaded_feeds";
my $PROJECT_HOME 					= "/Users/nurquiza/kk/amazon/amazon-feeds-parser";
my $XML_SOURCES_FOLDER				= $PROJECT_HOME . "/xml-sources";
my $TMP_FOLDER						= $PROJECT_HOME . "/tmp"; 
my $OUTPUT_FOLDER					= $PROJECT_HOME . "/output";
my $OUT_CATEGORIES					= "categories";
my $OUT_PRODUCTS					= "products";
my $OUT_PRODUCT_CATEGORY 			= "product_category";
my $XGAWK_CATEGORIES_SCRIPT			= $PROJECT_HOME . "/amz_categories.xgawk";
my $XGAWK_PRODUCTS_SCRIPT			= $PROJECT_HOME . "/amz_products.xgawk";
my $XGAWK_PRODUCT_CATEGORY_SCRIPT	= $PROJECT_HOME . "/amz_product_category.xgawk";

print "Removing files from XML source and output folders...\n";
my $cmd = "rm $XML_SOURCES_FOLDER/*";
runCommand($cmd);

#Process all files inside GZIP folder.
#TODO: Apply same logic checking for MD5 to just process brand new files
finddepth (\&wanted, $GZIP_SOURCES_FOLDER);

print "Waiting for all processes to finish ...\n";
foreach (@children) {
  waitpid($_, 0);
}

print "Took me " . (time - $^T) . " seconds \n";

sub wanted {
  if($_ =~ m/.*\.gz$/){
	$gzFilePath = $File::Find::name;
    my $fileName = "";
	if ($gzFilePath  =~ m/([^\/]*)\.xml\.gz/i){
	  $fileName  = $1;
	}
	$xmlFilePath = $XML_SOURCES_FOLDER . "/" . $fileName . ".xml";
    
    print "Found " . $xmlFilePath  . "\n";
	 
    my $pid = fork();
    if ($pid) {
	   # parent
	   print "I'm the parent of $pid\n";
	   push(@children, $pid);
	 } elsif ($pid == 0) {
	   # child
	   print "I'm a child processing $fileName\n\n";
	   print "Uncompressing [$gzFilePath] to [$xmlFilePath ] ...\n";
       my $cmd = "gzip -cd $gzFilePath > $xmlFilePath";
       runCommandAndFailOnError($cmd);
		
	   print "Creating TSV files. This will take a while ...\n";
	   #Create the three TSV files in tmp directory, then move them to the output directory
	   my $tmpCategoriesTsvPath = $TMP_FOLDER . "/" . $fileName . "_" . $OUT_CATEGORIES . ".tsv";
	   my $tmpProductsTsvPath = $TMP_FOLDER . "/" . $fileName . "_" . $OUT_PRODUCTS . ".tsv";
	   my $tmpProductCategoryTsvPath = $TMP_FOLDER . "/" . $fileName . "_" . $OUT_PRODUCT_CATEGORY . ".tsv";
	   my $outputCategoriesTsvPath = $OUTPUT_FOLDER . "/" . $fileName . "_" . $OUT_CATEGORIES . ".tsv";
	   my $outProductsTsvPath = $OUTPUT_FOLDER . "/" . $fileName . "_" . $OUT_PRODUCTS . ".tsv";
	   my $outProductCategoryTsvPath = $OUTPUT_FOLDER . "/" . $fileName . "_" . $OUT_PRODUCT_CATEGORY . ".tsv";
	
       $categoriesCommand = "xgawk -f $XGAWK_CATEGORIES_SCRIPT $xmlFilePath > $tmpCategoriesTsvPath";
       $productsCommand = "xgawk -f $XGAWK_PRODUCTS_SCRIPT $xmlFilePath > $tmpProductsTsvPath";
       $productCategoryCommand = "xgawk -f $XGAWK_PRODUCT_CATEGORY_SCRIPT $xmlFilePath > $tmpProductCategoryTsvPath";
	   runCommandAndFailOnError($categoriesCommand);
	   $cmd = "mv $tmpCategoriesTsvPath $outputCategoriesTsvPath";
       runCommandAndFailOnError($cmd);
       runCommandAndFailOnError($productsCommand);
       $cmd = "mv $tmpProductsTsvPath $outProductsTsvPath";
       runCommandAndFailOnError($cmd);
       runCommandAndFailOnError($productCategoryCommand);
       $cmd = "mv $tmpProductCategoryTsvPath $outProductCategoryTsvPath";
       runCommandAndFailOnError($cmd);     
	    
       #$catCategoriesCommand = "find $OUTPUT_FOLDER -name \"*" . $OUT_CATEGORIES . ".tsv\"|xargs cat > $OUTPUT_FOLDER" . "/" . $OUT_CATEGORIES;
       #$catProductsCommand = "find $OUTPUT_FOLDER -name \"*" . $OUT_PRODUCTS . ".tsv\"|xargs cat > $OUTPUT_FOLDER" . "/" . $OUT_PRODUCTS;
       #$catProductCategoryCommand = "find $OUTPUT_FOLDER -name \"*" . $OUT_PRODUCT_CATEGORY . ".tsv\"|xargs cat > $OUTPUT_FOLDER" . "/" . $OUT_PRODUCT_CATEGORY;
       #runCommand($catCategoriesCommand);
       #runCommand($catProductsCommand);
       #runCommand($catProductCategoryCommand);
	
	   exit(0);
	 } else {
	   die "Probably no more resources and I couldn’t fork: $!\n";
	 }
  }
}

sub runCommand {
    $command = $_[0];
    print "Running $command\n";
    system($command);
    if($? != 0){
       my $message = "Failed processing command [ $command ]. Signal:" . $?;
       print "$message\n";
       #continue processing and just inform about the error
    }
}

sub runCommandAndFailOnError {
    $command = $_[0];
    print "Running: $command\n";
    system($command);
    if($? != 0){
       my $message = "Failed processing command [ $command ]. Signal:" . $?;
       die("$message\n");
    }
}

