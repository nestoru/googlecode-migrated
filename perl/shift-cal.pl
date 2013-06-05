#!/usr/bin/perl -w
# shift-cal.pl
#
# @usage: ./shift-cal.pl <MM> <yyyy> <initialsFile>
# @example: ./shift-cal.pl 12 2013 initials.txt
# @author: Nestor Urquiza
# @credits: Adaptation of the original example of the linux cal command written by the creator of the Calendar:Simple perl module.
#
#################################################################################################################################
  use strict;
  use Calendar::Simple;

  my @months = qw(January February March April May June July August
                  September October November December);

  my $mon = shift;
  my $yr  = shift;
  my $initialsFileName = shift;
  open( INITIALS_FILE, $initialsFileName );
  chomp( my @allInitials = <INITIALS_FILE> );
  my $totalInitials = scalar @allInitials;
  my $initialsCounter = 0;
  my $initials = "   ";
  my @month = calendar($mon, $yr);

  print "\n$months[$mon - 1] $yr\n\n";
  print "Su       Mo       Tu       We       Th       Fr       Sa\n";
  foreach (@month) {
    my $weekday = 0;
    foreach (@$_) {
      #print $_;
      if( $totalInitials != 0 ) {
        if( $initialsCounter == $totalInitials ) {
          $initialsCounter = 0;
        }
        if($_ && 0 < $weekday && $weekday < 6) {
          $initials = $allInitials[$initialsCounter++];
          printf("%-9s", "$_-$initials");
        } else {
          print '         ';
        }
      }
      #$_ && 0 < $weekday && $weekday < 6 ? printf("%-9s", "$_-$initials") : print '         '; 
      $weekday++;
    }
    #print map { $_ ? sprintf "%2d%s", $_, "-".$initials."   " : '         ' } @$_;
    print "\n";
  }
