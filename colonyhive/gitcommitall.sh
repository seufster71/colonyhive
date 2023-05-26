
checkAndCommit() {
	OUTPUT=$(git status --porcelain)
	if [ -z "$OUTPUT" ]; then
		echo "#### No changes found"
	else
		echo "#### Changes found"
		echo $OUTPUT
		echo "#### Add any new files"
		git add .
		echo "#### Committing files"
		git commit -m "${1}"
		echo "#### Pushing to repo"
		git push
		git log -1
		echo "#### Complete"
	fi
}

commitAll() {
	
	ECHO "#### core"
	cd ~/gitcolony/colonyhive/colonyhive/src/main/js/core
	checkAndCommit "${1}"
	
	ECHO "#### coreView"
	cd ~/gitcolony/colonyhive/colonyhive/src/main/js/coreView
	checkAndCommit "${1}"
	
	ECHO "#### main"
	cd ~/gitcolony/colonyhive/colonyhive
	checkAndCommit "${1}"
	
	ECHO "#### Done committing"
}

all() {
	commitAll "$*"
}


	
if [ $# -eq 0 ]; then
	echo "You must supply a comment"
else 
	str="$*"
	echo Your comment is $str
	all $str
fi	
