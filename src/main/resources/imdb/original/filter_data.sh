#!/bin/bash


# This script filters all records based on the year 2018. It first processes the file with all the titles
# and prints only the rows where the column storing the year is 2018. It prints those rows in a new file called
# 2018titles.tsv. It then uses the titleIds from the 2018titles.tsv to print out only the corresponding rows with 
# the matching titleId for each of the other files. The filtering of the name.basics.tsv (Actors) file can only be done
# after the filtering of the title.principals.tsv (Cast) file given that the titleId does not exist on the Actors file
# and the Cast file is the only mapping for Actors to Movies. This takes approx 2.5 mins to execute

# Filter Titles
awk -F"\t" '$6 == "2018" { print $0 }' title.basics.tsv > 2018titles.tsv

# Filter Episodes
awk -F'\t' 'NR==FNR{c[$1]++;next};c[$2] > 0' 2018titles.tsv title.episode.tsv > 2018episodes.tsv

# Filter Cast
awk -F'\t' 'NR==FNR{c[$1]++;next};c[$1] > 0' 2018titles.tsv title.principals.tsv > 2018cast.tsv

# Filter Actor
awk -F'\t' 'NR==FNR{c[$3]++;next};c[$1] > 0' 2018cast.tsv name.basics.tsv > 2018actors.tsv

#Filter Ratings
awk -F'\t' 'NR==FNR{c[$1]++;next};c[$1] > 0' 2018titles.tsv title.ratings.tsv > 2018ratings.tsv

# Move files to different directory
mv 2018titles.tsv 2018episodes.tsv 2018cast.tsv 2018actors.tsv 2018ratings.tsv ../filtered